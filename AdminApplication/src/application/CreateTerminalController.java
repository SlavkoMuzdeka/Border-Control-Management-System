package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.*;
import javafx.scene.control.Alert;

public class CreateTerminalController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField numberOfEntrancesField;
	@FXML
	private TextField numberOfExitsField;
	@FXML
	private Button createBtn;

	@FXML
	public void create(ActionEvent event) {
		if (fieldAreNotEmpty()) {
			if (valuesAreValid()) {
				if (isCreated()) {
					AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful creation",
							"Uspjesno ste kreirali terminal.");
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Terminal Creation Failure");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "You did not enter the I/O number correctly");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in");
		}
		resetFields();
	}

	private boolean fieldAreNotEmpty() {
		if (nameField.getText() == null || nameField.getText() == "") {
			return false;
		} else if (numberOfEntrancesField.getText() == null || numberOfEntrancesField.getText() == "") {
			return false;
		} else if (numberOfExitsField.getText() == null || numberOfExitsField.getText() == "") {
			return false;
		}
		return true;
	}

	private boolean valuesAreValid() {
		try {
			nameField.getText();
			Integer.parseInt(numberOfEntrancesField.getText());
			Integer.parseInt(numberOfExitsField.getText());
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	private void resetFields() {
		nameField.setText("");
		numberOfEntrancesField.setText("");
		numberOfExitsField.setText("");
	}

	private boolean isCreated() {
		try {
			String name = nameField.getText();
			Integer numberOfEntrances = Integer.parseInt(numberOfEntrancesField.getText());
			Integer numberOfExits = Integer.parseInt(numberOfExitsField.getText());
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			if (service.kreirajTerminal(name, numberOfEntrances, numberOfExits)) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

}
