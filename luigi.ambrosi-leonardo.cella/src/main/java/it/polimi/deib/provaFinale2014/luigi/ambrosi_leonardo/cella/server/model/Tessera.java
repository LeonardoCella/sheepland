package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the card of a territory. It has a type and a price.
 */
public class Tessera {
	private final int tipoTerreno;
	private int costo;

	public Tessera(int s, int c) {
		this.tipoTerreno = s;
		this.costo = c;
	}

	public int getTipoTerreno() {
		return this.tipoTerreno;
	}

	public int getCosto() {
		return this.costo;
	}

}
