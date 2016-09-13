package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class MazziTest {

	@Test
	public void testPescaDalMazzo() throws EccClient {
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		int dimPrima, dimDopo;
		dimPrima = p.getStatoCorrente().getCarte().getInstance().get(1).size();
		p.getStatoCorrente().getCarte().pescaDalMazzo(1);
		dimDopo = p.getStatoCorrente().getCarte().getInstance().get(1).size();
		assertEquals(dimPrima - 1, dimDopo);
	}

}
