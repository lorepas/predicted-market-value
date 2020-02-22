package dm;
	
import java.awt.Component;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import dm.classifier.RandomTreeClassifier;
import dm.persistence.DAOPlayer;
import dm.persistence.IDAOPlayer;
import dm.view.AppController;
import javafx.application.Application;
import javafx.stage.Stage;
import weka.core.Instances;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class App extends Application {
	public static App sharedInstance = new App();
	private IDAOPlayer daoPlayer = new DAOPlayer();
	private RandomTreeClassifier rtc = new RandomTreeClassifier();
	private AppController appController = new AppController();
	private Instances dataSet = null;
	private Stage primaryStage;
	
	public IDAOPlayer getDaoPlayer() {
		return daoPlayer;
	}
	
	public RandomTreeClassifier getRandomTreeClassifier() {
		return rtc;
	}
	
	public static App getSharedInstance() {
		return sharedInstance;
	}

	public RandomTreeClassifier getRtc() {
		return rtc;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public AppController getAppController() {
		return appController;
	}

	public Instances getDataSet() {
		return dataSet;
	}

	public void setDataSet(Instances dataSet) {
		this.dataSet = dataSet;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			Logger mongoLogger = loggerContext.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.OFF);
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/dm/view/App.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("/dm/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(739);
			primaryStage.setMinWidth(866);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
