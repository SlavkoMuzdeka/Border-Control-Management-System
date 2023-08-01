package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	private static Handler handler;
	private static final String CONFIG_PATH = "resources/config.properties";
	public static String URL_CENTRAL_REGISTRY;

	static {
		try {
			handler = new FileHandler("TestApplication.log");
			Logger.getLogger(Main.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(Main.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void loadConfig() {
		try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
			Properties config = new Properties();
			config.load(fis);
			URL_CENTRAL_REGISTRY = config.getProperty("URL_CENTRAL_REGISTRY");
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
			Scene scene = new Scene(anchorPane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Test application");
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		loadConfig();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
