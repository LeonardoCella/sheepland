package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Tessera;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It implements the move of buying a card.
 */
public class MossaAcquisto extends Mossa {

	private Pastore soggettoMossa;
	private int tipoTerreno;
	private Partita partitaRiferimento;

	public MossaAcquisto(Pastore sbj, int tipo, Partita p) {
		this.soggettoMossa = sbj;
		this.tipoTerreno = tipo;
		this.partitaRiferimento = p;

	}

	/**
	 * It overrides the method eseguiMossa() of the abstract class Mossa.
	 * 
	 * @return true if the move is exectued and it is valid, false othwerwise
	 * 
	 */
	@Override
	public boolean eseguiMossa() {
		if (acquistaTessera(soggettoMossa, tipoTerreno)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Soggetto getSoggetto() {
		return this.soggettoMossa;
	}

	/**
	 * It is a private method called by eseguiMossa().
	 * 
	 * @param pastore
	 *            it is the sobject that makes the purchase.
	 * @param tipoTerreno
	 *            it is the type of the card that I want to buy.
	 * @return
	 */
	private boolean acquistaTessera(Pastore pastore, int tipoTerreno) {
		// posso comprare una tessera terreno solo se il suo tipo è lo stesso di
		// quello di uno dei due terreni accanto al pastore

		Stato statoPartita = partitaRiferimento.getStatoCorrente();

		if (statoPartita.getCarte().getInstance().get(tipoTerreno).isEmpty()) {
			return false;
		}
		Tessera tesseraDesiderata = statoPartita.getCarte().getInstance()
				.get(tipoTerreno).get(0);
		Tessera tesseraComprata;

		if ((pastore.getPosizione().getRegione1().getTipoTerreno() == tipoTerreno)
				|| (pastore.getPosizione().getRegione2().getTipoTerreno() == tipoTerreno)) {
			if (pastore.getGiocatore().getSoldi() < tesseraDesiderata
					.getCosto()) {
				// se non ho soldi per comprare la tessera ritorno false
				return false;

			} else {
				tesseraComprata = statoPartita.getCarte().pescaDalMazzo(
						tipoTerreno);
				if (tesseraComprata == null) {
					return false;
				} else {
					pastore.getGiocatore().aggiungiTessera(tesseraComprata);
					pastore.getGiocatore().paga(tesseraComprata.getCosto());
					return true;
				}
			}
		} else {
			// se il tipo della tessera che cerco non è uno dei accanto al
			// pastore ritorno false
			return false;

		}

	}

	@Override
	public String toString() {
		String ret = new String();
		ret = "\nSobj: " + this.soggettoMossa + " tipoTerr: "
				+ this.tipoTerreno;
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((partitaRiferimento == null) ? 0 : partitaRiferimento
						.hashCode());
		result = (prime * result)
				+ ((soggettoMossa == null) ? 0 : soggettoMossa.hashCode());
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
		MossaAcquisto other = (MossaAcquisto) obj;
		if (partitaRiferimento == null) {
			if (other.partitaRiferimento != null) {
				return false;
			}
		} else if (!partitaRiferimento.equals(other.partitaRiferimento)) {
			return false;
		}
		if (soggettoMossa == null) {
			if (other.soggettoMossa != null) {
				return false;
			}
		} else if (!soggettoMossa.equals(other.soggettoMossa)) {
			return false;
		}
		if (tipoTerreno != other.tipoTerreno) {
			return false;
		}
		return true;
	}

}
