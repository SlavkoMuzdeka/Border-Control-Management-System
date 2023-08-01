package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AdminCredentialsController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private Button createBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button changePasswordBtn;
	@FXML
	private Button getPasswordBtn;

	public void create(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("CreateCredentialsForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void deleteCredentials(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("DeleteCredentialsForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void changePassword(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("ChangePasswordField.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void getPassword(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("GetPasswordForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}
}
