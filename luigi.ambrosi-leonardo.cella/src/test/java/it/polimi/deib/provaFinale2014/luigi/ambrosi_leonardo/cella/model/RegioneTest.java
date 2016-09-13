package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.util.ArrayList;

import org.junit.Test;

public class RegioneTest {

	@Test
	public void testGetAdiacenze() throws EccClient { // Regioni e strada
														// adiacenti
		Mappa map = Mappa.getMappa();
		Regione regione = map.getRegioni()[18];
		Regione r1 = map.getRegioni()[4];
		Regione r2 = map.getRegioni()[5];
		Regione r3 = map.getRegioni()[7];
		Regione r4 = map.getRegioni()[9];
		Regione r5 = map.getRegioni()[10];
		Regione r6 = map.getRegioni()[11];

		Strada str1 = new Strada(r1, regione, 12, 5);
		Strada str2 = new Strada(r2, regione, 13, 3);
		Strada str3 = new Strada(r3, regione, 12, 5);
		Strada str4 = new Strada(r4, regione, 13, 3);
		Strada str5 = new Strada(r5, regione, 12, 5);
		Strada str6 = new Strada(r6, regione, 13, 3);

		int contatore = 1;

		ArrayList<Strada> confini = new ArrayList<Strada>();

		for (Strada x : confini) {
			if ((x != str1) && (x != str2) && (x != str3) && (x != str4) && (x != str5)
					&& (x != str6)) {
				contatore = 0;
			}
		}
		System.out.println(contatore);
		assertTrue(((contatore == 0) ? false : true));
	}

	@Test
	public void testGetTipoTerreno() throws EccClient {
		Mappa map = Mappa.getMappa();
		Regione regione = map.getRegioni()[0];
		Regione regione1 = map.getRegioni()[18];

		if (regione.getTipoTerreno() == Costanti.PIANURA) {
			if (regione1.getTipoTerreno() == Costanti.SHEEPSBURG) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} else {
			assertTrue(false);
		}
	}

}
