package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.controller;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.MainClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ClientRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.ICanaleClient;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.IPing;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection.PingRMI;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.Stub;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.MyLogger;
import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.controller.StructCanali;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConnettoreRMI {
	private static String username;
	private static int portaRMI = 4100;

	private static Stub stub;
	private static String ipServer;

	private static ICanaleClient canale;
	private static IPing canalePing;

	private ConnettoreRMI() {

	}

	/**
	 * @param ip
	 *            is the ip to locate the registry.
	 * @return the remote interface of RicevitoreRMI
	 * @throws RemoteException
	 */
	public static Stub avvioRMI(String ip) throws RemoteException {
		Registry registry;

		try {
			registry = LocateRegistry.getRegistry(ip, portaRMI);

			stub = (Stub) registry.lookup("aggiungiDaRemoto");

		} catch (ConnectIOException e) {
			MyLogger.gestisci("ProbStub", e);
			return null;
		} catch (Exception e) {
			MyLogger.gestisci("ProbStub", e);
			return null;
		}

		return stub;
	}

	/**
	 * It creates che channels, it publishes them on the registry. It checks if
	 * the channels are available, based on the the username given by the
	 * player.
	 * 
	 * @return the channel if it is available, otherwise null.
	 * @throws RemoteException
	 *             problems during the publishing of RMI.
	 */
	public static StructCanali addRMI(String user) throws RemoteException {
		int esito;
		int colore1, colore2;
		try {
			username = user;

			canale = new ClientRMI(username, ipServer, true);
			canalePing = new PingRMI(username, ipServer);

			// Pubblico sul registro i due canali
			canale.accendi();
			canalePing.accendi();

			esito = stub.disponibile(username, InetAddress.getLocalHost()
					.getHostAddress());

			if (esito == -1) {
				return null;
			} else {
				if (esito != -2) { // reconnection
					if (esito - 10 > 0) {
						colore1 = esito / 10;
						colore2 = esito - (colore1 * 10);
						MainClient.settaColori(colore1, colore2);
					} else {
						colore1 = esito;
						MainClient.settaColore(colore1);
					}
					canale.toggle();
				}
			}

			canale.inizio();
			canalePing.inizio();

		} catch (UnknownHostException e) {
			MyLogger.gestisci("info", e);
			return null;
		} catch (Exception e) {
			MyLogger.gestisci("info", e);
			return null;
		}
		return new StructCanali(canale, canalePing);
	}

}
