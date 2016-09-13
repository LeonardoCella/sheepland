package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Luigi Ambrosi, Leonardo Cella
 * 
 *         It is the interface used by clients to implement RMI communication.
 * 
 */
public interface IServerRem extends Remote {
	public void setBuffer(Object obj) throws RemoteException;

}
