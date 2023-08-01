package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import service.TerminalServis;
import service.TerminalServisServiceLocator;

public class ShowEvidenceController implements Initializable{
	
	@FXML private TextArea passengersArea;
	
	private void getRecordedPassengers() {
		try {
			TerminalServisServiceLocator loc = new TerminalServisServiceLocator();
			TerminalServis service = loc.getTerminalServis();
			String str = service.dohvatiProvjerenePutnike();
			if(!"".equals(str)) {
				passengersArea.setText(str);
			}else {
				AlertShow.showAlert(Alert.AlertType.INFORMATION, "Announcements", "There's not a single verified passenger");
				passengersArea.setText("");
			}
		}catch(Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING,ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		passengersArea.setText("");
		getRecordedPassengers();
	}
	
}
