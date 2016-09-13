package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPong;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the single thread for the single match.
 * 
 * 
 * 
 */
public class Partita extends Thread {
	// implementa il fatto del numero progressivo, cioè il numero di thread
	private int codicePartita;

	// 1 se è con piu di due giocatori, 2 se con due giocatori
	private int tipoPartita, giocatoreCorr;

	private ResponderPong gestoreDisconnessione;
	private Mappa mappa;
	private Stato statoPartita;

	// è la fotografia di statoPartita, sarà l'oggetto che invieremo al client
	private StatoClient screenshot;
	private Object tokenWait;
	private boolean[] canaleDisponibile;
	private List<ICanaleServer> canali;
	private TraduttoreMossa traduttore;
	private boolean faseFinale, test;
	private GestorePartitaServer gestorePartita;

	// qui ci va anche tutta la parte di connessione:
	// connessione socket: leggo il MESSAGGIO che mi arriva dal client, vedo che
	// tipo di messaggio è,
	// se è un messaggio di mossa creo l'oggetto mossa e lo passo al gestore

	/**
	 * @param m
	 *            it is the map
	 * @param canaliGiocatori
	 *            it is the set of all the comunication channel of the players
	 *            of the match
	 * @param canaliPong
	 *            it is the set of all the comunication pong-channel of the
	 *            players of the match
	 * @param codice
	 *            it is the code of the match
	 * @param pTest
	 *            true if I want to use the class Partita in a test, otherwise
	 *            false
	 */
	public Partita(Mappa m, List<ICanaleServer> canaliGiocatori,
			List<IPong> canaliPong, int codice, boolean pTest) {
		this.test = pTest;
		int indiceCanali = 0;
		this.codicePartita = codice;
		this.mappa = m;
		indiceCanali = canaliGiocatori.size();
		this.canaleDisponibile = new boolean[indiceCanali];

		for (int i = 0; i < indiceCanali; i++) {
			this.canaleDisponibile[i] = true;
		}
		if (!this.test) {
			this.gestoreDisconnessione = new ResponderPong(canaliPong, this);
		}

		// caso partita con soli due giocatori
		if (canaliGiocatori.size() == 2) {
			this.tipoPartita = 2;
			this.statoPartita = new Stato(m, canaliGiocatori.get(0),
					canaliGiocatori.get(1), this);
		} else {
			// caso partita con più di due giocatori
			this.tipoPartita = 1;
			this.statoPartita = new Stato(m, canaliGiocatori, this);
		}

		this.screenshot = new StatoClient();
		this.canali = canaliGiocatori;
		Partita p = this;
		this.traduttore = new TraduttoreMossa(p);
		this.faseFinale = false;
		this.tokenWait = new Object();
		this.gestorePartita = new GestorePartitaServer();
	}

	// calcola il vincitore

	/**
	 * @return true if the match is in the last phase: it there are more than 20
	 *         normale fences. Otherwise false.
	 */
	private boolean controllaFase() {
		if (this.statoPartita.getRecinti().size() == 20) {
			return true;
		} else {
			return false;
		}
	}

	public boolean[] getCanaliDisponibili() {
		return this.canaleDisponibile;
	}

	public List<ICanaleServer> getCanali() {
		return this.canali;
	}

	public int getTipoPartita() {
		return this.tipoPartita;
	}

	public StatoClient getScreenshot() {
		return this.screenshot;
	}

	public TraduttoreMossa getTraduttore() {
		return this.traduttore;
	}

	public int getCodicePartita() {
		return this.codicePartita;
	}

	public Stato getStatoCorrente() {
		return this.statoPartita;
	}

	public Object getToken() {
		return this.tokenWait;
	}

	public int getPartita() {
		return this.tipoPartita;
	}

	public Mappa getMappa() {
		return this.mappa;
	}

	private void chiudiCanali() {
		Server s;
		s = Server.getServer();
		s.finePartita(this.canali, this.codicePartita);

		return;
	}

	/**
	 * @param giocatore
	 *            is the communication channel of the player that is
	 *            reconnecting
	 * @param canalePong
	 *            is the pong-channel of the player that is reconnecting
	 * @return true if the reconnection is ok, otherwise false
	 */
	public boolean rendiDisp(ICanaleServer giocatore, IPong canalePong) {
		int i = 0;

		for (ICanaleServer c : this.canali) {
			if (c.getUsername().equals(giocatore.getUsername())) {
				if (!this.canaleDisponibile[i]) {
					this.canali.set(i, giocatore);
					this.canaleDisponibile[i] = true;
					this.gestoreDisconnessione.riabilita(i, canalePong);
				}
				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * It is the control flow of the match
	 */
	@Override
	public void run() {
		if (!this.test) {
			this.gestoreDisconnessione.start();
		}
		int i = 0;
		int numGiocatori = this.canali.size();
		try {
			gestorePartita.impostazioniPreliminari(this);
		} catch (IOException e) {
			MyLogger.gestisci("Partita 69", e);
		}

		try {
			gestorePartita.posizionaPastori(this);
		} catch (EccCanale e2) {
			MyLogger.gestisci("info", e2);
			this.canaleDisponibile[i] = false;
		} catch (RemoteException e2) {
			MyLogger.gestisci("info", e2);
		}

		try {
			// invio il primo screenshot
			gestorePartita.inviaAggiornamenti(this);
		} catch (EccCanale e1) {
			MyLogger.gestisci("info", e1);
			this.canaleDisponibile[i] = false;
		} catch (IOException e1) {
			MyLogger.gestisci("info", e1);
		}

		while (!faseFinale) {
			i = 0;
			while (i < numGiocatori) {
				while (sommaDisponibili() < 2) {

				}
				if (canaleDisponibile[i]) {
					giocatoreCorr = i;
					gestorePartita.accomodaGiocatore(this, i);

					this.faseFinale = controllaFase();
					gestorePartita.controllaEtaOvini(this);
				}
				i++;
				if (i == numGiocatori) {
					if (statoPartita.getGestoreLupo().muovi()) {
						statoPartita.getGestoreLupo().mangia();
					}
				}
			}
		}

		// ultimo giro
		while (i < numGiocatori) {
			if (canaleDisponibile[i]) {
				gestorePartita.accomodaGiocatore(this, i);
			}
			i++;
		}

		gestorePartita.punteggioFinale(this);

		chiudiCanali();

		if (!this.test) {
			this.gestoreDisconnessione.ferma();
		}

	}

	public GestorePartitaServer getGestore() {
		return this.gestorePartita;
	}

	/**
	 * @param indiceGiocatore
	 *            is the index of the player who is disconnecting.
	 * @return
	 */
	public boolean sospendi(int indiceGiocatore) {
		this.canaleDisponibile[indiceGiocatore] = false;
		return (giocatoreCorr == indiceGiocatore)
				&& (this.canali.get(indiceGiocatore).getClass() == ServerRMI.class);
	}

	/**
	 * @return the number of player connected to the match
	 */
	private int sommaDisponibili() {
		int somma = 0;
		for (int i = 0; i < canaleDisponibile.length; i++) {
			if (canaleDisponibile[i]) {
				somma++;
			}
		}
		return somma;
	}
}
