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

public class ChangePasswordController {

	@FXML
	private PasswordField oldPasswordField;
	@FXML
	private PasswordField newPasswordField;
	@FXML
	private Button changeBtn;

	private static final String PUT = "PUT";

	public void changePassword(ActionEvent e) {
		if (fieldsAreNotEmpty()) {
			if (LoginController.pass.equals(oldPasswordField.getText())) {
				String username = LoginController.username;
				String password = newPasswordField.getText();
				if (isPasswordUpdated(username, password)) {
					AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful updating",
							"You have successfully updated your password.");
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Failed password update.");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "You entered the 'old' password incorrectly.");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in.");
		}
		resetFields();
	}

	private boolean fieldsAreNotEmpty() {
		if (oldPasswordField.getText() == null || oldPasswordField.getText() == "") {
			return false;
		} else if (newPasswordField.getText() == null || newPasswordField.getText() == "") {
			return false;
		}
		return true;
	}

	private void resetFields() {
		oldPasswordField.setText("");
		newPasswordField.setText("");
	}

	public boolean isPasswordUpdated(String username, String password) {
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
