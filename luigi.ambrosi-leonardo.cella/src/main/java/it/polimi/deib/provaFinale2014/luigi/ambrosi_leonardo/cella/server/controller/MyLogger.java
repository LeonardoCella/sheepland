package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class to handle the exceptions.
 * 
 */
public class MyLogger {
	private static final Logger LOGGER = Logger.getLogger(MyLogger.class
			.getName());

	private MyLogger() {

	}

	public static void gestisci(String info, Exception exception) {
		LOGGER.log(Level.FINE, "messaggio", exception);
	}
}
