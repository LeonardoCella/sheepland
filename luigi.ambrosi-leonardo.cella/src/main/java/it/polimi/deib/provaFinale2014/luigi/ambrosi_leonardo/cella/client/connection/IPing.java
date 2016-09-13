package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the interface for PingRMI and PingSck.
 * 
 */
public interface IPing {

	public void accendi() throws EccPing;

	public void inizio() throws EccPing;

	public String leggiPong() throws EccPing;

	public void invioPing(MessaggioStringa ultimoPing) throws EccPing;
}
