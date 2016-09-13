package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

/**
 * Is a single thread based on a single channel, client side. Send all the ping
 * signal as soon as it receive a ping from the server.
 */
public class Ping extends Thread {

	private boolean gira;
	private String sentinella;
	private IPing canale;
	private Object mutex;

	public Ping(IPing canale) {
		this.gira = true;
		this.canale = canale;
		this.sentinella = "on";
		this.mutex = new Object();
	}

	@Override
	public void run() {
		synchronized (this.mutex) {
			while (this.gira) {
				try {
					this.sentinella = this.canale.leggiPong();
					if (this.sentinella.equals("passaggio")) {
						try {
							sleep(400);
						} catch (InterruptedException e) {
							MyLogger.gestisci("Ecc Int", e);
							return;
						}
						this.canale.invioPing(new MessaggioStringa(""
								+ System.currentTimeMillis()));

					}
				} catch (EccPing e) {
					MyLogger.gestisci("Ecc Ping", e);
					return;
				}
			}
		}
	}

	public void spegni() {
		this.gira = false;
	}
}
