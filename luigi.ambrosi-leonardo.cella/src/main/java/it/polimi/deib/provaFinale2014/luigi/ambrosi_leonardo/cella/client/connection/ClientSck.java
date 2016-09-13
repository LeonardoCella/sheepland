package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the class for the Socket communication client-side.
 * 
 */
public class ClientSck implements ICanaleClient {
	private String username;

	// invio obj
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private boolean nuovo;

	public ClientSck(String user, ObjectInputStream paramOIS,
			ObjectOutputStream paramOOS, boolean nonPresente)
			throws IOException {
		this.username = user;
		this.oos = paramOOS;
		this.oos.flush();
		this.ois = paramOIS;
		this.nuovo = nonPresente;
	}

	/**
	 * It writes an Object MessaggioMossa on the ObjectOutputStream.
	 * 
	 * @param messaggio
	 *            is the Object MessaggioMossa that I write on the stream.
	 * @exception IOException
	 *                problems in writing on the stream.
	 */
	public void invioMsgMossa(MessaggioMossa intenzioneDiMossa)
			throws EccCanale {
		try {
			this.oos.writeObject(intenzioneDiMossa);
			this.oos.flush();
		} catch (IOException e) {
			MyLogger.gestisci("EccCanale", e);

		}
	}

	/**
	 * It reads an Object StatoClient from the ObjectInputStream.
	 * 
	 * @exception IOException
	 *                problems in reading from the stream.
	 * @exception ClassNotFoundException
	 *                I read an Object of a type different from StatoClient
	 * @return the StatoClient read from the stream.
	 */
	public StatoClient leggoStatoAggiornato() throws EccCanale {
		StatoClient ritorno = null;
		try {
			ritorno = (StatoClient) this.ois.readObject();
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		}
		return ritorno;
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
		} catch (IOException e) {
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
		MessaggioServizio tmp = null;

		try {
			tmp = (MessaggioServizio) this.ois.readObject();
		} catch (IOException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		} catch (ClassNotFoundException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(this.username);
		}

		return tmp;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean getTipo() {
		return this.nuovo;
	}

	public void accendi() {
		return;
	}

	public void inizio() {
		return;
	}

	public void toggle() {
		return;
	}

}
