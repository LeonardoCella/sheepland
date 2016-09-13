package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StatoTest {

	@Test
	public void testInizializzaPecore() throws EccClient { // controllo che ci
															// sia una pecora in
		// ogni regione
		boolean presenti = true;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		List<Ovino> gregge = p.getStatoCorrente().getOvini();
		for (int i = 0; i < gregge.size(); i++) {
			if (i != gregge.get(i).getPosizione().getCodice()) {
				presenti = false;
			}
		}
		assertTrue(presenti);
	}

	@Test
	public void testStradaLibera() throws EccClient { // controllo su una strada
														// occupata e una
		// libera
		boolean risultato1, risultato2;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);

		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		pastore.setPosizione(map.getStrade()[15]);
		risultato1 = p.getStatoCorrente().stradaLibera(map.getStrade()[15]);
		risultato2 = p.getStatoCorrente().stradaLibera(map.getStrade()[16]);
		assertFalse(risultato1);
		assertTrue(risultato2);
	}
	
	@Test
	public void testStradaLibera2() throws EccClient { // controllo su una strada
														// occupata e una
		// libera
		boolean risultato1, risultato2;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);

		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		pastore.setPosizione(map.getStrade()[40]);
		risultato1 = p.getStatoCorrente().stradaLibera(map.getStrade()[40]);
		risultato2 = p.getStatoCorrente().stradaLibera(map.getStrade()[16]);
		assertFalse(risultato1);
		assertTrue(risultato2);
	}


	@Test
	public void testStradaLiberaDaRecinti1() throws EccClient {
		boolean risultato1 = true, risultato2 = false;
		Mappa map = Mappa.getMappa();

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Recinto r = new Recinto(map.getStrade()[15]);
		p.getStatoCorrente().registraRecinto(r);
		risultato1 = p.getStatoCorrente().stradaLiberaDaRecinti(
				map.getStrade()[15]);
		risultato2 = p.getStatoCorrente().stradaLiberaDaRecinti(
				map.getStrade()[16]);
		assertFalse(risultato1);
		assertTrue(risultato2);

	}
	
	@Test
	public void testStradaLiberaDaRecinti2() throws EccClient {
		boolean risultato1 = true, risultato2 = false;
		Mappa map = Mappa.getMappa();

		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore past=p.getStatoCorrente().getGiocatori().get(1).getPastori().get(0);
		past.setPosizione(map.getStrade()[40]);
		Recinto r = new Recinto(map.getStrade()[35]);
		p.getStatoCorrente().registraRecinto(r);
		risultato1 = p.getStatoCorrente().stradaLiberaDaRecinti(
				map.getStrade()[35]);
		risultato2 = p.getStatoCorrente().stradaLiberaDaRecinti(
				map.getStrade()[40]);
		assertFalse(risultato1);
		assertTrue(risultato2);

	}

	@Test
	public void testStradaPastore1() throws EccClient {
		boolean risultato1 = false;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Strada s = map.getStrade()[15];
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(0);
		pastore.setPosizione(s);
		risultato1 = p.getStatoCorrente().stradaPastore(s, pastore);
		assertTrue(risultato1);
	}

	
	@Test
	public void testStradaPastore2() throws EccClient {
		boolean risultato1 = false;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Strada s = map.getStrade()[38];
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(1)
				.getPastori().get(0);
		pastore.setPosizione(s);
		risultato1 = p.getStatoCorrente().stradaPastore(s, pastore);
		assertTrue(risultato1);
	}
	
	@Test
	public void testRegistraRecinto1() throws EccClient {
		boolean risultato1 = false;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Strada s = map.getStrade()[15];
		Recinto r = new Recinto(s);
		p.getStatoCorrente().registraRecinto(r);
		for (int i = 0; i < p.getStatoCorrente().getRecinti().size(); i++) {
			if (p.getStatoCorrente().getRecinti().get(i) == r) {
				risultato1 = true;
			}
		}
		assertTrue(risultato1);
	}

	@Test
	public void testRegistraRecinto2() throws EccClient {
		boolean risultato1 = false;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Strada s = map.getStrade()[40];
		Recinto r = new Recinto(s);
		p.getStatoCorrente().registraRecinto(r);
		for (int i = 0; i < p.getStatoCorrente().getRecinti().size(); i++) {
			if (p.getStatoCorrente().getRecinti().get(i) == r) {
				risultato1 = true;
			}
		}
		assertTrue(risultato1);
	}
}
