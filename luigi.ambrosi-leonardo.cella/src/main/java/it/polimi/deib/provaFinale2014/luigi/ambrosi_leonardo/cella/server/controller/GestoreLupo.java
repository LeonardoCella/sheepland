package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import java.util.List;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.EccezioneNonAdiacenza;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Lupo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

public class GestoreLupo {
	private Lupo lupo;
	private Stato statoPartita;

	public GestoreLupo(Lupo l, Stato s) {
		this.lupo = l;
		this.statoPartita = s;
	}

	/**
	 * It moves the wolf randomly in a adjacent land.
	 * 
	 * @return true if the move is valid, otherwise false.
	 */
	public boolean muovi() {
		// ritorna true se la nera si muove, false altrimenti
		Regione regioneDestinazione = null;
		// genero un numero casuale intero tra 1 e 6
		int dado = (int) Math.floor(((Math.random()) * 6) + 1);
		// strade contiene tutte le strade che circondano la regione in cui si
		// trova il lupo
		Strada[] strade = (Strada[]) lupo.getPosizione().getAdiacenze();

		int numStrade = strade.length;
		boolean recintiOvunque = true;
		// ciclo su tutte le strade attorno alla regione in cui è il lupo
		for (int i = 0; i < numStrade; i++) {

			// se una di queste strade ha lo stesso codiceDado del numero
			// casuale entro nell'if
			if (strade[i].getCodiceDado() == dado) {
				// se la strada è libera da recinti posso muovere il lupo
				if (statoPartita.stradaLiberaDaRecinti(strade[i])) {
					try {
						// trovo la regione di destinazione
						regioneDestinazione = strade[i].getConfinante(lupo
								.getPosizione());

					} catch (EccezioneNonAdiacenza e) {
						MyLogger.gestisci("EccCanale", e);
					}
					lupo.setPosizione(regioneDestinazione);
					return true;
				} else {
					// se la strada ha un recinto devo controllare che tutte le
					// altre strade abbiano un recinto affinchè il lupo possa
					// saltare
					for (int k = 0; k < numStrade; k++) {
						if (statoPartita.stradaLiberaDaRecinti(strade[k])) {
							recintiOvunque = false;
						}
					}
					if (recintiOvunque) {
						// se tutte le altre strade della regione hanno un
						// recinto
						try {
							// trovo la regione di destinazione
							regioneDestinazione = strade[i].getConfinante(lupo
									.getPosizione());
						} catch (EccezioneNonAdiacenza e) {
							MyLogger.gestisci("EccCanale", e);
						}
						lupo.setPosizione(regioneDestinazione);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * It is used during the tests.
	 * 
	 * @param numDado
	 *            it is the value(from 1 to 6) of the street where the wolf will
	 *            go
	 * @return true if the move is valid, otherwise false
	 */
	public boolean muoviDeterministico(int numDado) {
		// è un metodo uguale a muovi, solo che non genera un numero casuale, mi
		// serve per fare i test
		Regione regioneDestinazione = null;
		int dado = numDado;
		// strade contiene tutte le strade che circondano la regione in cui si
		// trova il lupo
		Strada[] strade = (Strada[]) lupo.getPosizione().getAdiacenze();
		// è il numero di strade che circondando la regione in cui è il lupo
		int numStrade = strade.length;
		// ipotizzo che ci siano recinti su tutte le strade attorno al lupo
		boolean recintiOvunque = true;
		// ciclo su tutte le strade attorno alla regione in cui è il lupo
		for (int i = 0; i < numStrade; i++) {
			// se una di queste strade ha lo stesso codiceDado del numero
			// casuale entro nell'if
			if (strade[i].getCodiceDado() == dado) {
				// se la strada è libera da recinti posso muovere il lupo
				if (statoPartita.stradaLiberaDaRecinti(strade[i])) {
					try {
						// trovo la regione di destinazione, come la confinante
						// alla regione in cui si trova il lupo tramite la
						// strada con codice uguale al dado
						regioneDestinazione = strade[i].getConfinante(lupo
								.getPosizione());
					} catch (EccezioneNonAdiacenza e) {
						MyLogger.gestisci("EccCanale", e);
					}
					lupo.setPosizione(regioneDestinazione);
					return true;
				} else {
					// se la strada ha un recinto devo controllare che tutte le
					// altre strade abbiano un recinto affinchè il lupo possa
					// saltare
					for (int k = 0; k < strade.length; k++) {
						if (statoPartita.stradaLiberaDaRecinti(strade[k])) {
							recintiOvunque = false;
						}
					}
					// se tutte le altre strade della regione hanno un recinto
					if (recintiOvunque) {
						try {
							// trovo la regione di destinazione
							regioneDestinazione = strade[i].getConfinante(lupo
									.getPosizione());
						} catch (EccezioneNonAdiacenza e) {
							MyLogger.gestisci("EccCanale", e);
						}
						lupo.setPosizione(regioneDestinazione);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return true if the wolf eats a sheep, otherwise false.
	 */
	public boolean mangia() {
		List<Ovino> gregge = statoPartita.getOvini();
		int numPecore = gregge.size();
		for (int i = 0; i < numPecore; i++) {
			if (gregge.get(i).getPosizione() == lupo.getPosizione()) {
				gregge.remove(i);
				return true;
			}
		}
		return false;
	}

}
