package dm.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import dm.App;
import dm.persistence.DAOException;
import dm.utility.Utils;

public class AppController {
	@FXML Button generateFileJSON;
	@FXML Button generateFileCSV;
	@FXML Label fileResult;
	
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
}
