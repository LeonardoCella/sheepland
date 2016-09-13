package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * It is the class for the streets that form the map.
 * 
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 */
public class Strada extends Luogo {
	private final int codiceStrada;
	private final int codiceDado;
	private Regione regione1;
	private Regione regione2;

	public Strada(Regione regioneA, Regione regioneB, int cod, int dado) {
		this.regione1 = regioneA;
		this.regione2 = regioneB;
		this.codiceStrada = cod;
		this.codiceDado = dado;
	}

	@Override
	public int getCodice() {
		return this.codiceStrada;
	}

	public int getCodiceDado() {
		return this.codiceDado;
	}

	public Regione getRegione1() {
		return this.regione1;
	}

	public Regione getRegione2() {
		return this.regione2;
	}

	/**
	 * @param r
	 *            is the region that I want to verify if is adjacent to the
	 *            street.
	 * @return true if the region r is one of the two adjacent to the street,
	 *         otherwise false.
	 */
	public boolean verificaAdiacenza(Regione r) {
		return ((r == this.regione1) || (r == this.regione2)) ? true : false;
	}

	/**
	 * @return lands spletted by that region
	 */
	@Override
	public Luogo[] getAdiacenze() { 
		return new Regione[] { this.regione1, this.regione2 };
	}

	public Regione getConfinante(Regione regionePartenza)
			throws EccezioneNonAdiacenza {
		String strErrore = "La strada selezionata non separa la regione"
				+ regionePartenza.getCodice();
		if ((regionePartenza != this.regione1)
				&& (regionePartenza != this.regione2)) {
			throw new EccezioneNonAdiacenza(strErrore);
		}

		return (regionePartenza == this.regione1) ? this.regione2
				: this.regione1;
	}

	/**
	 * @return all the other streets that are linked to this.
	 */
	public Strada[] getStradeCollegate() {
		// Variabili di Appoggio:
		Regione a, b;
		Strada[] confiniRegione1, confiniRegione2;
		List<Regione> regioniComuni = new ArrayList<Regione>();
		Regione[] regioniConfinantiA, regioniConfinantiB;
		List<Strada> stradeAdiacenti = new ArrayList<Strada>(); // ritorno
																// della
																// funzione

		/*
		 * Istanzio a,b <- regioni separate dalla strada corrente
		 */
		a = this.regione1;
		b = this.regione2;

		/*
		 * STRADE Confini della regione a,b.
		 */
		confiniRegione1 = (Strada[]) a.getAdiacenze();
		confiniRegione2 = (Strada[]) b.getAdiacenze();

		/*
		 * Calcolo delle regioni confinanti alle due regioni iniziali
		 * confinantiA,B sono le REGIONI confinanti ad a-b.
		 */

		regioniConfinantiA = new Regione[confiniRegione1.length];
		regioniConfinantiB = new Regione[confiniRegione2.length];

		for (int i = 0; i < regioniConfinantiA.length; i++) {
			try {
				regioniConfinantiA[i] = confiniRegione1[i].getConfinante(a);
			} catch (EccezioneNonAdiacenza e) {
				MyLogger.gestisci("Non adiacenza", e);
			}
		}

		for (int j = 0; j < regioniConfinantiB.length; j++) {
			try {
				regioniConfinantiB[j] = confiniRegione2[j].getConfinante(b);
			} catch (EccezioneNonAdiacenza e) {
				MyLogger.gestisci("Non adiacenza", e);
			}
		}

		/*
		 * Calcolo delle REGIONI comuni tra quelle che confinano con
		 * regione1,regione2
		 */
		for (Regione regA : regioniConfinantiA) {
			for (Regione regB : regioniConfinantiB) {
				if (regA.equals(regB)) {
					regioniComuni.add(regA);
				}
			}
		}

		for (Luogo y : a.getAdiacenze()) {
			for (Regione x : regioniComuni) {
				for (Luogo z : x.getAdiacenze()) {
					if (z.equals(y)) {
						stradeAdiacenti.add((Strada) z);
					}
				}
			}
		}

		for (Luogo y : b.getAdiacenze()) {
			for (Regione x : regioniComuni) {
				for (Luogo z : x.getAdiacenze()) {
					if (z.equals(y)) {
						stradeAdiacenti.add((Strada) z);
					}
				}
			}
		}

		Strada[] ritorno = new Strada[stradeAdiacenti.size()];
		for (int l = 0; l < ritorno.length; l++) {
			ritorno[l] = stradeAdiacenti.get(l);
		}

		return ritorno;
	}

	@Override
	public String toString() {
		String ret = new String();

		ret += "\nstrada: " + this.codiceStrada + " dado: " + this.codiceDado;
		ret += "\nr1: " + this.regione1.getCodice() + " r2: "
				+ this.regione2.getCodice();

		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + codiceDado;
		result = (prime * result) + codiceStrada;
		result = (prime * result)
				+ ((regione1 == null) ? 0 : regione1.hashCode());
		result = (prime * result)
				+ ((regione2 == null) ? 0 : regione2.hashCode());
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
		Strada other = (Strada) obj;
		if (codiceDado != other.codiceDado) {
			return false;
		}
		if (codiceStrada != other.codiceStrada) {
			return false;
		}
		if (regione1 == null) {
			if (other.regione1 != null) {
				return false;
			}
		} else if (!regione1.equals(other.regione1)) {
			return false;
		}
		if (regione2 == null) {
			if (other.regione2 != null) {
				return false;
			}
		} else if (!regione2.equals(other.regione2)) {
			return false;
		}
		return true;
	}

}