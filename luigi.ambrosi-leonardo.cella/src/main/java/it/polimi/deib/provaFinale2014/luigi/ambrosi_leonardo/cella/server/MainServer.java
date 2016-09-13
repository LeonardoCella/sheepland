package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Server;

public class MainServer {

	/**
	 * Indirect starter
	 */
	public static void menu() {
		int scelta = 1;
		boolean uscita = true;
		Server server = null;

		server = Server.getServer();

		while (uscita) {

			if (scelta == 1) {
				avviaSessione(server);
			}
			uscita = false;
		}

		return;
	}

	/**
	 * @description real starter of the game
	 */
	public static void avviaSessione(Server server) {
		try {
			server.start();
		} catch (Exception e) {
			MyLogger.gestisci("AvvioErr", e);
			server.chiudiRicevitori();
			return;
		}

	}

	public static void main(String[] args) {
		menu();
	}
}
