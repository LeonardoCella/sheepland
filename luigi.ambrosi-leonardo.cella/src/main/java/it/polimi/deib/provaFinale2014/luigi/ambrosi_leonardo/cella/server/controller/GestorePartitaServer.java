package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerSck;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mazzi;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Tessera;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class GestorePartitaServer {

	/*
	 * informo tutti i client sul numero di giocatori nella partita, e invio a
	 * tutti i client i colori dei loro pastori
	 */
	/**
	 * It creates all the correct settings to start a new match.
	 * 
	 * @param p
	 *            the match
	 * @throws IOException
	 */
	public void impostazioniPreliminari(Partita p) throws IOException {

		int numGiocatori, colore1, colore2, tipo;
		List<Pastore> pastoriGiocatore = null;
		MessaggioServizio msg1, msg2;

		numGiocatori = p.getStatoCorrente().getGiocatori().size();

		// COMUNICO IL NUMERO DEI GIOCATORI / Tipologia della partita
		tipo = p.getTipoPartita();

		msg1 = new MessaggioServizio((tipo == 2) ? 4 : 5, 0, 0);

		// Invio in broadcast il tipo di partita!
		for (int i = 0; i < numGiocatori; i++) {
			try {
				msg1.setCorpo(i);
				if (p.getCanaliDisponibili()[i]) {
					(p.getCanali().get(i)).invioMsgServizio(msg1);
				}
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, i);
			}
		}

		// COMUNICO I COLORI DEI PASTORI
		if (tipo == 2) {
			for (int i = 0; i < 2; i++) {
				pastoriGiocatore = p.getStatoCorrente().getGiocatori().get(i)
						.getPastori();
				colore1 = pastoriGiocatore.get(0).getColore();
				colore2 = pastoriGiocatore.get(1).getColore();
				msg2 = new MessaggioServizio(3, colore1, colore2);
				try {
					if (p.getCanaliDisponibili()[i]) {
						p.getCanali().get(i).invioMsgServizio(msg2);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					sospensione(p, e, i);
				}
			}
		} else {
			for (int i = 0; i < numGiocatori; i++) {
				colore1 = p.getStatoCorrente().getGiocatori().get(i)
						.getPastori().get(0).getColore();
				msg2 = new MessaggioServizio(2, colore1, 0);
				try {
					if (p.getCanaliDisponibili()[i]) {
						p.getCanali().get(i).invioMsgServizio(msg2);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					sospensione(p, e, i);
				}
			}
		}
	}

	/**
	 * It set the position of the shepeherd/shepherds of every player.
	 * 
	 * @param p
	 *            is the match.
	 * @throws EccCanale
	 *             if there are problems during the connection.
	 * @throws RemoteException
	 *             if there are problems during the invocation of remote method.
	 * 
	 */
	public void posizionaPastori(Partita p) throws EccCanale, RemoteException {

		int i;
		MessaggioMossa msg1, msg2;
		MessaggioServizio msgServ;
		msg1 = msg2 = null;
		Mossa mossa1, mossa2;
		boolean procedi = false;
		boolean esito1 = false, esito2 = false;

		int numGiocatori = p.getStatoCorrente().getGiocatori().size();

		if (p.getTipoPartita() == 2) {
			while (!procedi) {
				i = 0;
				while (i < 2) {
					p.getCanali().get(i)
							.invioMsgServizio(new MessaggioServizio(15, 0, 0));
					try {
						if (p.getCanaliDisponibili()[i]) {
							msg1 = p.getCanali().get(i).leggoMsgMossa();
						}
					} catch (EccCanale e1) {
						MyLogger.gestisci("EccCanale", e1);
						sospensione(p, e1, i);
					}
					mossa1 = p.getTraduttore().traduciMessaggioMossa(msg1);
					
					if (mossa1 != null) {
						esito1 = mossa1.eseguiMossa();
					}

					try {
						if (p.getCanaliDisponibili()[i]) {
							msg2 = p.getCanali().get(i).leggoMsgMossa();
						}
					} catch (EccCanale e) {
						MyLogger.gestisci("EccCanale", e);
						sospensione(p, e, i);
					} catch (Exception e) {
						MyLogger.gestisci("EccCanale", e);
						sospensione(p, e, i);
					}

					mossa2 = p.getTraduttore().traduciMessaggioMossa(msg2);
					
					if (mossa2 != null) {
						esito2 = mossa2.eseguiMossa();
					}

					if ((esito1) && (esito2)){
						if (i == 1) {
							// uscita dal ciclo
							procedi = true;
						}

						// Riscontro positivo sul posizionamento
						msgServ = new MessaggioServizio(7, 0, 0);
						try {
							if (p.getCanaliDisponibili()[i]) {
								p.getCanali().get(i).invioMsgServizio(msgServ);
							}
						} catch (EccCanale e) {
							MyLogger.gestisci("EccCanale", e);
							sospensione(p, e, i);
						}
						i++;
						
					} else {
						// Riscontro negativo sul posizionamento
						msgServ = new MessaggioServizio(6, 0, 0);
						try {
							if (p.getCanaliDisponibili()[i]) {
								p.getCanali().get(i).invioMsgServizio(msgServ);
							}
							// Annullo eventuali posizionamenti già effettuati
							p.getStatoCorrente().getGiocatori().get(i)
									.getPastori().get(0).azzeraPosizione();
							p.getStatoCorrente().getGiocatori().get(i)
									.getPastori().get(1).azzeraPosizione();
						} catch (EccCanale e) {
							MyLogger.gestisci("EccCanale", e);
							sospensione(p, e, i);
						}
					}
				}

			}
		}

		else {
			i = 0;
			while (i < numGiocatori) {
				p.getCanali().get(i)
						.invioMsgServizio(new MessaggioServizio(15, 0, 0));
				try {
					if (p.getCanaliDisponibili()[i]) {
						msg1 = p.getCanali().get(i).leggoMsgMossa();
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					sospensione(p, e, i);
				}

				mossa1 = p.getTraduttore().traduciMessaggioMossa(msg1);
				if (mossa1 != null) {
					esito1 = mossa1.eseguiMossa();
				}

				if ((esito1) && (mossa1 != null)) {
					if (i == 1) {
						// esco dal ciclo esterno
						procedi = true;
					}
					// Riscontro positivo posizionamento
					msgServ = new MessaggioServizio(7, 0, 0);
					try {
						if (p.getCanaliDisponibili()[i]) {
							p.getCanali().get(i).invioMsgServizio(msgServ);
						}
					} catch (EccCanale e) {
						MyLogger.gestisci("EccCanale", e);
						sospensione(p, e, i);
					}
					i++;
				} else {
					// Riscontro negativo posizionamento
					msgServ = new MessaggioServizio(6, 0, 0);
					try {
						if (p.getCanaliDisponibili()[i]) {
							p.getCanali().get(i).invioMsgServizio(msgServ);
						}
						// Riscontro negativo
						p.getStatoCorrente().getGiocatori().get(i).getPastori()
								.get(0).azzeraPosizione();
					} catch (EccCanale e) {
						MyLogger.gestisci("EccCanale", e);
						sospensione(p, e, i);
					}
				}
			}
		}
	}

	/**
	 * It handles one turn of the game, three moves for every player.
	 * 
	 * @param p
	 *            is the match
	 * @param n
	 *            is the index of the player who has the turn.
	 */
	public void accomodaGiocatore(Partita p, int n) {

		boolean risultatoMossa;
		int contatoreMossa = 1;
		MessaggioMossa messaggioMossa = null;
		MessaggioServizio messaggioServ = null;
		Mossa mossaCorrente = null;

		messaggioServ = new MessaggioServizio(1, 1, 0);
		try {
			if (p.getCanaliDisponibili()[n]) {
				p.getCanali().get(n).invioMsgServizio(messaggioServ);
			}
		} catch (EccCanale e) {
			MyLogger.gestisci("EccCanale", e);
			sospensione(p, e, n);
			return;
		}

		muoviNeraRandom(p);

		while (contatoreMossa < 4) {

			messaggioServ = new MessaggioServizio(1, contatoreMossa, 0);
			try {
				if (p.getCanaliDisponibili()[n]) {
					p.getCanali().get(n).invioMsgServizio(messaggioServ);
				}
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, n);
				return;
			}

			try {
				inviaAggiornamenti(p);
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, n);
				return;
			} catch (IOException e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, n);
				return;
			}

			try {
				if (p.getCanaliDisponibili()[n]) {
					messaggioMossa = p.getCanali().get(n).leggoMsgMossa();
				}
				mossaCorrente = p.getTraduttore().traduciMessaggioMossa(
						messaggioMossa);
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, n);
				return;
			}

			// Traduzione non andata a buon fine
			if (mossaCorrente == null) {
				messaggioServ = new MessaggioServizio(6, 0, 0);
				try {
					if (p.getCanaliDisponibili()[n]) {
						p.getCanali().get(n).invioMsgServizio(messaggioServ);
					}
				} catch (EccCanale e) {
					MyLogger.gestisci("EccCanale", e);
					sospensione(p, e, n);
					return;
				}
			} else {
				// Provo a rieseguire la mossa
				risultatoMossa = mossaCorrente.eseguiMossa();

				// La mossa non è valida --> non conta come mossa eseguita
				if (!risultatoMossa) {
					messaggioServ = new MessaggioServizio(6, 0, 0);
					try {
						if (p.getCanaliDisponibili()[n]) {
							p.getCanali().get(n)
									.invioMsgServizio(messaggioServ);
						}
					} catch (EccCanale e) {
						MyLogger.gestisci("EccCanale", e);
						sospensione(p, e, n);
						return;
					}
				} else {
					// Riscontro positivo
					messaggioServ = new MessaggioServizio(7, 0, 0);
					try {
						if (p.getCanaliDisponibili()[n]) {
							p.getCanali().get(n)
									.invioMsgServizio(messaggioServ);
						}
					} catch (EccCanale e1) {
						MyLogger.gestisci("EccCanale", e1);
						sospensione(p, e1, n);
						return;
					}
					contatoreMossa++;
				}
			}
		}
	}

	/**
	 * It randomly moves the black sheep.
	 * 
	 * @param p
	 *            is the match.
	 * @return true if the black sheep has moved, otherwise false.
	 */
	private boolean muoviNeraRandom(Partita p) {
		if (p.getStatoCorrente().getGestoreNera().muoviRandom()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * It calculates the final score of every player and communicates who is the
	 * winner.
	 * 
	 * @param p
	 *            is the match whose final score I want to calculate.
	 */

	public void punteggioFinale(Partita p) {
		MessaggioServizio msg;
		PecoraNera pn = p.getStatoCorrente().getNera();
		int tipoTerrenoNera = pn.getPosizione().getTipoTerreno();
		List<Ovino> gregge = p.getStatoCorrente().getOvini();
		int[] pecoreTerreno = new int[6];
		int j, tipoTerreno, numPossedimenti, indiceVincitore, numGiocatori;
		Giocatore giocatore;
		List<Tessera> possedimenti;
		numGiocatori = p.getStatoCorrente().getGiocatori().size();

		for (int i = 0; i < 6; i++) {
			pecoreTerreno[i] = 0;
		}

		for (int i = 0; i < gregge.size(); i++) {
			j = gregge.get(i).getPosizione().getTipoTerreno();
			pecoreTerreno[j]++;
		}

		if (tipoTerrenoNera != 6) {
			pecoreTerreno[tipoTerrenoNera] += 2;
		}

		int[] punteggi = new int[numGiocatori];

		for (int i = 0; i < numGiocatori; i++) {
			giocatore = p.getStatoCorrente().getGiocatori().get(i);
			// Inizializzo i punteggi con i soldi rimanenti ad ogni giocatore
			punteggi[i] = giocatore.getSoldi();
			possedimenti = giocatore.getPossedimenti();
			numPossedimenti = possedimenti.size();
			for (int k = 0; k < numPossedimenti; k++) {
				tipoTerreno = possedimenti.get(k).getTipoTerreno();
				punteggi[i] += pecoreTerreno[tipoTerreno];
			}
		}

		indiceVincitore = 0;
		for (int i = 0; i < numGiocatori; i++) {
			if (punteggi[i] > punteggi[indiceVincitore]) {
				indiceVincitore = i;
			}
		}

		// Invio a tutti se hanno vinto o perso
		for (int i = 0; i < numGiocatori; i++) {
			if (i == indiceVincitore) {
				msg = new MessaggioServizio(11, 0, punteggi[i]);
			} else {
				msg = new MessaggioServizio(12, indiceVincitore, punteggi[i]);
			}
			try {
				if (p.getCanaliDisponibili()[i]) {
					p.getCanali().get(i).invioMsgServizio(msg);
				}
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, i);
			}
		}

		// invio a tutti l'ultimo stato
		try {
			inviaAggiornamenti(p);
		} catch (EccCanale e) {
			MyLogger.gestisci("EccCanale", e);
			sospensione(p, e, 15);
		} catch (IOException e) {
			MyLogger.gestisci("EccCanale", e);
			sospensione(p, e, 15);
		}

		// invio a tutti il messaggio di chiusura
		msg = new MessaggioServizio(15, 0, 0);
		for (int i = 0; i < numGiocatori; i++) {
			try {
				if (p.getCanaliDisponibili()[i]) {
					p.getCanali().get(i).invioMsgServizio(msg);
				}
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, i);
			}
		}
	}

	/**
	 * It sends the screenshot of the state to every player.
	 * 
	 * @param p
	 *            is the match whose state I want to send.
	 * @throws EccCanale
	 *             if there are problems during the connection.
	 * @throws IOException
	 */
	public void inviaAggiornamenti(Partita p) throws EccCanale, IOException {
		// client
		int numCanali = p.getCanali().size();
		MessaggioServizio msg = new MessaggioServizio(8, 0, 0);

		// Scatta uno screen a partire dallo stato consistente
		scattaScreenshot(p);

		// Invio in broadcast
		for (int i = 0; i < numCanali; i++) {
			try {
				if (p.getCanaliDisponibili()[i]) {
					// Avviso il client che riceverà un aggiornamento di stato
					p.getCanali().get(i).invioMsgServizio(msg);
					// invio l'aggiornamento vero e proprio
					p.getCanali().get(i).invioStatoClient(p.getScreenshot());
				}
			} catch (EccCanale e) {
				MyLogger.gestisci("EccCanale", e);
				sospensione(p, e, i);
			}
		}
	}

	/**
	 * It makes the photo of the state.
	 * 
	 * @param p
	 *            is the match whose state I want to photograph.
	 */
	public void scattaScreenshot(Partita p) {
		resetScreenshot(p);
		fotografaOvini(p);
		fotografaNera(p);
		fotografaLupo(p);
		fotografaRecinti(p);
		fotografaPastori(p);
		fotografaMazzi(p);
		fotografaSoldi(p);
	}

	/**
	 * It makes a photo of the situation of the sheeps.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaOvini(Partita partita) {
		Stato stato = partita.getStatoCorrente();
		List<Ovino> gregge = stato.getOvini();
		int numPecore = gregge.size();
		int[] femmine, maschi, bambini;
		femmine = new int[18];
		maschi = new int[18];
		bambini = new int[18];
		Ovino ovino;
		int codiceRegione;
		for (int i = 0; i < numPecore; i++) {
			ovino = gregge.get(i);
			codiceRegione = ovino.getPosizione().getCodice();
			if (!ovino.verificaMaggioreEta()) {
				bambini[codiceRegione]++;
			} else if (ovino.getMaschio()) {
				maschi[codiceRegione]++;
			} else {
				femmine[codiceRegione]++;
			}
		}
		for (int i = 0; i < 18; i++) {
			partita.getScreenshot().getPecoreRegioni()[i] = "" + femmine[i]
					+ ":" + maschi[i] + ":" + bambini[i];
		}
	}

	/**
	 * It makes a photo of the position of the black sheep.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaNera(Partita partita) {
		partita.getScreenshot()
				.setPosizioneNera(
						partita.getStatoCorrente().getNera().getPosizione()
								.getCodice());
	}

	/**
	 * It makes a photo of the position of the wolf.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaLupo(Partita partita) {
		partita.getScreenshot()
				.setPosizioneLupo(
						partita.getStatoCorrente().getLupo().getPosizione()
								.getCodice());
	}

	/**
	 * It makes a photo of the position of fences.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaRecinti(Partita partita) {
		Stato stato = partita.getStatoCorrente();
		int indiceStrada, indiceStrada2;
		Recinto recinto = null, recintoFinale = null;
		for (int i = 0; i < stato.getRecinti().size(); i++) {
			recinto = stato.getRecinti().get(i);
			indiceStrada = recinto.getPoSizione().getCodice();
			partita.getScreenshot().getStradeRecinti()[indiceStrada] = 1;
		}
		for (int k = 0; k < stato.getRecintiFinali().size(); k++) {
			recintoFinale = stato.getRecintiFinali().get(k);
			indiceStrada2 = recintoFinale.getPoSizione().getCodice();
			partita.getScreenshot().getStradeRecinti()[indiceStrada2] = 2;
		}
	}

	/**
	 * It makes a photo of the position of the shepherds.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaPastori(Partita partita) {
		// cicla su tutti i giocatori, per ogni giocatore cicla su ogni pastore,
		// di ogni
		// pastore trova la strada su cui è posizionato
		Stato stato = partita.getStatoCorrente();
		int numGiocatori = stato.getGiocatori().size();
		Pastore p1, p2;
		int cod1, cod2;

		for (int i = 0; i < numGiocatori; i++) {
			p1 = stato.getGiocatori().get(i).getPastori().get(0);
			cod1 = p1.getPosizione().getCodice();
			partita.getScreenshot().getStradePastori()[cod1] = p1.getColore();

			if (stato.getPartita().getTipoPartita() == 2) {
				p2 = stato.getGiocatori().get(i).getPastori().get(1);
				cod2 = p2.getPosizione().getCodice();
				partita.getScreenshot().getStradePastori()[cod2] = p2
						.getColore();
			}

		}
	}

	/**
	 * It makes a photo of the situation of the 6 decks.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaMazzi(Partita partita) {
		Stato stato = partita.getStatoCorrente();
		Mazzi m = stato.getCarte();
		for (int i = 0; i < 6; i++) {
			int costo;
			// costo rappresenta il costo della carta acquistabile del terreno
			// di tipo i-esimo

			if (m.getInstance().get(i).isEmpty()) {
				costo = -1;
			} else {
				costo = m.getInstance().get(i).get(0).getCosto();
				partita.getScreenshot().getCostoTessera()[i] = costo;
			}
		}

	}

	/**
	 * It makes a photo of the money that every player has.
	 * 
	 * @param partita
	 *            is the match whose state I want to photograph.
	 */
	private void fotografaSoldi(Partita partita) {
		Stato stato = partita.getStatoCorrente();
		int numGiocatori = stato.getGiocatori().size();
		for (int i = 0; i < numGiocatori; i++) {
			partita.getScreenshot().getSoldiGiocatori()[i] = stato
					.getGiocatori().get(i).getSoldi();
		}
	}

	/**
	 * It cancel the previous photo for preparing another shooting.
	 * 
	 * @param partita
	 *            is the match whose state I want to reset.
	 * 
	 */
	private void resetScreenshot(Partita partita) {
		StatoClient screenshot = partita.getScreenshot();
		for (int i = 0; i < 42; i++) {
			screenshot.getStradeRecinti()[i] = 0;
			screenshot.getStradePastori()[i] = 0;
		}
		for (int i = 0; i < 18; i++) {
			screenshot.getPecoreRegioni()[i] = "";
		}
		for (int i = 0; i < 6; i++) {
			screenshot.getSoldiGiocatori()[i] = 0;
		}
	}

	/**
	 * It makes the sheeps older of one year. If the sheep has 2 years it isn't
	 * more a lamb.
	 * 
	 * @param p
	 *            is the match.
	 */
	public void controllaEtaOvini(Partita p) {
		List<Ovino> gregge = p.getStatoCorrente().getOvini();
		for (int i = 0; i < gregge.size(); i++) {
			gregge.get(i).aumentaEta();
			if (gregge.get(i).getEta() == 2) {
				gregge.get(i).setDiventaGrande();
			}
		}

	}

	/**
	 * Is the method that handle the disconnection.
	 * 
	 * @param p
	 *            is the match.
	 * @param e
	 *            is the exception that notify the disconnection.
	 * @param i
	 *            is the player who is now disconnected.
	 */
	public void sospensione(Partita p, Exception e, int i) {
		if(p.getCanali().get(i).getClass()==ServerSck.class){
			p.sospendi(i);
		}
	}
}
