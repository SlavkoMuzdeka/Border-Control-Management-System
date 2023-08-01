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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangePasswordController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField newPasswordField;
	@FXML
	private Button changeBtn;

	private static final String PUT = "PUT";

	public void changePassword(ActionEvent e) {
		if (fieldsAreNotEmpty()) {
			if (isPasswordChanged(usernameField.getText(), newPasswordField.getText())) {
				AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successfully update",
						"You have successfully changed your password.");
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Failed password change.");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in.");
		}
		resetFields();
	}

	private boolean fieldsAreNotEmpty() {
		if (usernameField.getText() == null || usernameField.getText() == "") {
			return false;
		} else if (newPasswordField.getText() == null || newPasswordField.getText() == "") {
			return false;
		}
		return true;
	}

	private void resetFields() {
		usernameField.setText("");
		newPasswordField.setText("");
	}

	public boolean isPasswordChanged(String username, String password) {
		try {
			URL url = new URL(Main.URL_MEMORY_SERVER + username);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(PUT);
			OutputStream os = conn.getOutputStream();
			os.write(password.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			}
			os.close();
			conn.disconnect();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return true;
	}
}
