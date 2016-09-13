package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.EccCanale;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IServerRem;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioMossa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioServizio;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.StatoClient;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the class for the RMI communication client-side.
 * 
 */
public class ClientRMI extends UnicastRemoteObject implements IClientRem,
		ICanaleClient {

	private Object semaforo, buffer;
	private boolean empty;
	private static final long serialVersionUID = 1L;
	private String username;
	private IServerRem serverRemoto;
	private int portaRMI = 4100;
	private Registry registry;
	private String ipServer;
	private boolean nuovo;

	public ClientRMI(String user, String ip, boolean nonPresente)
			throws IOException {
		this.username = user;
		this.buffer = null;
		this.semaforo = new Object();
		this.empty = true;
		this.ipServer = ip;
		this.nuovo = nonPresente;
	}

	/**
	 * It calls the remote method of the client setBuffer to write a
	 * MessaggioMossa on the buffer of the server.
	 * 
	 * @param intenzioneDiMossa
	 *            is the message that I want to write on the buffer of the
	 *            server.
	 * @exception e
	 *                if the remote method is not usable.
	 * 
	 */
	public void invioMsgMossa(MessaggioMossa intenzioneDiMossa)
			throws EccCanale {
		try {
			this.serverRemoto.setBuffer(intenzioneDiMossa);
		} catch (RemoteException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		}
		return;
	}

	/**
	 * It reads an Object StatoClient from the buffer of the client.
	 * 
	 * @exception e
	 *                when a thread is waiting, sleeping, or otherwise occupied,
	 *                and the thread is interrupted, either before or during the
	 *                activity.
	 * @return the object StatoClient.
	 */
	public StatoClient leggoStatoAggiornato() {
		// CONSUMATORE
		synchronized (this.semaforo) {
			while (this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("info", e);
				}
			}
			this.empty = true;
			this.semaforo.notifyAll();
			return (StatoClient) this.buffer;
		}
	}

	/**
	 * It calls the remote method of the server setBuffer to write a
	 * MessaggioServizio on the buffer of the server.
	 * 
	 * @param messaggio
	 *            is the message that I want to write on the buffer of the
	 *            server.
	 * @exception e
	 *                if the remote method is not usable.
	 * 
	 */
	public void invioMsgServizio(MessaggioServizio messaggio) throws EccCanale {
		try {
			this.serverRemoto.setBuffer(messaggio);
		} catch (RemoteException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		}
		return;
	}

	/**
	 * It reads an Object MessaggioServizio from the buffer of the client.
	 * 
	 * @exception e
	 *                when a thread is waiting, sleeping, or otherwise occupied,
	 *                and the thread is interrupted, either before or during the
	 *                activity.
	 * @return the object MessaggioServizio
	 */
	public MessaggioServizio leggoMsgServizio() throws EccCanale {

		synchronized (this.semaforo) {
			while (this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("info", e);
					throw new EccCanale();
				}
			}
			this.empty = true;
			this.semaforo.notifyAll();
			return (MessaggioServizio) this.buffer;
		}
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * It is the remote method used by server to write on the buffer of the
	 * client.
	 * 
	 * @param obj
	 *            is the object that is written on the buffer of the client.
	 * @exception e
	 *                when a thread is waiting, sleeping, or otherwise occupied,
	 *                and the thread is interrupted, either before or during the
	 *                activity.
	 */
	public void setBuffer(Object obj) throws RemoteException {
		synchronized (this.semaforo) { // PRODUTTORE
			while (!this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("info", e);
				}
			}
			this.empty = false;
			this.buffer = obj;
			this.semaforo.notifyAll();
			return;
		}
	}

	/**
	 * It sets the RMI connection on the side of client.
	 * 
	 * @exception ConnectIOException
	 *                it notify an error during RMI.
	 * 
	 */
	public void accendi() throws EccCanale {
		try {
			this.registry = LocateRegistry.getRegistry(this.ipServer,
					this.portaRMI);

			IClientRem istanza = this;

			this.registry.rebind("clientRem" + this.username, istanza);

		} catch (ConnectIOException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		} catch (Exception e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		}
	}

	/**
	 * It start the RMI conncetion on the side of client.
	 * 
	 * @exception AccessException
	 *                , RemoteException, NotBoundException errors during the
	 *                setting of RMI connection.
	 * 
	 */
	public void inizio() throws EccCanale {
		try {
			this.serverRemoto = (IServerRem) this.registry.lookup("serverRem"
					+ this.username);
		} catch (AccessException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		} catch (RemoteException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		} catch (NotBoundException e) {
			MyLogger.gestisci("info", e);
			throw new EccCanale();
		}
	}

	public boolean getTipo() {
		return this.nuovo;
	}

	public void toggle() {
		this.nuovo = !this.nuovo;
	}

}