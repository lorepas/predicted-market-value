package dm.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import dm.App;
import dm.persistence.DAOException;
import dm.utility.Utils;
import dm.classifier.*;

public class AppController {
	@FXML Button generateFileJSON;
	@FXML Button generateFileCSV;
	@FXML Label fileResult;
	@FXML TextField teamTextField;
	@FXML TextField presencesTextField;
	@FXML TextField yellowCardsTextField;
	@FXML TextField nationTextField;
	@FXML TextField goalsTextField;
	@FXML TextField doubleYellowCardsTextField;
	@FXML TextField roleTextField;
	@FXML TextField assistsTextField;
	@FXML TextField redCardsTextField;
	@FXML TextField bornDateTextField;
	@FXML TextField penaltyGoalsTextField;
	@FXML TextField minutesPlayedTextField;
	@FXML TextField callsTextField;
	@FXML TextField ownGoalsTextField;
	@FXML Button predictionButton;
	@FXML Label valuePredectedLabel;
	@FXML ComboBox<String> comboBoxTeam;
	@FXML ComboBox<String> comboBoxNation;
	@FXML ComboBox<String> comboBoxRole;
	ObservableList comboDefault = FXCollections.observableArrayList();
	@FXML Button insertFileButton;
	
	
	

	public void ActionGenerateFileJson(ActionEvent e) throws DAOException {
		System.out.println("Writing JSON file");
		generateFileCSV.setDisable(true);
		Utils.writeFileJSON(App.sharedInstance.getDaoPlayer().retrieveOverallStatisticsLastSeason());
		fileResult.setText("File JSON correctly generated");
		generateFileCSV.setDisable(false);
	}
	
	public void ActionGenerateFileCSV(ActionEvent e) throws DAOException {
		System.out.println("Writing CSV file");
		generateFileJSON.setDisable(true);
		Utils.writeFileCSV(App.sharedInstance.getDaoPlayer().retrieveOverallStatisticsLastSeason());
		fileResult.setText("File CSV correctly generated");
		generateFileJSON.setDisable(false);
	}
	public void ActionPredictionMarketValue(ActionEvent e) throws Exception {
		String textTeam = comboBoxTeam.getSelectionModel().getSelectedItem();
		String textNation = comboBoxNation.getSelectionModel().getSelectedItem(); 
		String textRole = comboBoxRole.getSelectionModel().getSelectedItem();
		String textBornDate = bornDateTextField.getText(); 
		Integer presenciesNumber = Integer.valueOf(presencesTextField.getText()); 
		Integer callsNumber = Integer.valueOf(callsTextField.getText());
		Integer goalsNumber = Integer.valueOf(goalsTextField.getText());
		Integer assistsNumber = Integer.valueOf(assistsTextField.getText());
		Integer penaltyGoalsNumber = Integer.valueOf(penaltyGoalsTextField.getText());
		Integer ownGoalsNumber = Integer.valueOf(ownGoalsTextField.getText()); 
		Integer yellowCardsNumber = Integer.valueOf(yellowCardsTextField.getText()); 
		Integer doubleYellowCardsNumber = Integer.valueOf(doubleYellowCardsTextField.getText()); 
		Integer redCardsNumber = Integer.valueOf(redCardsTextField.getText());
		Long minutesPlayedNumber = Long.valueOf(minutesPlayedTextField.getText());
		App.sharedInstance.getRandomTreeClassifier().classifier(textTeam,textNation,textRole,textBornDate,presenciesNumber,callsNumber,
				ownGoalsNumber,goalsNumber,assistsNumber,penaltyGoalsNumber,yellowCardsNumber,doubleYellowCardsNumber,redCardsNumber,minutesPlayedNumber);
		
	}
	public void ActionUpdateFile(ActionEvent e) throws IOException {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV files(*.csv)","*.csv" );
		chooser.getExtensionFilters().add(extensionFilter);
		String userDirectoryString = System.getProperty("user.home");
		File userDirectory = new File(userDirectoryString);
		if(!userDirectory.canRead()) {
		    userDirectory = new File("c:/");
		}
		chooser.setInitialDirectory(userDirectory);

		//Choose the file
		File chosenFile = chooser.showOpenDialog(null);
		//Make sure a file was selected, if not return default
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		    loadTrainingDataset(path);
			
		} else {
		    //default return value
		    path = null;
		}
		
	}
	
	public void loadTrainingDataset(String path) throws IOException {
		CSVLoader csv = new CSVLoader();
		csv.setSource(new File(path));
		//csv.setNoHeaderRowPresent(false);
		csv.setNominalAttributes("1-4");
		csv.setDateAttributes("5");
		csv.setDateFormat("dd/MM/yyyy");
		csv.setFieldSeparator(";");
		Instances trainDataset = csv.getDataSet();
		trainDataset.deleteAttributeAt(0);
		List<String> listTeams = App.getSharedInstance().getRandomTreeClassifier().getTeamNominal();
		List<String> listNations = App.getSharedInstance().getRandomTreeClassifier().getNationNominal();
		List<String> listRoles = App.getSharedInstance().getRandomTreeClassifier().getRoleNominal();
		for(int i=0;i<trainDataset.numDistinctValues(0);i++) {
			listTeams.add(trainDataset.attribute(0).value(i));
		}
		
		for(int i=0;i<trainDataset.numDistinctValues(1);i++) {
			listNations.add(trainDataset.attribute(1).value(i));
		}
		
		for(int i=0;i<trainDataset.numDistinctValues(2);i++) {
			listRoles.add(trainDataset.attribute(2).value(i));
		}
		
		ObservableList<String> obListTeams = FXCollections.observableArrayList(listTeams);
		ObservableList<String> obListNations = FXCollections.observableArrayList(listNations);
		ObservableList<String> obListRoles = FXCollections.observableArrayList(listRoles);
		comboBoxTeam.setItems(obListTeams);
		comboBoxRole.setItems(obListRoles);
		comboBoxNation.setItems(obListNations);
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);
		System.out.println(trainDataset);
		App.getSharedInstance().setDataSet(trainDataset);
		
		
		
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		comboBoxTeam.setItems(comboDefault);
		comboBoxNation.setItems(comboDefault);
		comboBoxRole.setItems(comboDefault);
	}
	
	
}
