package service;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper;
import de.undercouch.bson4jackson.BsonFactory;
import model.*;

public class TerminalService {

	public static ArrayList<Terminal> terminals = new ArrayList<>();
	public static String PATH_TERMINALS;
	public static String PATH_TERMINAL_NUMBERS;
	public static String PATH_PASSENGERS;
	public static Handler handler;

	public static final String EXT = ".ser";
	public static final String ENTRANCE = "Entrance";
	public static final String EXIT = "Exit";
	public static final String POLICE = "Police";
	public static final String CUSTOMS = "Customs";

	static {
		try {
			handler = new FileHandler("TerminalService.log");
			Logger.getLogger(TerminalService.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(TerminalService.class.getName()).addHandler(handler);
			loadConfig();
			deserializeTerminals();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void loadConfig() {
		try (FileInputStream fis = new FileInputStream(".\\src\\resources\\config.properties")) {
			Properties config = new Properties();
			config.load(fis);
			PATH_TERMINALS = config.getProperty("PATH_TERMINALS");
			PATH_TERMINAL_NUMBERS = config.getProperty("PATH_TERMINAL_NUMBERS");
			PATH_PASSENGERS = config.getProperty("PATH_PASSENGERS");
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	private static void deserializeTerminals() {
		try {
			ArrayList<String> serializedTerminals = new ArrayList<>();
			File file = new File(PATH_TERMINALS);
			if (file.listFiles().length != 0) {
				for (File f : file.listFiles()) {
					serializedTerminals.add(f.getName());
				}
				Terminal terminal = null;
				for (Integer i = 0; i < serializedTerminals.size(); i++) {
					String s = serializedTerminals.get(i);
					String str = s.substring(0, s.lastIndexOf('.'));
					Integer num = Integer.valueOf(str);
					if (num % 4 == 0) {
						terminal = javaDeserialization(str);
					} else if (num % 4 == 1) {
						terminal = kryoDeserialization(str);
					} else if (num % 4 == 2) {
						terminal = smileDeserialization(str);
					} else if (num % 4 == 3) {
						terminal = bsonDeserialization(str);
					}
					terminals.add(terminal);
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public boolean createTerminals(String name, Integer numberOfEntrances, Integer numberOfExits) {
		try {
			Terminal terminal = new Terminal(name, numberOfEntrances, numberOfExits);
			Integer rbT = terminal.getId();
			if (rbT % 4 == 0) {
				javaSerialization(terminal);
			} else if (rbT % 4 == 1) {
				kryoSerialization(terminal);
			} else if (rbT % 4 == 2) {
				smileSerialization(terminal);
			} else if (rbT % 4 == 3) {
				bsonSerialization(terminal);
			}
			terminals.add(terminal);
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return true;
	}

	private void javaSerialization(Terminal terminal) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(PATH_TERMINALS + File.separator + terminal.getId() + EXT));
		oos.writeObject(terminal);
		oos.flush();
		oos.close();
	}

	private static Terminal javaDeserialization(String str)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH_TERMINALS + File.separator + str + EXT));
		Terminal terminal = (Terminal) ois.readObject();
		ois.close();
		return terminal;
	}

	private void kryoSerialization(Terminal terminal) throws FileNotFoundException {
		Kryo kryo = createKryoObject();
		Output out = new Output(new FileOutputStream(PATH_TERMINALS + File.separator + terminal.getId() + EXT));
		kryo.writeClassAndObject(out, terminal);
		out.flush();
		out.close();
	}

	private static Terminal kryoDeserialization(String str) throws FileNotFoundException {
		Input input = new Input(new FileInputStream(PATH_TERMINALS + File.separator + str + EXT));
		Kryo kryo = createKryoObject();
		kryo.register(Terminal.class);
		kryo.register(Exit.class);
		kryo.register(Entrance.class);
		Terminal terminal = (Terminal) kryo.readClassAndObject(input);
		input.close();
		return terminal;
	}

	private static Kryo createKryoObject() {
		Kryo kryo = new Kryo();
		kryo.register(Terminal.class);
		kryo.register(Exit.class);
		kryo.register(Entrance.class);
		return kryo;
	}

	private void smileSerialization(Terminal terminal) throws IOException {
		SmileMapper mapper = new SmileMapper();
		byte[] data = mapper.writeValueAsBytes(terminal);
		FileOutputStream fos = new FileOutputStream(PATH_TERMINALS + File.separator + terminal.getId() + EXT);
		fos.write(data);
		fos.flush();
		fos.close();
	}

	private static Terminal smileDeserialization(String str) throws FileNotFoundException, IOException {
		SmileMapper mapper = new SmileMapper();
		Terminal terminal = mapper.readValue(new FileInputStream(PATH_TERMINALS + File.separator + str + EXT),
				Terminal.class);
		return terminal;
	}

	private void bsonSerialization(Terminal terminal) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new BsonFactory());
		FileOutputStream fos = new FileOutputStream(PATH_TERMINALS + File.separator + terminal.getId() + EXT);
		mapper.writeValue(fos, terminal);
		fos.close();
	}

	private static Terminal bsonDeserialization(String str)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectMapper mapper = new ObjectMapper(new BsonFactory());
		FileInputStream fis = new FileInputStream(PATH_TERMINALS + File.separator + str + EXT);
		Terminal terminal = mapper.readValue(fis, Terminal.class);
		fis.close();
		return terminal;
	}

	public boolean updateTerminals(String terminalName, Integer numberOfEntrances, Integer numberOfExits) {
		try {
			Terminal terminal = getTerminal(terminalName);
			if (terminal != null) {
				Integer idTerminala = terminal.getId();
				terminal.setNaziv(terminalName);
				terminal.setNumberOfEntrances(numberOfEntrances);
				terminal.setNumberOfExits(numberOfExits);
				deleteFile(idTerminala);
				if (idTerminala % 4 == 0) {
					javaSerialization(terminal);
				} else if (idTerminala % 4 == 1) {
					kryoSerialization(terminal);
				} else if (idTerminala % 4 == 2) {
					smileSerialization(terminal);
				} else if (idTerminala % 4 == 3) {
					bsonSerialization(terminal);
				}
				return true;
			}
			return false;
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	public boolean deleteTerminal(String terminalName) {
		Terminal terminal = getTerminal(terminalName);
		if (terminal != null) {
			terminals.remove(terminal);
			deleteFile(terminal.getId());
			return true;
		}
		return false;
	}

	public Terminal getTerminal(String terminalName) {
		Terminal terminal = null;
		for (Terminal t : terminals) {
			if (t.getNaziv().equals(terminalName)) {
				terminal = t;
			}
		}
		return terminal;
	}

	public boolean setIsAvailable(boolean t, String terminalName, Integer idUI, String ulazIzlaz, String tipUlazIzlaz) {
		Terminal terminal = getTerminal(terminalName);
		if (terminal != null && tipUlazIzlaz == null) {
			if (ENTRANCE.equals(ulazIzlaz)) {
				Entrance[] entrances = terminal.getEntrances();
				for (Integer i = 0; i < entrances.length; i++) {
					if (idUI == entrances[i].getId()) {
						entrances[i].setIsAvailable(t);
					}
				}
			} else if (EXIT.equals(ulazIzlaz)) {
				Exit[] exits = terminal.getExits();
				for (Integer i = 0; i < exits.length; i++) {
					if (idUI == exits[i].getId()) {
						exits[i].setIsAvailable(t);
					}
				}
			}
		} else if (terminal != null && tipUlazIzlaz != null) {
			if (POLICE.equals(tipUlazIzlaz) && ENTRANCE.equals(ulazIzlaz)) {
				Entrance[] entrances = terminal.getEntrances();
				for (Integer i = 0; i < entrances.length; i++) {
					if (idUI == entrances[i].getId()) {
						entrances[i].setIsPoliceControlAvailable(t);
					}
				}
			} else if (POLICE.equals(tipUlazIzlaz) && EXIT.equals(ulazIzlaz)) {
				Exit[] exits = terminal.getExits();
				for (Integer i = 0; i < exits.length; i++) {
					if (idUI == exits[i].getId()) {
						exits[i].setIsPoliceControlAvailable(t);
					}
				}
			} else if (CUSTOMS.equals(tipUlazIzlaz) && ENTRANCE.equals(ulazIzlaz)) {
				Entrance[] entrances = terminal.getEntrances();
				for (Integer i = 0; i < entrances.length; i++) {
					if (idUI == entrances[i].getId()) {
						entrances[i].setIsCustomControlAvailable(t);
					}
				}
			} else if (CUSTOMS.equals(tipUlazIzlaz) && EXIT.equals(ulazIzlaz)) {
				Exit[] exits = terminal.getExits();
				for (Integer i = 0; i < exits.length; i++) {
					if (idUI == exits[i].getId()) {
						exits[i].setIsCustomsControlAvailable(t);
					}
				}
			}
		}
		return true;
	}

	private void deleteFile(Integer id) {
		try {
			File file = new File(PATH_TERMINALS + File.separator + id + EXT);
			file.delete();
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public String getCheckedPassengers() {
		String putnici = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(PATH_PASSENGERS));
			String s = "";
			while ((s = in.readLine()) != null) {
				putnici += s + "\n";
			}
			in.close();
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return putnici;
	}

	public void addCheckedPassenger(Integer passengerId) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(PATH_PASSENGERS, true)))) {
			out.println(passengerId);
			out.flush();
		} catch (Exception ex) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public boolean personCheck1(String terminalName, Integer entranceId, Integer personId) {
		Terminal terminal = getTerminal(terminalName);
		for (Integer i = 0; i < terminal.getEntrances().length; i++) {
			if (terminal.getEntrances()[i].getId() == entranceId) {
				terminal.getEntrances()[i].setPersonId(personId);
				return true;
			}
		}
		return false;
	}

	public boolean personCheck2(String terminalName, Integer idIzlaza, Integer personId) {
		Terminal terminal = getTerminal(terminalName);
		for (Integer i = 0; i < terminal.getExits().length; i++) {
			if (terminal.getExits()[i].getId() == idIzlaza) {
				terminal.getExits()[i].setPersonId(personId);
				return true;
			}
		}
		return false;
	}

	public boolean isDocumentProcessed1(String terminalName, Integer entranceId, byte[] data, String zipFile) {
		Terminal terminal = getTerminal(terminalName);
		for (Integer i = 0; i < terminal.getEntrances().length; i++) {
			if (terminal.getEntrances()[i].getId() == entranceId) {
				terminal.getEntrances()[i].setData(data);
				terminal.getEntrances()[i].setFileName(zipFile);
				return true;
			}
		}
		return false;
	}

	public boolean isObradjenDokument2(String terminalName, Integer idIzlaza, byte[] data, String zipFile) {
		Terminal terminal = getTerminal(terminalName);
		for (Integer i = 0; i < terminal.getExits().length; i++) {
			if (terminal.getExits()[i].getId() == idIzlaza) {
				terminal.getExits()[i].setData(data);
				terminal.getExits()[i].setFileName(zipFile);
				return true;
			}
		}
		return false;
	}

	public void blokirajTerminal(boolean t, String terminalName) {
		Terminal terminal = getTerminal(terminalName);
		terminal.setIsBlocked(t);
	}

}