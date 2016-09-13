package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class PecoraNeraTest {

	@Test
	public void testMuoviDeterministico1() throws EccClient { // provo a muovere
																// la nera su
																// una
		// strada con un recinto, non
		// deve muoversi
		PecoraNera pn;
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
		pn = p.getStatoCorrente().getNera();
		p.getStatoCorrente().getRecinti().add(new Recinto(strade[22]));
		risultato = p.getStatoCorrente().getGestoreNera().muoviDeterministico(3);
		Regione r = map.getRegioni()[18];
		assertFalse(risultato);
		assertEquals(r, pn.getPosizione());
	}

	@Test
	public void testMuoviDeterministico2() throws EccClient { // provo a muovere
																// la nera su
																// una
		// strada con un pastore, non
		// deve muoversi
		PecoraNera pn;
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
		pn = p.getStatoCorrente().getNera();
		p.getStatoCorrente().getGiocatori().get(0).getPastori().get(0)
				.setPosizione(strade[22]);
		risultato = p.getStatoCorrente().getGestoreNera().muoviDeterministico(3);
		Regione r = map.getRegioni()[18];
		assertFalse(risultato);
		assertEquals(r, pn.getPosizione());
	}

	@Test
	public void testMuoviDeterministico3() throws EccClient { // provo a muovere
																// la nera su
																// una
		// strada libera, deve muoversi
		PecoraNera pn;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		pn = p.getStatoCorrente().getNera();
		risultato = p.getStatoCorrente().getGestoreNera().muoviDeterministico(3);
		Regione r = map.getRegioni()[11];
		assertTrue(risultato);
		assertEquals(r, pn.getPosizione());
	}
	
	@Test
	public void testMuoviDeterministico4() throws EccClient { // provo a muovere
																// la nera su
																// una
		// strada libera, deve muoversi
		PecoraNera pn;
		boolean risultato;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		pn = p.getStatoCorrente().getNera();
		risultato = p.getStatoCorrente().getGestoreNera().muoviDeterministico(6);
		Regione r = map.getRegioni()[4];
		assertTrue(risultato);
		assertEquals(r, pn.getPosizione());
	}
	
	@Test
	public void testMuoviDeterministico5() throws EccClient { // provo a muovere
																// la nera su
																// una
		// strada con un pastore, non
		// deve muoversi
		PecoraNera pn;
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
		pn = p.getStatoCorrente().getNera();
		p.getStatoCorrente().getGiocatori().get(1).getPastori().get(0)
				.setPosizione(strade[17]);
		risultato = p.getStatoCorrente().getGestoreNera().muoviDeterministico(1);
		Regione r = map.getRegioni()[18];
		assertFalse(risultato);
		assertEquals(r, pn.getPosizione());
	}

}
