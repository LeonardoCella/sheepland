package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.client.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Luigi Ambrosi, Leonardo Cella It is the remote interface that is used
 *         in the server to communicate with RMI
 * 
 */
public interface IClientRem extends Remote {
	public void setBuffer(Object obj) throws RemoteException;
}
