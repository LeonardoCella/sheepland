package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaPosizionamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class MossaPosizionamentoTest {

	@Test
	public void testEseguiMossa1() throws EccClient {
		MossaPosizionamento mossa1, mossa2, mossa3;
		boolean risultato1, risultato2, risultato3;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		mossa1 = new MossaPosizionamento(pastore1, map.getStrade()[19], p);
		mossa2 = new MossaPosizionamento(pastore2, map.getStrade()[19], p);
		mossa3 = new MossaPosizionamento(pastore2, map.getStrade()[20], p);
		risultato1 = mossa1.eseguiMossa();
		risultato2 = mossa2.eseguiMossa();
		risultato3 = mossa3.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertEquals(map.getStrade()[19], pastore1.getPosizione());
		assertEquals(map.getStrade()[20], pastore2.getPosizione());
	}

	@Test
	public void testEseguiMossa2() throws EccClient {
		MossaPosizionamento mossa1, mossa2, mossa3;
		boolean risultato1, risultato2, risultato3;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		mossa1 = new MossaPosizionamento(pastore1, map.getStrade()[30], p);
		mossa2 = new MossaPosizionamento(pastore2, map.getStrade()[30], p);
		mossa3 = new MossaPosizionamento(pastore2, map.getStrade()[40], p);
		risultato1 = mossa1.eseguiMossa();
		risultato2 = mossa2.eseguiMossa();
		risultato3 = mossa3.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertEquals(map.getStrade()[30], pastore1.getPosizione());
		assertEquals(map.getStrade()[40], pastore2.getPosizione());
	}
	
	@Test
	public void testEseguiMossa3() throws EccClient {
		MossaPosizionamento mossa1, mossa2, mossa3;
		boolean risultato1, risultato2, risultato3,risultato4;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		mossa1 = new MossaPosizionamento(pastore1, map.getStrade()[30], p);
		mossa2 = new MossaPosizionamento(pastore2, map.getStrade()[30], p);
		mossa3 = new MossaPosizionamento(pastore2, map.getStrade()[40], p);
		risultato1 = mossa1.eseguiMossa();
		risultato4=mossa1.eseguiMossa();
		risultato2 = mossa2.eseguiMossa();
		risultato3 = mossa3.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertFalse(risultato4);
		assertEquals(map.getStrade()[30], pastore1.getPosizione());
		assertEquals(map.getStrade()[40], pastore2.getPosizione());
	}
	
	@Test
	public void testEseguiMossa4() throws EccClient {
		MossaPosizionamento mossa1, mossa2, mossa3;
		boolean risultato1, risultato2, risultato3,risultato4;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		mossa1 = new MossaPosizionamento(pastore1, map.getStrade()[20], p);
		mossa2 = new MossaPosizionamento(pastore2, map.getStrade()[20], p);
		mossa3 = new MossaPosizionamento(pastore2, map.getStrade()[26], p);
		risultato1 = mossa1.eseguiMossa();
		risultato4=mossa1.eseguiMossa();
		risultato2 = mossa2.eseguiMossa();
		risultato3 = mossa3.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertFalse(risultato4);
		assertEquals(map.getStrade()[20], pastore1.getPosizione());
		assertEquals(map.getStrade()[26], pastore2.getPosizione());
	}
	
	@Test
	public void testEseguiMossa5() throws EccClient {
		MossaPosizionamento mossa1, mossa2, mossa3;
		boolean risultato1, risultato2, risultato3,risultato4;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		mossa1 = new MossaPosizionamento(pastore1, map.getStrade()[1], p);
		mossa2 = new MossaPosizionamento(pastore2, map.getStrade()[1], p);
		mossa3 = new MossaPosizionamento(pastore2, map.getStrade()[2], p);
		risultato1 = mossa1.eseguiMossa();
		risultato4=mossa1.eseguiMossa();
		risultato2 = mossa2.eseguiMossa();
		risultato3 = mossa3.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertFalse(risultato4);
		assertEquals(map.getStrade()[1], pastore1.getPosizione());
		assertEquals(map.getStrade()[2], pastore2.getPosizione());
	}
	
	
	
}
