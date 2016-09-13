package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class PastoreTest {

	@Test
	public void testMuovi() throws EccClient {
		Mappa map = Mappa.getMappa();
		// Regioni e strada adiacenti

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore17", "localhost"));
			canali.add(new ServerRMI("Giocatore18", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);

		pastore.setPosizione(map.getStrade()[15]);
		pastore.muovi(map.getStrade()[16]);

		assertEquals(map.getStrade()[16], pastore.getPosizione());

	}

	@Test
	public void testPastore() throws EccClient {
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore15", "localhost"));
			canali.add(new ServerRMI("Giocatore16", "localhost"));
		} catch (IOException e1) {
			fail("Non testabile --> RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1 = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		Pastore pastore2 = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Pastore pastore3 = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(0);
		Pastore pastore4 = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(1);

		assertEquals(4, pastore4.getColore());
		assertEquals(3, pastore3.getColore());
		assertEquals(2, pastore2.getColore());
		assertEquals(1, pastore1.getColore());

	}

	@Test
	public void testGetGiocatore() throws EccClient {
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		ICanaleServer g1, g2;
		try {
			g1 = new ServerRMI("Giocatore13", "localhost");
			g2 = new ServerRMI("Giocatore14", "localhost");
			canali.add(g1);
			canali.add(g2);
			Partita p = new Partita(map, canali, null, 0, true);
			Pastore pastore1 = p.getStatoCorrente().getGiocatori().get(0)
					.getPastori().get(0);
			Pastore pastore2 = p.getStatoCorrente().getGiocatori().get(0)
					.getPastori().get(1);
			Pastore pastore3 = p.getStatoCorrente().getGiocatori().get(1)
					.getPastori().get(0);
			Pastore pastore4 = p.getStatoCorrente().getGiocatori().get(1)
					.getPastori().get(1);

			assertEquals(g1.getUsername(), pastore1.getGiocatore().getCanale()
					.getUsername());
			assertEquals(g1.getUsername(), pastore2.getGiocatore().getCanale()
					.getUsername());
			assertEquals(g2.getUsername(), pastore3.getGiocatore().getCanale()
					.getUsername());
			assertEquals(g2.getUsername(), pastore4.getGiocatore().getCanale()
					.getUsername());

		} catch (IOException e) {
			fail("Non Testabile --> RMI");
		}

		assertTrue(true);

	}

	@Test
	public void testSetPosizione() throws EccClient {
		Mappa map = Mappa.getMappa();
		// Regioni e strada adiacenti
		Regione r1 = map.getRegioni()[7];
		Regione r2 = map.getRegioni()[6];
		Regione r3 = map.getRegioni()[8];
		Regione r11 = map.getRegioni()[11];
		Regione r16 = map.getRegioni()[16];
		Strada str = new Strada(r1, r2, 12, 5);
		Strada str1 = new Strada(r1, r3, 13, 3);
		Strada str2 = new Strada(r11, r16, 16, 6);
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore11", "localhost"));
			canali.add(new ServerRMI("Giocatore12", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		Pastore occupante = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(0);

		pastore.setPosizione(str);
		occupante.setPosizione(str1);

		assertEquals(pastore.setPosizione(str2), true);
	}

	@Test
	public void testGetPosizione() throws EccClient {
		Mappa map = Mappa.getMappa();
		Regione r1 = map.getRegioni()[7];
		Regione r2 = map.getRegioni()[6];
		Strada str = new Strada(r1, r2, 12, 5);

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore9", "localhost"));
			canali.add(new ServerRMI("Giocatore10", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);

		pastore.setPosizione(str);

		assertEquals(pastore.getPosizione(), str);
	}

	@Test
	public void testGetColore() throws EccClient {
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore7", "localhost"));
			canali.add(new ServerRMI("Giocatore8", "localhost"));
		} catch (IOException e1) {
			fail("Non testabile --> RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1 = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		Pastore pastore2 = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Pastore pastore3 = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(0);
		Pastore pastore4 = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(1);

		assertEquals(4, pastore4.getColore());
		assertEquals(3, pastore3.getColore());
		assertEquals(2, pastore2.getColore());
		assertEquals(1, pastore1.getColore());
	}

	@Test
	public void testGetPosizione1() throws EccClient {
		Mappa map = Mappa.getMappa();
		Regione r1 = map.getRegioni()[0];
		Regione r2 = map.getRegioni()[3];
		Strada str = new Strada(r1, r2, 2, 2);

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore5", "localhost"));
			canali.add(new ServerRMI("Giocatore6", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);

		pastore.setPosizione(str);

		assertEquals(pastore.getPosizione(), str);
	}

	@Test
	public void testGetPosizione2() throws EccClient {
		Mappa map = Mappa.getMappa();
		Regione r1 = map.getRegioni()[11];
		Regione r2 = map.getRegioni()[18];
		Strada str = new Strada(r1, r2, 22, 3);

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore3", "localhost"));
			canali.add(new ServerRMI("Giocatore4", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);

		pastore.setPosizione(str);

		assertEquals(pastore.getPosizione(), str);
	}

	@Test
	public void testGetPosizione3() throws EccClient {
		Mappa map = Mappa.getMappa();
		Regione r1 = map.getRegioni()[15];
		Regione r2 = map.getRegioni()[16];
		Strada str = new Strada(r1, r2, 41, 5);

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}

		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);

		pastore.setPosizione(str);

		assertEquals(pastore.getPosizione(), str);
	}
}
