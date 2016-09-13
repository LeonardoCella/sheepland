package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

/**
 * @author Luigi Ambrosi, Leonardo Cella It is the class to check if the timer
 *         for starting a new match has finished.
 */
public class Timer extends Thread {
	private Server server;
	private boolean possoAvviarePartita;

	public Timer(Server s) {
		this.server = s;
		this.possoAvviarePartita = true;
		segnalaScatto();
	}

	/**
	 * Communicates to the server that a timer for the match that server is
	 * organizing has already started.
	 */
	private void segnalaScatto() {
		this.server.setSincroTimer(true);
	}

	/**
	 * Communicates to the server that the timer for the match that server is
	 * organizing is not running more.
	 */
	private void resettaScatto() {
		this.server.setSincroTimer(false);
	}

	/**
	 * It makes a new match start if the time out terminates, and there are at
	 * least two players.
	 */
	@Override
	public void run() {
		try {
			// 30 secondi
			sleep(30000);
		} catch (InterruptedException e) {
			MyLogger.gestisci("Ecc Int", e);
		}

		synchronized (this.server.getToken()) {
			if (this.server.numeroGiocatoriCorrenti() > 1) {
				resettaScatto();
				if (this.possoAvviarePartita) {
					this.server.iniziaPartita();
				}
			}
		}
	}

	public void settaPermesso(boolean i) {
		this.possoAvviarePartita = i;
	}

}
