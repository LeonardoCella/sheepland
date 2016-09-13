package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Lupo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class LupoTest {

	@Test
	public void testMuoviDeterministico1() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[16])); // occupo
																		// 1
																		// strada
																		// su 6,
																		// metto
																		// l'indice
																		// di
																		// una
																		// libera,
																		// il
																		// lupo
																		// può
																		// muoversi
		risultato = p.getStatoCorrente().getGestoreLupo().muoviDeterministico(3);
		Regione r = map.getRegioni()[11];
		assertTrue(risultato);
		assertEquals(r, lupo.getPosizione());
	}

	@Test
	public void testMuoviDeterministico2() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[16])); // occupo
																		// 5
																		// strade
																		// su 6,
																		// metto
																		// l'indice
																		// di
																		// una
																		// occupata,
																		// il
																		// lupo
																		// non
																		// deve
																		// saltare
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[17]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[18]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[21]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[22]));
		risultato =  p.getStatoCorrente().getGestoreLupo().muoviDeterministico(3);
		Regione r = map.getRegioni()[18];
		assertFalse(risultato);
		assertEquals(r, lupo.getPosizione());
	}

	@Test
	public void testMuoviDeterministico3() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[16])); // occupo
																		// 6
																		// strade
																		// su 6,
																		// metto
																		// l'indice
																		// di
																		// una
																		// occupata,
																		// il
																		// lupo
																		// deve
																		// saltare
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[17]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[18]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[21]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[22]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[26]));
		Regione r = map.getRegioni()[11];
		risultato = p.getStatoCorrente().getGestoreLupo().muoviDeterministico(3);
		assertTrue(risultato);
		assertEquals(r, lupo.getPosizione());
	}
	
	@Test
	public void testMuoviDeterministico4() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[16])); // occupo
																		// 5
																		// strade
																		// su 6,
																		// metto
																		// l'indice
																		// di
																		// una
																		// occupata,
																		// il
																		// lupo
																		// non
																		// deve
																		// saltare
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[17]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[18]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[21]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[26]));
		risultato =  p.getStatoCorrente().getGestoreLupo().muoviDeterministico(6);
		Regione r = map.getRegioni()[18];
		assertFalse(risultato);
		assertEquals(r, lupo.getPosizione());
	}
	
	@Test
	public void testMuoviDeterministico5() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		lupo.setPosizione(map.getRegioni()[1]);
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[0]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[2]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[3]));
		risultato =  p.getStatoCorrente().getGestoreLupo().muoviDeterministico(4);
		Regione r = map.getRegioni()[1];
		assertFalse(risultato);
		assertEquals(r, lupo.getPosizione());
	}

	
	@Test
	public void testMuoviDeterministico6() throws EccClient {
		Lupo lupo;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		Strada strade[] = map.getStrade();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		lupo.setPosizione(map.getRegioni()[1]);
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[0])); // occupo
																		// 4
																		// strade
																		// su 4,
																		// metto
																		// l'indice
																		// di
																		// una
																		// occupata,
																		// il
																		// lupo
																		// deve
																		// saltare
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[2]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[3]));
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[4]));
		Regione r = map.getRegioni()[4];
		risultato = p.getStatoCorrente().getGestoreLupo().muoviDeterministico(4);
		assertTrue(risultato);
		assertEquals(r, lupo.getPosizione());
	}
	
	@Test
	public void testMangia1() throws EccClient { // provo a mangiare due volte
												// dalla stessa
		// regione, la prima volta il risultato sarà
		// true perchè ci sarà una pecora, la seconda
		// false

		boolean risultato1, risultato2;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		
		p.getStatoCorrente().getGestoreLupo().muoviDeterministico(3);
		risultato1 = p.getStatoCorrente().getGestoreLupo().mangia();
		risultato2 = p.getStatoCorrente().getGestoreLupo().mangia();
		assertTrue(risultato1);
		assertFalse(risultato2);

	}
	
	@Test
	public void testMangia2() throws EccClient { // provo a mangiare due volte
												// dalla stessa
		// regione, la prima volta il risultato sarà
		// true perchè ci sarà una pecora, la seconda
		// false

		boolean risultato1, risultato2;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		
		p.getStatoCorrente().getGestoreLupo().muoviDeterministico(6);
		risultato1 = p.getStatoCorrente().getGestoreLupo().mangia();
		risultato2 = p.getStatoCorrente().getGestoreLupo().mangia();
		assertTrue(risultato1);
		assertFalse(risultato2);

	}

}
