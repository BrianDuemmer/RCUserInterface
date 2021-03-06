package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import db.RCTables;
import foobarIO.Foobar;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import rc.QueueEntry;
import rc.RequestControl;
import rc.Song;
import util.TextAreaOutputStream;
import util.Util;
import util.ReqMode;
import util.SmartPrintStream;

public class MainWindowController 
{

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////// FXML OBJECTS /////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@FXML private Tab overviewPage; // Value injected by FXMLLoader

	@FXML private TextField globalCostSclEntry; // Value injected by FXMLLoader

	@FXML private TextField stdSongCooldownEntry; // Value injected by FXMLLoader

	@FXML private TextArea syserrLogTxtbox; // Value injected by FXMLLoader

	@FXML private Tab databasePage; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> songOverrideHistoryExpireMinsCol; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueUsernameCol; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueRatingCol; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueLengthCol; // Value injected by FXMLLoader

	@FXML private ToggleButton requestManualBtn; // Value injected by FXMLLoader

	@FXML private Tab vipUsersPage; // Value injected by FXMLLoader

	@FXML private WebView databaseWebView; // Value injected by FXMLLoader

	@FXML private CheckBox freeRequestsBox; // Value injected by FXMLLoader

	@FXML private TextField baseImmediateReplaySclEntry; // Value injected by FXMLLoader

	@FXML private Button viewSysoLogBtn; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> vipUsersNoteCol; // Value injected by FXMLLoader

	@FXML private CheckBox ignoreHistoryBox; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> vipUsersUserIDCol; // Value injected by FXMLLoader

	@FXML private CheckBox queueOpenBox; // Value injected by FXMLLoader

	@FXML private TextField queueCloseMinsEntry; // Value injected by FXMLLoader

	@FXML private TabPane tabPage; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> userBlacklistUserIDCol; // Value injected by FXMLLoader

	@FXML private Button exportQueueBtn; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueNameCol; // Value injected by FXMLLoader

	@FXML private TextField baseSongPriceMinEntry; // Value injected by FXMLLoader

	@FXML private Button revertParamsBtn; // Value injected by FXMLLoader

	@FXML private Label queueSizeLabel; // Value injected by FXMLLoader

	@FXML private TextArea sysoLogTxtbox; // Value injected by FXMLLoader

	@FXML private TextField baseHistoryExpireMinsEntry; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueOSTCol; // Value injected by FXMLLoader

	@FXML private TextField queueOpenMinsEntry; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> userBlacklistTimeBannedCol; // Value injected by FXMLLoader

	@FXML private Slider percentRandomSlider; // Value injected by FXMLLoader

	@FXML private Button clearSysoConsoleBtn; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> songOverrideBaseCostCol; // Value injected by FXMLLoader

	@FXML private Button viewSyserrLogBtn; // Value injected by FXMLLoader

	@FXML private TableView<QueueEntry> currentQueue; // Value injected by FXMLLoader

	@FXML private CheckBox dontRecordHistoryBox; // Value injected by FXMLLoader

	@FXML private Tab userBlacklistPage; // Value injected by FXMLLoader

	@FXML private TableView<?> userBlacklistTable; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> vipUsersCostSclCol; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueUserIDCol; // Value injected by FXMLLoader

	@FXML private Tab songOverridePage; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> songOverrideImmediateRelaySclCol; // Value injected by FXMLLoader

	@FXML private CheckBox requestsAutoBox; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> userBlacklistNoteCol; // Value injected by FXMLLoader

	@FXML private TextField stdUserCooldownEntry; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, String> currentQueueTimeRequestedCol; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> songOverrideSongNameCol; // Value injected by FXMLLoader

	@FXML private ToggleGroup reqManualControl; // Value injected by FXMLLoader

	@FXML private TableView<?> songOverrideTable; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> vipUsersUsernameCol; // Value injected by FXMLLoader

	@FXML private TableColumn<QueueEntry, Number> currentQueuePriorityCol; // Value injected by FXMLLoader

	@FXML private ProgressBar requestSessionPctFullBar; // Value injected by FXMLLoader

	@FXML private Label queueLengthLabel; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> userBlacklistUsernameCol; // Value injected by FXMLLoader

	@FXML private TableColumn<?, ?> songOverrideOwningOSTCol; // Value injected by FXMLLoader

	@FXML private TableView<?> vipUsersTable; // Value injected by FXMLLoader

	@FXML private Button clearSyserrConsoleBtn; // Value injected by FXMLLoader

	@FXML private Button saveParamsBtn; // Value injected by FXMLLoader

	@FXML private Button addSongBtn; // Value injected by FXMLLoader

	@FXML private Button addRandomBtn; // Value injected by FXMLLoader

	@FXML private Button regenPlaylistBtn; // Value injected by FXMLLoader

	@FXML private Button resetFoobarBtn;

	@FXML private ToggleButton foobarPlayStatusBtn;

	@FXML private CheckBox foobarOpenBox;

	@FXML private Button skipSongBtn;

	@FXML private Label nowPlayingLabel;

	@FXML private MenuItem queueTableMenuRemoveSong;


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


	/** Starts or resets foobar2000 */
	@FXML void resetFoobarOnAction(ActionEvent event) 
	{ 
		// Reset it to get foobar into a known state, ready to go
		Dystrack.foobar.startFoobar(Dystrack.rc.getAddSong());
		foobarPlayStatusBtn.setDisable(false);
	}

	/** Pauses or plays the foobar feed */
	@FXML void foobarPlayStatusBtnOnAction(ActionEvent event)
	{ 
		boolean playing = foobarPlayStatusBtn.isSelected();
		if(playing)
			Dystrack.foobar.playPlayback();
		else
			Dystrack.foobar.pausePlayback();
	}


	/** When foobar receives a play command if a song is playing, it will skip that song. Only run if foobar's playing */
	@FXML void skipSongBtnOnAction(ActionEvent event) { 
		if(Dystrack.foobar.isPlaying())
			Dystrack.foobar.playPlayback(); 
	}


	/** Clears the system.out console */
	@FXML void clearSysoLogConsoleBtnOnAction(ActionEvent event) { sysoLogTxtbox.clear(); }


	@FXML void viewSyserrLogOnAction(ActionEvent event) { }
	@FXML void viewSysoLogOnAction(ActionEvent event) { }

	/** Clears the system.err console */
	@FXML void clearSyserrConsoleBtnOnAction(ActionEvent event) { syserrLogTxtbox.clear(); }


	// All of the text entry parameters get verified, and written from here
	@FXML void queueOpenMinsOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("queueOpenMins", queueOpenMinsEntry); }
	@FXML void queueCloseMinsOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("queueCloseMins", queueCloseMinsEntry); }
	@FXML void stdSongCooldownOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("stdSongCooldown", stdSongCooldownEntry); }
	@FXML void stdUserCooldownOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("stdUserCooldown", stdUserCooldownEntry); }
	@FXML void globalCostSclOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("globalCostScl", globalCostSclEntry); }
	@FXML void baseSongPriceMinOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("baseSongPriceMin", baseSongPriceMinEntry); }
	@FXML void baseHistoryExpireMinsOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("baseHistoryExpireMins", baseHistoryExpireMinsEntry); }
	@FXML void baseImmediateReplaySclOnAction(ActionEvent event) { Util.writeDoubleTextFieldToDB("baseImmediateReplayScl", baseImmediateReplaySclEntry); }


	/**
	 * Gray / ungray the manual mode button depending on whether or not the
	 * "Auto" checkbox is selected
	 * @param event unused, can be null
	 */
	@FXML void requestsAutoBoxOnAction(ActionEvent event) {
		boolean isPressed = requestsAutoBox.isSelected();
		requestManualBtn.setDisable(isPressed);

		// push changes to the database
		Dystrack.db.writeRequestModeToDB(getCurrReqMode());
	}


	/**
	 * Just push changes to the database
	 */
	@FXML void requestManualBtnOnAction(ActionEvent event) { Dystrack.db.writeRequestModeToDB(getCurrReqMode()); }
	@FXML void freeRequestsBoxOnAction(ActionEvent event) { writeCheckboxToDB(freeRequestsBox, "freeRequests"); }
	@FXML void ignoreHistoryBoxOnAction(ActionEvent event) {  writeCheckboxToDB(ignoreHistoryBox, "ignoreHistory");  }
	@FXML void dontRecordHistoryBoxOnAction(ActionEvent event) {  writeCheckboxToDB(dontRecordHistoryBox, "dontRecordHistory");  }

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
		this.readConfigParams();
	}

	@FXML void exportQueueBtnOnAction(ActionEvent event) 
	{ 
		Thread t = new Thread(() -> { 
			Dystrack.db.dumpTableToCSV(Dystrack.queueDumpPath, RCTables.forwardQueueTable); 
			Platform.runLater(() -> {
				Alert a = new Alert(AlertType.INFORMATION, "Successfulley dumped the queue to the file \"" +Dystrack.queueDumpPath+ "\"");
				a.show();
			});
		});
		
		t.setDaemon(true);
		t.setName("dumpQueue");
		t.start();
	}
	
	
	
	@FXML void addSongRandom(ActionEvent event) 
	{ 
		Thread t = new Thread(() -> {
			QueueEntry q = QueueEntry.uniformRandomEntry();
			q.writeToDB();

			Platform.runLater(() -> { readQueueToTable(); });
		});

		t.setDaemon(true);
		t.setName("addSongRandom");
		t.start();
	}




	@FXML
	void addSongManual(ActionEvent event)
	{
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("application/DropdownSongEntry.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Enter a song");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.showAndWait(); 

			// Wait for that system to finish, then update the queue table (in the background)
			Thread t = new Thread(() -> { readQueueToTable(); });
			t.setDaemon(true);
			t.setName("readQueueToTable");
			t.start();

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





	@FXML void queueTableMenuRemoveSongOnAction(ActionEvent event)
	{
		int idx = currentQueue.getSelectionModel().getFocusedIndex();
		if(idx != -1)
		{
			QueueEntry q = queueEntries.remove(idx);
			Thread t = new Thread(() -> { 
				q.deleteFromForwardQueue(); 
				readQueueToTable();
			});

			t.setDaemon(true);
			t.setName("Delete from queue");
			t.start();
		}


	}
	
	
	
	// https://stackoverflow.com/questions/12353576/drag-drop-in-javafx-table
	// https://javafxdeveloper.wordpress.com/2013/10/28/simple-drag-and-drop-tutorial/
	// Is where The code for the drag & drop stuff came from
	@FXML void currentQueueDragDetected(MouseEvent event)
	{
//		System.out.println("drag detected");
//		QueueEntry sel = currentQueue.getSelectionModel().getSelectedItem();
//		if(sel != null)
//		{
//			Dragboard db = currentQueue.startDragAndDrop(TransferMode.ANY);
//			ClipboardContent cc = new ClipboardContent();
//			cc.put(queueEntryDataFormat, sel);
//			db.setContent(cc);
//			event.consume();
//		}
	}
	
	
	
	@FXML void currentQueueDragOver(DragEvent event)
	{
//		System.out.println("drag over");
//		Dragboard db = event.getDragboard();
//		if(db.hasContent(queueEntryDataFormat))
//			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//		
//		event.consume();
	}
	
	
	
	
	@FXML void currentQueueDragDropped(DragEvent event)
	{
//		System.out.println("drag dropped");
//		Dragboard db = event.getDragboard();
//		boolean success = false;
//		
//		if(db.hasContent(queueEntryDataFormat))
//		{
//			QueueEntry q = (QueueEntry) db.getContent(queueEntryDataFormat);
//			queueEntries.add(q);
//			currentQueue.setItems(queueEntries);
//			success = true;
//		}
//		
//		event.setDropCompleted(success);
//		event.consume();
	}






	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////  REGULAR OBJECTS ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Lists all of the queue entries in the database and the tableview
	private ObservableList<QueueEntry> queueEntries;
	
	@SuppressWarnings("unused")
	private DataFormat queueEntryDataFormat = new DataFormat("queueEntry");




	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////  REGULAR METHODS ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		asserts();


		// Setup output files
		PrintWriter stdOutFil = null;
		PrintWriter stdErrFil = null;
		try {
			stdOutFil = new PrintWriter(new FileOutputStream(Dystrack.appDir + "\\stdout.log", true));
			stdErrFil = new PrintWriter(new FileOutputStream(Dystrack.appDir + "\\stderr.log", true));
		} catch (FileNotFoundException e1) { System.err.println("failed to initialize log files"); }


		// redirect syso / syserr to go to the onscreen 
		PrintStream newOut = new SmartPrintStream(new TextAreaOutputStream(sysoLogTxtbox), stdOutFil);
		System.setOut(newOut);
		PrintStream newErr = new SmartPrintStream(new TextAreaOutputStream(syserrLogTxtbox), stdErrFil);
		System.setErr(newErr);



		System.out.println("Initializing primary controller...");

		// Add listeners to the control properties that need them
		// manual request mode, have button text change based on whether or not it is selected
		requestManualBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue.booleanValue()) // button pressed, set text to "Open"
					requestManualBtn.setText("Open");
				else // button not pressed, set text to "closed"
					requestManualBtn.setText("Closed");
			}
		});


		// Foobar play status, same deal as above
		foobarPlayStatusBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue.booleanValue()) // pressed, click to pause
					foobarPlayStatusBtn.setText("Pause Foobar");
				else // released, click to play
					foobarPlayStatusBtn.setText("Play Foobar");
			}
		});



		// initialize the database - Block the UI thread with this because we don't want the UI to initialize
		// without the database ready, but we also want syso and syserr redirected to the onscreen text boxes beforehand
		try { Dystrack.db.verifyConnected(); }
		catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "FATAL: Failed to open database connection! Check error log for more details.");
			a.setTitle("Database error");
			a.setHeaderText("Database Error");
			e.printStackTrace(); // print to the error log before showing the error message
			a.showAndWait();
		}


		// Initialize the parameters
		bindParametersOffFocus();

		// Load all of the parameters to the UI
		readConfigParams();

		// Initialize the tableViews
		initQueueTable();
		readQueueToTable();

		// Initialize the update services 
		Dystrack.UIUpdateService.scheduleAtFixedRate(uiUpdateTask, 1000, Dystrack.UIUpdateMillis, TimeUnit.MILLISECONDS); // Run immediately, 1.5 seconds between each update

		// Init the main controller / foobar interfaces
		Dystrack.rc = new RequestControl();
		Dystrack.foobar = new Foobar(Dystrack.foobarPath);
		RCTables.queueHistoryTable.verifyExists(Dystrack.db.getDb());
	}






	/**
	 * Updates the UI Request mode controls to reflect the given request mode status
	 */
	private void setReqModeUI(ReqMode mode)
	{
		if(mode == ReqMode.AUTO) { // checkbox selected, manual button grayed
			this.requestsAutoBox.setSelected(true);
			this.requestManualBtn.setDisable(true);
		} else if (mode == ReqMode.CLOSED) { // checkbox unselected, manual button active, text set to "Closed", not pressed - Should be handled by property listener!
			this.requestsAutoBox.setSelected(false);
			this.requestManualBtn.setDisable(false);
			this.requestManualBtn.selectedProperty().set(false);
		} else if (mode == ReqMode.OPEN) { // checkbox unselected, manual button active, text set to "Open", pressed - Should be handled by property listener!
			this.requestsAutoBox.setSelected(false);
			this.requestManualBtn.setDisable(false);
			this.requestManualBtn.selectedProperty().set(true);
		} else { // Some error occured, dump stack trace to stderr
			System.err.println("Invalid mode passed to setReqModeUI");
			Thread.dumpStack();
		}
	}




	/**
	 * Reads the current request mode from the UI
	 */
	private ReqMode getCurrReqMode()
	{
		ReqMode ret;

		if(requestsAutoBox.isSelected()) // auto box checked
			ret = ReqMode.AUTO;

		else if (requestManualBtn.isSelected()) // auto box not checked, and manual button pressed
			ret = ReqMode.OPEN;

		else // auto box not checked, manual button not pressed
			ret = ReqMode.CLOSED;

		return ret;
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
			double len = cellData.getValue().getSong().getLength();

			// format time as Mins:ss
			SimpleStringProperty prop = new SimpleStringProperty();
			prop.set(Util.secsToTimeString((int) len));
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
	private void readConfigParams()
	{
		System.out.println("Loading configuration parameters...");

		Task<Void> tsk = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception
			{
				try 
				{
					// parameter values - fetch here to use in the background thread that updates the UI
					double prs = Dystrack.db.readRealParam("percentRandom");
					String qome = Dystrack.db.readStringParam("queueOpenMins");
					String qcme = Dystrack.db.readStringParam("queueCloseMins");
					String ssce = Dystrack.db.readStringParam("stdSongCooldown");
					String gcse = Dystrack.db.readStringParam("stdUserCooldown");
					String suce = Dystrack.db.readStringParam("globalCostScl");
					String bspme = Dystrack.db.readStringParam("baseSongPriceMin");
					String bheme = Dystrack.db.readStringParam("baseHistoryExpireMins");
					String birse = Dystrack.db.readStringParam("baseImmediateReplayScl");

					// Control fields info
					ReqMode requestMode = Dystrack.db.getRequestMode();
					boolean freeReqests = Dystrack.db.readBoolParam("freeRequests");
					boolean ignoreHistory = Dystrack.db.readBoolParam("ignoreHistory");
					boolean dontRecordHistory = Dystrack.db.readBoolParam("dontRecordHistory");


					Platform.runLater(() -> // only update the GUI in app thread
					{
						try {
							// parameter values
							percentRandomSlider.setValue(prs);
							queueOpenMinsEntry.setText(qome);
							queueCloseMinsEntry.setText(qcme);
							stdSongCooldownEntry.setText(ssce);
							stdUserCooldownEntry.setText(gcse);
							globalCostSclEntry.setText(suce);
							baseSongPriceMinEntry.setText(bspme);
							baseHistoryExpireMinsEntry.setText(bheme);
							baseImmediateReplaySclEntry.setText(birse);

							// Control field values
							setReqModeUI(requestMode);
							freeRequestsBox.setSelected(freeReqests);
							ignoreHistoryBox.setSelected(ignoreHistory);
							dontRecordHistoryBox.setSelected(dontRecordHistory);

							System.out.println("Finished Loading configuration parameters");
						} catch(Exception e) 
						{
							System.err.println("Error occured trying to read config parametes"); e.printStackTrace(); 
							Alert a = new Alert(AlertType.ERROR, "Failed to read paramaters from database!");
							a.show();

						}
					});

				} catch(Exception e) // Exception occured loading parameters
				{
					e.printStackTrace();
					Platform.runLater(() -> {
						Alert a = new Alert(AlertType.ERROR, "Exception occured in loadConfigParams(). This is a serious issue that prevents proper reading from the database.");
						a.setTitle("Fatal error loading parameters");
						a.setHeaderText("FATAL:");
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
					Dystrack.db.setParameter("percentRandom", prs);
					Dystrack.db.setParameter("queueOpenMins", qome);
					Dystrack.db.setParameter("queueCloseMins", qcme);
					Dystrack.db.setParameter("stdSongCooldown", ssce);
					Dystrack.db.setParameter("stdUserCooldown", suce);
					Dystrack.db.setParameter("globalCostScl", gcse);
					Dystrack.db.setParameter("baseSongPriceMin", bspme);
					Dystrack.db.setParameter("baseHistoryExpireMins", bheme);
					Dystrack.db.setParameter("baseImmediateReplayScl", birse);

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
	 * Reads the variable values from the database. <p/>
	 * <b>NOTE</b>: This assumes it will be called in a background thread explicitly, 
	 * so don't call it from the UI thread!
	 */
	private void readVars()
	{	
		double reqSessionPctFull = Dystrack.db.readRealParam("reqSessionPctFull");
		int queueSize = Dystrack.db.readIntegerParam("queueSize");
		double queueLength = Dystrack.db.readRealParam("queueLength");
		boolean queueOpen = Dystrack.db.readBoolParam("queueOpen");
		boolean foobarOpen = Dystrack.db.readBoolParam("foobarOpen");

		Platform.runLater(() -> {
			if(getCurrReqMode() != ReqMode.AUTO) // session % full is only valid when in automatic mode
				requestSessionPctFullBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
			else
				requestSessionPctFullBar.setProgress(reqSessionPctFull);

			// Update the other variables
			queueLengthLabel.setText(Util.secsToTimeString((int) queueLength)+ " minutes");
			queueSizeLabel.setText(queueSize+ " songs");
			queueOpenBox.setSelected(queueOpen);
			foobarOpenBox.setSelected(foobarOpen);

			// Format the now playing tab
			String npt = "";
			Song song = Dystrack.foobar.getCurrSong();

			if(Dystrack.foobar.isPlaying() && song != null)
			{
				npt = String.format("NOW PLAYING: \"%s - %s\":  %s / %s", 
						Util.ellipseTrim(50, song.getOstName()), 
						Util.ellipseTrim(100, song.getSongName()),
						Util.secsToTimeString((int) (song.getLength() - Dystrack.foobar.getCurrSongRemaining())),
						Util.secsToTimeString((int) song.getLength()));
			} else
				npt = "Not Playing Anything!";
			nowPlayingLabel.setText(npt);

			// set the Foobar state box as disabled if necessary
			//				foobarPlayStatusBtn.setDisable(!foobarOpen);
		});
	}




	/**
	 * Reads the forward queue from the database, and updates the queue table on the GUI.<p/>
	 * <b>NOTE</b>: This method does not implicitly create a background thread to run the 
	 * database queries (but will run UI updates in the UI thread), so it should be called
	 * explicitly from a background thread
	 * 
	 * @deprecated use the {@link #smartReadQueueToTable()} version instead
	 */
	public void readQueueToTable()
	{
		queueEntries = QueueEntry.getForwardQueue(); 

		Platform.runLater(() ->  {
			currentQueue.getItems().clear();
			currentQueue.getItems().addAll(queueEntries);
		});
	}





	/**
	 * Reads the queue to the table, but only updates what needs to be updated, that is, 
	 * what's different <p/>
	 * <b>NOTE</b>: This method does not implicitly create a background thread to run the 
	 * database queries (but will run UI updates in the UI thread), so it should be called
	 * explicitly from a background thread
	 */
	public void smartReadQueueToTable1()
	{
		ObservableList<QueueEntry> dbEntries = QueueEntry.getForwardQueue();

		Platform.runLater(() -> {
			if(queueEntries != null)
			{
				int tblSize = queueEntries.size();

				// loop through, take out all the different ones
				for(int i=0; i< dbEntries.size(); i++)
				{
					if(tblSize >= i) { // these are new entries, so just add them
						queueEntries.add(dbEntries.get(i));
						tblSize++;
					} else if(!dbEntries.get(i).equals(queueEntries.get(i))) // the elements at this index are different, so update
						queueEntries.set(i, dbEntries.get(i));
				}
			} else // It hasn't been initialized yet, so just copy over the whole thing
				 queueEntries = dbEntries;
			
			currentQueue.setItems(queueEntries); // commit
		});

	}






	/**
	 * Writes the queueEntries list to the database. <br>
	 * <b>CAUTION:</b> This will override everything in the queue!
	 */
	private void writeQueueToDB()
	{
		Task<Void> tsk = new Task<Void>() {
			@Override
			protected Void call() throws Exception 
			{
				try { 
					RCTables.forwardQueueTable.verifyExists(Dystrack.db.getDb());
					Dystrack.db.execRaw("DELETE FROM " +RCTables.forwardQueueTable.getName());  // empty the table
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





	/**
	 * Binds the update methods on all of the parameter textfield inputs to the isFocused
	 * property, to act whenever focus is lost. <p/>
	 * 
	 * for each one, if the old value of isFocused() is true, the new value is false, meaning we lost focus
	 * If we lost focus, it's a safe bet the user is done editing the control, so it's safe to process / validate it
	 */
	private void bindParametersOffFocus()
	{
		queueOpenMinsEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				queueOpenMinsOnAction(null); 
		});

		queueCloseMinsEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				queueCloseMinsOnAction(null); 
		});

		stdSongCooldownEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				stdSongCooldownOnAction(null); 
		});

		stdUserCooldownEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				stdUserCooldownOnAction(null); 
		});

		stdUserCooldownEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				stdUserCooldownOnAction(null); 
		});

		globalCostSclEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				globalCostSclOnAction(null); 
		});

		baseSongPriceMinEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				baseSongPriceMinOnAction(null); 
		});

		baseHistoryExpireMinsEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				baseHistoryExpireMinsOnAction(null); 
		});

		baseImmediateReplaySclEntry.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
				baseImmediateReplaySclOnAction(null); 
		});

		// OnAction for this is disabled as that method gets spammed when dragging the slider, which wreaks havoc. Just write whenever focus is lost.
		percentRandomSlider.focusedProperty().addListener((arg0, oldVal, newVal) -> { 
			if(oldVal.booleanValue())
			{
				Thread t = new Thread(() -> {
					try { Dystrack.db.setParameter("percentRandom", percentRandomSlider.getValue()); } 
					catch (Exception e) {
						e.printStackTrace();
						Dystrack.databaseErrorAlert.showAndWait();
					}
				});

				t.setName("updatePercentRandom");
				t.setDaemon(true);
				t.start();
			}
		});
	}




	/**
	 * Writes the value of the given checkbox to the database
	 */
	private void writeCheckboxToDB(CheckBox box, String name) {
		Thread t = new Thread(() -> {
			try {
				Dystrack.db.setParameter(name, box.isSelected());
			} catch (Exception e) {
				e.printStackTrace();
				Dystrack.databaseErrorAlert.showAndWait();
			}
		});

		t.setDaemon(true);
		t.setName("write " +name+ " to DB");
		t.start();
	}




	/**
	 * This code will run periodically to keep the UI in sync with the database. We use this polling approach
	 * instead of an event driven approach because it is anticipated that outside / untracked changes to the 
	 * database can be made without warning, and we would like to register these <p/>
	 * 
	 */
	private Runnable uiUpdateTask = new Runnable() {
		@Override public void run() 
		{
			// we may add updating the queue table back, but as it stands it doesn't play nice with things like sorting
			if(!currentQueue.isFocused()) // only run if we aren't being clicked on
				readQueueToTable();

			// update the variables entries
			readVars();
		}
	};






}










