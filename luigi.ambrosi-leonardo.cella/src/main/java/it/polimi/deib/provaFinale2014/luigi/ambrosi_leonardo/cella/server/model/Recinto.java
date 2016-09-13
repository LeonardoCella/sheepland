package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the class for the fences.
 * 
 */
public class Recinto {
	Strada posizioneCorrente;

	public Recinto(Strada s) {
		this.posizioneCorrente = s;
	}

	public Strada getPoSizione() {
		return this.posizioneCorrente;
	}

}
