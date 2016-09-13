package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.io.Serializable;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the message that is used to implement the communication in
 *         ping-pong between client and server.
 * 
 */
public class MessaggioStringa implements Serializable {
	private static final long serialVersionUID = 171910612942413615L;
	private String corpo;

	public MessaggioStringa(String s) {
		this.corpo = s;
	}

	public String getCorpo() {
		return this.corpo;
	}

}
