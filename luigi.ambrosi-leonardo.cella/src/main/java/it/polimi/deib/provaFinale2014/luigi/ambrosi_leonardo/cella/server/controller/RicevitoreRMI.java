package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ICanaleServer;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.IPong;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.PongRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.ServerRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.Stub;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.model.Giocatore;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @description :- Listener of RMI requests.
 */
public class RicevitoreRMI extends UnicastRemoteObject implements Stub {

	private static final long serialVersionUID = -1246115694911679592L;
	private Server srv;
	private Registry registry;
	private int portaRMI;
	private static RicevitoreRMI ricevitore;

	private RicevitoreRMI(Server server, int portaRMI) throws RemoteException {
		this.srv = server;
		this.portaRMI = portaRMI;
	}

	public static RicevitoreRMI getRicevitore(Server server)
			throws RemoteException {

		if (ricevitore == null) {
			ricevitore = new RicevitoreRMI(server, 4100);
		}
		return ricevitore;
	}

	/**
	 * @description :- make the registry and public the listener on this
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void start() throws RemoteException, AlreadyBoundException {
		try {

			this.registry = LocateRegistry.createRegistry(this.portaRMI);

			RicevitoreRMI stub = RicevitoreRMI.ricevitore;

			this.registry.rebind("aggiungiDaRemoto", stub);

		} catch (RemoteException e) {
			MyLogger.gestisci("info", e);
			return;
		}
	}

	/**
	 * @throws generic
	 *             Exception contains also the Remote
	 * @description :- client RMI directly call this method, in fact it's on the
	 *              registry and here are made the new channels
	 */
	public int disponibile(String user, String ip) throws RemoteException {
		Giocatore giocatore;
		int numPastori, colore1, colore2;
		try {
			int esito;
			ICanaleServer nuovoGiocatore = new ServerRMI(user, ip);
			IPong canalePong = new PongRMI(user, ip, (ServerRMI) nuovoGiocatore);

			synchronized (this.srv.getBlocco()) {
				esito = this.srv.aggiungiGiocatore(nuovoGiocatore, canalePong);

				//Sono in coda
				if (esito == 1) { 
					return -1;
				} else {
					nuovoGiocatore.accendi();

					canalePong.accendi();

					//Riconnessione
					if (esito == 2) { 
						giocatore = this.srv.trovaGiocatore(user);
						numPastori = giocatore.getPastori().size();
						if (numPastori == 1) {
							colore1 = giocatore.getPastori().get(0).getColore();
							return colore1;
						} else {
							colore1 = giocatore.getPastori().get(0).getColore();
							colore2 = giocatore.getPastori().get(1).getColore();
							return (colore1 * 10) + colore2;
						}
					} else { 
						//nuovo giocatore
						return -2;
					}
				}
			}
		} catch (Exception e) {
			MyLogger.gestisci("info", e);
			return -1;
		}

	}

	/**
	 * close the listener by remove the interfaces from the registry
	 */
	public void stopThread() {
		try {
			UnicastRemoteObject.unexportObject(this.registry, true);
			this.registry.unbind("stub");
		} catch (NoSuchObjectException e) {
			MyLogger.gestisci("info", e);
		} catch (AccessException e) {
			MyLogger.gestisci("info", e);
		} catch (RemoteException e) {
			MyLogger.gestisci("info", e);
		} catch (NotBoundException e) {
			MyLogger.gestisci("info", e);
		}
	}

}