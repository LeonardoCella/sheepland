package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model;


public class EccezionePreImpoServer extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public EccezionePreImpoServer() {
		super();
	}

	public EccezionePreImpoServer(String s) {
		super(s);
	}

	public EccezionePreImpoServer(int n) {
		super("" + n);
	}

}
