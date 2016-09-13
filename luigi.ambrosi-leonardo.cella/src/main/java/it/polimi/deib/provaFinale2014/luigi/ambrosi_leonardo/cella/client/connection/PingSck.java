package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PingSck implements IPing {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	
	public PingSck(ObjectInputStream paramOIS, ObjectOutputStream paramOOS)
			throws IOException {
		this.oos = paramOOS;
		this.oos.flush();
		this.ois = paramOIS;
	}

	/**
	 * @throws EccPing when a disconnection occurs
	 * it send ping to the responderPong
	 */
	public void invioPing(MessaggioStringa ultimoPing) throws EccPing {
		try {
			this.oos.writeObject(ultimoPing);
			this.oos.flush();
		} catch (IOException e) {
			throw new EccPing(e);
		}
	}


	/**
	 * empty just inherited from the Interface for RMI
	 */
	public void accendi() {
		return;
	}

	/**
	 * empty just inherited from the Interface for RMI
	 */
	public void inizio() {
		return;
	}

	/**
	 * It reads pong messages sended from responderPong
	 * @return the last message.
	 */
	public String leggiPong() {
		MessaggioStringa tmp = null;
		try {
			tmp = (MessaggioStringa) this.ois.readObject();
		} catch (Exception e) {
			MyLogger.gestisci("LeggiPong", e);
		}
		return tmp.getCorpo();
	}
}
