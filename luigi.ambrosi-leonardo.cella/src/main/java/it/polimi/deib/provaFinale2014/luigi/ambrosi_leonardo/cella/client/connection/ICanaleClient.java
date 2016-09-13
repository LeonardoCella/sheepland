package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

/**
 * @author Luigi Ambrosi, Leonardo Cella It is the interface of the
 *         communication channel of client: it can be a socket or it can
 *         implement RMI
 * 
 */
public interface ICanaleClient {

	public void invioMsgMossa(MessaggioMossa m) throws EccCanale;

	public StatoClient leggoStatoAggiornato() throws EccCanale;

	public void accendi() throws EccCanale;

	public void inizio() throws EccCanale;

	public MessaggioServizio leggoMsgServizio() throws EccCanale;

	public void invioMsgServizio(MessaggioServizio m) throws EccCanale;

	public boolean getTipo();

	public void toggle();

	public String getUsername();
}
