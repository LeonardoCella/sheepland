package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

public class Arbitro extends Thread {
	private Server srv;
	private boolean continua;

	public Arbitro(Server srv) {
		this.srv = srv;
		this.continua = true;
	}

	/**
	 * It is called from the server to start new match when there are enough
	 * players in queue. It block the timer that is counting on the same match,
	 * with the method settaPermesso(false).
	 * 
	 */
	@Override
	public void run() {
		while (this.continua) {
			synchronized (this.srv.getToken()) {
				if (this.srv.numeroGiocatoriCorrenti() == 6) {
					this.srv.getTimer().settaPermesso(false);
					this.srv.iniziaPartita();
				}
			}
		}
	}

	/**
	 * Method called to stop the thread in a good way
	 */
	protected void stopThread() {
		this.continua = false;
	}
}
