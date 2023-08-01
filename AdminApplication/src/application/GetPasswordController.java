package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GetPasswordController {

	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button getBtn;

	public void getOne(ActionEvent e) {
		if (usernameField.getText() == null || usernameField.getText() == "") {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Please enter a user name.");
		} else {
			try {
				String lozinka = readOne(usernameField.getText());
				if (lozinka != null) {
					passwordField.setText(lozinka);
					passwordField.setVisible(true);
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "The username does not exist.");
					usernameField.setText("");
					passwordField.setText("");
				}
			} catch (IOException | JSONException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			}
		}
	}

	public String readOne(String username) throws IOException, JSONException {
		InputStream is = null;
		try {
			is = new URL(Main.URL_MEMORY_SERVER + username).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return null;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
