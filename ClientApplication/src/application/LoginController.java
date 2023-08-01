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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button loginBtn;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	public static String username;
	public static String pass;

	public void login(ActionEvent e) {
		if (fieldsAreNotEmpty()) {
			try {
				String pomPassword = readOne(usernameField.getText());
				if (passwordField.getText().equals(pomPassword)) {
					Node node = (Node) e.getSource();
					Stage s = (Stage) node.getScene().getWindow();
					s.close();
					showForm();
					username = usernameField.getText();
					pass = passwordField.getText();
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Wrong username and/or password.");
				}
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in.");
		}
		resetFields();
	}

	private void showForm() throws IOException {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("ClientApplicationForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Client application");
		stage.setX(300);
		stage.setY(200);
		stage.setResizable(false);
		stage.show();
	}

	private boolean fieldsAreNotEmpty() {
		if (usernameField.getText() == null || usernameField.getText() == "") {
			return false;
		} else if (passwordField.getText() == null || passwordField.getText() == "") {
			return false;
		}
		return true;
	}

	private void resetFields() {
		usernameField.setText("");
		passwordField.setText("");
	}

	public String readOne(String username) throws IOException, JSONException {
		InputStream is = null;
		try {
			is = new URL(Main.URL_MEMORY_SERVER + username).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String text = readAll(rd);
			return text;
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
