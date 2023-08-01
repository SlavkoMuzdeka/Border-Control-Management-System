package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Terminal;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import servisKlijent.KlijentServis;
import servisKlijent.KlijentServisServiceLocator;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class LoginController implements Initializable {

	@FXML
	private Button loginBtn;
	@FXML
	private TextField idField;
	@FXML
	private TextField terminalNameField;
	@FXML
	private ChoiceBox<String> crossingTypeBox;

	public static String terminalName;
	public static String entranceExit;
	public static String entranceExitId;

	public void login(ActionEvent e) {
		if (fieldsAreNotEmpty()) {
			if (valuesAreIntegers()) {
				if (terminalExists()) {
					if (isCrossingAvaliable()) {
						if (!isTerminalBlocked()) {
							try {
								Node node = (Node) e.getSource();
								Stage s = (Stage) node.getScene().getWindow();
								s.close();
								terminalName = terminalNameField.getText();
								entranceExit = crossingTypeBox.getSelectionModel().getSelectedItem();
								entranceExitId = idField.getText();
								loadPolicePart();
							} catch (Exception ex) {
								Logger.getLogger(Main.class.getName()).log(Level.WARNING,
										ex.fillInStackTrace().toString());
							}
						} else {
							AlertShow.showAlert(Alert.AlertType.ERROR, "Try again later",
									(crossingTypeBox.getSelectionModel().getSelectedItem().equals("Entrance")
											? "Entrance"
											: "Exit") + " on the terminal is temporarily blocked.");
						}
					} else {
						AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
								"Both customs and police applications must be launched");
					}
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
							"The terminal with the name does not exist.");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Id must be an integer value.");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Pokusajte ponovo", "All fields must be filled in.");
		}
		resetFields();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		crossingTypeBox.getItems().add("Entrance");
		crossingTypeBox.getItems().add("Exit");
	}

	private boolean fieldsAreNotEmpty() {
		if (terminalNameField.getText() == null || terminalNameField.getText() == "") {
			return false;
		} else if (idField.getText() == null || idField.getText() == "") {
			return false;
		} else if (crossingTypeBox.getValue() == null || "".equals(crossingTypeBox.getValue())) {
			return false;
		}
		return true;
	}

	private void resetFields() {
		terminalNameField.setText("");
		idField.setText("");
	}

	private boolean valuesAreIntegers() {
		try {
			Integer.parseInt(idField.getText());
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	private boolean terminalExists() {
		try {
			KlijentServisServiceLocator loc = new KlijentServisServiceLocator();
			KlijentServis service = loc.getKlijentServis();
			if (!service.postojiTerminal(terminalNameField.getText())) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	private boolean isCrossingAvaliable() {
		int idPrelaza = Integer.parseInt(idField.getText());
		String vrstaPrelaza = crossingTypeBox.getSelectionModel().getSelectedItem();
		try {
			KlijentServisServiceLocator loc = new KlijentServisServiceLocator();
			KlijentServis service = loc.getKlijentServis();
			if (service.aktivanPrelaz(vrstaPrelaza, idPrelaza, terminalNameField.getText())) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

	private void loadPolicePart() throws IOException {
		AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("PolicePartForm.fxml"));
		Scene scene = new Scene(anchorPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Police section of the terminal");
		stage.setResizable(false);
		stage.show();
	}

	public boolean isTerminalBlocked() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			Terminal terminal = service.dohvatiTerminal(terminalNameField.getText());
			boolean t = terminal.getIsBlocked();
			if (t) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return true;
		}
		return false;
	}

}
