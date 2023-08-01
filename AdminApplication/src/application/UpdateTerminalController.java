package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Terminal;
import service.*;

public class UpdateTerminalController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField numberOfEntrancesField;
	@FXML
	private TextField numberOfExitsField;
	@FXML
	private Button updateBtn;

	@FXML
	public void update(ActionEvent event) {
		if (fieldsAreNotEmpty()) {
			if (valuesAreValid()) {
				Terminal terminal = getTerminal();
				if (terminal != null) {
					if (isTerminalUpdated()) {
						AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful updating",
								"You've successfully updated the terminal.");
					} else {
						AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Terminal updating failure");
					}
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
							"The terminal with the given name does not exist.");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "You did not enter the I/O number correctly.");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in.");
		}
		resetFields();
	}

	private boolean fieldsAreNotEmpty() {
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
			Integer.parseInt(numberOfEntrancesField.getText());
			Integer.parseInt(numberOfExitsField.getText());
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	private Terminal getTerminal() {
		Terminal terminal = null;
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			terminal = service.dohvatiTerminal(nameField.getText());
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return terminal;
	}

	private void resetFields() {
		nameField.setText("");
		numberOfEntrancesField.setText("");
		numberOfExitsField.setText("");
	}

	private boolean isTerminalUpdated() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			if (service.azurirajTerminal(nameField.getText(), Integer.parseInt(numberOfEntrancesField.getText()),
					Integer.parseInt(numberOfExitsField.getText()))) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

}
