package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 * 
 */
public class EccClient extends Exception {

	private static final long serialVersionUID = -5871174866248553384L;

	public EccClient(Exception e) {
		MyLogger.gestisci("Eccezione canale client", e);
	}
}
