package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi,Leonardo Cella
 * 
 * 
 */

public class Lupo {
	private Regione posizioneLupo;

	public Lupo(Regione r) {
		this.posizioneLupo = r;
	}

	public Regione getPosizione() {
		return this.posizioneLupo;
	}

	/**
	 * @param r
	 *            it is the new position of the wolf.
	 * @return true always.
	 */
	public boolean setPosizione(Regione r) {
		this.posizioneLupo = r;
		return true;
	}

}
