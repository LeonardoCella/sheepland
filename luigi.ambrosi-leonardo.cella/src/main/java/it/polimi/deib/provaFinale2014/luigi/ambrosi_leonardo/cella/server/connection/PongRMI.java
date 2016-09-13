package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.EccPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.IPingRem;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Ritardo;

public class PongRMI extends UnicastRemoteObject implements IPong, IPongRem {

	private static final long serialVersionUID = 1L;
	private volatile long ultimoSample;
	private final int attesaMax;
	private String user, ipServer;
	private int portaRMI = 4100;
	private Registry registry;
	private IPingRem pingRemoto;
	private boolean empty;
	private Object semaforo;
	private ServerRMI canaleComunicazione;

	public PongRMI(String nome, String ip, ServerRMI canaleComm)
			throws IOException {
		this.ultimoSample = System.currentTimeMillis();
		this.attesaMax = 6000;
		this.user = nome;
		this.semaforo = new Object();
		this.empty = true;
		this.ipServer = ip;
		this.canaleComunicazione = canaleComm;
	}

	/**
	 * @return true or false based only the time of the last sample
	 **/
	private boolean verificaStato() {
		return (System.currentTimeMillis() - this.ultimoSample) < this.attesaMax;
	}

	public void accendi() throws Exception {
		try {
			this.registry = LocateRegistry.getRegistry(this.ipServer,
					this.portaRMI);

			this.registry.rebind("pongRem" + user, this);

			this.pingRemoto = (IPingRem) this.registry.lookup("pingRem"
					+ this.user);
		} catch (ConnectIOException e) {
			MyLogger.gestisci("Ecc IO", e);
			throw new EccCanale(this.user);
		} catch (AccessException e) {
			MyLogger.gestisci("Ecc", e);
			throw new EccCanale(this.user);
		} catch (RemoteException e) {
			MyLogger.gestisci("Ecc", e);
			throw new EccCanale(this.user);
		} catch (NotBoundException e) {
			MyLogger.gestisci("Ecc", e);
			throw new EccCanale(this.user);
		}
	}

	public void invioPong(MessaggioStringa messaggio) throws EccPing {
		try {
			this.pingRemoto.inviaPong(messaggio);
		} catch (RemoteException e) {
			MyLogger.gestisci("Ecc", e);
			throw new EccPing(e);
		}
	}

	public void leggiPing() throws Ritardo {
		synchronized (this.semaforo) { // CONSUMATORE
			while (this.empty) {
				try {
					// wait timeout
					this.semaforo.wait(4000);
					if (!verificaStato()) {
						throw new Ritardo();
					}
				} catch (InterruptedException e) {
					MyLogger.gestisci("Ecc", e);
					if (!verificaStato()) {
						throw new Ritardo();
					}
				}
			}
			this.empty = true;
			this.semaforo.notifyAll();
			return;
		}
	}

	/**
	 * @String return the user from the channel
	 */
	public String getUser() {
		return this.user;
	}

	public ServerRMI getCanaleAss() {
		return this.canaleComunicazione;
	}

	public void inviaPing(MessaggioStringa msg) throws RemoteException {
		synchronized (this.semaforo) { // PRODUTTORE
			while (!this.empty) {
				try {
					this.semaforo.wait();
				} catch (InterruptedException e) {
					MyLogger.gestisci("Ecc", e);
					throw new RemoteException();
				}
			}
			this.empty = false;
			this.ultimoSample = System.currentTimeMillis();
			this.semaforo.notifyAll();
			return;
		}
	}

}
