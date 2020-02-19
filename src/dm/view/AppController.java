package dm.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		String textTeam = teamTextField.getText(); 
		String textNation = nationTextField.getText(); 
		String textRole = roleTextField.getText(); 
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
}
