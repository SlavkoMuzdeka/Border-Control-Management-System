package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;

public class ServerThread extends Thread {

	private BufferedReader in;
	private PrintWriter out;
	private SSLSocket socket;
	private SecureServer server;
	public String title;

	public ServerThread(SSLSocket socket, SecureServer ss) {
		super();
		this.socket = socket;
		this.server = ss;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		} catch (IOException ex) {
			Logger.getLogger(SecureServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void run() {
		try {
			showUsers();
			String user = in.readLine();
			server.addUser(user);
			server.send("ALL", "", "New user in communication: " + user, "Broadcast message", this);
			this.title = user;
			String message = "";
			while (!"END".equals(message = in.readLine())) {
				String[] arr = message.split("-");
				String sender = "[" + user + "]";
				String recipient = arr[2];
				String messageToSend = arr[0];
				String messageType = arr[1];
				server.send(recipient, sender, messageToSend, messageType, this);
			}
			server.deleteUser(user, this);
			socket.close();
			in.close();
			out.close();
		} catch (Exception ex) {
			Logger.getLogger(SecureServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void showUsers() {
		if (server.userExists()) {
			out.println("Active applications - " + server.getUsers());
		} else {
			out.println("There is no active applications");
		}
	}

	public void sendMessage(String message) {
		out.println(message);
		out.flush();
	}
}
