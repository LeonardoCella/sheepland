package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.model;

import static org.junit.Assert.assertTrue;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Lupo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class StatoClientTest {

	@Test
	public void testScattaScreenshot1() throws EccClient {
		Recinto recinto;
		boolean controlloOvini = true;
		Lupo lupo;
		String msg1, msg2, msg3;
		msg1 = "1:0:0";
		msg2 = "0:1:0";
		msg3 = "0:0:1";
		String[] posOvini;
		StatoClient screenshot;
		Strada[] strade;
		Regione[] regioni;
		PecoraNera pn;
		;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		
		
		
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		screenshot = p.getScreenshot();
		regioni = map.getRegioni();
		strade = map.getStrade();
		recinto = new Recinto(strade[25]);
		p.getStatoCorrente().registraRecinto(recinto);
		Pastore pastore1, pastore2, pastore3, pastore4;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(1);
		pastore3 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore4 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(1);
		pastore1.setPosizione(strade[0]);
		pastore2.setPosizione(strade[1]);
		pastore3.setPosizione(strade[2]);
		pastore4.setPosizione(strade[3]);
		pn = p.getStatoCorrente().getNera();
		pn.muovi(regioni[0]);
		pastore1.getGiocatore().paga(10);
		lupo.setPosizione(regioni[1]);
		p.getGestore().scattaScreenshot(p);
		posOvini = screenshot.getOviniRegioni();
		for (int i = 0; i < 18; i++) {
			if (!posOvini[i].equals(msg1) && !posOvini[i].equals(msg2)
					&& !posOvini[i].equals(msg3)) {
				controlloOvini = false;
			}
		}
		assertTrue(screenshot.getPosizioneLupo() == 1);
		assertTrue(screenshot.getPosizioneNera() == 0);
		assertTrue(screenshot.getSoldiGiocatori()[0] == 20);
		assertTrue(screenshot.getSoldiGiocatori()[1] == 30);
		assertTrue(screenshot.getStradePastori()[0] == pastore1.getColore());
		assertTrue(screenshot.getStradePastori()[1] == pastore2.getColore());
		assertTrue(screenshot.getStradePastori()[2] == pastore3.getColore());
		assertTrue(screenshot.getStradePastori()[3] == pastore4.getColore());
		assertTrue(screenshot.getCostoTessera()[0] == 0);
		assertTrue(screenshot.getStradeRecinti()[25] == 1);
		assertTrue(controlloOvini);
	}

	@Test
	public void testScattaScreenshot2() throws EccClient {
		Recinto recinto;
		boolean controlloOvini = true;
		Lupo lupo;
		String msg1, msg2, msg3;
		msg1 = "1:0:0";
		msg2 = "0:1:0";
		msg3 = "0:0:1";
		String[] posOvini;
		StatoClient screenshot;
		Strada[] strade;
		Regione[] regioni;
		PecoraNera pn;
		;
		Mappa map = Mappa.getMappa();
		ArrayList<ICanaleServer> canali = new ArrayList<ICanaleServer>();
		try {
			canali.add(new ServerRMI("Giocatore1", "localhost"));
			canali.add(new ServerRMI("Giocatore2", "localhost"));
		} catch (IOException e1) {
			System.out.println("Errore in RMI");
		}
		
		
		
		Partita p = new Partita(map, canali, null, 0, true);
		lupo = p.getStatoCorrente().getLupo();
		screenshot = p.getScreenshot();
		regioni = map.getRegioni();
		strade = map.getStrade();
		recinto = new Recinto(strade[25]);
		p.getStatoCorrente().registraRecinto(recinto);
		Pastore pastore1, pastore2, pastore3, pastore4;
		pastore1 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(0);
		pastore2 = p.getStatoCorrente().getGiocatori().get(0).getPastori()
				.get(1);
		pastore3 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(0);
		pastore4 = p.getStatoCorrente().getGiocatori().get(1).getPastori()
				.get(1);
		pastore1.setPosizione(strade[0]);
		pastore2.setPosizione(strade[1]);
		pastore3.setPosizione(strade[2]);
		pastore4.setPosizione(strade[3]);
		pn = p.getStatoCorrente().getNera();
		pn.muovi(regioni[12]);
		pastore1.getGiocatore().paga(13);
		lupo.setPosizione(regioni[7]);
		p.getGestore().scattaScreenshot(p);
		posOvini = screenshot.getOviniRegioni();
		for (int i = 1; i < 18; i++) {
			if (!posOvini[i].equals(msg1) && !posOvini[i].equals(msg2)
					&& !posOvini[i].equals(msg3)) {
				controlloOvini = false;
			}
		}
		assertTrue(screenshot.getPosizioneLupo() == 7);
		assertTrue(screenshot.getPosizioneNera() == 12);
		assertTrue(screenshot.getSoldiGiocatori()[0] == 17);
		assertTrue(screenshot.getSoldiGiocatori()[1] == 30);
		assertTrue(screenshot.getStradePastori()[0] == pastore1.getColore());
		assertTrue(screenshot.getStradePastori()[1] == pastore2.getColore());
		assertTrue(screenshot.getStradePastori()[2] == pastore3.getColore());
		assertTrue(screenshot.getStradePastori()[3] == pastore4.getColore());
		assertTrue(screenshot.getCostoTessera()[0] == 0);
		assertTrue(screenshot.getStradeRecinti()[25] == 1);
		assertTrue(controlloOvini);
	}
}
