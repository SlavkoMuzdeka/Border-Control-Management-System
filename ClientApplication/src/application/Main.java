package application;

import java.io.*;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	private static Handler handler;
	private static final String CONFIG_PATH = "resources/config.properties";
	public static String URL_MEMORY_SERVER;
	public static String CLIENT_POLICY_PATH;
	public static String HOST;
	public static String KEY_STORE_PATH;
	public static String KEY_STORE_PASSWORD;
	public static int PORT;

	static {
		try {
			handler = new FileHandler("ClientApplication.log");
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
			URL_MEMORY_SERVER = config.getProperty("URL_MEMORY_SERVER");
			CLIENT_POLICY_PATH = config.getProperty("CLIENT_POLICY_PATH");
			HOST = config.getProperty("HOST");
			KEY_STORE_PATH = config.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = config.getProperty("KEY_STORE_PASSWORD");
			PORT = Integer.parseInt(config.getProperty("PORT"));
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("CleintForm.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sing in to the client application");
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
