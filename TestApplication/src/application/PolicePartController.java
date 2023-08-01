package application;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import servisKlijent.KlijentServis;
import servisKlijent.KlijentServisServiceLocator;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

public class PolicePartController {
	
	@FXML private Button checkBtn;
	@FXML private TextField personIdField;
	
	private String terminalName = LoginController.terminalName;
	private String entranceExit = LoginController.entranceExit;
	private Integer entranceExitId = Integer.parseInt(LoginController.entranceExitId);
	private Integer id = loadId();
	
	public static String personId;
	private static final String PATH = "IdAplikacije.txt";
	
	public void check(ActionEvent e) {
		if(personIdField.getText() == null || "".equals(personIdField.getText())) {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Enter person id.");
		}else {
			if(isValueInteger()) {
				try {
					KlijentServisServiceLocator loc = new KlijentServisServiceLocator();
					KlijentServis ks = loc.getKlijentServis();
					ks.provjeriIdSaTestneAplikacije(Integer.parseInt(personIdField.getText()), this.id, terminalName, entranceExit, entranceExitId);
					Integer value = -1;
					while(value == -1) {
						value = ks.naPotjernici(this.id);
						Thread.sleep(1000);
					}
					Node node = (Node)e.getSource();
					Stage s = (Stage) node.getScene().getWindow();
					s.close();
					if(value == 0) {
						personId = personIdField.getText();
						loadCustomsPart();
					}else {
						AlertShow.showAlert(Alert.AlertType.INFORMATION, "Announcements", "The person is on an APB.");
						sendOnWarrant(personIdField.getText());
						Dialog<String> dialog = new Dialog<String>();
						ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.setContentText("Have you processed the person?");
						dialog.getDialogPane().getButtonTypes().add(type);
						dialog.showAndWait();
						TerminalServisServiceLocator locator = new TerminalServisServiceLocator();
						TerminalServis servis = locator.getTerminalServis();
						servis.blokirajTerminal(false, terminalName);
					}
				}catch(Exception ex) {
					Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
				}
			}else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "The id of the person must be an integer.");
			}
		}
		personIdField.setText("");
	}
	
	private boolean isValueInteger() {
		try {
			Integer.parseInt(personIdField.getText());
		}catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	private Integer loadId() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(PATH));
			Integer id = Integer.valueOf(bf.readLine());
			bf.close();
			Integer nextNum = id + 1;
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(PATH)));
			pw.println(nextNum);
			pw.close();
			return id;
		}catch(Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
			return 0;
		}
	}
	
	private void loadCustomsPart() throws IOException {
		AnchorPane anchorPane = (AnchorPane)FXMLLoader.load(getClass().getResource("CustomsPartForm.fxml"));
		Scene scene = new Scene(anchorPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Customs part of the terminal");
		stage.setResizable(false);
		stage.show();
	}
	
	private boolean sendOnWarrant(String personId) throws Exception{
		OutputStream os = null;
		HttpURLConnection conn = null;
			try {
				URL url = new URL(Main.URL_CENTRAL_REGISTRY);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				os = conn.getOutputStream();
				os.write(personId.getBytes());
				os.flush();
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return false;
				}
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
				return false;
			} finally {
				try {
					if(os != null) {
						os.close();
					}
					if(conn != null) {
						conn.disconnect();
					}
				}catch(Exception ex) {
					Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
				}
			}
			return true;
	}
							
}
