package view;
	
import java.io.IOException;

import controller.GUIController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	FXMLLoader loader;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
			Parent root = (Parent) loader.load();
	        Scene newScene = new Scene(root, 800, 700);
			newScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setScene(newScene);
	        GUIController controller = loader.getController();
	        controller.setStage(primaryStage);
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
