package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import application.ClientController;
import application.Main;
import javafx.scene.control.TextArea;

public class Client {

	public SSLSocket s;
	public PrintWriter out;
	public TextArea textArea;
	public String inputNum;
	public String terminalNum;
	public String crossingType;

	public Client(TextArea textArea) {
		super();
		this.textArea = textArea;
		this.inputNum = ClientController.entranceExit + "" + ClientController.id;
		this.terminalNum = ClientController.terminalName;
		this.crossingType = ClientController.crossingType;
	}

	public void execute() throws UnknownHostException, IOException {
		System.setProperty("javax.net.ssl.trustStore", Main.KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", Main.KEY_STORE_PASSWORD);

		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		s = (SSLSocket) sf.createSocket(Main.HOST, Main.PORT);

		new ReadThread(s, this, textArea).start();
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
		String poruka = this.inputNum + "/" + this.terminalNum + "/" + this.crossingType;
		out.println(poruka);
		textArea.appendText("\t\t\t\t\t" + poruka + "\n");
		textArea.appendText("\n");

	}

}
