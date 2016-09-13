package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ovino;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Pastore;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Regione;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Soggetto;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Strada;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class for the move of killing a sheep.
 */
public class MossaAbbattimento extends Mossa {

	Pastore pastore;
	Regione regioneAbbattimento;
	Partita partitaRiferimento;

	public MossaAbbattimento(Pastore x, Regione r, Partita p) {
		this.pastore = x;
		this.regioneAbbattimento = r;
		this.partitaRiferimento = p;
	}

	/**
	 * It overrides the method eseguiMossa() of the abstract class Mossa.
	 * 
	 * @return true if the move is exectued and it is valid, false othwerwise.
	 * 
	 */
	@Override
	public boolean eseguiMossa() {
		if (!this.pastore.getPosizione().verificaAdiacenza(
				this.regioneAbbattimento)) {
			return false;
		}
		int[] giocatoriDaPagare = new int[6];
		for (int i = 0; i < 6; i++) {
			giocatoriDaPagare[i] = 0;
		}
		int spesa;
		int dadiBuoni = 0;
		int indiceGiocatoreDaPagare;
		Strada posizionePastore = this.pastore.getPosizione();
		int dado;
		Strada[] incroci = posizionePastore.getStradeCollegate();
		List<Pastore> pastori = new ArrayList<Pastore>();
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		giocatori = this.partitaRiferimento.getStatoCorrente().getGiocatori();

		// metto tutti i pastori nell'arraylist pastore
		for (int i = 0; i < giocatori.size(); i++) {
			for (int k = 0; k < giocatori.get(i).getPastori().size(); k++) {
				pastori.add(giocatori.get(i).getPastori().get(k));
			}
		}

		for (Strada element : incroci) {
			for (int k = 0; k < pastori.size(); k++) {
				if (this.pastore.getGiocatore() != pastori.get(k)
						.getGiocatore()) {
					if (pastori.get(k).getPosizione() == element) {
						dado = (int) Math.floor(((Math.random()) * 6) + 1);
						if (dado >= 5) {
							indiceGiocatoreDaPagare = pastori.get(k)
									.getGiocatore().getIndiceNellaPartita();
							giocatoriDaPagare[indiceGiocatoreDaPagare]++;
							dadiBuoni++;
						} else {
							return false;
						}
					}
				}
			}
		}
		spesa = dadiBuoni * 2;
		if (this.pastore.getGiocatore().getSoldi() < spesa) {
			return false;
		} else {
			this.pastore.getGiocatore().paga(spesa);
			for (int i = 0; i < 6; i++) {
				if (giocatoriDaPagare[i] > 0) {
					this.partitaRiferimento.getStatoCorrente().getGiocatori()
							.get(i).paga(-(giocatoriDaPagare[i] * 2));
				}
			}
		}

		for (int i = 0; i < this.partitaRiferimento.getStatoCorrente()
				.getOvini().size(); i++) {
			Ovino ovino = this.partitaRiferimento.getStatoCorrente().getOvini()
					.get(i);
			if (ovino.getPosizione() == this.regioneAbbattimento) {
				this.partitaRiferimento.getStatoCorrente().getOvini().remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * It is the same of eseguiMossa() but it is not random. I use it for tests.
	 * 
	 * @return true if the move is exectued and it is valid, false othwerwise.
	 */
	public boolean eseguiMossaTest() {
		if (!this.pastore.getPosizione().verificaAdiacenza(
				this.regioneAbbattimento)) {
			return false;
		}
		int[] giocatoriDaPagare = new int[6];
		for (int i = 0; i < 6; i++) {
			giocatoriDaPagare[i] = 0;
		}
		int spesa;
		int dadiBuoni = 0;
		int indiceGiocatoreDaPagare;
		Strada posizionePastore = this.pastore.getPosizione();
		int dado;
		Strada[] incroci = posizionePastore.getStradeCollegate();
		List<Pastore> pastori = new ArrayList<Pastore>();
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		giocatori = this.partitaRiferimento.getStatoCorrente().getGiocatori();

		// metto tutti i pastori nell'arraylist pastore
		for (int i = 0; i < giocatori.size(); i++) {
			for (int k = 0; k < giocatori.get(i).getPastori().size(); k++) {
				pastori.add(giocatori.get(i).getPastori().get(k));
			}
		}

		for (Strada element : incroci) {
			for (int k = 0; k < pastori.size(); k++) {
				if (this.pastore.getGiocatore() != pastori.get(k)
						.getGiocatore()) {
					if (pastori.get(k).getPosizione() == element) {
						dado = 5;
						if (dado >= 5) {
							indiceGiocatoreDaPagare = pastori.get(k)
									.getGiocatore().getIndiceNellaPartita();
							giocatoriDaPagare[indiceGiocatoreDaPagare]++;
							dadiBuoni++;
						} else {
							return false;
						}
					}
				}
			}
		}
		spesa = dadiBuoni * 2;
		if (this.pastore.getGiocatore().getSoldi() < spesa) {
			return false;
		} else {
			this.pastore.getGiocatore().paga(spesa);
			for (int i = 0; i < 6; i++) {
				if (giocatoriDaPagare[i] > 0) {
					this.partitaRiferimento.getStatoCorrente().getGiocatori()
							.get(i).paga(-(giocatoriDaPagare[i] * 2));
				}
			}
		}

		for (int i = 0; i < this.partitaRiferimento.getStatoCorrente()
				.getOvini().size(); i++) {
			Ovino ovino = this.partitaRiferimento.getStatoCorrente().getOvini()
					.get(i);
			if (ovino.getPosizione() == this.regioneAbbattimento) {
				this.partitaRiferimento.getStatoCorrente().getOvini().remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public Soggetto getSoggetto() {
		return null;
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
				+ ((regioneAbbattimento == null) ? 0 : regioneAbbattimento
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
		MossaAbbattimento other = (MossaAbbattimento) obj;
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
		if (regioneAbbattimento == null) {
			if (other.regioneAbbattimento != null) {
				return false;
			}
		} else if (!regioneAbbattimento.equals(other.regioneAbbattimento)) {
			return false;
		}
		return true;
	}

}
