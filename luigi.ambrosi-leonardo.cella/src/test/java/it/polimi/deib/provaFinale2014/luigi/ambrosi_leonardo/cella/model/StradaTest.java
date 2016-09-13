package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.EccezioneNonAdiacenza;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.util.ArrayList;

import org.junit.Test;

public class StradaTest {

	@Test
	public void testGetConfinante() throws EccClient {
		Mappa mappa = Mappa.getMappa();
		Strada s1;
		Regione r1, r2;
		r1 = mappa.getRegioni()[2];
		r2 = mappa.getRegioni()[5];
		s1 = mappa.getStrade()[5];

		try {
			assertEquals(r2, s1.getConfinante(r1));
		} catch (EccezioneNonAdiacenza e) {
			System.out
					.println("Riguarda Test Strada | metodo getConfinante | NonAdiacenza!");
		}
	}

	@Test
	public void testGetStradeCollegate() throws EccClient {
		Mappa mappa = Mappa.getMappa();
		boolean trovato = true;
		Strada s1, s2, s3, s4, s5;
		ArrayList<Strada> expected = new ArrayList<Strada>();
		Strada[] risultato;

		s1 = mappa.getStrade()[35];

		risultato = s1.getStradeCollegate();

		System.out.println(risultato);

		s2 = mappa.getStrade()[31];
		s3 = mappa.getStrade()[36];
		s4 = mappa.getStrade()[39];
		s5 = mappa.getStrade()[41];

		expected.add(s2);
		expected.add(s3);
		expected.add(s4);
		expected.add(s5);

		for (int i = 0; i < risultato.length; i++) {
			System.out.println(risultato[i]);
			if (!(expected.contains(risultato[i]))) {
				trovato = false;
			}
		}
		assertEquals(trovato, true);
	}

	@Test
	public void testGetStradeCollegate2() throws EccClient {
		Mappa mappa = Mappa.getMappa();
		boolean trovato = true;
		Strada s1, s2, s3;
		ArrayList<Strada> expected = new ArrayList<Strada>();
		Strada[] risultato;

		s1 = mappa.getStrade()[1];

		risultato = s1.getStradeCollegate();

		System.out.println(risultato);

		s2 = mappa.getStrade()[5];
		s3 = mappa.getStrade()[6];

		expected.add(s2);
		expected.add(s3);

		for (int i = 0; i < risultato.length; i++) {
			System.out.println(risultato[i]);
			if (!(expected.contains(risultato[i]))) {
				trovato = false;
			}
		}
		assertEquals(trovato, true);
	}
}
