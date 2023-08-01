package application;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.TerminalServis;
import service.TerminalServisServiceLocator;
import servisKlijent.KlijentServis;
import servisKlijent.KlijentServisServiceLocator;

public class CustomsPartController {

	@FXML
	private Button attachDocumentsBtn;
	private String personId = PolicePartController.personId;
	private String zipFileName;
	private int id = loadId();
	private String terminalName = LoginController.terminalName;
	private String entranceExit = LoginController.entranceExit;
	private int entranceExitId = Integer.parseInt(LoginController.entranceExitId);
	
	private static final String PATH = "IdAplikacije.txt";

	public void attachDocudments(ActionEvent e) {
		try {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			List<File> files = fileChooser.showOpenMultipleDialog(stage);
			zipFiles(files);
			byte[] data = getZipFiles();
			KlijentServisServiceLocator loc = new KlijentServisServiceLocator();
			KlijentServis ks = loc.getKlijentServis();
			ks.isDokumentPoslat(this.id, data, terminalName, entranceExit, entranceExitId, zipFileName);
			int value = -1;
			while (value == -1) {
				value = ks.isDokumentSmjesten(this.id);
				Thread.sleep(1000);
			}
			if (value == 0) {
				AlertShow.showAlert(Alert.AlertType.INFORMATION, "Announcements", "The documents are stored.");
				Node node = (Node) e.getSource();
				Stage s = (Stage) node.getScene().getWindow();
				s.close();
				TerminalServisServiceLocator locator = new TerminalServisServiceLocator();
				TerminalServis servis = locator.getTerminalServis();
				servis.dodajProvjerenogPutnika(Integer.parseInt(this.personId));
			} else {
				AlertShow.showAlert(Alert.AlertType.ERROR, "Try again", "Failed to place documents");
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	private void zipFiles(List<File> files) throws IOException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		this.zipFileName = personId + "_" + format.format(date) + ".zip";
		File zipFile = new File(zipFileName);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

		for (File f : files) {
			zipFile(f.getAbsolutePath(), zos);
		}
		zos.flush();
		zos.close();
	}

	private void zipFile(String fileName, ZipOutputStream zos) throws IOException {
		File file = new File(fileName);
		byte[] data = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(data);
		fis.close();

		ZipEntry zipEntry = new ZipEntry(file.getName());
		zos.putNextEntry(zipEntry);
		zos.write(data);
		zos.flush();
		zos.closeEntry();
	}

	private byte[] getZipFiles() throws IOException {
		byte[] data = new byte[1024];
		File file = new File(zipFileName);
		FileInputStream fis = new FileInputStream(file);
		fis.read(data);
		fis.close();
		file.delete();
		return data;
	}

	private int loadId() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(PATH));
			int id = Integer.valueOf(bf.readLine());
			bf.close();
			int naredniBroj = id + 1;
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(PATH)));
			pw.println(naredniBroj);
			pw.close();
			return id;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return 0;
		}
	}

}
