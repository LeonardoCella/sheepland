package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class Mazzi {
	// Mazzi è un arraylist di arraylist
	private static final int NUMEROTERRENI = 6;

	private List<ArrayList<Tessera>> mazzi;

	/**
	 * It creates 6 decks of 5 cards, every deck contains cards of one type of
	 * land
	 */
	public Mazzi() {
		this.mazzi = new ArrayList<ArrayList<Tessera>>();
		// crea 6 mazzi cioè 6 arraylist, uno per ogni tipo di terreno
		for (int i = 0; i < NUMEROTERRENI; i++) {
			ArrayList<Tessera> al = new ArrayList<Tessera>();
			this.mazzi.add(al);
			// riempie ogni mazzo con 5 tessere di quel tipo
			for (int g = 0; g < 5; g++) {
				Tessera t = new Tessera(i, g);
				this.mazzi.get(i).add(t);
			}
		}
	}

	public List<ArrayList<Tessera>> getInstance() {
		return this.mazzi;
	}

	/**
	 * @param tipoTerreno
	 *            it is the type of the card that i want
	 * @return the card of type tipoTerreno at the top of the deck
	 */
	public Tessera pescaDalMazzo(int tipoTerreno) {
		// pesca la carta in cima al mazzo, cioè quella in posizione 0, e la
		// elimina dal mazzo dopo averla pescata
		Tessera t;
		t = this.mazzi.get(tipoTerreno).get(0);
		this.mazzi.get(tipoTerreno).remove(0);
		return t;
	}
}