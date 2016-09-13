package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAccoppiamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Stato;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MossaAccoppiamentoTest {

	@Test
	public void testEseguiMossa1() throws EccClient {
		Stato stato;
		MossaAccoppiamento mossa;
		int risultato = 0;
		boolean risultato2;
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
		stato = p.getStatoCorrente();
		ovini = stato.getOvini();
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1
		Ovino pecora = new Ovino(regioni[2], false);
		Ovino montone = new Ovino(regioni[2], true);
		stato.aggiungiOvino(pecora);
		stato.aggiungiOvino(montone);
		mossa = new MossaAccoppiamento(pastore1, regioni[2], p);

		risultato2 = mossa.eseguiMossaTest();

		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato++;
			}
		}
		assertTrue(risultato2);
		assertTrue(risultato == 4);
	}

	@Test
	public void testEseguiMossa2() throws EccClient {
		Stato stato;
		MossaAccoppiamento mossa;
		int risultato = 0;
		boolean risultato2;
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
		stato = p.getStatoCorrente();
		ovini = stato.getOvini();
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(strade[15]); // primo pastore del primo giocatore
											// in posizione 15
		Ovino pecora = new Ovino(regioni[2], false);
		Ovino montone = new Ovino(regioni[2], true);
		stato.aggiungiOvino(pecora);
		stato.aggiungiOvino(montone);
		mossa = new MossaAccoppiamento(pastore1, regioni[2], p);

		risultato2 = mossa.eseguiMossaTest();

		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato++;
			}
		}
		assertFalse(risultato2);
		assertTrue(risultato == 3);
	}
	
	@Test
	public void testEseguiMossa3() throws EccClient {
		Stato stato;
		MossaAccoppiamento mossa;
		int risultato = 0;
		boolean risultato2;
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
		stato = p.getStatoCorrente();
		ovini = stato.getOvini();
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1

		mossa = new MossaAccoppiamento(pastore1, regioni[2], p);

		risultato2 = mossa.eseguiMossaTest();

		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato++;
			}
		}
		assertFalse(risultato2);
		assertTrue(risultato == 1);
	}
	
	@Test
	public void testEseguiMossa4() throws EccClient {
		Stato stato;
		MossaAccoppiamento mossa;
		int risultato = 0;
		boolean risultato2;
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
		stato = p.getStatoCorrente();
		ovini = stato.getOvini();
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1

		mossa = new MossaAccoppiamento(pastore1, regioni[12], p);

		risultato2 = mossa.eseguiMossaTest();

		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato++;
			}
		}
		assertFalse(risultato2);
		assertTrue(risultato == 1);
	}

	@Test
	public void testEseguiMossa5() throws EccClient {
		Stato stato;
		MossaAccoppiamento mossa;
		int risultato = 0;
		boolean risultato2;
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
		stato = p.getStatoCorrente();
		ovini = stato.getOvini();
		Pastore pastore1;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore1.setPosizione(strade[1]); // primo pastore del primo giocatore
											// in posizione 1

		mossa = new MossaAccoppiamento(pastore1, regioni[6], p);

		risultato2 = mossa.eseguiMossaTest();

		for (int i = 0; i < ovini.size(); i++) {
			if (ovini.get(i).getPosizione() == regioni[2]) {
				risultato++;
			}
		}
		assertFalse(risultato2);
		assertTrue(risultato == 1);
	}
}
