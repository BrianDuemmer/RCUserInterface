package application;

import java.io.Console;
import java.io.File;
import java.io.PrintStream;

/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class. Mine for the copying and pasting
 */


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.web.WebView;
import util.TextAreaOutputStream;
import util.SmartPrintStream;

public class MainWindowController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="overviewPage"
	private Tab overviewPage; // Value injected by FXMLLoader

	@FXML // fx:id="globalCostSclEntry"
	private TextField globalCostSclEntry; // Value injected by FXMLLoader

	@FXML // fx:id="stdSongCooldownEntry"
	private TextField stdSongCooldownEntry; // Value injected by FXMLLoader

	@FXML // fx:id="databasePage"
	private Tab databasePage; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideHistoryExpireMinsCol"
	private TableColumn<?, ?> songOverrideHistoryExpireMinsCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueUsernameCol"
	private TableColumn<?, ?> currentQueueUsernameCol; // Value injected by FXMLLoader

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

	@FXML // fx:id="sysoLogTxtbox"
	private TextArea sysoLogTxtbox; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueNameCol"
	private TableColumn<?, ?> currentQueueNameCol; // Value injected by FXMLLoader

	@FXML // fx:id="baseSongPriceMinEntry"
	private TextField baseSongPriceMinEntry; // Value injected by FXMLLoader

	@FXML // fx:id="revertParamsBtn"
	private Button revertParamsBtn; // Value injected by FXMLLoader

	@FXML // fx:id="queueSizeLabel"
	private Label queueSizeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="baseHistoryExpireMinsEntry"
	private TextField baseHistoryExpireMinsEntry; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueOSTCol"
	private TableColumn<?, ?> currentQueueOSTCol; // Value injected by FXMLLoader

	@FXML // fx:id="queueOpenMinsEntry"
	private TextField queueOpenMinsEntry; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistTimeBannedCol"
	private TableColumn<?, ?> userBlacklistTimeBannedCol; // Value injected by FXMLLoader

	@FXML // fx:id="percentRandomSlider"
	private Slider percentRandomSlider; // Value injected by FXMLLoader

	@FXML // fx:id="syserrLogTxtbox"
	private TextArea syserrLogTxtbox; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideBaseCostCol"
	private TableColumn<?, ?> songOverrideBaseCostCol; // Value injected by FXMLLoader

	@FXML // fx:id="viewSysoLogBtn"
	private Button viewSysoLogBtn; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueue"
	private TableView<?> currentQueue; // Value injected by FXMLLoader

	@FXML // fx:id="dontRecordHistoryBox"
	private CheckBox dontRecordHistoryBox; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistPage"
	private Tab userBlacklistPage; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistTable"
	private TableView<?> userBlacklistTable; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersCostSclCol"
	private TableColumn<?, ?> vipUsersCostSclCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueUserIDCol"
	private TableColumn<?, ?> currentQueueUserIDCol; // Value injected by FXMLLoader

	@FXML // fx:id="songOverridePage"
	private Tab songOverridePage; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideImmediateRelaySclCol"
	private TableColumn<?, ?> songOverrideImmediateRelaySclCol; // Value injected by FXMLLoader

	@FXML // fx:id="viewSyserrLogBtn"
	private Button viewSyserrLogBtn; // Value injected by FXMLLoader

	@FXML // fx:id="requestsAutoBox"
	private CheckBox requestsAutoBox; // Value injected by FXMLLoader

	@FXML // fx:id="userBlacklistNoteCol"
	private TableColumn<?, ?> userBlacklistNoteCol; // Value injected by FXMLLoader

	@FXML // fx:id="stdUserCooldownEntry"
	private TextField stdUserCooldownEntry; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueueTimeRequestedCol"
	private TableColumn<?, ?> currentQueueTimeRequestedCol; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideSongNameCol"
	private TableColumn<?, ?> songOverrideSongNameCol; // Value injected by FXMLLoader

	@FXML // fx:id="reqManualControl"
	private ToggleGroup reqManualControl; // Value injected by FXMLLoader

	@FXML // fx:id="songOverrideTable"
	private TableView<?> songOverrideTable; // Value injected by FXMLLoader

	@FXML // fx:id="vipUsersUsernameCol"
	private TableColumn<?, ?> vipUsersUsernameCol; // Value injected by FXMLLoader

	@FXML // fx:id="currentQueuePriorityCol"
	private TableColumn<?, ?> currentQueuePriorityCol; // Value injected by FXMLLoader

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

	@FXML // fx:id="saveParamsBtn"
	private Button saveParamsBtn; // Value injected by FXMLLoader

	@FXML
	void viewSysoLogOnAction(ActionEvent event) {

	}

	@FXML
	void viewSyserrLogOnAction(ActionEvent event) {

	}

	@FXML
	void queueOpenMinsOnAction(ActionEvent event) {

	}

	@FXML
	void queueCloseMinsOnAction(ActionEvent event) {

	}

	@FXML
	void stdSongCooldownOnAction(ActionEvent event) {

	}

	@FXML
	void stdUserCooldownOnAction(ActionEvent event) {

	}

	@FXML
	void globalCostSclOnAction(ActionEvent event) {

	}

	@FXML
	void baseSongPriceMinOnAction(ActionEvent event) {

	}

	@FXML
	void baseHistoryExpireMinsOnAction(ActionEvent event) {

	}

	@FXML
	void baseImmediateReplaySclOnAction(ActionEvent event) {

	}

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
	void saveParamsBtnOnAction(ActionEvent event) {

	}

	@FXML
	void revertParamsBtnOnAction(ActionEvent event) {

	}

	@FXML
	void exportQueueBtnOnAction(ActionEvent event) {

	}

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
		System.err.println("An error");

	}




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
}

