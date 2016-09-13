package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.InterfacciaIO;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.gioco.Step2;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login.PiePagina;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

public class Buffer implements InterfacciaIO {

	private static Buffer istanza;
	private static Step2 display;
	private static Object tknUser, tknIP, tknMod, tknStrada, tknRegione,
			tknMossa, tknSchermo, tknPedina;
	private static String dispU, dispIP, schermo;
	private static int dispM, strada, regione, mossa, pedina;
	private static PiePagina footer;
	private static Finestra finestra;

	private Buffer() {
		tknUser = new Object();
		tknIP = new Object();
		tknMod = new Object();
		tknStrada = new Object();
		tknRegione = new Object();
		tknMossa = new Object();
		tknSchermo = new Object();
		tknPedina = new Object();
		dispU = null;
		dispIP = null;
		dispM = 0;
		strada = -1;
		regione = -1;
		mossa = -1;
		pedina = -1;
	}

	public static Buffer getBuffer() {
		if (istanza == null) {
			istanza = new Buffer();
		}
		return istanza;
	}

	public void reset() {
		dispU = null;
		dispIP = null;
		dispM = 0;
		return;
	}

	public String leggiIP() {
		synchronized (tknIP) {
			while (dispIP == null) {
				try {
					tknIP.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknIP.notify();
			return dispIP;
		}
	}

	public int leggiSceltaConn() {
		synchronized (tknMod) {
			while (dispM == 0) {
				try {
					tknMod.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknMod.notify();
			return dispM;
		}
	}

	public String leggiUser() {
		synchronized (tknUser) {
			while (dispU == null) {
				try {
					tknUser.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknUser.notify();
			return dispU;
		}
	}

	public Object getTknIP() {
		return tknIP;
	}

	public Object getTknUser() {
		return tknUser;
	}

	public Object getTknDisplay() {
		return tknSchermo;
	}

	public Object getTknMod() {
		return tknMod;
	}

	public Object getTknStrada() {
		return tknStrada;
	}

	public Object getTknRegione() {
		return tknRegione;
	}

	public void avviso() {
		footer.setAvviso("Verifica che il server sia disponibile oppure l'username scelto è già attivo!");
		return;
	}

	public void riscontro() {
		footer.setRiscontro("Username Valido, attendi gli altri giocatori...");
		return;
	}

	public void riconnect() {
		footer.setRiscontro("Bentornato, attendi di essere reindirizzato alla tua partita");
		return;
	}

	public void scrivi(String string) {
		return;
	}

	public void scriviIndice(int indiceNellaPartita) {
		String msg = new String("");
		Step2 secondaSchermata = new Step2(false);
		finestra.getContentPane().removeAll();
		finestra.getContentPane().add(secondaSchermata);
		finestra.abilita();
		display = secondaSchermata;
		msg = "Sei il giocatore n° " + indiceNellaPartita;
		display.scrivi(msg);
		return;
	}

	public void scriviTessIn(String tipoTerreno) {
		display.scriviTessera(tipoTerreno);
		return;
	}

	public void colPast(int colorePastore1, int tipo) {
		display.coloraPastore(colorePastore1, tipo);
		return;
	}

	public int leggiPosizionamento() {
		int tmp;
		synchronized (tknStrada) {
			display.scrivi("posiziona il tuo pastore");
			while (strada == -1) {
				try {
					tknStrada.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknStrada.notify();
			display.posiziona(strada);
			tmp = strada;
			strada = -1;
			return tmp;
		}
	}

	public int leggiSceltaMossa() {
		display.scrivi("Scegli che mossa effettuare");
		int tmp;
		synchronized (tknMossa) {
			while (mossa == -1) {
				try {
					tknMossa.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknMossa.notify();
			tmp = mossa;
			mossa = -1;
			return tmp;
		}
	}

	public int leggiStrada() {
		int tmp;
		synchronized (tknStrada) {
			while (strada == -1) {
				try {
					tknStrada.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknStrada.notify();
			tmp = strada;
			strada = -1;
			return tmp;
		}
	}

	public int leggiNeraSINO() {
		int scelta = 0;
		schermo = "";
		display.scrivi("1 per muovere la nera,0 altrimenti");
		synchronized (tknSchermo) {
			while ("".equals(schermo)) {
				try {
					tknSchermo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknSchermo.notify();
			scelta = Integer.parseInt(schermo);
			schermo = "";
			if (scelta != 1 && scelta != 0)
				scelta = 0;
			return scelta;
		}
	}

	public int leggiRegione() {
		int tmp = 0 ;
		synchronized (tknRegione) {
			while (regione == -1) {
				try {
					tknRegione.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknRegione.notify();
			tmp = regione;
			regione = -1;
			return tmp;
		}
	}

	public int leggiTipoTess() {
		int scelta = 0;
		schermo = "";
		display.scrivi("GRANO=0\nMONTAGNA=1\nSABBIA=2\nFIUMI=3\nBOSCO=4\nPIANURA=5\n");
		synchronized (tknSchermo) {
			while ("".equals(schermo)) {
				try {
					tknSchermo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknSchermo.notify();
			scelta = Integer.parseInt(schermo);
			schermo = "";
			if (scelta < 0 && scelta > 5){
				scelta = 0;
			}
			return scelta;
		}
	}

	public void fuoriOrdine(String string) {
		display.scrivi("La mossa non rispetta l'ordine imposto,rieffettuala");
		return;
	}

	public void errorePosizionamento(String string) {
		display.scrivi("Errore posizionamento,riposiziona");
		return;
	};

	public void attesa(int tipo) {
		display.scrivi("Attendi il posizionamento degli altri giocatori");
	}

	public int leggiIntero() {
		return -1;
	}

	public void aggiornaStato(StatoClient stato, int indiceNellaPartita) {
		display.aggiornaStato(stato, indiceNellaPartita);
		return;
	}

	public void win(int valore) {
		display.scrivi("HAI VINTO, con: " + valore + " punti!!!");
	}

	public void lose(int corpo, int valore) {
		display.scrivi("HAI PERSO, con: " + valore + " punti, vince " + corpo);
	}

	public void scriviMessaggioServizio(MessaggioServizio messaggioServ) {
		display.scrivi(messaggioServ.toString());
	}

	public void erroreMossa(MessaggioServizio messaggioServ) {
		display.scrivi("mossa NON valida");
	}

	public void fineTurno(String string) {
		display.scrivi("Il tuo turno è terminato");
	}

	public void selezionaPastore(int colorePastore1, int colorePastore2) {
		display.scrivi("seleziona con quale pastore effettuare la mossa");
	}

	public int leggiPastore() {
		int tmp;
		synchronized (tknPedina) {
			while (pedina == -1) {
				try {
					tknPedina.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Error wait", e);
				}
			}
			tknPedina.notify();
			display.scrivi("Hai selezionato "+ pedina);
			tmp = pedina;
			pedina = -1;
			return tmp-2;
		}
	}

	public void setContext(PiePagina piepagina, Finestra fin) {
		footer = piepagina;
		finestra = fin;
		return;
	}

	public String checkU() {
		return dispU;
	}

	public String checkIP() {
		return dispIP;
	}

	public int checkMod() {
		return dispM;
	}

	public int checkStrada() {
		return strada;
	}

	public int checkRegione() {
		return regione;
	}

	public int checkMossa() {
		return mossa;
	}

	public void disIP(String valore) {
		dispIP = valore;
		return;
	}

	public void disMod(int valore) {
		dispM = valore;
		return;
	}

	public void disUser(String valore) {
		dispU = valore;
		return;
	}

	public void settaStrada(int pStrada) {
		strada = pStrada;
		return;
	}

	public void settaRegione(int pRegione) {
		regione = pRegione;
		return;
	}

	public void settaMossa(int pMossa) {
		mossa = pMossa;
		return;
	}

	public void resetStrada() {
		strada = -1;
		return;
	}

	public void resetRegione() {
		regione = -1;
		return;
	}

	public void resetMossa() {
		mossa = -1;
		return;
	}

	public Object getTknMossa() {
		return tknMossa;
	}


	public void settaDisplay(String text) {
		schermo = text;
	}

	public String checkDisplay() {
		return schermo;
	}


	public void resetPedina() {
		pedina = -1;
		return;
	}

	public Object getTknPedina() {
		return tknPedina;
	}

	public int checkPedina(){
		return pedina;
	}

	public void setPedina(int val){
		pedina = val;
	}
}