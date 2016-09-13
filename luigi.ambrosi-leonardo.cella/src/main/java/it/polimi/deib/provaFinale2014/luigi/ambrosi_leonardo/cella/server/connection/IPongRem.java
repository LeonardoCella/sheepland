package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPongRem extends Remote {
	public void inviaPing(MessaggioStringa msg) throws RemoteException;
}
