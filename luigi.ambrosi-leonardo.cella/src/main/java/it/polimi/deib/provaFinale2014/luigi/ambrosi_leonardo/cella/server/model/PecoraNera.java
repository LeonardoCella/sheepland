package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.Partita;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         The class for the black sheep.
 */
public class PecoraNera extends Soggetto {

	private Regione posizioneNera;
	private Partita partitaRiferimento;

	public PecoraNera(Partita p) {
		this.partitaRiferimento = p;
		this.posizioneNera = this.partitaRiferimento.getMappa().getRegioni()[18];
	}

	public Regione getPosizione() {
		return this.posizioneNera;
	}

	/**
	 * @param destinazione
	 *            the new position of the black sheep
	 * @return always true
	 */
	@Override
	public boolean muovi(Luogo destinazione) {
		Regione nuovaPosizione = (Regione) destinazione;
		this.posizioneNera = nuovaPosizione;
		return true;
	}
}
