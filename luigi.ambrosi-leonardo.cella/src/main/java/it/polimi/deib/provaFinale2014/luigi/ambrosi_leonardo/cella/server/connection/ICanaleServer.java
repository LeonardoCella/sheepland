package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the interface of the communication channels of the server. They
 *         can be socket channel or RMI channel.
 */
public interface ICanaleServer {
	public MessaggioMossa leggoMsgMossa() throws EccCanale;

	public void invioStatoClient(StatoClient stato) throws EccCanale;

	public void invioMsgServizio(MessaggioServizio messaggio) throws EccCanale;

	public MessaggioServizio leggoMsgServizio() throws EccCanale;

	public String getUsername();

	public void accendi() throws Exception;

}