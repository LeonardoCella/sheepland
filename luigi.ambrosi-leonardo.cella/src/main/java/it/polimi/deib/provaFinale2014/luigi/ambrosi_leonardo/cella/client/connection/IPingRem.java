package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection.MessaggioStringa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPingRem extends Remote {
	public void inviaPong(MessaggioStringa msg) throws RemoteException;
}
