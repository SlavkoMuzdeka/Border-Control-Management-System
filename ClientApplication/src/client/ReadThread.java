package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import application.Main;
import javafx.scene.control.TextArea;

public class ReadThread extends Thread{
	private SSLSocket socket;
	private Client client;
	private BufferedReader in;
	public TextArea textArea;
	
	public ReadThread(SSLSocket socket,Client client,TextArea textArea) throws IOException {
		super();
		this.socket = socket;
		this.client = client;
		this.textArea = textArea;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				String response = in.readLine();
				textArea.appendText(response + "\n");
			}catch(Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
				break;
			}
		}
	}

	public SSLSocket getSocket() {
		return socket;
	}
	public void setSocket(SSLSocket socket) {
		this.socket = socket;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}		
}
