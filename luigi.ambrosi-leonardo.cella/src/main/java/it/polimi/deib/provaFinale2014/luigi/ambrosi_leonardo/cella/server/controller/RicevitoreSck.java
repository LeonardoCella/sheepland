package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPong;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.PongSck;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerSck;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description :- thread called from the srv, is a listener of all the socket
 *              connection to the srv
 */
public class RicevitoreSck extends Thread {
	private Server srv;
	private int portaG, portaP;
	private ServerSocket srvSck, pongSck;
	private boolean cicla = true;

	public RicevitoreSck(Server s, int portaGioco, int portaPong) {
		this.srv = s;
		this.portaG = portaGioco;
		this.portaP = portaPong;
		this.cicla = true;
		try { 
			this.srvSck = new ServerSocket(this.portaG);
			this.pongSck = new ServerSocket(this.portaP);
		} catch (IOException e) {
			MyLogger.gestisci("Ecc IO", e);
		}
	}

	/**
	 * made the channels on the srv side. it made the pingChannel and also the
	 * main channel for the communication.
	 */
	@Override
	public void run() {
		Giocatore giocatore;
		int colore1, colore2, numPastori;
		int esito;
		String username;

		Socket socketGiocatore, socketPong;
		ObjectOutputStream oosG, oosP;
		ObjectInputStream oisG, oisP;
		MessaggioStringa mex;
		

		while (this.cicla) {
			try {
				// Builds the channels <communication , ping>
				socketGiocatore = this.srvSck.accept();

				oosG = new ObjectOutputStream(socketGiocatore.getOutputStream());
				oosG.flush();
				oisG = new ObjectInputStream(socketGiocatore.getInputStream());

				socketPong = this.pongSck.accept();

				oosP = new ObjectOutputStream(socketPong.getOutputStream());
				oosP.flush();
				oisP = new ObjectInputStream(socketPong.getInputStream());

				// First communication in this protocol, allow the client to
				// send the player name, because the server have to check it
				mex = new MessaggioStringa("user:");

				oosG.writeObject(mex);
				oosG.reset();

				mex = (MessaggioStringa) oisG.readObject();

				ICanaleServer nuovoGiocatore = new ServerSck(mex.getCorpo(),
						oisG, oosG);

				IPong canalePong = new PongSck(oisP, oosP, mex.getCorpo());
				// I made the socket channels

				synchronized (this.srv.getBlocco()) {
					// I check the presence in the server of this name
					// and I send an answer to the client
					esito = this.srv.aggiungiGiocatore(nuovoGiocatore,
							canalePong);
					if (esito == 1) {
						oosG.writeObject(new MessaggioStringa("err:"));
						oosG.reset();
					} else {
						if (esito == 2) {
							username = mex.getCorpo();
							giocatore = this.srv.trovaGiocatore(username);
							numPastori = giocatore.getPastori().size();
							if (numPastori == 1) {
								colore1 = giocatore.getPastori().get(0)
										.getColore();
								oosG.writeObject(new MessaggioStringa(""
										+ colore1 + ":0"));
							} else {
								colore1 = giocatore.getPastori().get(0)
										.getColore();
								colore2 = giocatore.getPastori().get(1)
										.getColore();
								oosG.writeObject(new MessaggioStringa(""
										+ colore1 + ":" + colore2));
							}
							oosG.reset();
						} else {
							oosG.writeObject(new MessaggioStringa("valido:"));
							oosG.reset();
						}
					}
				}
			} catch (Exception e) {
				MyLogger.gestisci("Ecc", e);
			}
		}
	}

	public void stopThread() {
		this.cicla = false;
	}
}
