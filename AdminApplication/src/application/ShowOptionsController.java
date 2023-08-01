package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShowOptionsController {

	@FXML
	private Button terminalBtn;
	@FXML
	private Button credentialBtn;
	@FXML
	private Button evidenceBtn;
	@FXML
	private Button documentBtn;

	public void showForm1(ActionEvent e) {
		try {
			BorderPane borederPane = FXMLLoader.load(getClass().getResource("AdminTerminalsForm.fxml"));
			Scene scene = new Scene(borederPane, 500, 300);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Admin - Working with terminals");
			stage.setResizable(false);
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}

	}

	public void showForm2(ActionEvent e) {
		try {
			BorderPane borederPane = FXMLLoader.load(getClass().getResource("AdminCredentialsForm.fxml"));
			Scene scene = new Scene(borederPane, 500, 300);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Admin - Working with credentials");
			stage.setResizable(false);
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void showForm3(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("PersonEvidenceForm.fxml"));
			Scene scene = new Scene(anchorPane, 500, 300);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Admin - Records of detected persons from warrants");
			stage.setResizable(false);
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void showForm4(ActionEvent e) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("DownloadDocumentForm.fxml"));
			Scene scene = new Scene(anchorPane);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Admin - Download documents");
			stage.setResizable(false);
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
