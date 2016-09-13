package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Costanti;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Lupo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mappa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Mazzi;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Tessera;

import java.util.ArrayList;
import java.util.List;

/**
 * It is the consistent state of the match: in every moments it contains all the
 * informations about it.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class Stato {
	// lo stato della partita presente sul server
	private Mappa mappa;
	// la mappa mi serve come riferimento per accedere alle regioni e alle
	// strade in quanto oggetti concreti, devono essere tutti riferimenti a
	// oggetti consistenti
	private Partita partitaRiferimento;
	private List<Giocatore> giocatori;
	private List<Ovino> gregge;
	private List<Recinto> recinti;
	private List<Recinto> recintiFinali;
	private PecoraNera pecoraNera;
	private Lupo lupo;
	private Mazzi tessere;
	private GestoreLupo gl;
	private GestoreNera gn;

	/**
	 * It is the constructor for the state in a match with only two players.
	 * 
	 * @param m
	 *            the map of the match
	 * @param gestore1
	 *            the channel of communication of the first player.
	 * @param gestore2
	 *            the channel of communication of the secondo player.
	 * @param p
	 *            the match of reference.
	 */
	public Stato(Mappa m, ICanaleServer gestore1, ICanaleServer gestore2,
			Partita p) {
		// caso con due soli giocatori
		this.partitaRiferimento = p;
		this.giocatori = new ArrayList<Giocatore>();
		Giocatore giocatore1 = new Giocatore(gestore1, Costanti.COLORE[1],
				Costanti.COLORE[2], 0);
		Giocatore giocatore2 = new Giocatore(gestore2, Costanti.COLORE[3],
				Costanti.COLORE[4], 1);
		giocatore1.aggiungiTessera(new Tessera(0, 0));
		giocatore2.aggiungiTessera(new Tessera(1, 0));
		this.giocatori.add(giocatore1);
		this.giocatori.add(giocatore2);

		this.mappa = m;
		this.gregge = new ArrayList<Ovino>();
		this.inizializzaOvini();
		this.recinti = new ArrayList<Recinto>();
		this.recintiFinali = new ArrayList<Recinto>();
		this.pecoraNera = new PecoraNera(p);
		this.lupo = new Lupo(m.getRegioni()[18]);
		this.tessere = new Mazzi();
		this.gl = new GestoreLupo(lupo, this);
		this.gn = new GestoreNera(pecoraNera, this);
	}

	/**
	 * It is the constructor for the state in a match with more than two
	 * players.
	 * 
	 * @param m
	 *            the map of the match
	 * @param gestori
	 * @param p
	 */
	public Stato(Mappa m, List<ICanaleServer> gestori, Partita p) {
		// caso con più di due giocatori
		this.partitaRiferimento = p;
		int numGiocatori = gestori.size();
		this.giocatori = new ArrayList<Giocatore>();
		for (int i = 0; i < numGiocatori; i++) {
			Giocatore nuovoGiocatore = new Giocatore(gestori.get(i),
					Costanti.COLORE[i + 1], i);
			nuovoGiocatore.aggiungiTessera(new Tessera(i, 0));
			this.giocatori.add(nuovoGiocatore);
		}

		this.mappa = m;
		this.gregge = new ArrayList<Ovino>();
		this.inizializzaOvini();
		this.recinti = new ArrayList<Recinto>();
		this.recintiFinali = new ArrayList<Recinto>();
		this.pecoraNera = new PecoraNera(p);
		this.tessere = new Mazzi();
		this.lupo = new Lupo(m.getRegioni()[18]);
		this.gl = new GestoreLupo(lupo, this);
		this.gn = new GestoreNera(pecoraNera, this);
	}

	/**
	 * It puts one sheep in every region.
	 */
	private void inizializzaOvini() {
		// creo un arraylist di 18 pecore, una in ogni regione tranne in
		// Sheepsburg
		for (int i = 0; i < 18; i++) {
			Ovino nuovoOvino = new Ovino(this.mappa.getRegioni()[i]);
			this.gregge.add(nuovoOvino);
		}
	}

	public List<Giocatore> getGiocatori() {
		return this.giocatori;
	}

	public List<Ovino> getOvini() {
		return this.gregge;
	}

	public List<Recinto> getRecinti() {
		return this.recinti;
	}

	public List<Recinto> getRecintiFinali() {
		return this.recintiFinali;
	}

	public PecoraNera getNera() {
		return this.pecoraNera;
	}

	public Mazzi getCarte() {
		return this.tessere;
	}

	/**
	 * @param s
	 *            is the street that I want to know about if it is free or not.
	 * @return true if the street is free from a shepherd or a fence, otherwise
	 *         false
	 */
	public boolean stradaLibera(Strada s) {
		for (int i = 0; i < this.recinti.size(); i++) {
			if (this.recinti.get(i).getPoSizione() == s) {
				return false;
			}
		}

		for (int i = 0; i < this.recintiFinali.size(); i++) {
			if (this.recintiFinali.get(i).getPoSizione() == s) {
				return false;
			}
		}

		for (int i = 0; i < this.giocatori.size(); i++) {
			for (int j = 0; j < this.giocatori.get(i).getPastori().size(); j++) {
				if (this.giocatori.get(i).getPastori().get(j).getPosizione() == s) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * @param s
	 *            is the street that I want to know about if it is free or not.
	 * @return true if the street is free from a fence, otherwise false.
	 */
	public boolean stradaLiberaDaRecinti(Strada s) {
		for (int i = 0; i < this.recinti.size(); i++) {
			if (this.recinti.get(i).getPoSizione() == s) {
				return false;
			}
		}

		for (int i = 0; i < this.recintiFinali.size(); i++) {
			if (this.recintiFinali.get(i).getPoSizione() == s) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param s
	 *            the street that I want to verify about.
	 * @param p
	 *            the shepherd that I want to verify about.
	 * @return true if the shepherd p is on the street s, otherwise false.
	 */
	public boolean stradaPastore(Strada s, Pastore p) {
		for (int i = 0; i < this.giocatori.size(); i++) {
			for (int j = 0; j < this.giocatori.get(i).getPastori().size(); j++) {
				if (this.giocatori.get(i).getPastori().get(j).getPosizione() == s) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param o
	 *            the sheep that I want to add in the match
	 */
	public void aggiungiOvino(Ovino o) {
		this.gregge.add(o);
	}

	public Lupo getLupo() {
		return this.lupo;
	}

	/**
	 * It adds a normal fence if the complessive number of them is minor than
	 * 20, otherwise it adds a final fence.
	 * 
	 * @param r
	 *            is the fence that I want to add in the mathc.
	 */
	public void registraRecinto(Recinto r) {
		if (this.recinti.size() < 20) {
			this.recinti.add(r);
		} else {
			this.recintiFinali.add(r);
		}
	}

	public GestoreLupo getGestoreLupo() {
		return this.gl;
	}

	public GestoreNera getGestoreNera() {
		return this.gn;
	}

	public Partita getPartita() {
		return this.partitaRiferimento;
	}

}
