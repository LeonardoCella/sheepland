package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ICanaleClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.IPing;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class to create the association between the communication
 *         channel and the ping pong channel.
 * 
 */
public class StructCanali {

	private ICanaleClient canaleComm;
	private IPing canalePing;

	public StructCanali(ICanaleClient c1, IPing c2) {
		this.canaleComm = c1;
		this.canalePing = c2;
	}

	public ICanaleClient getCanaleComm() {
		return canaleComm;
	}

	public IPing getCanalePing() {
		return canalePing;
	}

}
