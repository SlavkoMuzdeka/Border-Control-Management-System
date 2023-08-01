package servisKlijent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;
import model.Exit;
import model.Terminal;
import model.Entrance;
import service.TerminalServis;
import service.TerminalServisServiceLocator;

public class TestClientService {
	
	public static Handler handler;

	private static HashMap<Integer,Integer> returnValue1 = new HashMap<>();
	private static HashMap<Integer,Integer> testMediator1 = new HashMap<>();
	private static HashMap<Integer,Integer> policeMediator = new HashMap<>();
	
	private static HashMap<Integer,Integer> returnValue2 = new HashMap<>();
	private static HashMap<String,Integer> testMediator2 = new HashMap<>();
	private static HashMap<String,Integer> customsMediator = new HashMap<>();
	
    static {
    	try {
    		handler = new FileHandler("TestClientServer.log");
    		Logger.getLogger(TestClientService.class.getName()).setUseParentHandlers(false);
    		Logger.getLogger(TestClientService.class.getName()).addHandler(handler);
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static boolean terminalExists(String terminalName) {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			if(service.dohvatiTerminal(terminalName) != null) {
				return true;
			}
		}catch(Exception ex) {
			Logger.getLogger(TestClientService.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}
    
    public boolean isCrossingActive(String crossingType,Integer crossingId,String terminalName) {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			Terminal terminal = service.dohvatiTerminal(terminalName);
			if("Entrance".equals(crossingType)) {
				Entrance[] entrances = terminal.getEntrances();
				for(Integer i=0;i<entrances.length;i++) {
					if(entrances[i].getId() == crossingId && !entrances[i].getIsAvailable()) {
						return true;
					}
				}
			}else {
				Exit[] exits = terminal.getExits();
				for(Integer i=0;i<exits.length;i++) {
					if(exits[i].getId() == crossingId && !exits[i].getIsAvailable()) {
						return true;
					}
				}
			}
			return false;
		}catch(Exception ex) {
			Logger.getLogger(TestClientService.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
			return false;
		}
	}
    
    public void checkidFromTestApplication(Integer personId,Integer testAppId, String terminalName, String crossingType, Integer crossingId) {
    	try {
    		testMediator1.put(personId, testAppId);
    		returnValue1.put(testAppId, -1);
    		sendCheckOnPoliceControl(terminalName, crossingType, crossingId, personId);
    	}catch(Exception ex) {
    		Logger.getLogger(TestClientService.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
    	}
    }
    
    private void sendCheckOnPoliceControl(String terminalName, String crossingType, Integer crossingId, Integer personId) throws Exception {
    	TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
		TerminalServis service = loc.getTerminalServis();
		Terminal terminal = service.dohvatiTerminal(terminalName);
		if("Entrance".equals(crossingType)) {
			Entrance[] entrances = terminal.getEntrances();
			for(Integer i=0;i<entrances.length;i++) {
				if(entrances[i].getId() == crossingId) {
					service.provjeraOsobe1(terminalName, crossingId, personId);
					break;
				}
			}
		}else {
			Exit[] exits = terminal.getExits();
			for(Integer i=0;i<exits.length;i++) {
				if(exits[i].getId() == crossingId) {
					service.provjeraOsobe2(terminalName, crossingId, personId);
					break;
				}
			}
		}
    }
    
    public void sendFromPoliceControl(Integer personId, Integer temp) {
    	policeMediator.put(personId, temp);
    	Integer pom = testMediator1.get(personId);
    	returnValue1.put(pom, temp);
    } 
    
    public Integer onWarrant(Integer idAplikacije) {
    	return returnValue1.get(idAplikacije);
    }
    
    public Integer isDocumentSent(Integer testAppId,byte[] data, String terminalName, String crossingType, Integer crossingId, String nazivZipFajla) {
    	try {
    		testMediator2.put(nazivZipFajla, testAppId);
    		returnValue2.put(testAppId, -1);
    		processOnCustomsControl(terminalName, crossingType, crossingId, data, nazivZipFajla);
    	}catch(Exception ex) {
    		Logger.getLogger(TestClientService.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
    	}
    	return 0;
    }
    
    private void processOnCustomsControl(String terminalName, String crossingType, Integer crossingId, byte[] data, String nazivZipFajla) throws ServiceException, RemoteException {
    	TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
		TerminalServis service = loc.getTerminalServis();
		Terminal terminal = service.dohvatiTerminal(terminalName);
		if("Entrance".equals(crossingType)) {
			Entrance[] entrances = terminal.getEntrances();
			for(Integer i=0;i<entrances.length;i++) {
				if(entrances[i].getId() == crossingId) {
					service.isObradjenDokument1(terminalName, crossingId, data, nazivZipFajla);
					break;
				}
			}
		}else {
			Exit[] exits = terminal.getExits();
			for(Integer i=0;i<exits.length;i++) {
				if(exits[i].getId() == crossingId) {
					service.isObradjenDokument2(terminalName, crossingId, data, nazivZipFajla);
					break;
				}
			}
		}
    }
    
    public void sendToMediator(String name, Integer temp) {
    	customsMediator.put(name, temp);
    	Integer pom = testMediator2.get(name);
    	returnValue2.put(pom, temp);
    }
    
    public Integer isDocumentSaved(Integer idAplikacije) {
    	return returnValue2.get(idAplikacije);
    }
      
}
