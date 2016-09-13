package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.IClientRem;
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
 *         It is the class for the RMI communication server-side.
 * 
 */
public class ServerRMI extends UnicastRemoteObject implements IServerRem,
		ICanaleServer {

	private static final long serialVersionUID = 1L;
	private boolean empty, problema;
	private Object semaforo, buffer;
	private IClientRem clientRemoto;
	private int PortaRMI = 4100;
	private String username, ipServer;
	private Registry registry;

	public ServerRMI(String user, String ip) throws IOException {
		this.username = user;
		this.ipServer = ip;
		this.semaforo = new Object();
		this.empty = true;
		this.problema = false;
	}

	/**
	 * It is the remote method used by client to write on the buffer of the
	 * client.
	 * 
	 * @param obj
	 *            is the object that is written on the buffer of the server.
	 * @exception e
	 *                when a thread is waiting, sleeping, or otherwise occupied,
	 *                and the thread is interrupted, either before or during the
	 *                activity.
	 */
	public void setBuffer(Object obj) throws RemoteException {
		// PRODUTTORE
		synchronized (this.semaforo) {
			while (!this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("EccCanale", e);
					throw new RemoteException();
				}
			}
			this.empty = false;
			this.buffer = obj;
			this.semaforo.notifyAll();
			return;
		}
	}

	/**
	 * It reads an Object MessaggioMossa from the buffer of the server.
	 * 
	 * @exception e
	 *                when a thread is waiting, sleeping, or otherwise occupied,
	 *                and the thread is interrupted, either before or during the
	 *                activity.
	 * @return the object MessaggioMossa
	 */
	public MessaggioMossa leggoMsgMossa() throws EccCanale {
		// CONSUMATORE
		synchronized (this.semaforo) {
			while (this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("EccCanale", e);
					throw new EccCanale(e);
				}

				if (this.problema) {
					problema = false;
					throw new EccCanale(new Exception());
				}
			}
			this.empty = true;
			this.semaforo.notifyAll();
			return (MessaggioMossa) this.buffer;
		}
	}

	/**
	 * It calls the remote method of the client setBuffer to write a StatoClient
	 * on the buffer of the client.
	 * 
	 * @param stato
	 *            is the message that I want to write on the buffer of the
	 *            client.
	 * @exception e
	 *                if the remote method is not usable.
	 * 
	 */
	public void invioStatoClient(StatoClient stato) throws EccCanale {
		try {
			this.clientRemoto.setBuffer(stato);
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		}
	}

	/**
	 * It calls the remote method of the client setBuffer to write a
	 * MessaggioServizio on the buffer of the client.
	 * 
	 * @param messaggio
	 *            is the message that I want to write on the buffer of the
	 *            client.
	 * @exception e
	 *                if the remote method is not usable.
	 * 
	 */
	public void invioMsgServizio(MessaggioServizio messaggio) throws EccCanale {
		try {
			this.clientRemoto.setBuffer(messaggio);
		} catch (Exception e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		}
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * It sets the RMI connection on the side of client.
	 * 
	 * @exception
	 * 
	 */
	public void accendi() throws EccCanale {
		String user = this.username;

		try {
			this.registry = LocateRegistry.getRegistry(this.ipServer,
					this.PortaRMI);

			this.registry.rebind("serverRem" + user, this);

			this.clientRemoto = (IClientRem) this.registry.lookup("clientRem"
					+ this.username);

		} catch (ConnectIOException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		} catch (AccessException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		} catch (RemoteException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		} catch (NotBoundException e) {
			MyLogger.gestisci("EccCanale", e);
			throw new EccCanale(e);
		}
	}

	/**
	 * It reads an Object MessaggioServizio from the buffer of the server.
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
					MyLogger.gestisci("EccCanale", e);
					throw new EccCanale(e);
				}

				if (this.problema) {
					problema = false;
					throw new EccCanale(new Exception());
				}
			}

			this.empty = true;
			this.semaforo.notifyAll();
			return (MessaggioServizio) this.buffer;
		}
	}

	public Object getSemaforo() {
		return this.semaforo;
	}

	public void setProblema() {
		this.problema = true;
	}
}
