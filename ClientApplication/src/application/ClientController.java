package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Entrance;
import model.Exit;
import model.Terminal;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import javafx.scene.control.Alert;

public class ClientController implements Initializable {

	@FXML
	private ChoiceBox<String> entranceExitBox;
	@FXML
	private ChoiceBox<String> entranceExitTypeBox;
	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private Button loginBtn;

	private Terminal terminal;
	public boolean isPoliceTerminalAvailable;
	public boolean isCustomsTerminalAvailable;

	public static String terminalName;
	public static String entranceExit;
	public static String id;
	public static String crossingType;
	private static final String ENTRANCE = "Entrance";
	private static final String EXIT = "Exit";
	private static final String POLICE = "Police";
	private static final String CUSTOMS = "Customs";

	public void login(ActionEvent event) {
		if (fieldsAreNotEmpty()) {
			if (areValuesNumbers()) {
				if (terminalExists()) {
					if (entranceExitExists()) {
						if (isAvailable()) {
							setEntranceExitAvailable();
							Node node = (Node) event.getSource();
							Stage s = (Stage) node.getScene().getWindow();
							s.close();
							try {
								AnchorPane anchorPane = (AnchorPane) FXMLLoader
										.load(getClass().getResource("LoginForm.fxml"));
								Scene scene = new Scene(anchorPane);
								Stage stage = new Stage();
								stage.setScene(scene);
								stage.setTitle("Enter credentials");
								stage.show();
								terminalName = nameField.getText();
								entranceExit = entranceExitBox.getSelectionModel().getSelectedItem();
								id = idField.getText();
								crossingType = entranceExitTypeBox.getSelectionModel().getSelectedItem();
							} catch (Exception ex) {
								Logger.getLogger(Main.class.getName()).log(Level.WARNING,
										ex.fillInStackTrace().toString());
							}
						} else {
							AlertShow.showAlert(Alert.AlertType.ERROR, "Pokusajte ponovo",
									(entranceExitTypeBox.getSelectionModel().getSelectedItem().equals(POLICE) ? POLICE
											: CUSTOMS)
											+ (entranceExitBox.getSelectionModel().getSelectedItem().equals(ENTRANCE)
													? " entrance"
													: " exit")
											+ " is busy.");
						}
					} else {
						AlertShow.showAlert(Alert.AlertType.ERROR, "Try again",
								(entranceExitBox.getSelectionModel().getSelectedItem().equals(ENTRANCE) ? ENTRANCE
										: EXIT) + " there is no id.");
					}
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "The terminal with the given name does not exist.");
				}
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Id must be an integer value.");
			}
		} else {
			AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "All fields must be filled in.");
		}
		resetFields();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entranceExitBox.getItems().add(ENTRANCE);
		entranceExitBox.getItems().add(EXIT);
		entranceExitTypeBox.getItems().add(POLICE);
		entranceExitTypeBox.getItems().add(CUSTOMS);
	}

	private boolean fieldsAreNotEmpty() {
		if (nameField.getText() == null || nameField.getText() == "") {
			return false;
		} else if (entranceExitBox.getSelectionModel() == null) {
			return false;
		} else if (entranceExitTypeBox.getSelectionModel() == null) {
			return false;
		} else if (idField.getText() == null || idField.getText() == "") {
			return false;
		}
		return true;
	}

	private void resetFields() {
		nameField.setText("");
		idField.setText("");
	}

	private boolean areValuesNumbers() {
		try {
			Integer.parseInt(idField.getText());
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}

	private boolean terminalExists() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			Terminal terminal = service.dohvatiTerminal(nameField.getText());
			if (terminal != null) {
				this.terminal = terminal;
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return false;
	}

	private boolean entranceExitExists() {
		String crossingType = entranceExitBox.getSelectionModel().getSelectedItem();
		if (ENTRANCE.equals(crossingType)) {
			Entrance[] entrances = terminal.getEntrances();
			for (int i = 0; i < entrances.length; i++) {
				if (entrances[i].getId() == Integer.parseInt(idField.getText())) {
					return true;
				}
			}
		} else {
			Exit[] exits = terminal.getExits();
			for (int i = 0; i < exits.length; i++) {
				if (exits[i].getId() == Integer.parseInt(idField.getText())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isAvailable() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();

			String crossingType = entranceExitBox.getSelectionModel().getSelectedItem();
			String controlType = entranceExitTypeBox.getSelectionModel().getSelectedItem();
			Integer num = Integer.parseInt(idField.getText());
			if (ENTRANCE.equals(crossingType) && POLICE.equals(controlType)) {
				Entrance entrance = findEntrance(num);
				if (this.isPoliceTerminalAvailable = entrance.getIsPoliceControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), entrance.getId(), ENTRANCE, POLICE);
					return true;
				}
			} else if (ENTRANCE.equals(crossingType) && CUSTOMS.equals(controlType)) {
				Entrance entrance = findEntrance(num);
				if (this.isCustomsTerminalAvailable = entrance.getIsCustomControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), entrance.getId(), ENTRANCE, CUSTOMS);
					return true;
				}
			} else if (EXIT.equals(crossingType) && POLICE.equals(controlType)) {
				Exit exit = findExit(num);
				if (this.isPoliceTerminalAvailable = exit.getIsPoliceControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), exit.getId(), EXIT, POLICE);
					return true;
				}
			} else if (EXIT.equals(crossingType) && CUSTOMS.equals(controlType)) {
				Exit exit = findExit(num);
				if (this.isCustomsTerminalAvailable = exit.getIsCustomsControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), exit.getId(), EXIT, CUSTOMS);
					return true;
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		return false;
	}

	private Entrance findEntrance(Integer entranceNumber) {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			this.terminal = service.dohvatiTerminal(nameField.getText());
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		Entrance entrance = null;
		for (int i = 0; i < terminal.getEntrances().length; i++) {
			if (terminal.getEntrances()[i].getId() == entranceNumber) {
				entrance = terminal.getEntrances()[i];
			}
		}
		return entrance;
	}

	private Exit findExit(Integer exitNumber) {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			this.terminal = service.dohvatiTerminal(nameField.getText());
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		Exit exit = null;
		for (int i = 0; i < terminal.getNumberOfExits(); i++) {
			if (terminal.getExits()[i].getId() == exitNumber) {
				exit = terminal.getExits()[i];
			}
		}
		return exit;
	}

	private void setEntranceExitAvailable() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			String crossingType = entranceExitBox.getSelectionModel().getSelectedItem();
			Integer num = Integer.parseInt(idField.getText());
			if (ENTRANCE.equals(crossingType)) {
				Entrance entrance = findEntrance(num);
				if (!entrance.getIsPoliceControlAvailable() && !entrance.getIsCustomControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), entrance.getId(), ENTRANCE, null);
				}
			} else {
				Exit exit = findExit(num);
				if (!exit.getIsPoliceControlAvailable() && !exit.getIsCustomsControlAvailable()) {
					service.setSlobodan(false, nameField.getText(), exit.getId(), ENTRANCE, null);
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
