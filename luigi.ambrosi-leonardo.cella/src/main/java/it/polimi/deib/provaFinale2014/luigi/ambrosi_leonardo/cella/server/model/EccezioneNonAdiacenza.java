package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;


public class EccezioneNonAdiacenza extends Exception {
	/**
	 * It is the exception thrown if a region A is not attainable form a regione B through the street C.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Eccezione relativa alle classi Soggetto-Mossa
	 */
	public EccezioneNonAdiacenza() {
		super();
	}

	public EccezioneNonAdiacenza(String s) {
		super(s);
	}

	public EccezioneNonAdiacenza(int n) {
		super("" + n);
	}
}
