package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPong;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.PongRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ritardo;

import java.util.List;
/**
 * Is the First class that catch client disconnections, and throw it on 
 * GestorePartitaServer - Partita and Server, to allow them cache refreshing
 */
public class ResponderPong extends Thread {
	private List<IPong> canali;
	private int numeroCanali = 0;
	// variable that is used to stop the thread in a good way
	private boolean gira;

	private Partita partitaCorr;

	// An boolean array that contains the status of each channel.
	private int[] avaiable;

	public ResponderPong(List<IPong> can, Partita partita) {
		this.canali = can;
		this.numeroCanali = can.size();
		this.gira = true;
		this.partitaCorr = partita;
		// tri-state 0:OFF 1:ON 2:FIRST TIME ON
		this.avaiable = new int[can.size()];
		for (int i = 0; i < this.avaiable.length; i++) {
			this.avaiable[i] = 1;
		}
	}

	/**
	 * Method that manage the pong answer 
	 */
	@Override
	public void run() {
		while (this.gira) {
			for (int i = 0; i < this.numeroCanali; i++) {
				if (this.avaiable[i] == 1) {
					try {
						this.canali.get(i).invioPong(
								new MessaggioStringa("passaggio"));
					} catch (EccPing e) {
						MyLogger.gestisci("EccPing", e);
						sospendi(i);
					}
				}
			}

			for (int i = 0; i < this.numeroCanali; i++) {
				if (this.avaiable[i] == 1 || this.avaiable[i] == 2) {

					// Pre-settings in reconnection
					if (this.avaiable[i] == 2) {
						try {
							this.canali.get(i).invioPong(
									new MessaggioStringa("passaggio"));
						} catch (EccPing e) {
							MyLogger.gestisci("EccPing", e);
							sospendi(i);
						}
						this.avaiable[i] = 1;
					}

					try {
						this.canali.get(i).leggiPing();
					} catch (Ritardo r) {
						MyLogger.gestisci("Ritardo", r);
						sospendi(i);
					}
				}

			}
		}
		return;
	}

	public void ferma() {
		this.gira = false;
		return;
	}

	public void riabilita(int i, IPong canalePong) {
		this.canali.set(i, canalePong);
		this.avaiable[i] = 2;
		return;
	}

	/**
	 * Disable a channel when it disconnect
	 * @param the player index in the registry 
	 */
	public void sospendi(int indiceG) {
		PongRMI tmp;
		ServerRMI canale;
		this.avaiable[indiceG] = 0;

		Server s = Server.getServer();
		s.sospendi(this.canali.get(indiceG).getUser());

		if (partitaCorr.sospendi(indiceG)) {
			tmp = (PongRMI) (this.canali.get(indiceG));
			canale = tmp.getCanaleAss();
			synchronized (canale.getSemaforo()) {
				canale.setProblema();
				canale.getSemaforo().notifyAll();
			}
		}
		return;
	}
}
