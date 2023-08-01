package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarrantRegister extends Remote {
	boolean checkPerson(Integer id) throws RemoteException;
}
