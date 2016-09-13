package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.EccezioneNonAdiacenza;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.PecoraNera;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

public class GestoreNera {
	private PecoraNera pn;
	private Stato statoPartita;

	public GestoreNera(PecoraNera pecora, Stato s) {
		this.pn = pecora;
		this.statoPartita = s;
	}

	/**
	 * It moves the black sheep randomly in a adjacent land.
	 * 
	 * @return true if the move is valid, otherwise false.
	 */
	public boolean muoviRandom() {
		// ritorna true se la nera si muove, false altrimenti
		Regione regioneDestinazione = null;
		// genero un numero casuale intero tra 1 e 6
		int dado = (int) Math.floor(((Math.random()) * 6) + 1);
		Strada[] strade;
		// strade contiene tutte le strade che circondano la regione in cui si
		// trova la nera
		strade = (Strada[]) pn.getPosizione().getAdiacenze();

		for (Strada element : strade) {
			// se una di queste strade ha lo stesso codiceDado del numero
			// casuale entro nell'if
			if (element.getCodiceDado() == dado) {
				if (statoPartita.stradaLibera(element)) {
					try {
						regioneDestinazione = element.getConfinante(pn
								.getPosizione());
					} catch (EccezioneNonAdiacenza e) {
						MyLogger.gestisci("Non adiacenza", e);
					}
					pn.muovi(regioneDestinazione);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * It is used during the tests.
	 * 
	 * @param i
	 *            it is the value(from 1 to 6) of the street where the wolf will
	 *            go
	 * @return true if the move is valid, otherwise false
	 */
	public boolean muoviDeterministico(int i) {
		// mi serve per i test

		Regione regioneDestinazione = null;
		int dado = i;

		Strada[] strade;

		strade = (Strada[]) pn.getPosizione().getAdiacenze();
		for (Strada element : strade) {
			if (element.getCodiceDado() == dado) {

				if (statoPartita.stradaLibera(element)) {

					try {
						regioneDestinazione = element.getConfinante(pn
								.getPosizione());

					} catch (EccezioneNonAdiacenza e) {
						MyLogger.gestisci("Non adiacenza", e);
					}
					pn.muovi(regioneDestinazione);
					return true;
				}
			}
		}
		return false;
	}
}
