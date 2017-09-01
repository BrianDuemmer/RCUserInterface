package application;
	
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import db.DatabaseIO;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
public class Main extends Application 
{
	public static final String appDir = System.getenv("APPDATA") + "/KKDystrack";
	public static DatabaseIO db;
	private static final String version ="v 1.0";
	
	// Periodic update thread pools
	public static ScheduledExecutorService UIUpdateService = Executors.newSingleThreadScheduledExecutor();
	
	@Override public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root,1600,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
