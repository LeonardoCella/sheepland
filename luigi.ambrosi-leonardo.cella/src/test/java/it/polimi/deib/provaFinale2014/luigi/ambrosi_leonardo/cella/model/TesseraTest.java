package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Tessera;

import org.junit.Test;

public class TesseraTest {

	@Test
	public void testGetTipoTerreno() {
		Tessera t = new Tessera(1, 2);
		assertEquals(t.getTipoTerreno(), 1);
	}

	@Test
	public void testGetCosto() {
		Tessera t = new Tessera(1, 2);
		assertEquals(t.getCosto(), 2);
	}

}
