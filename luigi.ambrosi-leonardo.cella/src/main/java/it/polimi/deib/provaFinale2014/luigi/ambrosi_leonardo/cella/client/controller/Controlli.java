package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller;

//Classe con funzioni di controllo sintattico-semantico, non di gestione connessione o altro!
public class Controlli {

	/**
	 * @param ip
	 *            is the ip that I want to check.
	 * @return true if the ip is valid, otherwise false.
	 * 
	 */
	public static boolean checkIP(String ip) {
		String[] campi;
		String separatore = ".";

		campi = ip.split(separatore);
		if (campi.length != 4) {
			return false;
		}

		for (String el : campi) {
			if ((Integer.parseInt(el) > 255) || (Integer.parseInt(el) < 0)) {
				return false;
			}
		}

		return true;
	}

	private Controlli() {

	}
}
