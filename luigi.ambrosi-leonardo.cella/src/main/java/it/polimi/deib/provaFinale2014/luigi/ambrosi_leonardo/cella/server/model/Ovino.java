package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class Ovino extends Soggetto {

	private Regione posizioneCorrente;
	private Boolean maschio, agnello;
	private int eta;

	/**
	 * It creates a sheep. It can randomly be a sheep, a ram or a lamb.
	 * 
	 * @param r
	 *            is the region where I want to put the sheep
	 */
	public Ovino(Regione r) {
		int i;
		this.posizioneCorrente = r;
		i = (int) Math.floor(((Math.random()) * 9) + 1);

		if ((i % 3) == 0) {
			this.maschio = true;
			this.agnello = false;
			this.eta = 3;
		} else if ((i % 3) == 1) {
			this.maschio = false;
			this.agnello = false;
			this.eta = 3;
		} else if ((i % 3) == 2) {
			this.agnello = true;
			this.eta = 0;
		}
	}

	/**
	 * It creates a sheep or a ram. I use this for tests.
	 * 
	 * @param r
	 *            is the region where I want to put the sheep
	 * @param m
	 *            is the sex of the sheep. True if I want a ram, false if I want
	 *            a sheep
	 */
	public Ovino(Regione r, boolean m) {
		this.posizioneCorrente = r;
		this.agnello = false;
		this.maschio = m;
	}

	public void setAgnello() {
		this.agnello = true;
		this.eta = 0;
	}

	public void aumentaEta() {
		this.eta++;
	}

	/**
	 * The lamb becomes randomly a ram or a sheep.
	 */
	public void setDiventaGrande() {
		double i;
		this.agnello = false;
		i = (int) Math.floor(((Math.random()) * 10) + 1);
		if ((i % 2) == 0) {
			this.maschio = true;
			this.eta = 3;
		} else {
			this.maschio = false;
			this.eta = 3;
		}
	}

	public boolean getMaschio() {
		return this.maschio;
	}

	/**
	 * @return true if the sheep is not a lamb, otherwise false.
	 */
	public boolean verificaMaggioreEta() {
		return !this.agnello;
	}

	public int getEta() {
		return this.eta;
	}

	public Regione getPosizione() {
		return this.posizioneCorrente;
	}

	@Override
	public boolean muovi(Luogo luogo) {
		Regione destinazione = (Regione) luogo;
		this.posizioneCorrente = destinazione;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((agnello == null) ? 0 : agnello.hashCode());
		result = (prime * result) + eta;
		result = (prime * result)
				+ ((maschio == null) ? 0 : maschio.hashCode());
		result = (prime * result)
				+ ((posizioneCorrente == null) ? 0 : posizioneCorrente
						.hashCode());
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
		Ovino other = (Ovino) obj;
		if (agnello == null) {
			if (other.agnello != null) {
				return false;
			}
		} else if (!agnello.equals(other.agnello)) {
			return false;
		}
		if (eta != other.eta) {
			return false;
		}
		if (maschio == null) {
			if (other.maschio != null) {
				return false;
			}
		} else if (!maschio.equals(other.maschio)) {
			return false;
		}
		if (posizioneCorrente == null) {
			if (other.posizioneCorrente != null) {
				return false;
			}
		} else if (!posizioneCorrente.equals(other.posizioneCorrente)) {
			return false;
		}
		return true;
	}

}
