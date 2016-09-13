package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.MainClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ClientSck;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ICanaleClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.IPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.PingSck;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.StructCanali;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Speak with the Receiver Socket to add a new channel, contains the code based
 * on the socket rules.
 */
public class ConnettoreSck {

	private static ObjectOutputStream oos, oosP;
	private static ObjectInputStream ois, oisP;
	private static int portaSck = 4000;
	private static int portaPong = 5000;

	private ConnettoreSck() {

	}

	/**
	 * @param ipServer
	 *            the IP address of Server
	 * @param user
	 *            the username of the player
	 * @return the couple communication channel-pong channel
	 */
	@SuppressWarnings("resource")
	public static StructCanali avvioSocket(String ipServer, String user) {
		ICanaleClient canale = null;
		IPing gestorePing;
		String username = user;
		MessaggioStringa stringaConferma = null;
		int colore1, colore2;
		try {

			Socket sc = new Socket(ipServer, portaSck);
			oos = new ObjectOutputStream(sc.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(sc.getInputStream());

			Socket scP = new Socket(ipServer, portaPong);
			oosP = new ObjectOutputStream(scP.getOutputStream());
			oosP.flush();
			oisP = new ObjectInputStream(scP.getInputStream());

			try {
				stringaConferma = (MessaggioStringa) ois.readObject();
			} catch (ClassNotFoundException e1) {
				MyLogger.gestisci("ProbAvvioSck", e1);
				return null;
			}

			if (stringaConferma.getCorpo().equals("user:")) {
				oos.writeObject(new MessaggioStringa(username));
			}

			try {
				stringaConferma = (MessaggioStringa) ois.readObject();
			} catch (ClassNotFoundException e) {
				MyLogger.gestisci("ProbAvvioSck", e);
				return null;
			}

			if (stringaConferma.getCorpo().equals("err:")) {
				return null;
			} else if (stringaConferma.getCorpo().equals("valido:")) {
				canale = new ClientSck(username, ois, oos, true);
			} else {
				colore1 = Integer.parseInt(stringaConferma.getCorpo()
						.split(":")[0]);
				colore2 = Integer.parseInt(stringaConferma.getCorpo()
						.split(":")[1]);
				if (colore2 != 0) {
					MainClient.settaColori(colore1, colore2);
				} else {
					MainClient.settaColore(colore1);
				}
				canale = new ClientSck(username, ois, oos, false);
			}

			gestorePing = new PingSck(oisP, oosP);

		} catch (UnknownHostException e) {
			MyLogger.gestisci("Server spento", e);
			return null;
		} catch (IOException e) {
			MyLogger.gestisci("Server spento", e);

			return null;
		}

		return new StructCanali(canale, gestorePing);
	}

}
