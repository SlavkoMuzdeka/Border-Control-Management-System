package rmi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIService implements RMIInterface {

	private static Handler handler;
	private static final String PATH = "documents";
	private static final String NAME = "FileServer";
	private static final String SERVER_FILE = "server_policyfile.txt";

	static {
		try {
			handler = new FileHandler("RegisterServer.log");
			Logger.getLogger(RMIService.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(RMIService.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public RMIService() {
		super();
	}

	@SuppressWarnings({ "removal" })
	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy", SERVER_FILE);
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			RMIService service = new RMIService();
			RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(service, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind(NAME, stub);
			System.out.println("Fajl server has been started...");
		} catch (Exception ex) {
			Logger.getLogger(RMIService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public boolean saveFile(byte[] data, String zipFileName) {
		try {
			File file = new File(PATH + File.separator + zipFileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception ex) {
			Logger.getLogger(RMIService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

}
