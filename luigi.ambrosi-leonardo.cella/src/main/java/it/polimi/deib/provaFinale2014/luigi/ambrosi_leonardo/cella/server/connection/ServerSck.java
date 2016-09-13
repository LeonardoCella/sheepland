package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the class for the Socket communication server-side.
 * 
 */
public class ServerSck implements ICanaleServer {
	private String username;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public ServerSck(String user, ObjectInputStream paramOIS,
			ObjectOutputStream paramOOS) throws IOException {
		this.username = user;
		this.oos = paramOOS;
		this.oos.flush();
		this.ois = paramOIS;
	}

	/**
	 * It writes an Object StatoClient on the ObjectOutputStream.
	 * 
	 * @param postMossa
	 *            is the Object StatoClient that I write on the stream.
	 * @exception IOException
	 *                problems in writing on the stream.
	 */
	public void invioStatoClient(StatoClient postMossa) throws EccCanale {

		try {
			this.oos.writeObject(postMossa);
			this.oos.reset();
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		}
	}

	/**
	 * It reads an Object MessaggioMossa from the ObjectInputStream.
	 * 
	 * @exception IOException
	 *                problems in reading from the stream.
	 * @return the MessaggioMossa read from the stream.
	 */
	public MessaggioMossa leggoMsgMossa() throws EccCanale {
		MessaggioMossa msg = null;
		try {
			msg = (MessaggioMossa) this.ois.readObject();
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		}
		return msg;
	}

	/**
	 * It writes an Object MessaggioServizio on the ObjectOutputStream.
	 * 
	 * @param messaggio
	 *            is the Object MessaggioServizio that I write on the stream.
	 * @exception IOException
	 *                problems in writing on the stream.
	 */
	public void invioMsgServizio(MessaggioServizio messaggio) throws EccCanale {
		try {
			this.oos.writeObject(messaggio);
			this.oos.flush();
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		}
	}

	/**
	 * It reads an Object MessaggioServizio from the ObjectInputStream.
	 * 
	 * @exception IOException
	 *                problems in reading from the stream.
	 * @return the MessaggioServizio read from the stream.
	 */
	public MessaggioServizio leggoMsgServizio() throws EccCanale {
		MessaggioServizio msg = null;
		try {
			msg = (MessaggioServizio) this.ois.readObject();
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);

		}
		return msg;
	}

	public String getUsername() {
		return this.username;
	}

	public void accendi() {
		return;
	}

}
