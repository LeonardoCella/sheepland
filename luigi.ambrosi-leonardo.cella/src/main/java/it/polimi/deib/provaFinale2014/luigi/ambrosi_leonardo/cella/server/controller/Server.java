package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPong;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.FlipFlop;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         Singleton in fact we've just one Server. Owner of the map and it has
 *         all the client-channels(+pong) and also the list of the current
 *         matches related with the players.
 */
public class Server {
	private Mappa mappa;

	// ArrayList of active matches,new ping channels,new player channels
	protected List<Partita> partiteAttive;
	private List<IPong> canaliPong;
	private List<ICanaleServer> canaliGiocatori;

	// Cache composed by < player - status - match >
	private List<FlipFlop> registro;

	// Doors for the communication and to scan the connection status
	private static final int PORTASCK = 4000;
	private static final int PORTAPONGSCK = 5000;

	private static Server srv = null;

	// Thread that starts new match
	private Arbitro arbitro;
	// Thread listener client-RMI requests
	private RicevitoreRMI ascRMI;
	// Thread listener client-Sck requests
	private RicevitoreSck ascSck;
	private boolean sincroTimer;

	private Timer timer;
	// Token that synchronized Arbitro and Timer
	private Object token;

	private Server() {

		try {
			this.mappa = Mappa.getMappa();
		} catch (EccClient e) {
			MyLogger.gestisci("EccClient", e);
			mappa = null;
		}
		this.sincroTimer = false;
		this.canaliGiocatori = new ArrayList<ICanaleServer>();
		this.registro = new ArrayList<FlipFlop>();
		this.partiteAttive = new ArrayList<Partita>();
		this.canaliPong = new ArrayList<IPong>();
		this.token = new Object();

	}

	public static Server getServer() {
		// crea l'oggetto solo se non esiste
		if (srv == null) {
			srv = new Server();
		}
		return srv;
	}

	/**
	 * It set the pre-setting and start the listeners.
	 * 
	 * @throws Exception
	 *             when almost one listener doesn't start
	 */
	public void start() throws Exception {
		// Inizializzazione
		this.ascSck = new RicevitoreSck(srv, PORTASCK, PORTAPONGSCK);

		try {
			this.ascRMI = RicevitoreRMI.getRicevitore(srv);
		} catch (RemoteException e) {
			MyLogger.gestisci("RMIErr", e);
			return;
		}

		this.arbitro = new Arbitro(srv);

		try {
			this.ascRMI.start();
			this.ascSck.start();
			this.arbitro.start();
		} catch (Exception e) {
			MyLogger.gestisci("EccAvvio", e);
			throw e;
		}
		return;
	}

	/**
	 * @param canalePong
	 *            :- channel that alert about potential disconnection
	 * @param ICanaleServer
	 *            :- channel of comunication RMI-Socket
	 * @throws Exception
	 */
	public int aggiungiGiocatore(ICanaleServer nuovoGiocatore, IPong canalePong)
			throws Exception {
		int pGioc = 0, codicePartita = 0;
		String user = nuovoGiocatore.getUsername();

		pGioc = inRegistro(user);

		// E' nella coda o in una partita e ha stato attivo
		if (pGioc >= 0) {
			return 1;
		} else {
			// Reconnection
			if (pGioc == -2) {
				for (FlipFlop gioc : this.registro) {
					if (gioc.getUsername().equals(user)) {
						gioc.setStato(true);
						codicePartita = gioc.getPartita();
					}
				}

				for (Partita p : this.partiteAttive) {
					synchronized (p.getToken()) {
						if (codicePartita == p.getCodicePartita()) {
							p.rendiDisp(nuovoGiocatore, canalePong);
						}
					}
				}
				return 2;
			} else {
				// New player
				this.canaliGiocatori.add(nuovoGiocatore);
				this.canaliPong.add(canalePong);
				this.registro.add(new FlipFlop(0, nuovoGiocatore.getUsername(),
						true));
				if (!this.sincroTimer) {
					this.timer = new Timer(this);
					this.timer.start();
				}
				return 0;
			}
		}
	}

	/**
	 * 
	 * @param username
	 *            of the player that we want to know about
	 * @return 0 if is in queue for a new match 2 if it's off (disconnected) -1
	 *         if it's a new player.
	 */
	private int inRegistro(String username) {
		for (FlipFlop gioc : this.registro) {
			if (gioc.getUsername().equals(username)) {
				if (gioc.getStato()) {
					// Match ID, 0 if is in queue
					return gioc.getPartita();
				} else {
					// The related player is off
					return -2;
				}
			}
		}
		// New Player
		return -1;
	}

	/**
	 * @return the token for the listeners of new players to synchronized them
	 *         on the method aggiungiGiocatore()
	 */
	public Object getToken() {
		return this.token;
	}

	/**
	 * @return :- number of players, is called from the referee to know when a
	 *         new match can starts
	 */
	public int numeroGiocatoriCorrenti() {
		return this.canaliGiocatori.size();
	}

	/**
	 * @description :- Called from the referee as soon as there are 4 players!
	 */
	public void iniziaPartita() {
		List<ICanaleServer> tmpCanaliGiocatori = this.canaliGiocatori;
		List<IPong> tmpPong = this.canaliPong;
		Partita p;

		this.canaliGiocatori = new ArrayList<ICanaleServer>();
		this.canaliPong = new ArrayList<IPong>();

		p = new Partita(this.mappa, tmpCanaliGiocatori, tmpPong,
				this.partiteAttive.size() + 1, false);

		this.partiteAttive.add(p);

		this.aggiornaCache(p.getCodicePartita(), tmpCanaliGiocatori);

		p.start();
	}

	/**
	 * @param num
	 *            - match ID
	 * @param giocatoriNuovi
	 *            - new players that passed from the matchID 0 to the real match
	 *            ID that was set up when the match started.
	 */
	private void aggiornaCache(int num, List<ICanaleServer> giocatoriNuovi) {
		for (ICanaleServer giocNuovo : giocatoriNuovi) {
			for (FlipFlop giocPartita : this.registro) {
				if (giocPartita.getUsername().equals(giocNuovo.getUsername())
						&& (giocPartita.getPartita() == 0)) {
					giocPartita.setPartita(num);
				}
			}
		}
		return;
	}

	/**
	 * Method called to turn off the player with the username passed as
	 * parameter. This method is called from the RespondePong when occur a
	 * disconnection.
	 * 
	 * @param user
	 */
	public void sospendi(String user) {
		for (FlipFlop gioc : this.registro) {
			if (gioc.getUsername().equals(user)) {
				gioc.setStato(false);

			}
		}
	}

	/**
	 * 
	 * @return the timer used to start an particular match
	 */
	public Timer getTimer() {
		return this.timer;
	}

	/**
	 * @description :- Used to close all the listeners in one step composed
	 *              almost from two players
	 */
	public void chiudiRicevitori() {
		this.ascSck.stopThread();
		this.ascRMI.stopThread();
		this.arbitro.stopThread();
	}

	/**
	 * 
	 * @return the socket door for the players channel
	 */
	public int getPortaSCK() {
		return PORTASCK;
	}

	/**
	 * Used to start check when I've to start the timer
	 * 
	 * @param b
	 */
	public void setSincroTimer(boolean b) {
		this.sincroTimer = b;
	}

	/**
	 * 
	 * @return object used for synchronized the add method
	 */
	public Object getBlocco() {
		return this.token;
	}

	/**
	 * @param giocatori
	 *            - players of the current match
	 * @param num
	 *            - match identifier, it's used to find the match in the cache
	 */
	public void finePartita(List<ICanaleServer> giocatori, int num) {
		int i = 0;
		for (FlipFlop giocatoriTotali : this.registro) {
			for (ICanaleServer giocatoriParziali : giocatori) {
				if (giocatoriParziali.getUsername() == giocatoriTotali
						.getUsername()) {
					this.registro.remove(i);
				}
			}
			i++;
		}
		return;
	}

	/**
	 * @param user
	 *            is the username of the player that I want to find
	 * @return the player with the username user
	 */
	public Giocatore trovaGiocatore(String user) {
		int numGiocatori;
		List<Giocatore> giocatoriPartita;
		for (int i = 0; i < partiteAttive.size(); i++) {
			giocatoriPartita = partiteAttive.get(i).getStatoCorrente()
					.getGiocatori();
			numGiocatori = giocatoriPartita.size();
			for (int k = 0; k < numGiocatori; k++) {
				if (giocatoriPartita.get(k).getCanale().getUsername()
						.equals(user)) {
					return giocatoriPartita.get(k);
				}
			}
		}
		return null;
	}
}
