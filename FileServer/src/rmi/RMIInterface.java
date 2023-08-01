package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{
	boolean saveFile(byte[] data, String zipFileName) throws RemoteException;
}
