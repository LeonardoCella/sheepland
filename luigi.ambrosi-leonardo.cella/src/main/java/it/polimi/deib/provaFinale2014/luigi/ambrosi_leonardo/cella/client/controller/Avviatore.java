package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.MainClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.view.InterfacciaIO;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.StructCanali;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;

import java.rmi.RemoteException;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 */
public class Avviatore {
	private static String ipServer;
	private static String username;
	private static int scelta;
	private static InterfacciaIO ui = MainClient.getUI();
	private static Object bloccante = new Object();

	private Avviatore() {

	}

	/**
	 * It makes the player choose a way of connection, and starts it.
	 * 
	 * @return It returns the channel of communication.
	 */
	public static StructCanali run() {
		StructCanali structCanali = null;
		boolean rmiAvviato = false;

		while (structCanali == null) {
			credenziali();

			if (scelta == 1) {
				structCanali = ConnettoreSck.avvioSocket(ipServer, username);
			} else {
				if (scelta == 2) {

					if (!rmiAvviato) {
						try {
							ConnettoreRMI.avvioRMI(ipServer);
						} catch (RemoteException e) {
							MyLogger.gestisci("Ecc Remota", e);
							ui.reset();
							return null;
						}
					}

					try {
						structCanali = ConnettoreRMI.addRMI(username);
					} catch (RemoteException e) {
						MyLogger.gestisci("Ecc Remota", e);
						ui.reset();
						return null;
					}
				}
			}

			if (structCanali == null) {
				ui.reset();
				ui.avviso();
			} else {
				if (structCanali.getCanaleComm().getTipo()) {
					ui.reset();
					ui.riscontro();
				} else {
					ui.reset();
					ui.riconnect();
				}
			}
		}

		return structCanali;
	}

	/**
	 * 
	 * @return the choice of the player, or -1 if there's an error.
	 */
	private static void credenziali() {

		String messaggio;
		synchronized (bloccante) {
			ui.scrivi("Inserisci l'indirizzo ip del server");

			ipServer = ui.leggiIP();

			messaggio = "Inserisci: 1 per Socket , 2 per RMI";

			ui.scrivi(messaggio);
			scelta = ui.leggiSceltaConn();

			ui.scrivi("Inserisci il tuo username:");
			username = ui.leggiUser();

			if ((scelta != Costanti.SOCKET) && (scelta != Costanti.RMI)) {
				credenziali();
			}
		}
		return;
	}
}
