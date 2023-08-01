package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SecureServer {

	private static final String CONFIG_PATH = "resources/config.properties";
	private static final String BROADCAST = "Broadcast message";
	private static final String MULTICAST = "Multicast message";
	private static final String UNICAST = "Unicast message";
	private static int PORT;
	private static String KEY_STORE_PATH;
	private static String KEY_STORE_PASSWORD;
	private Set<String> users = new HashSet<>();
	private Set<ServerThread> threads = new HashSet<>();
	public Handler handler;

	{
		try {
			handler = new FileHandler("ChatServer.log");
			Logger.getLogger(SecureServer.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(SecureServer.class.getName()).addHandler(handler);
			loadConfig();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void loadConfig() {
		try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
			Properties config = new Properties();
			config.load(fis);
			PORT = Integer.parseInt(config.getProperty("PORT"));
			KEY_STORE_PATH = config.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = config.getProperty("KEY_STORE_PASSWORD");
		} catch (Exception ex) {
			Logger.getLogger(SecureServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public SecureServer() {
		super();
	}

	public void execute() throws IOException {
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);

		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket ss = ssf.createServerSocket(PORT);
		System.out.println("Chat server has started...");

		while (true) {
			SSLSocket s = (SSLSocket) ss.accept();
			ServerThread thread = new ServerThread(s, this);
			threads.add(thread);
			thread.start();
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			SecureServer server = new SecureServer();
			server.execute();
		} catch (Exception ex) {
			Logger.getLogger(SecureServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void send(String recipient, String sender, String message, String messageType,
			ServerThread thread) {
		if (BROADCAST.equals(messageType)) {
			sendBroadcastMessage(sender, message, thread);
		} else if (MULTICAST.equals(messageType)) {
			sendMulticastMessage(sender, recipient, message, thread);
		} else if (UNICAST.equals(messageType)) {
			sendUnicastMessage(sender, recipient, message, thread);
		}
	}

	private void sendBroadcastMessage(String sender, String message, ServerThread thread) {
		for (ServerThread t : threads) {
			if (t != thread) {
				t.sendMessage(sender + " " + message);
			}
		}
	}

	private void sendMulticastMessage(String sender, String recipient, String message, ServerThread thread) {
		for (ServerThread t : threads) {
			if (t != thread && t.title.contains(recipient)) {
				t.sendMessage(sender + " " + message);
			}
		}
	}

	private void sendUnicastMessage(String sender, String recipient, String message, ServerThread thread) {
		for (ServerThread t : threads) {
			if (t != thread && t.title.equals(recipient)) {
				t.sendMessage(sender + " " + message);
			}
		}
	}

	public void addUser(String user) {
		this.users.add(user);
	}

	public void deleteUser(String user, ServerThread thread) {
		boolean deleted = users.remove(user);
		if (deleted) {
			threads.remove(thread);
		}
	}

	public boolean userExists() {
		return !this.users.isEmpty();
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

	public Set<ServerThread> getThreads() {
		return threads;
	}

	public void setThreads(Set<ServerThread> threads) {
		this.threads = threads;
	}

}
