package application;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteCredentialsForm {

	@FXML
	private TextField usernameField;
	@FXML
	private Button deleteBtn;

	private static final String DELETE = "DELETE";

	public void delete(ActionEvent e) {
		if (usernameField.getText() != null && usernameField.getText() != "") {
			if (areCredentialsDeleted(usernameField.getText())) {
				AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful deletion",
						"You've successfully erased the credentials.");
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Unsuccessful deletion of the credentials.");
			}
			usernameField.setText("");
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "You must enter a user name");
		}
		usernameField.setText("");
	}

	public boolean areCredentialsDeleted(String username) {
		try {
			URL url = new URL(Main.URL_MEMORY_SERVER + username);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(DELETE);
			OutputStream os = conn.getOutputStream();
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			}
			os.close();
			conn.disconnect();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return true;
	}

}
