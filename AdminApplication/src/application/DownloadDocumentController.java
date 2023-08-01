package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class DownloadDocumentController implements Initializable {

	@FXML
	private ChoiceBox<String> documentNames;
	@FXML
	private Button downloadBtn;

	public void download(ActionEvent e) {
		try {
			if (documentNames.getValue() == null || "".equals(documentNames.getValue())) {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Select the document you want to download.");
			} else {
				Node node = (Node) e.getSource();
				Stage s = (Stage) node.getScene().getWindow();
				s.close();
				String name = documentNames.getSelectionModel().getSelectedItem();
				int size = pronadjiVelicinu(name);
				if (getDocument(name, size)) {
					AlertShow.showAlert(Alert.AlertType.INFORMATION, "Successful download",
							"You successfully downloaded the document.");
				} else {
					AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Unsuccessful file download.");
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String documents = getAllDocumentNames();
			if (!"[]".equals(documents)) {
				String str = documents.substring(1, documents.length() - 1);
				String[] niz = str.split(",");
				for (int i = 0; i < niz.length; i++) {
					documentNames.getItems().add(niz[i].substring(1, niz[i].length() - 5));
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public String getAllDocumentNames() throws IOException, JSONException {
		InputStream is = null;
		try {
			is = new URL(Main.URL_FILE_SERVER).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = get(rd);
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

	private String get(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private boolean getDocument(String documentName, int size) throws IOException {
		InputStream is = new URL(Main.URL_FILE_SERVER + documentName).openStream();
		File file = new File(Main.PATH_TO_DOWNLOAD + File.separator + documentName + ".zip");
		FileOutputStream fos = new FileOutputStream(file);

		byte[] data = new byte[size];
		is.read(data);
		is.close();

		fos.write(data);
		fos.flush();
		fos.close();
		return true;
	}

	private int pronadjiVelicinu(String documentName) throws IOException {
		try {
			InputStream is = new URL(Main.URL_FILE_SERVER + documentName + "/size").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String text = get(rd);
			return Integer.parseInt(text);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return -1;
		}
	}
}
