package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ICanaleClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.Ping;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller.Avviatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller.GestoreMessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.IO;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.InterfacciaIO;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Buffer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.Finestra;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.gui.login.Form;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.StructCanali;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.IOException;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the control flow of the client.
 */
public class MainClient {

	private static ICanaleClient canale;
	private static StructCanali coppiaCanali;

	private static InterfacciaIO ui;

	private static boolean turno;
	private static StatoClient stato;
	private static int tipoPartita;
	private static int colorePastore1;
	private static int colorePastore2;
	private static GestoreMessaggioMossa gestore;
	private static int indiceNellaPartita;
	private static boolean canaleDisponibile = true;

	private MainClient() {

	}

	/**
	 * @param colorePastore
	 *            the colour of the shepherd that will make the move.
	 * @return a MessaggioMossa.
	 * @throws IOException
	 */
	private static MessaggioMossa creaMessaggioMossa(int colorePastore)
			throws IOException {
		// Può tornare un MessaggioMossa che rispetti le precedenze imposte
		// dalle regole!
		int tipoMossa = 0, soggettoMossa = 0, obiettivo = -1, muoviNera = 0;
		MessaggioMossa msg = null;
		boolean possibile = false;
		while (!possibile) {
			soggettoMossa = colorePastore;
			while ((tipoMossa != Costanti.SPOSTAPASTORE)
					&& (tipoMossa != Costanti.SPOSTAPECORA)
					&& (tipoMossa != Costanti.COMPRATESSERA)
					&& (tipoMossa != Costanti.ACCOPPIAMENTO)
					&& (tipoMossa != Costanti.ABBATTIMENTO)) {
				ui.scrivi("\n\nScegli quale mossa effettuare. Inserisci:\n1 per spostare il pastore\n2 per spostare una pecora\n3 per comprare una tessera terreno\n4 per far avvenire un accoppiamento\n5 per compiere un abbattimento");
				tipoMossa = ui.leggiSceltaMossa();
			}

			switch (tipoMossa) {
			case Costanti.SPOSTAPASTORE: {
				while ((obiettivo < 0) || (obiettivo > 41)) {
					ui.scrivi("Inserisci il numero della strada in cui spostare il pastore");
					obiettivo = ui.leggiStrada();
				}
				break;
			}
			case Costanti.SPOSTAPECORA: {
				while ((obiettivo < 0) || (obiettivo > 17)
						|| ((muoviNera != 0) && (muoviNera != 1))) {
					ui.scrivi("Inserisci 1 se vuoi muovere la nera, 0 altrimenti");
					muoviNera = ui.leggiNeraSINO();
					ui.scrivi("Inserisci il numero della regione in cui spostare la pecora");
					obiettivo = ui.leggiRegione();
				}
				break;
			}
			case Costanti.COMPRATESSERA: {
				while ((obiettivo < 0) || (obiettivo > 5)) {
					ui.scrivi("Inserisci il tipo di tessera terreno che vuoi comprare:\nGRANO=0\nMONTAGNA=1\nSABBIA=2\nFIUMI=3\nBOSCO=4\nPIANURA=5\n");
					obiettivo = ui.leggiTipoTess();
				}
				break;
			}
			case Costanti.ACCOPPIAMENTO: {
				while ((obiettivo < 0) || (obiettivo > 17)) {
					ui.scrivi("Inserisci il numero della regione in cui far avvenire l'accoppiamento");
					obiettivo = ui.leggiRegione();
				}
				break;
			}
			case Costanti.ABBATTIMENTO: {
				while ((obiettivo < 0) || (obiettivo > 17)) {
					ui.scrivi("Inserisci il numero della regione in cui far avvenire l'abbattimento");
					obiettivo = ui.leggiRegione();
				}
				break;
			}
			default: {
				break;
			}
			}
			msg = new MessaggioMossa(tipoMossa, soggettoMossa, obiettivo);
			if (tipoMossa == 2) {
				msg.setMuoviNera(muoviNera);
			}

			if (gestore.controllaMessaggioMossa(msg)) {
				possibile = true;
			} else {
				soggettoMossa = 0;
				obiettivo = -1;
				ui.fuoriOrdine("La tua mossa non rispetta l'ordine imposto dal gioco, devi riformularla");
			}
		}
		return msg;
	}

	/**
	 * It creates the preliminary settings of the match.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static void impostazioniPreliminari()
			throws ClassNotFoundException, IOException {
		// Setta la modalità di partita e il numero di giocatori
		MessaggioServizio msg1, msg2;
		msg1 = msg2 = null;
		try {
			if (canaleDisponibile) {
				msg1 = canale.leggoMsgServizio();
			}
			indiceNellaPartita = msg1.getCorpo();

			ui.scriviIndice(indiceNellaPartita);

			ui.scriviTessIn(Costanti.getTipoTerreno(indiceNellaPartita));
		} catch (EccCanale e) {
			MyLogger.gestisci("EccCanale", e);
			canaleDisponibile = false;
		}

		canale.inizio();

		try {
			if (canaleDisponibile) {
				msg2 = canale.leggoMsgServizio();
			}
		} catch (EccCanale e) {
			MyLogger.gestisci("EccCanale", e);
			canaleDisponibile = false;
		}
		// Partita a due giocatori
		if (msg1.getTipoMessaggio() == 4) {
			tipoPartita = 2;
			colorePastore1 = msg2.getCorpo();
			colorePastore2 = msg2.getValore();
			ui.colPast(colorePastore1, 1);
			ui.colPast(colorePastore2, 2);
			// Partita a più di due giocatori
		} else if (msg1.getTipoMessaggio() == 5) {
			tipoPartita = 1;
			colorePastore1 = msg2.getCorpo();
			ui.colPast(colorePastore1, 0);
		}
	}

	/**
	 * It sets the position of the only shepherd of the player.
	 * 
	 * @throws IOException
	 */
	private static void posizionaUnPastore() throws IOException {
		MessaggioServizio msg = null;
		int dest1 = -1;
		boolean procedi = false;
		MessaggioMossa msg1;
		while (!procedi) {
			msg = canale.leggoMsgServizio();
			if (msg.getTipoMessaggio() == 15) {
				while ((dest1 < 0) || (dest1 > 41)) {
					ui.scrivi("Inserisci la strada in cui vuoi posizionare il tuo primo pastore");
					dest1 = ui.leggiPosizionamento();
				}

				msg1 = new MessaggioMossa(Costanti.POSIZIONAPASTORE,
						colorePastore1, dest1);

				try {
					if (canaleDisponibile) {
						canale.invioMsgMossa(msg1);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}

				try {
					if (canaleDisponibile) {
						msg = canale.leggoMsgServizio();
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}
				// Nel caso di conferma esco dal ciclo ponendo procedi=true
				if (msg.getTipoMessaggio() == 7) {
					procedi = true;
				} else {
					ui.errorePosizionamento("Il posizionamento dei tuoi pastori non va bene.\n\n");
					ui.resetStrada();
					dest1 = -1;
				}
			}
		}
		ui.attesa(1);
	}

	/**
	 * It sets the position of the two shepherds of the player.
	 * 
	 * @throws IOException
	 */
	private static void posizionaDuePastori() throws IOException {
		MessaggioServizio msg = null;
		int dest1 = -1, dest2 = -1;
		boolean procedi = false;
		MessaggioMossa msg1, msg2;
		while (!procedi) {
			msg = canale.leggoMsgServizio();
			if (msg.getTipoMessaggio() == 15) {
				while ((dest1 < 0) || (dest1 > 41)) {
					ui.scrivi("Inserisci la strada in cui vuoi posizionare il tuo primo pastore");
					dest1 = ui.leggiPosizionamento();
				}
				ui.resetStrada();
				while ((dest2 < 0) || (dest2 > 41)) {
					ui.scrivi("Inserisci la strada in cui vuoi posizionare il tuo secondo pastore");
					dest2 = ui.leggiPosizionamento();
				}

				msg1 = new MessaggioMossa(Costanti.POSIZIONAPASTORE,
						colorePastore1, dest1);
				msg2 = new MessaggioMossa(Costanti.POSIZIONAPASTORE,
						colorePastore2, dest2);

				ui.attesa(1);

				try {
					if (canaleDisponibile) {
						canale.invioMsgMossa(msg1);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}

				try {
					if (canaleDisponibile) {
						canale.invioMsgMossa(msg2);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
				}
				try {
					if (canaleDisponibile) {
						msg = canale.leggoMsgServizio();
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}
				if (msg.getTipoMessaggio() == 7) {
					procedi = true;
				} else {
					ui.errorePosizionamento("Il posizionamento dei tuoi pastori non va bene.\n\n");
					ui.resetStrada();
					dest1 = -1;
					dest2 = -1;
				}
			}
		}
	}

	/**
	 * The real control flow of the match.
	 * 
	 * @param tipoConnessione
	 *            is true if the connection is setted for the first time. It is
	 *            false if the player has disconnected, and then he has joined
	 *            the match again: he does not have to positionate again the
	 *            shepherds.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static void gioca(boolean tipoConnessione)
			throws ClassNotFoundException, IOException {
		int coloreRiferimento = 0;
		int contatoreMossa = 1;
		MessaggioServizio messaggioServ = null;
		MessaggioMossa messaggioMossaCorrente = null;

		if (tipoConnessione) {
			if (tipoPartita == 2) {
				posizionaDuePastori();
			} else {
				posizionaUnPastore();
			}
			messaggioServ = canale.leggoMsgServizio();

			// Stato aggiornato
			if (messaggioServ.getTipoMessaggio() == 8) {
				try {
					if (canaleDisponibile) {
						// Stato in seguito al posizionamento dei pastori
						stato = canale.leggoStatoAggiornato();
					}
				} catch (EccCanale e) {
					canaleDisponibile = false;
					MyLogger.gestisci("Main Client Disconessione 307", e);
				}
				ui.aggiornaStato(stato, indiceNellaPartita);

			}
		}

		while (true) {

			turno = false;

			if (canaleDisponibile) {
				messaggioServ = canale.leggoMsgServizio();
			}

			if (messaggioServ.getTipoMessaggio() == 8) {
				try {
					if (canaleDisponibile) {
						stato = canale.leggoStatoAggiornato();
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}
				ui.aggiornaStato(stato, indiceNellaPartita);

			}

			else if ((messaggioServ.getTipoMessaggio() == 1)
					&& (messaggioServ.getCorpo() == 1)) {
				turno = true;
				contatoreMossa = 1;
			} else if (messaggioServ.getTipoMessaggio() == 11) {
				// Comunicazione Punteggio
				ui.win(messaggioServ.getValore());
			} else if (messaggioServ.getTipoMessaggio() == 12) {
				// Indice vintitore, tuo punteggio
				ui.lose(messaggioServ.getCorpo(), messaggioServ.getValore());
			} else if (messaggioServ.getTipoMessaggio() == 15) {
				return;
			}

			// Ho 3 mosse per turno, contano solo quelle valide
			while ((turno) && (contatoreMossa < 4)) {

				try {
					if (canaleDisponibile) {
						// Leggo la mossa desiderata
						messaggioServ = canale.leggoMsgServizio();
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}

				// Mostro il messaggio
				ui.scriviMessaggioServizio(messaggioServ);

				try {
					messaggioServ = canale.leggoMsgServizio();
				} catch (EccCanale e1) {
					MyLogger.gestisci("EccCanale", e1);
					canaleDisponibile = false;
				}
				if (messaggioServ.getTipoMessaggio() == 8) {
					try {
						if (canaleDisponibile) {
							// Leggo lo stato su cui farò la mossa
							stato = canale.leggoStatoAggiornato();
							ui.aggiornaStato(stato, indiceNellaPartita);

						}
					} catch (EccCanale e) {
						MyLogger.gestisci("EccCanale", e);
						canaleDisponibile = false;
					}
				}

				if ((tipoPartita == 2) && (contatoreMossa == 1)) {
					// Scelgo il colore del pastore nel caso di partita a 2
					// giocatori
					coloreRiferimento = sceltaPastore();
				} else if ((tipoPartita == 1) && (contatoreMossa == 1)) {
					coloreRiferimento = colorePastore1;
				}

				// Creo la mossa sul colore scelto
				messaggioMossaCorrente = creaMessaggioMossa(coloreRiferimento);

				try {
					// Invio la mossa
					if (canaleDisponibile) {
						canale.invioMsgMossa(messaggioMossaCorrente);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					canaleDisponibile = false;
				}

				try {
					if (canaleDisponibile) {
						messaggioServ = canale.leggoMsgServizio();
					}
				} catch (EccCanale e) {
					canaleDisponibile = false;
					MyLogger.gestisci("EccCanale", e);
				}

				if (messaggioServ.getTipoMessaggio() == 6) {
					// Mossa non valida
					ui.erroreMossa(messaggioServ);
				} else {
					// Se la mossa è valida incremento il contatore
					if (messaggioServ.getTipoMessaggio() == 7) {
						ui.scriviMessaggioServizio(messaggioServ);
						gestore.registra(messaggioMossaCorrente);
						if (contatoreMossa == 3) {
							turno = false;
							ui.fineTurno("Il tuo turno e' finito");
						}
						contatoreMossa++;
					}
				}
			}

		}
	}

	/**
	 * It the match is with only two players, before every move, the player has
	 * to choose between his two shepherds. This method do that.
	 * 
	 * @return the colour of the shepherd chosen.
	 */
	private static int sceltaPastore() {
		int scelta = 0;
		while ((scelta != 1) && (scelta != 2)) {
			ui.selezionaPastore(colorePastore1, colorePastore2);
			try {
				scelta = ui.leggiPastore();
			} catch (NumberFormatException e) {
				MyLogger.gestisci("EccCanale", e);
			}
		}
		if (scelta == 1) {
			return colorePastore1;
		} else {
			return colorePastore2;
		}
	}

	public static void settaColore(int colore1) {
		colorePastore1 = colore1;
		tipoPartita = 1;
		indiceNellaPartita = colore1 - 1;
		ui.colPast(colorePastore1, 0);
		ui.scriviIndice(indiceNellaPartita);
	}

	public static void settaColori(int colore1, int colore2) {
		colorePastore1 = colore1;
		colorePastore2 = colore2;
		tipoPartita = 2;
		if (colore1 == Costanti.ROSSO) {
			indiceNellaPartita = 0;
		} else {
			indiceNellaPartita = 1;
		}
		ui.colPast(colorePastore1, 1);
		ui.colPast(colorePastore2, 2);
		ui.scriviIndice(indiceNellaPartita);
	}

	public static InterfacciaIO getUI() {
		return ui;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		int appoggio=0;
		ui = new IO();

		ui.scrivi("BENVENUTO IN SHEEPLAND!!!\n\n\n");
		
		while(appoggio!=1 && appoggio!=2){
			ui.scrivi("\nInserisci 1 per CLC e 2 per la grafica\n");
			appoggio=ui.leggiIntero();
		}

		if (appoggio == 2) {

			Finestra f1 = new Finestra();
			Form vista = new Form(f1.getX(), f1.getY(), f1.getWdt(),
					f1.getHgt());
			f1.getContentPane().add(vista);
			f1.abilita();
			ui = Buffer.getBuffer();
			Buffer.getBuffer().setContext(vista.getDivBtn(), f1);

		} else {
			ui = new IO();
		}

		gestore = new GestoreMessaggioMossa();

		coppiaCanali = Avviatore.run();

		canale = coppiaCanali.getCanaleComm();

	

		Ping processoPing = new Ping(coppiaCanali.getCanalePing());

		processoPing.start();

		if (canale.getTipo()) {
			impostazioniPreliminari();
		}

		gioca(canale.getTipo());
	}
}
