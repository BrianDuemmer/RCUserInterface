package application;

import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import db.RCTables;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class. Mine for the copying and pasting
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import rc.QueueEntry;
import util.TextAreaOutputStream;
import util.SmartPrintStream;

public class MainWindowController 
{

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////// FXML OBJECTS /////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@FXML // fx:id="overviewPage"
	private Tab overviewPage; // Value injected by FXMLLoader

	@FXML // fx:id="globalCostSclEntry"
	private TextField globalCostSclEntry; // Value injected by FXMLLoader

	@FXML // fx:id="stdSongCooldownEntry"
	private TextField stdSongCooldownEntry; // Value injected by FXMLLoader

	@FXML // fx:id="syserrLogTxtbox"
	private TextArea syserrLogTxtbox; // Value injected by FXMLLoader

	@FXML // fx:id="databasePage"
	private Tab databasePage; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideHistoryExpireMinsCol"
	private TableColumn<?, ?> songOverrideHistoryExpireMinsCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueUsernameCol"
	private TableColumn<QueueEntry, String> currentQueueUsernameCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueRatingCol"
	private TableColumn<QueueEntry, String> currentQueueRatingCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueLengthCol"
	private TableColumn<QueueEntry, String> currentQueueLengthCol; // Value injected by FXMLLoader

	@FXML // fx:id="requestManualBtn"
	private ToggleButton requestManualBtn; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersPage"
	private Tab vipUsersPage; // Value injected by FXMLLoader

	@FXML // fx:id="databaseWebView"
	private WebView databaseWebView; // Value injected by FXMLLoader

	@FXML // fx:id="freeRequestsBox"
	private CheckBox freeRequestsBox; // Value injected by FXMLLoader

	@FXML // fx:id="baseImmediateReplaySclEntry"
	private TextField baseImmediateReplaySclEntry; // Value injected by FXMLLoader

	@FXML // fx:id="viewSysoLogBtn"
	private Button viewSysoLogBtn; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersNoteCol"
	private TableColumn<?, ?> vipUsersNoteCol; // Value injected by FXMLLoader

	@FXML // fx:id="ignoreHistoryBox"
	private CheckBox ignoreHistoryBox; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersUserIDCol"
	private TableColumn<?, ?> vipUsersUserIDCol; // Value injected by FXMLLoader

	@FXML // fx:id="queueOpenBox"
	private CheckBox queueOpenBox; // Value injected by FXMLLoader

	@FXML // fx:id="queueCloseMinsEntry"
	private TextField queueCloseMinsEntry; // Value injected by FXMLLoader

	@FXML // fx:id="tabPage"
	private TabPane tabPage; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistUserIDCol"
	private TableColumn<?, ?> userBlacklistUserIDCol; // Value injected by FXMLLoader

	@FXML // fx:id="exportQueueBtn"
	private Button exportQueueBtn; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueNameCol"
	private TableColumn<QueueEntry, String> currentQueueNameCol; // Value injected by FXMLLoader

	@FXML // fx:id="baseSongPriceMinEntry"
	private TextField baseSongPriceMinEntry; // Value injected by FXMLLoader

	@FXML // fx:id="revertParamsBtn"
	private Button revertParamsBtn; // Value injected by FXMLLoader

	@FXML // fx:id="queueSizeLabel"
	private Label queueSizeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="sysoLogTxtbox"
	private TextArea sysoLogTxtbox; // Value injected by FXMLLoader

	@FXML // fx:id="baseHistoryExpireMinsEntry"
	private TextField baseHistoryExpireMinsEntry; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueOSTCol"
	private TableColumn<QueueEntry, String> currentQueueOSTCol; // Value injected by FXMLLoader

	@FXML // fx:id="queueOpenMinsEntry"
	private TextField queueOpenMinsEntry; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistTimeBannedCol"
	private TableColumn<?, ?> userBlacklistTimeBannedCol; // Value injected by FXMLLoader

	@FXML // fx:id="percentRandomSlider"
	private Slider percentRandomSlider; // Value injected by FXMLLoader

	@FXML // fx:id="clearSysoConsoleBtn"
	private Button clearSysoConsoleBtn; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideBaseCostCol"
	private TableColumn<?, ?> songOverrideBaseCostCol; // Value injected by FXMLLoader

	@FXML // fx:id="viewSyserrLogBtn"
	private Button viewSyserrLogBtn; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueue"
	private TableView<QueueEntry> currentQueue; // Value injected by FXMLLoader

	@FXML // fx:id="dontRecordHistoryBox"
	private CheckBox dontRecordHistoryBox; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistPage"
	private Tab userBlacklistPage; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistTable"
	private TableView<?> userBlacklistTable; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersCostSclCol"
	private TableColumn<?, ?> vipUsersCostSclCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueUserIDCol"
	private TableColumn<QueueEntry, String> currentQueueUserIDCol; // Value injected by FXMLLoader

	@FXML // fx:id="songOverridePage"
	private Tab songOverridePage; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideImmediateRelaySclCol"
	private TableColumn<?, ?> songOverrideImmediateRelaySclCol; // Value injected by FXMLLoader

	@FXML // fx:id="requestsAutoBox"
	private CheckBox requestsAutoBox; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistNoteCol"
	private TableColumn<?, ?> userBlacklistNoteCol; // Value injected by FXMLLoader

	@FXML // fx:id="stdUserCooldownEntry"
	private TextField stdUserCooldownEntry; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueTimeRequestedCol"
	private TableColumn<QueueEntry, String> currentQueueTimeRequestedCol; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideSongNameCol"
	private TableColumn<?, ?> songOverrideSongNameCol; // Value injected by FXMLLoader

	@FXML // fx:id="reqManualControl"
	private ToggleGroup reqManualControl; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideTable"
	private TableView<?> songOverrideTable; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersUsernameCol"
	private TableColumn<?, ?> vipUsersUsernameCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueuePriorityCol"
	private TableColumn<QueueEntry, Number> currentQueuePriorityCol; // Value injected by FXMLLoader

	@FXML // fx:id="requestSessionPctFullBar"
	private ProgressBar requestSessionPctFullBar; // Value injected by FXMLLoader

	@FXML // fx:id="queueLengthLabel"
	private Label queueLengthLabel; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistUsernameCol"
	private TableColumn<?, ?> userBlacklistUsernameCol; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideOwningOSTCol"
	private TableColumn<?, ?> songOverrideOwningOSTCol; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersTable"
	private TableView<?> vipUsersTable; // Value injected by FXMLLoader

	@FXML // fx:id="clearSyserrConsoleBtn"
	private Button clearSyserrConsoleBtn; // Value injected by FXMLLoader

	@FXML // fx:id="saveParamsBtn"
	private Button saveParamsBtn; // Value injected by FXMLLoader

	@FXML // fx:id="addSongBtn"
	private Button addSongBtn; // Value injected by FXMLLoader

	@FXML // fx:id="addRandomBtn"
	private Button addRandomBtn; // Value injected by FXMLLoader
	
	@FXML // fx:id="regenPlaylistBtn"
	private Button regenPlaylistBtn; // Value injected by FXMLLoader



	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////// FXML METHODS /////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	private void asserts()
	{
		assert overviewPage != null : "fx:id=\"overviewPage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert globalCostSclEntry != null : "fx:id=\"globalCostSclEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert stdSongCooldownEntry != null : "fx:id=\"stdSongCooldownEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert databasePage != null : "fx:id=\"databasePage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideHistoryExpireMinsCol != null : "fx:id=\"songOverrideHistoryExpireMinsCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueueUsernameCol != null : "fx:id=\"currentQueueUsernameCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert requestManualBtn != null : "fx:id=\"requestManualBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersPage != null : "fx:id=\"vipUsersPage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert databaseWebView != null : "fx:id=\"databaseWebView\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert freeRequestsBox != null : "fx:id=\"freeRequestsBox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert baseImmediateReplaySclEntry != null : "fx:id=\"baseImmediateReplaySclEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersNoteCol != null : "fx:id=\"vipUsersNoteCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert ignoreHistoryBox != null : "fx:id=\"ignoreHistoryBox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersUserIDCol != null : "fx:id=\"vipUsersUserIDCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert queueOpenBox != null : "fx:id=\"queueOpenBox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert queueCloseMinsEntry != null : "fx:id=\"queueCloseMinsEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert tabPage != null : "fx:id=\"tabPage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistUserIDCol != null : "fx:id=\"userBlacklistUserIDCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert exportQueueBtn != null : "fx:id=\"exportQueueBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert sysoLogTxtbox != null : "fx:id=\"sysoLogTxtbox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueueNameCol != null : "fx:id=\"currentQueueNameCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert baseSongPriceMinEntry != null : "fx:id=\"baseSongPriceMinEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert revertParamsBtn != null : "fx:id=\"revertParamsBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert queueSizeLabel != null : "fx:id=\"queueSizeLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert baseHistoryExpireMinsEntry != null : "fx:id=\"baseHistoryExpireMinsEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueueOSTCol != null : "fx:id=\"currentQueueOSTCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert queueOpenMinsEntry != null : "fx:id=\"queueOpenMinsEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistTimeBannedCol != null : "fx:id=\"userBlacklistTimeBannedCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert percentRandomSlider != null : "fx:id=\"percentRandomSlider\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert sysoLogTxtbox != null : "fx:id=\"requestControllerLogTxtbox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideBaseCostCol != null : "fx:id=\"songOverrideBaseCostCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert viewSysoLogBtn != null : "fx:id=\"viewSysoLogBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueue != null : "fx:id=\"currentQueue\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert dontRecordHistoryBox != null : "fx:id=\"dontRecordHistoryBox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistPage != null : "fx:id=\"userBlacklistPage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistTable != null : "fx:id=\"userBlacklistTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersCostSclCol != null : "fx:id=\"vipUsersCostSclCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueueUserIDCol != null : "fx:id=\"currentQueueUserIDCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverridePage != null : "fx:id=\"songOverridePage\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideImmediateRelaySclCol != null : "fx:id=\"songOverrideImmediateRelaySclCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert viewSyserrLogBtn != null : "fx:id=\"viewSyserrLogBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert requestsAutoBox != null : "fx:id=\"requestsAutoBox\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistNoteCol != null : "fx:id=\"userBlacklistNoteCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert stdUserCooldownEntry != null : "fx:id=\"stdUserCooldownEntry\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueueTimeRequestedCol != null : "fx:id=\"currentQueueTimeRequestedCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideSongNameCol != null : "fx:id=\"songOverrideSongNameCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert reqManualControl != null : "fx:id=\"reqManualControl\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideTable != null : "fx:id=\"songOverrideTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersUsernameCol != null : "fx:id=\"vipUsersUsernameCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert currentQueuePriorityCol != null : "fx:id=\"currentQueuePriorityCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert requestSessionPctFullBar != null : "fx:id=\"requestSessionPctFullBar\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert queueLengthLabel != null : "fx:id=\"queueLengthLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userBlacklistUsernameCol != null : "fx:id=\"userBlacklistUsernameCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert songOverrideOwningOSTCol != null : "fx:id=\"songOverrideOwningOSTCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert vipUsersTable != null : "fx:id=\"vipUsersTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert saveParamsBtn != null : "fx:id=\"saveParamsBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}




	@FXML
	void viewSysoLogOnAction(ActionEvent event) {

	}

	/**
	 * Clears the system.out console
	 * @param event
	 */
	@FXML
	void clearSysoLogConsoleBtnOnAction(ActionEvent event) { sysoLogTxtbox.clear(); }

	@FXML
	void viewSyserrLogOnAction(ActionEvent event) {

	}


	/**
	 * Clears the system.err console
	 * @param event
	 */
	@FXML
	void clearSyserrConsoleBtnOnAction(ActionEvent event) { syserrLogTxtbox.clear(); }

	
	// All of the text entry parameters get verified, and written from here
	@FXML void queueOpenMinsOnAction(ActionEvent event) { writeDoubleTextFieldToDB("queueOpenMins", queueOpenMinsEntry); }
	@FXML void queueCloseMinsOnAction(ActionEvent event) { writeDoubleTextFieldToDB("queueCloseMins", queueCloseMinsEntry); }
	@FXML void stdSongCooldownOnAction(ActionEvent event) { writeDoubleTextFieldToDB("stdSongCooldown", stdSongCooldownEntry); }
	@FXML void stdUserCooldownOnAction(ActionEvent event) { writeDoubleTextFieldToDB("stdUserCooldown", stdUserCooldownEntry); }
	@FXML void globalCostSclOnAction(ActionEvent event) { writeDoubleTextFieldToDB("globalCostScl", globalCostSclEntry); }
	@FXML void baseSongPriceMinOnAction(ActionEvent event) { writeDoubleTextFieldToDB("baseSongPriceMin", baseSongPriceMinEntry); }
	@FXML void baseHistoryExpireMinsOnAction(ActionEvent event) { writeDoubleTextFieldToDB("baseHistoryExpireMins", baseHistoryExpireMinsEntry); }
	@FXML void baseImmediateReplaySclOnAction(ActionEvent event) { writeDoubleTextFieldToDB("baseImmediateReplayScl", baseImmediateReplaySclEntry); }

	@FXML
	void requestsAutoBoxOnAction(ActionEvent event) 
	{
		
	}

	@FXML
	void requestManualBtnOnAction(ActionEvent event) {

	}

	@FXML
	void freeRequestsBoxOnAction(ActionEvent event) {

	}

	@FXML
	void ignoreHistoryBoxOnAction(ActionEvent event) {

	}

	@FXML
	void dontRecordHistoryBoxOnAction(ActionEvent event) {

	}

	@FXML
	void saveParamsBtnOnAction(ActionEvent event) 
	{
		System.out.println("Saving configuration parameters...");
		this.writeConfigParams();
	}

	@FXML
	void revertParamsBtnOnAction(ActionEvent event) 
	{ 
		System.out.println("Reverting configuration parameters...");
		this.loadConfigParams();
	}

	@FXML
	void exportQueueBtnOnAction(ActionEvent event) {
		writeQueueToDB(); // TEMPORARY!!!!
	}

	@FXML
	void addSongRandom(ActionEvent event)
	{
		
	}

	@FXML
	void addSongManual(ActionEvent event)
	{
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("application/SongEntry.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Enter a song");
			stage.setScene(new Scene(root, 450, 169));
			stage.setResizable(false);
			stage.showAndWait(); // Wait for that system to finish, then update the queue table
			readQueueToTable();
			
		} catch(Exception e)
		{
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Error encountered in song adder. Check error log for more details");
			a.setTitle("Playlist Generator Error");
			a.showAndWait();
		}
	}
	
	
	@FXML // opens up the playlist regenerator window and runs the playlist generator algorithm
	void regenPlaylistOnAction(ActionEvent event)
	{
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("application/plGenEntry.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Playlist Generator");
			stage.setScene(new Scene(root, 500, 160));
			stage.show();
		} catch(Exception e)
		{
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Error encountered in playlist generator. Check error log for more details");
			a.setTitle("Playlist Generator Error");
			a.showAndWait();
		}
	}
	
	
	
	/**
	 * Verifies a textField for data valididty (As a double), alerts the user if something's wrong, or
	 * writes to the database if it's good 
	 * @param name the settings key name to write to
	 * @param field
	 */
	private void writeDoubleTextFieldToDB(String name, TextField field) {
		try {
			double val = Double.parseDouble(field.getText()); // Attempt to parse out the field
			
			Thread t = new Thread(() -> {  // If we get here the field is valid, so we can push it to the database
				try { Main.db.setParameter(name, val); } 
				
				// DB error. Usually an SQLITE_BLOCKED due to the DB viewer being open with pending writes
				catch (SQLException e) {new Alert(AlertType.ERROR, "Failed to push parameter \"" +name+ "\" to database. See error log for more details").showAndWait(); }
			});
			
			t.setDaemon(true);
			t.setName("WriteDoubleTextFieldToDB");
			t.start();
		} catch (Exception e) // format / display the error message, then update the field to be a safe value
		{
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("The value \"" +field.getText()+ "\" is not a valid number!");
			a.setTitle("Bad input!");
			a.setHeaderText("Bad input!");
			a.showAndWait();
			
			field.setText("0");
		}
	}





	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////  REGULAR OBJECTS ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Lists all of the queue entries in the database and the tableview
	private ObservableList<QueueEntry> queueEntries;




	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////  REGULAR METHODS ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		asserts();
		// set up phplitemyadmin
		databaseWebView.getEngine().load("http://localhost/phpliteadmin.php");

		// redirect syso / syserr to go to the onscreen 
		PrintStream newOut = new SmartPrintStream(new TextAreaOutputStream(sysoLogTxtbox));
		System.setOut(newOut);
		PrintStream newErr = new SmartPrintStream(new TextAreaOutputStream(syserrLogTxtbox));
		System.setErr(newErr);

		System.out.println("Initializing primary controller...");
		loadConfigParams();
		initQueueTable();
		readQueueToTable();
	}




	/**
	 * Sets up the queue tableview to accept an @link QueueEntry object
	 */
	private void initQueueTable()
	{
		// Convert the UNIX timestamp to a nice looking date
		currentQueueTimeRequestedCol.setCellValueFactory(cellData -> 
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date(cellData.getValue().getTime() * 1000)));
			return prop;
		});



		//Convert the length in seconds to the format mins:ss
		currentQueueLengthCol.setCellValueFactory(cellData -> 
		{
			int len = cellData.getValue().getSong().getLength();

			// format time as Mins:ss
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.set(String.format("%d:%02d", Math.floorDiv(len, 60), len%60));
			return prop;
		});




		// Just sets the property as the song name
		currentQueueNameCol.setCellValueFactory(cellData ->
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(cellData.getValue().getSong().getSongName());
			return prop;
		});




		// Just sets the property as the ost name
		currentQueueOSTCol.setCellValueFactory(cellData ->
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(cellData.getValue().getSong().getOstName());
			return prop;
		});



		// Just sets the property as the priority
		currentQueuePriorityCol.setCellValueFactory(cellData ->
		{
			SimpleIntegerProperty prop = new SimpleIntegerProperty();
			prop.setValue(cellData.getValue().getPriority());
			return prop;
		});



		// Just sets the property as the userID
		currentQueueUserIDCol.setCellValueFactory(cellData ->
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(cellData.getValue().getVw().getUserID());
			return prop;
		});



		// Just sets the property as the username
		currentQueueUsernameCol.setCellValueFactory(cellData ->
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(cellData.getValue().getVw().getUsername());
			return prop;
		});




		// Just sets the property as the rating's raw text
		currentQueueRatingCol.setCellValueFactory(cellData ->
		{
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.setValue(cellData.getValue().getRating().toString());
			return prop;
		});
	}






	/**
	 * Loads all of the configuration parameters from the database to the UI
	 */
	private void loadConfigParams()
	{
		System.out.println("Loading configuration parameters...");

		Task<Void> tsk = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception
			{
				// control values - fetch here to use in the background thread that updates the database
				double prs = Main.db.readRealParam("percentRandom");
				String qome = Main.db.readStringParam("queueOpenMins");
				String qcme = Main.db.readStringParam("queueCloseMins");
				String ssce = Main.db.readStringParam("stdSongCooldown");
				String gcse = Main.db.readStringParam("stdUserCooldown");
				String suce = Main.db.readStringParam("globalCostScl");
				String bspme = Main.db.readStringParam("baseSongPriceMin");
				String bheme = Main.db.readStringParam("baseHistoryExpireMins");
				String birse = Main.db.readStringParam("baseImmediateReplayScl");

				Platform.runLater(() -> // only update the GUI in app thread
				{
					try {
						percentRandomSlider.setValue(prs);
						queueOpenMinsEntry.setText(qome);
						queueCloseMinsEntry.setText(qcme);
						stdSongCooldownEntry.setText(ssce);
						stdUserCooldownEntry.setText(gcse);
						globalCostSclEntry.setText(suce);
						baseSongPriceMinEntry.setText(bspme);
						baseHistoryExpireMinsEntry.setText(bheme);
						baseImmediateReplaySclEntry.setText(birse);

						System.out.println("Finished Loading configuration parameters");
					} catch(Exception e) 
					{
						System.err.println("Error occured trying to read config parametes"); e.printStackTrace(); 
						Alert a = new Alert(AlertType.ERROR, "Failed to read paramaters from database!");
						a.show();

					}
				});

				return null;
			}
		};

		Thread t = new Thread(tsk);
		t.setDaemon(true);
		t.start();


	}




	private void writeConfigParams()
	{
		System.out.println("Writing configuration parameters...");

		// control values - fetch here to use in the background thread that updates the database
		double prs = percentRandomSlider.getValue();
		String qome = queueOpenMinsEntry.getText();
		String qcme = queueCloseMinsEntry.getText();
		String ssce = stdSongCooldownEntry.getText();
		String gcse = globalCostSclEntry.getText();
		String suce = stdUserCooldownEntry.getText();
		String bspme = baseSongPriceMinEntry.getText();
		String bheme = baseHistoryExpireMinsEntry.getText();
		String birse = baseImmediateReplaySclEntry.getText();

		Task<Void> tsk = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception
			{
				try {
					Main.db.setParameter("percentRandom", prs);
					Main.db.setParameter("queueOpenMins", qome);
					Main.db.setParameter("queueCloseMins", qcme);
					Main.db.setParameter("stdSongCooldown", ssce);
					Main.db.setParameter("stdUserCooldown", suce);
					Main.db.setParameter("globalCostScl", gcse);
					Main.db.setParameter("baseSongPriceMin", bspme);
					Main.db.setParameter("baseHistoryExpireMins", bheme);
					Main.db.setParameter("baseImmediateReplayScl", birse);

					System.out.println("Finished writing params");
				} catch(Exception e) 
				{
					Platform.runLater(() ->
					{
						System.err.println("Error occured trying to write config parametes"); e.printStackTrace(); 
						Alert a = new Alert(AlertType.ERROR, "Failed to write paramaters to database!");
						a.show();
					});
				}

				return null;
			}
		};


		Thread t = new Thread(tsk);
		t.setDaemon(true);
		t.start();
	}




	/**
	 * Reads the forward queue from the database, and updates the queue table on the GUI
	 */
	public void readQueueToTable()
	{
		Task<ObservableList<QueueEntry>> tsk = new Task<ObservableList<QueueEntry>>() {
			@Override // get the data from the forward queue
			protected ObservableList<QueueEntry> call() throws Exception 
			{
				RCTables.forwardQueueTable.verifyExists(Main.db.getDb());
				return QueueEntry.getForwardQueue(); 
			}

			@Override // set the table elements fresh
			protected void succeeded() {
				Platform.runLater(() ->  {
					// update variable / table at same time
					queueEntries = getValue();
					currentQueue.getItems().clear();
					currentQueue.getItems().addAll(getValue());
				});
			}
		};

		Thread t = new Thread(tsk);
		t.setDaemon(true);
		t.setName("ReadQueueToTable");
		t.start();
	}



	/**
	 * Writes the queueEntries list to the database. <br>
	 * <b>CAUTION:</b> This will override everything in the queue!
	 */
	public void writeQueueToDB()
	{
		Task<Void> tsk = new Task<Void>() {
			@Override
			protected Void call() throws Exception 
			{
				try { 
					RCTables.forwardQueueTable.verifyExists(Main.db.getDb());
					Main.db.execRaw("DELETE FROM " +RCTables.forwardQueueTable.getName());  // empty the table
					for(QueueEntry q : queueEntries) // Write each entry one by one
						q.writeToDB();

				} 
				catch (SQLException e) 
				{ 
					e.printStackTrace(); 
					Platform.runLater(() -> { // this should only run with direct user intervention, so an alert box would be appropriate
						new Alert(AlertType.ERROR, "Failed to push queue to database! Check error log for more details").show();
					});
				}

				return null;
			}
		};

		Thread t = new Thread(tsk);
		t.setDaemon(true);
		t.setName("writeQueueToDB");
		t.start();
	}
	
	
	
	
	
	
//	/**
//	 * UPSATE THREAD - This code will be called periodically to keep the main UI synced with the database.
//	 * This includes reading / writing variable data, keeping the onscreen tables updated, and possibly 
//	 * other things as they arise in the future
//	 */	
//	private void initGuiUpdateService()
//	{
//		ScheduledExecutorService sexs = Executors.newScheduledThreadPool(1);
//		sexs.scheduleAtFixedRate(command, initialDelay, period, unit)
//	}

	


}










