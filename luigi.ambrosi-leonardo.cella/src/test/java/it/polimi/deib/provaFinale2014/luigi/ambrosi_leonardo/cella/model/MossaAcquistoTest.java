package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAcquisto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class MossaAcquistoTest {

	@Test
	public void testEseguiMossa1() throws EccClient {
		MossaAcquisto mossa1, mossa2;
		int soldi1, soldi2;
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
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[9]); // primo pastore del primo
													// giocatore
		// in posizione 9
		mossa1 = new MossaAcquisto(pastore1, Costanti.GRANO, p);
		risultato1 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		mossa2 = new MossaAcquisto(pastore1, Costanti.SABBIA, p);
		risultato2 = mossa2.eseguiMossa();
		soldi2 = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(soldi1 == 30);
		assertTrue(soldi2 == 30);
	}

	@Test
	public void testEseguiMossa2() throws EccClient {
		MossaAcquisto mossa1;
		int soldi1;
		boolean risultato1, risultato2, risultato3, risultato4, risultato5, risultato6;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[9]); // primo pastore del primo
													// giocatore
		// in posizione 9
		mossa1 = new MossaAcquisto(pastore1, Costanti.GRANO, p);
		risultato1 = mossa1.eseguiMossa();
		risultato2 = mossa1.eseguiMossa();
		risultato3 = mossa1.eseguiMossa();
		risultato4 = mossa1.eseguiMossa();
		risultato5 = mossa1.eseguiMossa();
		risultato6 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertTrue(risultato2);
		assertTrue(risultato3);
		assertTrue(risultato4);
		assertTrue(risultato5);
		assertFalse(risultato6);
		assertTrue(soldi1 == 20);
	}

	@Test
	public void testEseguiMossa3() throws EccClient {
		MossaAcquisto mossa1, mossa2;
		int soldi1, soldi2;
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
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[29]); // primo pastore del primo
													// giocatore
		// in posizione 9
		mossa1 = new MossaAcquisto(pastore1, Costanti.BOSCO, p);
		risultato1 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		mossa2 = new MossaAcquisto(pastore1, Costanti.SABBIA, p);
		risultato2 = mossa2.eseguiMossa();
		soldi2 = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(soldi1 == 30);
		assertTrue(soldi2 == 30);
	}
	
	@Test
	public void testEseguiMossa4() throws EccClient {
		MossaAcquisto mossa1, mossa2,mossa3;
		int soldi1, soldi2,soldi3;
		boolean risultato1, risultato3,risultato2;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[37]); // primo pastore del primo
													// giocatore
		// in posizione 9
		mossa1 = new MossaAcquisto(pastore1, Costanti.FIUMI, p);
		risultato1 = mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		mossa2 = new MossaAcquisto(pastore1, Costanti.SABBIA, p);
		risultato2 = mossa2.eseguiMossa();
		soldi2 = pastore1.getGiocatore().getSoldi();
		mossa3 = new MossaAcquisto(pastore1, Costanti.FIUMI, p);
		risultato3 = mossa3.eseguiMossa();
		soldi3 = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertTrue(risultato2);
		assertTrue(soldi1 == 30);
		assertTrue(soldi2 == 30);
		assertTrue(risultato3);
		assertTrue(soldi3==29);
	}
	
	@Test
	public void testEseguiMossa5() throws EccClient {
		MossaAcquisto mossa1;
		int soldi1;
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
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(map.getStrade()[37]); // primo pastore del primo
													// giocatore
		// in posizione 9
		mossa1 = new MossaAcquisto(pastore1, Costanti.FIUMI, p);
		mossa1.eseguiMossa();
		mossa1.eseguiMossa();
		mossa1.eseguiMossa();
		mossa1.eseguiMossa();
		risultato1=mossa1.eseguiMossa();
		risultato2=mossa1.eseguiMossa();
		soldi1 = pastore1.getGiocatore().getSoldi();
		assertTrue(risultato1);
		assertFalse(risultato2);
		assertTrue(soldi1 == 20);
	}
}
