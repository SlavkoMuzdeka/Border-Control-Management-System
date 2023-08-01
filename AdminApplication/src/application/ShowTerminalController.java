package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Terminal;

public class ShowTerminalController implements Initializable {

	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField numberOfEntrancesField;
	@FXML
	private TextField numberOfExitsField;

	public static Terminal terminal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (terminal != null) {
			idField.setText(String.valueOf(terminal.getId()));
			nameField.setText(terminal.getNaziv());
			numberOfEntrancesField.setText(String.valueOf(terminal.getNumberOfEntrances()));
			numberOfExitsField.setText(String.valueOf(terminal.getNumberOfExits()));
		}
	}

}
