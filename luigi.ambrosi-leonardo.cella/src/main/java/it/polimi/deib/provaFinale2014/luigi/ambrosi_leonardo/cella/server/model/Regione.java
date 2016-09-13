package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import java.util.Arrays;

/**
 * It is the class for the regions that form the map.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class Regione extends Luogo {
	private final int codiceRegione;
	private final int tipoTerreno;
	private Strada[] confini;

	public Regione(int codice, int terreno) {
		this.tipoTerreno = terreno;
		this.codiceRegione = codice;
	}

	public int getTipoTerreno() {
		return this.tipoTerreno;
	}

	@Override
	public int getCodice() {
		return this.codiceRegione;
	}

	/**
	 * @return all the street that surround the region.
	 */
	@Override
	public Luogo[] getAdiacenze() {
		// ritorna le strade che circondano la regione
		return this.confini;
	}

	public void setConfini(Strada[] strade) {
		if (strade == null) {
			this.confini = new Strada[0];
		} else {
			this.confini = Arrays.copyOf(strade, strade.length);
		}
	}

	@Override
	public String toString() {
		String str = new String();
		str += "\nreg: " + this.codiceRegione + " terr: " + this.tipoTerreno
				+ "\nconf:";

		for (Strada x : this.confini) {
			str += " " + x.getCodice();
		}
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + codiceRegione;
		result = (prime * result) + Arrays.hashCode(confini);
		result = (prime * result) + tipoTerreno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Regione other = (Regione) obj;
		if (codiceRegione != other.codiceRegione) {
			return false;
		}
		if (!Arrays.equals(confini, other.confini)) {
			return false;
		}
		if (tipoTerreno != other.tipoTerreno) {
			return false;
		}
		return true;
	}

}
