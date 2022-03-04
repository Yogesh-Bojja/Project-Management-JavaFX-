package model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertClass {

	public static void giveALert(String title, String header) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
//    	alert.setContentText("ssss");
    	alert.show();
	}
}
