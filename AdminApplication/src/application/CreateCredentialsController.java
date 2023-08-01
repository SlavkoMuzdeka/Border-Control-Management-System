package application;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateCredentialsController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField againPasswordField;
	@FXML
	private Button createBtn;

	private static final String POST = "POST";
	private static final String SEPARATOR = ",";

	public void create(ActionEvent e) throws IOException, JSONException {
		if (fieldsAreNotEmpty()) {
			if (arePasswordsEqual()) {
				if (isCredentialCreated(usernameField.getText(), passwordField.getText())) {
					AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful addition",
							"You've successfully added new credentials.");
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
							"Unsuccessful addition of the credentials.");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Passwords must be the same");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in");
		}
		resetFields();
	}

	private boolean fieldsAreNotEmpty() {
		if (usernameField.getText() == null || usernameField.getText() == "") {
			return false;
		} else if (passwordField.getText() == null || passwordField.getText() == "") {
			return false;
		} else if (againPasswordField.getText() == null || againPasswordField.getText() == "") {
			return false;
		}
		return true;
	}

	private void resetFields() {
		usernameField.setText("");
		passwordField.setText("");
		againPasswordField.setText("");
	}

	private boolean arePasswordsEqual() {
		if (!passwordField.getText().equals(againPasswordField.getText())) {
			return false;
		}
		return true;
	}

	public boolean isCredentialCreated(String username, String password) {
		OutputStream os = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(Main.URL_MEMORY_SERVER);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(POST);
			os = conn.getOutputStream();
			os.write((username + SEPARATOR + password).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			}
		}
		return true;
	}

}
