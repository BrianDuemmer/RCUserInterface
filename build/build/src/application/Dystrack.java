package application;
	

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import db.DatabaseIO;
import foobarIO.Foobar;
import javafx.application.Application;
import javafx.stage.Stage;
import rc.RequestControl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;




/**
 * This is the primary controller of the entire system.
 * This is the device that decides when requests should open and close,
 * decide which songs should be sent to foobarIO when, etc. 
 * 
 * @author Duemmer
 *
 */
public class Dystrack extends Application 
{
	// constants
	public static final String appDir = System.getenv("APPDATA") + "\\KKDystrack";
	public static final String version ="v 1.0";
//	public static final String playlistRoot = "C:\\users\\duemmer\\music\\mh"; // my playlist root
	public static final String playlistRoot = "D:\\Stream"; // Dys's playlist root
	public static final String foobarPath = "c:\\program files (x86)\\foobar2000\\foobar2000.exe";
	public static final int simulatorUpdateMillis = 50;
	public static final int varCalcUpdateMillis = 1000;
	public static final int UIUpdateMillis = 500;
	public static final String queueDumpPath = appDir+ "\\queue.csv";
	
	
	// Actors
	public static RequestControl rc;
	public static DatabaseIO db;
	public static Foobar foobar;
	
	
	/** A typical alert for a database error*/
	public static Alert databaseErrorAlert;
	static {
		databaseErrorAlert = new Alert(AlertType.ERROR);
		databaseErrorAlert.setTitle("Database Error");
		databaseErrorAlert.setHeaderText("Database Error");
		databaseErrorAlert.setContentText("An error occured trying to acces the database. Check the error log for more info");
	}
	
	
	
	
	
	// Periodic update thread pools - make sure to make it daemon / set a good name
	public static ScheduledExecutorService UIUpdateService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setName("UI Updates");
			return t;
		}
	});
	
	
	
	
	public static ScheduledExecutorService varCalcService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setName("Variable calculation");
			return t;
		}
	});
	
	
	
	
	
	
	@Override public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root,1600,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("KKDystrack.png")));
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("K. K. DysTrack - " +version);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override public void stop()
	{
		// Stop all update threads, provided they aren't null
		if(UIUpdateService != null)
			UIUpdateService.shutdown();
	}
	
	
	
	public static void main(String[] args) 
	{
		new File(appDir).mkdirs(); // verify the property directory exists
		db = new DatabaseIO(appDir +"/KKDystrack.sqlite");
		
		
		// This method won't finish until the application exits
		launch(args);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
