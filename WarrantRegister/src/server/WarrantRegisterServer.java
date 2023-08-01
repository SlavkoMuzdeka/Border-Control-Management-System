package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarrantRegisterServer implements WarrantRegister{

	public static Handler handler;
	public static Set<Integer> peronIds = new HashSet<>();
	
    static {
    	try {
    		handler = new FileHandler("WarrantRegisterServer.log");
    		Logger.getLogger(WarrantRegisterServer.class.getName()).setUseParentHandlers(false);
    		Logger.getLogger(WarrantRegisterServer.class.getName()).addHandler(handler);
    		init();
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
	
	public WarrantRegisterServer() throws RemoteException{
		super();
	}
	
	private static void init() {
		for(int i=0;i<=50;i+=5) {
			peronIds.add(i);
		}
	}
	
	@Override
	public boolean checkPerson(Integer id) throws RemoteException {
		return peronIds.contains(id);
	}
	
	@SuppressWarnings({ "removal" })
	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy","server_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			WarrantRegisterServer server = new WarrantRegisterServer();
			WarrantRegister stub = (WarrantRegister) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1000);
			registry.rebind("Register", stub);
			System.out.println("The warrant register has been launched");
		}catch(Exception ex) {
			Logger.getLogger(WarrantRegisterServer.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
		}
	}
}
