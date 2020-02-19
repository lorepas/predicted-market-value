package dm;
	
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import dm.persistence.DAOPlayer;
import dm.persistence.IDAOPlayer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class App extends Application {
	public static App sharedInstance = new App();
	private IDAOPlayer daoPlayer = new DAOPlayer();
	
	public IDAOPlayer getDaoPlayer() {
		return daoPlayer;
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
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
