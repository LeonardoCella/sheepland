package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ritardo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PongSck implements IPong {

	// stream for input and output, are only objects
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	// Related player
	private String user;

	// Method used to check the connection status
	private volatile long ultimoSample;
	private final int attesaMax;

	/**
	 * @param oisP
	 *            - input stream
	 * @param oosP
	 *            - output stream
	 * @param username
	 *            - player reference
	 * @throws IOException
	 */
	public PongSck(ObjectInputStream oisP, ObjectOutputStream oosP,
			String username) throws IOException {
		this.ultimoSample = System.currentTimeMillis();
		this.attesaMax = 300;
		this.user = username;
		this.oos = oosP;
		this.oos.flush();
		this.ois = oisP;
	}

	/**
	 * 
	 * @return true or false based only the time of the last sample
	 */
	private boolean verificaStato() {
		return (System.currentTimeMillis() - this.ultimoSample) < this.attesaMax;
	}

	/**
	 * empty is from the common interface
	 */
	public void accendi() throws Exception {
	}

	/**
	 * send a ping to the client
	 */
	public void invioPong(MessaggioStringa messaggio) throws EccPing {
		try {
			this.oos.writeObject(messaggio);
			this.oos.flush();
		} catch (IOException e) {
			MyLogger.gestisci("Ecc IO", e);
			throw new EccPing(e);
		}
	}

	/**
	 * check the last ping!
	 * 
	 * @throws Ritado
	 *             in case the connection was interrupted
	 */
	public void leggiPing() throws Ritardo {
		try {
			this.ois.readObject();
			this.ultimoSample = System.currentTimeMillis();
			if (!verificaStato()) {
				throw new Ritardo();
			}
		} catch (Exception e) {
			MyLogger.gestisci("Ecc", e);
			throw new Ritardo();
		}
		return;
	}

	public String getUser() {
		return this.user;
	}
}
