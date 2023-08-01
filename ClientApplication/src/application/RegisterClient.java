package application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.Client;
import model.Entrance;
import model.Exit;
import model.Terminal;
import server.RegistarInterface;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import servisKlijent.KlijentServis;
import servisKlijent.KlijentServisServiceLocator;

public class RegisterClient extends Thread {

	private String terminalName;
	private String entranceExit;
	private Integer entranceExitId;
	private Client client;

	public RegisterClient(String terminalName, String entranceExit, Integer entranceExitId, Client client) {
		super();
		this.terminalName = terminalName;
		this.entranceExit = entranceExit;
		this.entranceExitId = entranceExitId;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			KlijentServisServiceLocator locator = new KlijentServisServiceLocator();
			KlijentServis ks = locator.getKlijentServis();

			while (true) {
				Terminal terminal = service.dohvatiTerminal(terminalName);
				if ("Ulaz".equals(entranceExit)) {
					Entrance[] entrances = terminal.getEntrances();
					for (int i = 0; i < entrances.length; i++) {
						if (entrances[i].getId() == entranceExitId && entrances[i].getPersonId() != -1) {
							int pom = entrances[i].getPersonId();
							int temp = rmiCheck(pom);
							if (temp == 1) {
								sendMulticastMessage1();
								service.blokirajTerminal(true, terminalName);
							}
							ks.posaljiSaPolicijskeKontrole(pom, temp);
							service.provjeraOsobe1(terminalName, entranceExitId, -1);
							if (temp == 1) {
								while (true) {
									terminal = service.dohvatiTerminal(terminalName);
									if (!terminal.getIsBlocked()) {
										sendMulticastMessage2();
										break;
									}
									Thread.sleep(200);
								}
							}
						}
					}
				} else {
					Exit[] exits = terminal.getExits();
					for (int i = 0; i < exits.length; i++) {
						if (exits[i].getId() == entranceExitId && exits[i].getPersonId() != -1) {
							int pom = exits[i].getPersonId();
							int temp = rmiCheck(pom);
							if (temp == 1) {
								sendMulticastMessage1();
								service.blokirajTerminal(true, terminalName);
							}
							ks.posaljiSaPolicijskeKontrole(pom, temp);
							service.provjeraOsobe2(terminalName, entranceExitId, -1);
							if (temp == 1) {
								while (true) {
									terminal = service.dohvatiTerminal(terminalName);
									if (!terminal.getIsBlocked()) {
										sendMulticastMessage2();
										break;
									}
									Thread.sleep(200);
								}
							}
						}
					}
				}
				sleep(1000);
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	private Integer rmiCheck(int idOsobe) throws Exception {
		System.setProperty("java.security.policy", Main.CLIENT_POLICY_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		String name = "Registar";
		Registry registry = LocateRegistry.getRegistry(1000);
		RegistarInterface registarPotjernica = (RegistarInterface) registry.lookup(name);
		boolean t = registarPotjernica.provjeraOsobe(idOsobe);
		if (t) {
			return 1;
		}
		return 0;
	}

	private void sendMulticastMessage1() {
		client.out.println(
				"The person on the warrant registry has been detected!" + "-" + "Multicast message" + "-" + client.terminalNum);
	}

	private void sendMulticastMessage2() {
		client.out.println("The person is processed." + "-" + "Multicast message" + "-" + client.terminalNum);
	}

}
