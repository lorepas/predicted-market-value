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
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceWithMissingValue;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
	@FXML Label pathFile;
	@FXML Button predictionButton;
	@FXML Label valuePredectedLabel;
	@FXML ComboBox<String> comboBoxTeam;
	@FXML ComboBox<String> comboBoxNation;
	@FXML ComboBox<String> comboBoxRole;
	ObservableList comboDefault = FXCollections.observableArrayList();
	@FXML Button insertFileButton;
	@FXML Label maxMinValue;
	
	
	

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
		double res = App.sharedInstance.getRandomTreeClassifier().classifier(textTeam,textNation,textRole,textBornDate,presenciesNumber,callsNumber,
				ownGoalsNumber,goalsNumber,assistsNumber,penaltyGoalsNumber,yellowCardsNumber,doubleYellowCardsNumber,redCardsNumber,minutesPlayedNumber);
		String predRes = NumberFormat.getInstance(new Locale("it", "IT")).format(res);
		valuePredectedLabel.setText(predRes+" €");
		double error = (res*47)/100;
		double maxValue = res + error;
		double minValue = res - error;
		String predMax = NumberFormat.getInstance(new Locale("it", "IT")).format(maxValue);
		String predMin = NumberFormat.getInstance(new Locale("it", "IT")).format(minValue);
		maxMinValue.setText(predMax+" € - "+predMin+" €");
	}
	
	public void ActionUpdateFile(ActionEvent e) throws Exception {
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
		    pathFile.setText(path);
		    loadTrainingDataset(path);
			
		} else {
		    //default return value
		    path = null;
		}
		
	}
	
	public void loadTrainingDataset(String path) throws Exception {
		CSVLoader csv = new CSVLoader();
		csv.setSource(new File(path));
		csv.setNoHeaderRowPresent(false);
		csv.setNominalAttributes("1-4");
		csv.setDateAttributes("5");
		csv.setDateFormat("dd/MM/yyyy");
		csv.setMissingValue("null");
		Instances trainDataset = csv.getDataSet();
		
		//Declaration of all filters
		Remove remove = new Remove();
		Filter[] filters = new Filter[4];
		for(int i=0;i<4;i++)
			filters[i] = new RemoveWithValues();
		
		//Remove attribute season
		String[] optionsRemove = new String[2];
		optionsRemove[0]="-R";
		optionsRemove[1]="1";
		remove.setOptions(optionsRemove);
		remove.setInputFormat(trainDataset);
		
		//Create new dataset with one attribute less
		Instances datasetFiltered = Filter.useFilter(trainDataset, remove);
		
		//Remove missing values
		String[] optionsRemoveMV = new String[7];
		optionsRemoveMV[0]="-S";
		optionsRemoveMV[1]="0.0";
		optionsRemoveMV[2]="-C";
		optionsRemoveMV[3]="4";
		optionsRemoveMV[4]="-L";
		optionsRemoveMV[5]="first-last";
		optionsRemoveMV[6]="-M";
		filters[0].setOptions(optionsRemoveMV);
		filters[0].setInputFormat(datasetFiltered);
		datasetFiltered = Filter.useFilter(datasetFiltered, filters[0]);
		
		//Remove Portieri
		String[] optionsRemoveP = new String[6];
		optionsRemoveP[0]="-S";
		optionsRemoveP[1]="0.0";
		optionsRemoveP[2]="-C";
		optionsRemoveP[3]="3";
		optionsRemoveP[4]="-L";
		optionsRemoveP[5]="3";
		filters[1].setOptions(optionsRemoveP);
		filters[1].setInputFormat(datasetFiltered);
		datasetFiltered = Filter.useFilter(datasetFiltered, filters[1]);
		
		//Remove old
		String[] optionsRemoveOld = new String[6];
		optionsRemoveOld[0]="-S";
		optionsRemoveOld[1]="4.73382E11";
		optionsRemoveOld[2]="-C";
		optionsRemoveOld[3]="4";
		optionsRemoveOld[4]="-L";
		optionsRemoveOld[5]="first-last";
		filters[2].setOptions(optionsRemoveOld);
		filters[2].setInputFormat(datasetFiltered);
		datasetFiltered = Filter.useFilter(datasetFiltered, filters[2]);
		
		//Remove young
		String[] optionsRemoveYoung = new String[7];
		optionsRemoveYoung[0]="-S";
		optionsRemoveYoung[1]="1.0412892E12";
		optionsRemoveYoung[2]="-C";
		optionsRemoveYoung[3]="4";
		optionsRemoveYoung[4]="-L";
		optionsRemoveYoung[5]="first-last";
		optionsRemoveYoung[6]="-V";
		filters[3].setOptions(optionsRemoveYoung);
		filters[3].setInputFormat(datasetFiltered);
		datasetFiltered = Filter.useFilter(datasetFiltered, filters[3]);
		
		System.out.println(datasetFiltered.numInstances());
		List<String> listTeams = App.getSharedInstance().getRandomTreeClassifier().getTeamNominal();
		List<String> listNations = App.getSharedInstance().getRandomTreeClassifier().getNationNominal();
		List<String> listRoles = App.getSharedInstance().getRandomTreeClassifier().getRoleNominal();
		for(int i=0;i<datasetFiltered.numDistinctValues(0);i++) {
			listTeams.add(datasetFiltered.attribute(0).value(i));
		}
		
		for(int i=0;i<datasetFiltered.numDistinctValues(1);i++) {
			listNations.add(datasetFiltered.attribute(1).value(i));
		}
		
		for(int i=0;i<datasetFiltered.numDistinctValues(2);i++) {
			if(datasetFiltered.attribute(2).value(i).equals("Portiere"))
				continue;
			listRoles.add(datasetFiltered.attribute(2).value(i));
		}
		
		ObservableList<String> obListTeams = FXCollections.observableArrayList(listTeams);
		ObservableList<String> obListNations = FXCollections.observableArrayList(listNations);
		ObservableList<String> obListRoles = FXCollections.observableArrayList(listRoles);
		FXCollections.sort(obListTeams);
		FXCollections.sort(obListNations);
		FXCollections.sort(obListRoles);
		comboBoxTeam.setItems(obListTeams);
		comboBoxRole.setItems(obListRoles);
		comboBoxNation.setItems(obListNations);
		datasetFiltered.setClassIndex(datasetFiltered.numAttributes()-1);
		App.getSharedInstance().setDataSet(datasetFiltered);
	}
	
	public void ActionRefresh(ActionEvent e) {
		bornDateTextField.setText(""); 
		presencesTextField.setText(""); 
		callsTextField.setText("");
		goalsTextField.setText("");
		assistsTextField.setText("");
		penaltyGoalsTextField.setText("");
		ownGoalsTextField.setText("");
		yellowCardsTextField.setText("");
		doubleYellowCardsTextField.setText(""); 
		redCardsTextField.setText("");
		minutesPlayedTextField.setText("");
		valuePredectedLabel.setText("");
		maxMinValue.setText("");
		comboBoxTeam.getSelectionModel().clearSelection();
		comboBoxNation.getSelectionModel().clearSelection();
		comboBoxRole.getSelectionModel().clearSelection();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		comboBoxTeam.setItems(comboDefault);
		comboBoxNation.setItems(comboDefault);
		comboBoxRole.setItems(comboDefault);
	}
	
	
}
