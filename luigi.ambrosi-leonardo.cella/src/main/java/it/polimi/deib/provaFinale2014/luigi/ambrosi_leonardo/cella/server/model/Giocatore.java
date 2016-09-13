package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         The player
 */
public class Giocatore {
	private List<Tessera> possedimenti;
	private int soldi;
	private List<Pastore> pastori;
	private ICanaleServer canale;
	private int indiceNellaPartita;

	/**
	 * @param pa
	 *            the match of reference
	 * @param can
	 *            the connection channel of reference
	 * @param colorePastore1
	 *            the colour of the first shepherd
	 * @param colorePastore2
	 *            the colour of the second shepherd
	 * @param indice
	 *            the index in the group of all the players of that match
	 */
	public Giocatore(ICanaleServer can, int colorePastore1, int colorePastore2,
			int indice) {
		// costruttore per partite a 2 giocatori
		this.pastori = new ArrayList<Pastore>();
		this.pastori.add(new Pastore(colorePastore1));
		this.pastori.add(new Pastore(colorePastore2));
		this.pastori.get(0).setGiocatore(this);
		this.pastori.get(1).setGiocatore(this);
		this.soldi = 30;
		this.canale = can;
		this.possedimenti = new ArrayList<Tessera>();
		this.indiceNellaPartita = indice;
	}

	/**
	 * @param pa
	 *            the match of reference
	 * @param can
	 *            the connection channel of reference
	 * @param colorePastore
	 *            the colour of the first shepherd
	 * @param indice
	 *            the index in the group of all the players of that match
	 */
	public Giocatore(ICanaleServer can, int colorePastore, int indice) {
		// costruttore per partite con più di due giocatori
		this.pastori = new ArrayList<Pastore>();
		this.pastori.add(new Pastore(colorePastore));
		this.pastori.get(0).setGiocatore(this);
		this.soldi = 20;
		this.canale = can;
		this.possedimenti = new ArrayList<Tessera>();
		this.indiceNellaPartita = indice;
	}

	public int getIndiceNellaPartita() {
		return this.indiceNellaPartita;
	}

	public List<Pastore> getPastori() {
		return this.pastori;
	}

	public List<Tessera> getPossedimenti() {
		return this.possedimenti;
	}

	public void aggiungiTessera(Tessera t) {
		this.possedimenti.add(t);
	}

	/**
	 * @param costo
	 *            the amount of money to pay
	 * @return the rest of money after payment
	 */
	public int paga(int costo) {
		this.soldi = this.soldi - costo;
		return this.soldi;
	}

	public int getSoldi() {
		return this.soldi;
	}

	public ICanaleServer getCanale() {
		return this.canale;
	}
}
