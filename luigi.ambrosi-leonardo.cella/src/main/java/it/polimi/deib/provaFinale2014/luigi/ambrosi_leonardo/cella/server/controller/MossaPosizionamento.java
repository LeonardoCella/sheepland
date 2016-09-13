package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It implements the move for positioning a shepherd.
 */
public class MossaPosizionamento extends Mossa {

	private Pastore soggettoMossa;
	private Strada posizioneIniziale;
	private Partita partitaRiferimento;

	public MossaPosizionamento(Pastore p, Strada s, Partita pa) {
		this.soggettoMossa = p;
		this.posizioneIniziale = s;
		this.partitaRiferimento = pa;
	}

	/**
	 * it overrides the method eseguiMossa() of the abstract class Mossa.
	 * 
	 * @return true if the street posizioneIniziale is free and the move is
	 *         exectued and it is valid, false othwerwise
	 * 
	 */
	@Override
	public boolean eseguiMossa() {
		if (partitaRiferimento.getStatoCorrente().stradaLibera(
				posizioneIniziale)) {
			return soggettoMossa.setPosizione(posizioneIniziale);
		} else {
			return false;
		}
	}

	@Override
	public Soggetto getSoggetto() {
		return this.soggettoMossa;
	}

	@Override
	public String toString() {
		String ret = new String();
		ret = "Sono una mossa posizionamento, Sobj: " + this.soggettoMossa
				+ " pos: " + this.posizioneIniziale;
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((posizioneIniziale == null) ? 0 : posizioneIniziale
						.hashCode());
		result = (prime * result)
				+ ((soggettoMossa == null) ? 0 : soggettoMossa.hashCode());
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
		MossaPosizionamento other = (MossaPosizionamento) obj;
		if (posizioneIniziale == null) {
			if (other.posizioneIniziale != null) {
				return false;
			}
		} else if (!posizioneIniziale.equals(other.posizioneIniziale)) {
			return false;
		}
		if (soggettoMossa == null) {
			if (other.soggettoMossa != null) {
				return false;
			}
		} else if (!soggettoMossa.equals(other.soggettoMossa)) {
			return false;
		}
		return true;
	}

}
