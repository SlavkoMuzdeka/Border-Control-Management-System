package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientApplicationController implements Initializable {

	@FXML
	private Button changePasswordBtn;
	@FXML
	private Button showEvidenceBtn;
	@FXML
	private Button logoutBtn;
	@FXML
	private Button sendBtn;
	@FXML
	private BorderPane borderPane;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField textField;
	@FXML
	private ChoiceBox<String> mesageTypeBox;
	@FXML
	private TextField applicationNameField;
	@FXML
	private Label label1;
	@FXML
	private Label label2;

	public Client client;
	public RegisterClient regClient;
	public CustomsClient customsClient;

	private static final String END = "END";
	private static final String UNICAST = "Unicast message";
	private static final String MULTICAST = "Multicast message";
	private static final String BROADCAST = "Broadcast message";
	private static final String POLICE = "Police";
	private static final String CUSTOMS = "Customs";

	public void changePassword(ActionEvent e) {
		try {
			AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("ChangePasswordForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void showEvidence(ActionEvent e) {
		try {
			AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("ShowEvidenceForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void logout(ActionEvent e) {
		try {
			client.out.println(END);
			Node node = (Node) e.getSource();
			Stage s = (Stage) node.getScene().getWindow();
			s.close();
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("ClientForm.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Sign in to the client application");
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void sendMessage(ActionEvent e) {
		try {
			Node node = (Node) e.getSource();
			Stage s = (Stage) node.getScene().getWindow();
			s.setOnCloseRequest(ev -> client.out.println("END"));
			if (fieldsAreFilledIn()) {
				String pom = mesageTypeBox.getSelectionModel().getSelectedItem();
				if (UNICAST.equals(pom)) {
					if (applicationNameField.getText().contains("/")) {
						client.out.println(textField.getText() + "-" + pom + "-" + applicationNameField.getText());
					}
				} else if (MULTICAST.equals(pom)) {
					if (!applicationNameField.getText().contains("/")) {
						client.out.println(textField.getText() + "-" + pom + "-" + applicationNameField.getText());
					}
				} else if (BROADCAST.equals(pom)) {
					client.out.println(textField.getText() + "-" + pom + "-" + "ALL");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Fill in all fields.");
			}
			textField.setText("");
			applicationNameField.setText("");
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	private boolean fieldsAreFilledIn() {
		if (mesageTypeBox.getValue() == null) {
			return false;
		} else {
			String str = mesageTypeBox.getSelectionModel().getSelectedItem();
			if (UNICAST.equals(str)) {
				if (applicationNameField.getText() == null || applicationNameField.getText().equals("")) {
					return false;
				}
			} else if (MULTICAST.equals(str)) {
				if (applicationNameField.getText() == null || applicationNameField.getText().equals("")) {
					return false;
				}
			}
		}
		if (textField.getText() == null || textField.getText().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mesageTypeBox.getItems().add(UNICAST);
		mesageTypeBox.getItems().add(MULTICAST);
		mesageTypeBox.getItems().add(BROADCAST);
		mesageTypeBox.setOnAction(event -> {
			String str = mesageTypeBox.getSelectionModel().getSelectedItem();
			if (BROADCAST.equals(str)) {
				applicationNameField.setVisible(false);
				label1.setVisible(false);
				label2.setVisible(false);
			} else if (MULTICAST.equals(str)) {
				applicationNameField.setVisible(true);
				label1.setVisible(true);
				label2.setVisible(false);
			} else {
				applicationNameField.setVisible(true);
				label1.setVisible(false);
				label2.setVisible(true);
			}
		});
		try {
			client = new Client(textArea);
			client.execute();
			if (POLICE.equals(ClientController.crossingType)) {
				regClient = new RegisterClient(ClientController.terminalName, ClientController.entranceExit,
						Integer.parseInt(ClientController.id), client);
				regClient.start();
			} else if (CUSTOMS.equals(ClientController.crossingType)) {
				customsClient = new CustomsClient(ClientController.terminalName, ClientController.entranceExit,
						Integer.parseInt(ClientController.id));
				customsClient.start();
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
