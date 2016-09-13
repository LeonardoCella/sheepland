package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Luogo;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Recinto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the move to implements the moving of a sheep or a shepherd.
 */
public class MossaSpostamento extends Mossa {
	// soggetto può essere una pecora o un pastore
	private Soggetto soggettoMossa;
	// il luogo di partenza è memorizzato nella posizione corrente del Soggetto
	private Luogo arrivo;

	private Partita partitaRiferimento;

	public MossaSpostamento(Soggetto s, Luogo a, Partita p) {
		this.soggettoMossa = s;
		this.arrivo = a;
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
		if (soggettoMossa.getClass() == Pastore.class) {
			if (eseguiSpostamentoPastore(soggettoMossa, arrivo)) {
				return true;
			}
		} else if (soggettoMossa.getClass() == Ovino.class) {
			if (eseguiSpostamentoOvino(soggettoMossa, arrivo)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * It is a private method called by eseguiMossa() when the move is a move
	 * for moving a sheep
	 * 
	 * @param s
	 *            is the sheep that I want to move
	 * @param luogo
	 *            is the region where I want to move the seep
	 * @return true if the move is executed and is valid, otherwise false
	 */
	private boolean eseguiSpostamentoOvino(Soggetto s, Luogo luogo) {
		Ovino ovino = (Ovino) s;
		if (ovino.muovi(luogo)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * It is a private method called by eseguiMossa() when the move is a move
	 * for moving a shepherd
	 * 
	 * @param s
	 *            is the shepherd that I want to move
	 * @param luogo
	 *            is the region where I want to move the shepherd
	 * @return true if the move is executed and is valid, otherwise false
	 */
	private boolean eseguiSpostamentoPastore(Soggetto s, Luogo luogo) {
		Pastore pastore = (Pastore) s;
		Strada destinazione = (Strada) luogo;
		Recinto nuovoRecinto;
		boolean trovato = false;
		int size;
		// se la strada in cui voglio muovere il pastore è occupata non devo
		// eseguire la mossa
		if (!partitaRiferimento.getStatoCorrente().stradaLibera(destinazione)) {

			return false;
		}
		// memorizzo le strade adiacenti alla posizione corrente
		Strada[] incroci = pastore.getPosizione().getStradeCollegate();

		size = incroci.length;

		for (int i = 0; i < size; i++) {
			if (incroci[i] == destinazione) {
				trovato = true;
			}
		}

		if (trovato) {
			// se la strada è adiacente lo spostamento è gratis
			nuovoRecinto = new Recinto(pastore.getPosizione());
			partitaRiferimento.getStatoCorrente().registraRecinto(nuovoRecinto);
			if (pastore.muovi(luogo)) {
				return true;
			} else {
				return false;
			}
		} else {
			// se la strada non è adiacente lo spostamento costa 1
			if (pastore.getGiocatore().getSoldi() < 1) {
				// se il giocatore non ha soldi non eseguo la mossa
				return false;
			} else {
				pastore.getGiocatore().paga(1);
				nuovoRecinto = new Recinto(pastore.getPosizione());
				partitaRiferimento.getStatoCorrente().registraRecinto(
						nuovoRecinto);
				if (pastore.muovi(luogo)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public Soggetto getSoggetto() {
		return this.soggettoMossa;
	}

	@Override
	public String toString() {
		String m;
		if (this.arrivo.getClass() == Regione.class) {
			m = "Mossa spostamento pecora in regione "
					+ this.arrivo.getCodice();
		} else {
			m = "\nSobj: " + this.soggettoMossa + " arrivo: " + this.arrivo;
		}
		return m;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((arrivo == null) ? 0 : arrivo.hashCode());
		result = (prime * result)
				+ ((partitaRiferimento == null) ? 0 : partitaRiferimento
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
		MossaSpostamento other = (MossaSpostamento) obj;
		if (arrivo == null) {
			if (other.arrivo != null) {
				return false;
			}
		} else if (!arrivo.equals(other.arrivo)) {
			return false;
		}
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
		return true;
	}

}
