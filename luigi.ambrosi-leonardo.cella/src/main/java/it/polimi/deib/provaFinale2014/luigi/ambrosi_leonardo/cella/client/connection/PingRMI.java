package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPongRem;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the implementation of the ping in RMI (client side). It
 *         communicate with PongRMI.
 * 
 */
public class PingRMI extends UnicastRemoteObject implements IPingRem, IPing {

	private static final long serialVersionUID = 1L;
	private Object semaforo;
	private boolean empty;
	private String username, ipServer;
	private IPongRem pongRemoto;
	private int portaRMI = 4100;
	private Registry registry;
	private MessaggioStringa messaggio;

	public PingRMI(String user, String ip) throws IOException {
		this.username = user;
		this.semaforo = new Object();
		this.empty = true;
		this.ipServer = ip;
	}

	/**
	 * It publishes the remote method in the registry.
	 * 
	 * @EccPing if there are problems of disconnection.
	 */
	public void accendi() throws EccPing {
		try {
			this.registry = LocateRegistry.getRegistry(this.ipServer,
					this.portaRMI);

			IPingRem istanza = this;

			this.registry.rebind("pingRem" + this.username, istanza);

		} catch (ConnectIOException e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		} catch (Exception e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		}
	}

	/**
	 * "download" from the registry 
	 * 
	 * @EccPing if a disconnection occurs.
	 */
	public void inizio() throws EccPing {
		try {
			this.pongRemoto = (IPongRem) this.registry.lookup("pongRem"
					+ this.username);
		} catch (AccessException e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		} catch (RemoteException e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		} catch (NotBoundException e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		}
	}

	/**
	 * It reads the message sended form the Pong, 
	 * @return String is the String sended from te ResponderPong
	 */
	public String leggiPong() throws EccPing {
		synchronized (this.semaforo) {
			while (this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("EccPing", e);
					throw new EccPing(e);
				}
			}
			this.empty = true;
			this.semaforo.notifyAll();
			return this.messaggio.getCorpo();
		}
	}

	/**
	 * Method called from the class Ping and it sends signal to te server
	 * for show that it's online. 
	 */
	public void invioPing(MessaggioStringa ping) throws EccPing {
		try {
			this.pongRemoto.inviaPing(ping);
		} catch (RemoteException e) {
			MyLogger.gestisci("EccPing", e);
			throw new EccPing(e);
		}
		return;
	}

	
	/**
	 * Method called by remote as RMI works. It write a msg in the field messaggio.
	 */
	public void inviaPong(MessaggioStringa msg) throws RemoteException {
		synchronized (this.semaforo) {
			while (!this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("EccPing", e);
					throw new RemoteException();
				}
			}
			this.empty = false;
			this.messaggio = msg;
			this.semaforo.notifyAll();
			return;
		}
	}

}
