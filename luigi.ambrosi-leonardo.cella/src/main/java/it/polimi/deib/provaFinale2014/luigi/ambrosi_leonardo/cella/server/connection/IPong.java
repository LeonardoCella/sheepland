package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ritardo;

public interface IPong {
	public void accendi() throws Exception;

	public void invioPong(MessaggioStringa messaggio) throws EccPing;

	public void leggiPing() throws Ritardo;

	public String getUser();
}
