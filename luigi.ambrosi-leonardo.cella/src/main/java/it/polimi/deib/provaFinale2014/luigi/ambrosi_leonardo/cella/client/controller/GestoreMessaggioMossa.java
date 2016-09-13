package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;

public class GestoreMessaggioMossa {
	MessaggioMossa[] mossePrecedenti;
	boolean pastoreMosso;
	int indiceMossaCorrente;

	public GestoreMessaggioMossa() {
		this.pastoreMosso = false;
		this.indiceMossaCorrente = 0;
		this.mossePrecedenti = new MessaggioMossa[3];
		this.mossePrecedenti[0] = null;
		this.mossePrecedenti[1] = null;
		this.mossePrecedenti[2] = null;

	}

	/**
	 * @param messaggioMossa
	 *            the message that I want to check.
	 * @return true if the messaggioMossa respect the rules of precedence of
	 *         Sheepland, otherwise false.
	 */
	public boolean controllaMessaggioMossa(MessaggioMossa messaggioMossa) {

		// Se è di tipo muovi pastore posso sempre eseguirlo
		if (messaggioMossa.getTipoMossa() == 1) {
			return true;
		}

		// Valuto il numero della mossa
		switch (this.indiceMossaCorrente) {

		// la prima mossa delle tre può sempre essere eseguita
		case 0: {
			return true;
		}

		// la seconda mossa può essere eseguita solo se è di tipo
		// diverso rispetto alla prima
		case 1: {
			if (this.mossePrecedenti[0].getTipoMossa() != messaggioMossa
					.getTipoMossa()) {
				return true;
			}
			return false;

		}
		// la terza mossa, se il pastore non è stato mosso, non può
		// essere diversa da muovi pastore (ritorna true nel primo
		// if)
		case 2: {
			if (!this.pastoreMosso) {
				return false;
			}
			if (this.pastoreMosso
					&& (this.mossePrecedenti[1].getTipoMossa() != messaggioMossa
							.getTipoMossa())) {
				return true;
			}
		}
		default:
			break;
		}

		return false;
	}

	/**
	 * It saves the previous message in the memory.
	 * 
	 * @param messaggioMossa
	 *            is the message to registry in memory.
	 */
	public void registra(MessaggioMossa messaggioMossa) {
		if (messaggioMossa.getTipoMossa() == 1) {
			this.pastoreMosso = true;
		}
		this.mossePrecedenti[this.indiceMossaCorrente] = messaggioMossa;
		this.indiceMossaCorrente++;
		if (this.indiceMossaCorrente == 3) {
			resettaGestore();
		}
	}

	/**
	 * It reset the memory of this Object.
	 */
	private void resettaGestore() {
		this.indiceMossaCorrente = 0;
		this.pastoreMosso = false;
		int i;
		for (i = 0; i < 3; i++) {
			this.mossePrecedenti[i] = null;
		}
	}

}
