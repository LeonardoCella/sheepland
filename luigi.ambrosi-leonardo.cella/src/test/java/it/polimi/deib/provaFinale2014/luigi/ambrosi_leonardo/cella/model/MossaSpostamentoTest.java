package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaSpostamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MossaSpostamentoTest {

	@Test
	public void testEseguiSpostamentoOvino1() throws EccClient {
		List<Ovino> ovini;
		Regione destinazione;
		Ovino ovino;
		MossaSpostamento mossa;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		ovino = ovini.get(0);
		destinazione = map.getRegioni()[1];
		mossa = new MossaSpostamento(ovino, destinazione, p);
		mossa.eseguiMossa();
		assertEquals(destinazione, ovino.getPosizione());
	}
	
	@Test
	public void testEseguiSpostamentoOvino2() throws EccClient {
		List<Ovino> ovini;
		Regione destinazione;
		Ovino ovino;
		MossaSpostamento mossa;
		Mappa map = Mappa.getMappa();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		ovino = ovini.get(9);
		destinazione = map.getRegioni()[13];
		mossa = new MossaSpostamento(ovino, destinazione, p);
		mossa.eseguiMossa();
		assertEquals(destinazione, ovino.getPosizione());
	}
	
	@Test
	public void testEseguiSpostamentoOvino3() throws EccClient {
		List<Ovino> ovini;
		Regione destinazione;
		Ovino ovino;
		MossaSpostamento mossa;
		Mappa map = Mappa.getMappa();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		ovino = ovini.get(15);
		destinazione = map.getRegioni()[14];
		mossa = new MossaSpostamento(ovino, destinazione, p);
		mossa.eseguiMossa();
		assertEquals(destinazione, ovino.getPosizione());
	}


	@Test
	public void testEseguiSpostamentoPastore1() throws EccClient {
		Recinto recinto;
		boolean risultato1, risultato2, risultato3, risultato4;
		Pastore pastore1, pastore2;
		int soldi1, soldi3;
		MossaSpostamento mossa1, mossa2, mossa3, mossa4;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		recinto = new Recinto(map.getStrade()[32]);
		p.getStatoCorrente().registraRecinto(recinto);
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[1]);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore2.setPosizione(map.getStrade()[25]);
		mossa1 = new MossaSpostamento(pastore1, map.getStrade()[5], p);
		risultato1 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		mossa2 = new MossaSpostamento(pastore1, map.getStrade()[25], p);
		risultato2 = mossa2.eseguiMossa();
		mossa3 = new MossaSpostamento(pastore1, map.getStrade()[26], p);
		risultato3 = mossa3.eseguiMossa();
		soldi3 = pastore1.getGiocatore().getSoldi();
		mossa4 = new MossaSpostamento(pastore1, map.getStrade()[32], p);
		risultato4 = mossa4.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertFalse(risultato4);
		assertTrue(soldi1 == 30);
		assertTrue(soldi3 == 29);
	}

	@Test
	public void testEseguiSpostamentoPastore2() throws EccClient {
		Recinto recinto;
		boolean risultato1, risultato2, risultato3, risultato4;
		Pastore pastore1, pastore2;
		int soldi1, soldi3;
		MossaSpostamento mossa1, mossa2, mossa3, mossa4;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		recinto = new Recinto(map.getStrade()[32]);
		p.getStatoCorrente().registraRecinto(recinto);
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[1]);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore2.setPosizione(map.getStrade()[26]);
		mossa1 = new MossaSpostamento(pastore1, map.getStrade()[5], p);
		risultato1 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		mossa2 = new MossaSpostamento(pastore1, map.getStrade()[26], p);
		risultato2 = mossa2.eseguiMossa();
		mossa3 = new MossaSpostamento(pastore1, map.getStrade()[27], p);
		risultato3 = mossa3.eseguiMossa();
		soldi3 = pastore1.getGiocatore().getSoldi();
		mossa4 = new MossaSpostamento(pastore1, map.getStrade()[32], p);
		risultato4 = mossa4.eseguiMossa();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(risultato3);
		assertFalse(risultato4);
		assertTrue(soldi1 == 30);
		assertTrue(soldi3 == 29);
	}
	
	@Test
	public void testEseguiSpostamentoPastore3() throws EccClient {
		Recinto recinto;
		boolean risultato1, risultato2, risultato3, risultato4;
		Pastore pastore1;
		int soldi;
		MossaSpostamento mossa1, mossa2, mossa3, mossa4;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		recinto = new Recinto(map.getStrade()[32]);
		p.getStatoCorrente().registraRecinto(recinto);
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[1]);
		mossa1 = new MossaSpostamento(pastore1, map.getStrade()[8], p);
		risultato1 = mossa1.eseguiMossa();
		mossa2 = new MossaSpostamento(pastore1, map.getStrade()[26], p);
		risultato2 = mossa2.eseguiMossa();
		mossa3 = new MossaSpostamento(pastore1, map.getStrade()[40], p);
		risultato3 = mossa3.eseguiMossa();
		mossa4 = new MossaSpostamento(pastore1, map.getStrade()[3], p);
		risultato4 = mossa4.eseguiMossa();
		soldi = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertTrue(risultato2);
		assertTrue(risultato3);
		assertTrue(risultato4);
		assertTrue(soldi == 26);
	}
	
	@Test
	public void testEseguiSpostamentoPastore4() throws EccClient {
		Recinto recinto;
		boolean risultato1, risultato2, risultato3, risultato4;
		Pastore pastore1;
		int soldi;
		MossaSpostamento mossa1, mossa2, mossa3, mossa4;
		Mappa map = Mappa.getMappa();

		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		recinto = new Recinto(map.getStrade()[32]);
		p.getStatoCorrente().registraRecinto(recinto);
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[1]);
		mossa1 = new MossaSpostamento(pastore1, map.getStrade()[5], p);
		risultato1 = mossa1.eseguiMossa();
		mossa2 = new MossaSpostamento(pastore1, map.getStrade()[4], p);
		risultato2 = mossa2.eseguiMossa();
		mossa3 = new MossaSpostamento(pastore1, map.getStrade()[3], p);
		risultato3 = mossa3.eseguiMossa();
		mossa4 = new MossaSpostamento(pastore1, map.getStrade()[9], p);
		risultato4 = mossa4.eseguiMossa();
		soldi = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertTrue(risultato2);
		assertTrue(risultato3);
		assertTrue(risultato4);
		assertTrue(soldi == 30);
	}

}
