package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

public class EccPing extends Exception {
	private static final long serialVersionUID = 7305425005994964940L;

	public EccPing(Exception eccezione) {
		MyLogger.gestisci("Eccezione Ping ", eccezione);
	}
}
