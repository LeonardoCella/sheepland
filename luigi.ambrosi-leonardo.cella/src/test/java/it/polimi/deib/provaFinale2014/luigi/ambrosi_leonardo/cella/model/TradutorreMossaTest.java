package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Mossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAbbattimento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAccoppiamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaAcquisto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaPosizionamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MossaSpostamento;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TradutorreMossaTest {

	@Test
	public void testTraduciMessaggioMossa1() throws EccClient { // creo una
																// mossa
																// posizionamento
		// iniziale di un pastore su una
		// strada libera

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(0, 2, 15);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];
		mossaAttesa = new MossaPosizionamento(pastore, strada, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);

	}

	@Test
	public void testTraduciMessaggioMossa2() throws EccClient { // creo una
																// mossa
																// posizionamento
		// iniziale di un pastore che
		// non esiste

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null;

		MessaggioMossa msg = new MessaggioMossa(0, 17, 15);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertNull(mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa3() throws EccClient { // creo una
																// mossa
																// spostamento
		// pastore

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(1, 2, 15);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];

		mossaAttesa = new MossaSpostamento(pastore, strada, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);

	}

	@Test
	public void testTraduciMessaggioMossa4() throws EccClient { // creo una
																// mossa
																// spostamento
		// pecora bianca che può essere
		// eseguita

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(2, 2, 3);
		msg.setMuoviNera(0);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];
		pastore.setPosizione(strada);
		Regione destinazione = map.getRegioni()[3];
		Regione partenza = map.getRegioni()[4];
		Ovino pecora = null;
		List<Ovino> pecore = p.getStatoCorrente().getOvini();
		for (int i = 0; i < pecore.size(); i++) {
			if (pecore.get(i).getPosizione().equals(partenza)) {
				pecora = pecore.get(i);
			}
		}
		mossaAttesa = new MossaSpostamento(pecora, destinazione, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa5() throws EccClient { // creo una
																// mossa
																// spostamento
		// pecora bianca verso una
		// regione che
		// non confina con la strada su
		// cui è il pastore, quindi il
		// traduttore ritorna null

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null;

		MessaggioMossa msg = new MessaggioMossa(2, 2, 3);
		msg.setMuoviNera(0);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[32];
		pastore.setPosizione(strada);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertNull(mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa6() throws EccClient { // creo una
																// mossa
																// spostamento
		// pecora bianca, ma nella
		// regione di partenza non ci
		// sono pecore, deve ritornare
		// null

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null;

		MessaggioMossa msg = new MessaggioMossa(2, 2, 3);
		msg.setMuoviNera(0);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];
		pastore.setPosizione(strada);
		Regione partenza = map.getRegioni()[4];
		List<Ovino> pecore = p.getStatoCorrente().getOvini();
		for (int i = 0; i < pecore.size(); i++) {
			if (pecore.get(i).getPosizione().equals(partenza)) {
				pecore.get(i).muovi(map.getRegioni()[7]);
			}
		}

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertNull(mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa7() throws EccClient { // creo una
																// mossa
																// spostamento
		// pecora nera che puo essere
		// eseguito

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(2, 2, 4);
		msg.setMuoviNera(1);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[16];
		pastore.setPosizione(strada);
		PecoraNera pecoraNera = p.getStatoCorrente().getNera();
		mossaAttesa = new MossaSpostamento(pecoraNera, map.getRegioni()[4], p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa8() throws EccClient { // creo una
																// mossa
																// acquisto
		// terreno

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(3, 2, 4);
		msg.setMuoviNera(1);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[16];
		pastore.setPosizione(strada);
		mossaAttesa = new MossaAcquisto(pastore, 4, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa9() throws EccClient { // creo una
																// mossa
																// spostamento
		// accoppiamento

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(4, 2, 3);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];
		pastore.setPosizione(strada);
		Regione regioneAbbattimento = map.getRegioni()[3];
		mossaAttesa = new MossaAccoppiamento(pastore, regioneAbbattimento, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa10() throws EccClient { // creo una
																	// mossa
																	// spostamento
		// abbattimento pecora

		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null, mossaAttesa;

		MessaggioMossa msg = new MessaggioMossa(5, 2, 3);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[15];
		pastore.setPosizione(strada);
		Regione regioneAbbattimento = map.getRegioni()[3];
		mossaAttesa = new MossaAbbattimento(pastore, regioneAbbattimento, p);

		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertEquals(mossaAttesa, mossaTradotta);
	}

	@Test
	public void testTraduciMessaggioMossa11() throws EccClient { 
		
		Mappa map = Mappa.getMappa();

		Mossa mossaTradotta = null;

		MessaggioMossa msg = new MessaggioMossa(2, 2, 18);
		msg.setMuoviNera(0);
		List<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		Partita p = new Partita(map, canali, null, 0, true);
		Pastore pastore = p.getStatoCorrente().getGiocatori().get(0)
				.getPastori().get(1);
		Strada strada = map.getStrade()[16];
		pastore.setPosizione(strada);
	
		mossaTradotta = p.getTraduttore().traduciMessaggioMossa(msg);

		assertNull(mossaTradotta);
	}
}
