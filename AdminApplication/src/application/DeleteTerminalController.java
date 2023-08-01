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

public class DeleteTerminalController {

	@FXML
	private TextField nameField;
	@FXML
	private Button deleteBtn;

	@FXML
	public void delete(ActionEvent event) {
		if (nameField.getText() == null || nameField.getText() == "") {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "The field must be filled in.");
		} else {
			if (isNameValid()) {
				if (isDeleted()) {
					AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful deletion",
							"You successfully deleted the terminal.");
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Terminal deletion failure");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
						"You did not enter the terminal number correctly.");
			}
		}
		nameField.setText("");
	}

	private boolean isNameValid() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			Terminal terminal = service.dohvatiTerminal(nameField.getText());
			if (terminal != null) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

	private boolean isDeleted() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			if (service.obrisiTerminal(nameField.getText())) {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

}
