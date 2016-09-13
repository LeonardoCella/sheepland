package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;

import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class for the move of creating a new lamb.
 */
public class MossaAccoppiamento extends Mossa {

	Pastore pastore;
	Regione regioneAccoppiamento;
	Partita partitaRiferimento;

	public MossaAccoppiamento(Pastore past, Regione reg, Partita pa) {
		this.pastore = past;
		this.regioneAccoppiamento = reg;
		this.partitaRiferimento = pa;
	}

	/**
	 * It overrides the method eseguiMossa() of the abstract class Mossa.
	 * 
	 * @return true if the move is exectued and it is valid, false othwerwise.
	 * 
	 */
	@Override
	public boolean eseguiMossa() {
		boolean trovatoPecora = false, trovatoMontone = false;
		Stato stato = this.partitaRiferimento.getStatoCorrente();
		List<Ovino> gregge = stato.getOvini();
		int dado, numPecore;
		numPecore = gregge.size();
		if (!this.pastore.getPosizione().verificaAdiacenza(
				this.regioneAccoppiamento)) {
			return false;
		}

		for (int i = 0; i < numPecore; i++) {
			if (gregge.get(i).getPosizione() == this.regioneAccoppiamento) {
				if (gregge.get(i).verificaMaggioreEta()) {
					if (gregge.get(i).getMaschio()) {
						trovatoMontone = true;
					} else {
						trovatoPecora = true;
					}
				}
			}
		}
		if (!trovatoPecora || !trovatoMontone) {
			return false;
		} else {
			dado = (int) Math.floor(((Math.random()) * 6) + 1); 
			// numero
			// casuale
			// intero tra 1
			// e 6
			if (dado == this.pastore.getPosizione().getCodiceDado()) {
				Ovino agnello = new Ovino(this.regioneAccoppiamento);
				agnello.setAgnello();
				stato.aggiungiOvino(agnello);
				return true;
			}

			else {
				return true;
			}
		}
	}

	/**
	 * it is the same of eseguiMossa() but it is not random. I use it for tests.
	 * 
	 * @return true if the move is exectued and it is valid, false othwerwise.
	 */
	public boolean eseguiMossaTest() {
		boolean trovatoPecora = false, trovatoMontone = false;
		Stato stato = this.partitaRiferimento.getStatoCorrente();
		List<Ovino> gregge = stato.getOvini();
		int dado, numPecore;
		numPecore = gregge.size();
		if (!this.pastore.getPosizione().verificaAdiacenza(
				this.regioneAccoppiamento)) {
			return false;
		}

		for (int i = 0; i < numPecore; i++) {
			if (gregge.get(i).getPosizione() == this.regioneAccoppiamento) {
				if (gregge.get(i).verificaMaggioreEta()) {
					if (gregge.get(i).getMaschio()) {
						trovatoMontone = true;
					} else {
						trovatoPecora = true;
					}
				}
			}
		}
		if (!trovatoPecora || !trovatoMontone) {
			return false;
		} else {
			dado = this.pastore.getPosizione().getCodiceDado();
			if (dado == this.pastore.getPosizione().getCodiceDado()) {
				Ovino agnello = new Ovino(this.regioneAccoppiamento);
				agnello.setAgnello();
				stato.aggiungiOvino(agnello);
				return true;
			} else {
				return true;
			}
		}
	}

	@Override
	public Soggetto getSoggetto() {

		return this.pastore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((partitaRiferimento == null) ? 0 : partitaRiferimento
						.hashCode());
		result = (prime * result)
				+ ((pastore == null) ? 0 : pastore.hashCode());
		result = (prime * result)
				+ ((regioneAccoppiamento == null) ? 0 : regioneAccoppiamento
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
		MossaAccoppiamento other = (MossaAccoppiamento) obj;
		if (partitaRiferimento == null) {
			if (other.partitaRiferimento != null) {
				return false;
			}
		} else if (!partitaRiferimento.equals(other.partitaRiferimento)) {
			return false;
		}
		if (pastore == null) {
			if (other.pastore != null) {
				return false;
			}
		} else if (!pastore.equals(other.pastore)) {
			return false;
		}
		if (regioneAccoppiamento == null) {
			if (other.regioneAccoppiamento != null) {
				return false;
			}
		} else if (!regioneAccoppiamento.equals(other.regioneAccoppiamento)) {
			return false;
		}
		return true;
	}

}
