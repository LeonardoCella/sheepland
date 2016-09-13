package it.polimi.deib.provaFinale2014.luigi.ambrosi_leonardo.cella.server.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Luigi Ambrosi, Leonardo Cella.
 * 
 *         It is the remote interface of server on the RMI registry and it is
 *         used by client.
 * 
 */
public interface Stub extends Remote {

	public int disponibile(String user, String ip) throws RemoteException;

}
