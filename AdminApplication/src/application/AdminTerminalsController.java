package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Terminal;
import service.TerminalServis;
import service.TerminalServisServiceLocator;

public class AdminTerminalsController {

	@FXML
	private Button createlBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button getBtn;

	@FXML
	private BorderPane borderPane;

	public void create(ActionEvent event) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("CreateTerminalForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void update(ActionEvent event) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("UpdateTerminalForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void delete(ActionEvent event) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("DeleteTerminalForm.fxml"));
			borderPane.setCenter(anchorPane);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public void getOne(ActionEvent event) {
		try {
			borderPane.setCenter(null);
			TextInputDialog inputdialog = new TextInputDialog();
			inputdialog.setContentText("Enter terminal name: ");
			inputdialog.setHeaderText("Enter terminal name");
			inputdialog.showAndWait();
			if (inputdialog.getEditor().getText() != null && !inputdialog.getEditor().getText().equals("")) {
				TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
				TerminalServis service = loc.getTerminalServis();
				Terminal terminal = service.dohvatiTerminal(inputdialog.getEditor().getText());
				ShowTerminalController.terminal = terminal;
				AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("ShowTerminalForm.fxml"));
				borderPane.setCenter(anchorPane);
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
