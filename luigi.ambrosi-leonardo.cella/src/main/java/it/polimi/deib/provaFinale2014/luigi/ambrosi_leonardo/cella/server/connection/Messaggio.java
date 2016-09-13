package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.io.Serializable;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the abstract class of all the kind of messages.
 */
public abstract class Messaggio implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int mod;
	protected int corpo;
	protected int destinatario;

}
