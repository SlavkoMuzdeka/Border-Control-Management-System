package application;

import java.io.BufferedReader;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class PersonEvidenceContoller implements Initializable {

	@FXML
	private TextArea area;

	private String showPersonIds() {
		String personIds = "";
		try {
			String pom = readAll();
			String str = pom.substring(1, pom.length() - 1);
			String[] niz = str.split(",");
			for (int i = 0; i < niz.length; i++) {
				personIds += "An APB person whose ID has been detected has been detected - " + niz[i] + "\n";
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return personIds;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		area.setText("");
		area.appendText(showPersonIds());
	}

	public String readAll() throws IOException, JSONException {
		InputStream is = null;
		try {
			is = new URL(Main.URL_CENTRAL_REGISTRY).openStream();
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
