package application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Exit;
import model.Terminal;
import model.Entrance;
import rmi.RMIInterface;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import servisKlijent.KlijentServis;
import servisKlijent.KlijentServisServiceLocator;

public class CustomsClient extends Thread{

	private String terminalName;
	private String entranceExit;
	private Integer entranceExitId;
	
	public CustomsClient(String terminalName, String entranceExit, Integer entranceExitId) {
		super();
		this.terminalName = terminalName;
		this.entranceExit = entranceExit;
		this.entranceExitId = entranceExitId;
	}
	
	@Override
	public void run() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			KlijentServisServiceLocator locator = new KlijentServisServiceLocator();
			KlijentServis ks = locator.getKlijentServis();
			while(true) {
				Terminal terminal = service.dohvatiTerminal(terminalName);
				if("Entrance".equals(entranceExit)) {
					Entrance[] entrances = terminal.getEntrances();
					for(Integer i=0;i<entrances.length;i++) {
						if(entrances[i].getId() == entranceExitId && entrances[i].getData() != null && !"".equals(entrances[i].getFileName())) {
							String name = entrances[i].getFileName();
							Integer temp = sendDocument(entrances[i].getData(), name);
							ks.slanjeNaPosrednika(name, temp);
							service.isObradjenDokument1(terminalName, entranceExitId, null, "");
						}
					}
				}else {
					Exit[] exits = terminal.getExits();
					for(Integer i=0;i<exits.length;i++) {
						if(exits[i].getId() == entranceExitId && exits[i].getData() != null && !"".equals(exits[i].getFileName())) {
							Integer temp = sendDocument(exits[i].getData(), exits[i].getFileName());
							ks.slanjeNaPosrednika(exits[i].getFileName(), temp);
							service.isObradjenDokument2(terminalName, entranceExitId, null, "");
						}
					}
				}
				sleep(1000);
			}
		}catch(Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
		}
	}
	
	private Integer sendDocument(byte[] data,String naziv) throws Exception {
		System.setProperty("java.security.policy",Main.CLIENT_POLICY_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		String name = "FajlServer";
		Registry registry = LocateRegistry.getRegistry(1099);
		RMIInterface it = (RMIInterface) registry.lookup(name);
		boolean t = it.sacuvajFajl(data, naziv);
		if(t) {
			return 0;
		}
		return 1;
	}
	
}
