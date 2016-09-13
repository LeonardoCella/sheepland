package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAbbattimento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MossaAbbattimentoTest {

	@Test
	public void testEseguiMossa1() throws EccClient {
		MossaAbbattimento mossa;
		boolean risultato = false, risultato2;
		Mappa map = Mappa.getMappa();
		List<Ovino> ovini;
		Strada strade[] = map.getStrade();
		Regione regioni[] = map.getRegioni();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		Pastore pastore1, pastore2, pastore3;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore3 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(1);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1
		pastore2.setPosizione(strade[5]); // primo pastore del secondo giocatore
											// in posizione 5
		pastore3.setPosizione(strade[6]); // secondo pastore del secondo
											// giocatore in posizione 6
		mossa = new MossaAbbattimento(pastore1, regioni[2], p);
		risultato2 = mossa.eseguiMossaTest();
		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato = true;
			}
		}
		assertTrue(risultato2);
		assertFalse(risultato);
		assertTrue(p.getStatoCorrente().getGiocatori().get(1).getSoldi() == 34);
		assertTrue(p.getStatoCorrente().getGiocatori().get(0).getSoldi() == 26);
	}

	@Test
	public void testEseguiMossa2() throws EccClient {
		MossaAbbattimento mossa;
		boolean risultato = false, risultato2;
		Mappa map = Mappa.getMappa();
		List<Ovino> ovini;
		Strada strade[] = map.getStrade();
		Regione regioni[] = map.getRegioni();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		Pastore pastore1, pastore2;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(1);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1
		pastore2.setPosizione(strade[5]); // primo pastore del secondo giocatore
											// in posizione 5
		mossa = new MossaAbbattimento(pastore1, regioni[2], p);
		risultato2 = mossa.eseguiMossaTest();
		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato = true;
			}
		}
		assertTrue(risultato2);
		assertFalse(risultato);
		assertTrue(p.getStatoCorrente().getGiocatori().get(0).getSoldi() == 30);
	}

	@Test
	public void testEseguiMossa3() throws EccClient {
		MossaAbbattimento mossa;
		boolean risultato = false, risultato2;
		Mappa map = Mappa.getMappa();
		List<Ovino> ovini;
		Strada strade[] = map.getStrade();
		Regione regioni[] = map.getRegioni();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
			canali.add(new ServerRMI("Giocatore3", "localhost"));
			canali.add(new ServerRMI("Giocatore4", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		Pastore pastore1, pastore2, pastore3, pastore4;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore3 = p.getStatoCorrente().getGiocatori().get(2).getPastori()
				.get(0);
		pastore4 = p.getStatoCorrente().getGiocatori().get(3).getPastori()
				.get(0);
		pastore1.setPosizione(strade[17]);
		pastore2.setPosizione(strade[10]);
		pastore3.setPosizione(strade[16]);
		pastore4.setPosizione(strade[18]);

		mossa = new MossaAbbattimento(pastore1, regioni[5], p);
		risultato2 = mossa.eseguiMossaTest();
		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[5]) {
				risultato = true;
			}
		}

		assertTrue(risultato2);
		assertFalse(risultato);
		assertTrue(p.getStatoCorrente().getGiocatori().get(1).getSoldi() == 22);
		assertTrue(p.getStatoCorrente().getGiocatori().get(0).getSoldi() == 14);
		assertTrue(p.getStatoCorrente().getGiocatori().get(2).getSoldi() == 22);
		assertTrue(p.getStatoCorrente().getGiocatori().get(3).getSoldi() == 22);
	}

	@Test
	public void testEseguiMossa4() throws EccClient {
		MossaAbbattimento mossa;
		boolean risultato = false, risultato2;
		Mappa map = Mappa.getMappa();
		List<Ovino> ovini;
		Strada strade[] = map.getStrade();
		Regione regioni[] = map.getRegioni();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
			canali.add(new ServerRMI("Giocatore3", "localhost"));
			canali.add(new ServerRMI("Giocatore4", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		Pastore pastore1, pastore2, pastore3, pastore4;
		p.getStatoCorrente().getGiocatori().get(0).paga(15); // il giocatore1 ha
																// 5, dovrebbe
																// pagare 6, la
																// mossa non va
																// eseguita
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore3 = p.getStatoCorrente().getGiocatori().get(2).getPastori()
				.get(0);
		pastore4 = p.getStatoCorrente().getGiocatori().get(3).getPastori()
				.get(0);
		pastore1.setPosizione(strade[17]);
		pastore2.setPosizione(strade[10]);
		pastore3.setPosizione(strade[16]);
		pastore4.setPosizione(strade[18]);

		mossa = new MossaAbbattimento(pastore1, regioni[5], p);
		risultato2 = mossa.eseguiMossaTest();
		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[5]) {
				risultato = true;
			}
		}

		assertFalse(risultato2);
		assertTrue(risultato);
		assertTrue(p.getStatoCorrente().getGiocatori().get(0).getSoldi() == 5);
		assertTrue(p.getStatoCorrente().getGiocatori().get(1).getSoldi() == 20);
		assertTrue(p.getStatoCorrente().getGiocatori().get(2).getSoldi() == 20);
		assertTrue(p.getStatoCorrente().getGiocatori().get(3).getSoldi() == 20);
	}

	@Test
	public void testEseguiMossa5() throws EccClient {
		MossaAbbattimento mossa;
		boolean risultato = false, risultato2;
		Mappa map = Mappa.getMappa();
		List<Ovino> ovini;
		Strada strade[] = map.getStrade();
		Regione regioni[] = map.getRegioni();
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
			canali.add(new ServerRMI("Giocatore3", "localhost"));
			canali.add(new ServerRMI("Giocatore4", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		ovini = p.getStatoCorrente().getOvini();
		Pastore pastore1, pastore2, pastore3, pastore4;
		p.getStatoCorrente().getGiocatori().get(0).paga(15); // il giocatore1 ha
																// 5, dovrebbe
																// pagare 6, la
																// mossa non va
																// eseguita
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore3 = p.getStatoCorrente().getGiocatori().get(2).getPastori()
				.get(0);
		pastore4 = p.getStatoCorrente().getGiocatori().get(3).getPastori()
				.get(0);
		pastore1.setPosizione(strade[17]);
		pastore2.setPosizione(strade[10]);
		pastore3.setPosizione(strade[16]);
		pastore4.setPosizione(strade[18]);

		mossa = new MossaAbbattimento(pastore1, regioni[9], p);
		risultato2 = mossa.eseguiMossaTest();
		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[5]) {
				risultato = true;
			}
		}

		assertFalse(risultato2);
		assertTrue(risultato);
		assertTrue(p.getStatoCorrente().getGiocatori().get(0).getSoldi() == 5);
		assertTrue(p.getStatoCorrente().getGiocatori().get(1).getSoldi() == 20);
		assertTrue(p.getStatoCorrente().getGiocatori().get(2).getSoldi() == 20);
		assertTrue(p.getStatoCorrente().getGiocatori().get(3).getSoldi() == 20);
	}

}
