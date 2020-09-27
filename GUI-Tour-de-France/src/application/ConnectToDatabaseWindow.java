package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectToDatabaseWindow 
{
	public static void connectToDatabaseWindow()
	{
		Stage ldbStage = new Stage();
		VBox ldbVBox = new VBox();
		Scene dbwScene = new Scene(ldbVBox);
		ldbStage.setScene(dbwScene);
		
		Label lb_username = new Label("Benutzername:");
		Label lb_password = new Label("Passwort");
		
		TextField tf_username = new TextField();
		PasswordField pf_password = new PasswordField();
		
		Label lb_headerLabel = new Label("Zu welcher Datenbank soll eine Verbindung aufgebaut werden?");
		ComboBox<String> cb_AvailableDatabases = new ComboBox<String>();
		Button bt_connectToDatabase = new Button("Verbinden");
		Button bt_Cancel = new Button("Abbrechen");
		
		VBox vbox_chooseDB = new VBox();
		HBox hbox_Buttons = new HBox();
		hbox_Buttons.setPadding(new Insets(15));
		hbox_Buttons.setSpacing(25);
		
		//Datenbanknamen für die ComboBox vom Server ziehen
		cb_AvailableDatabases.getItems().addAll(DatabaseFunctions.getAvailableDatabases(Main.serverConnection));
		
		vbox_chooseDB.getChildren().addAll(lb_headerLabel, cb_AvailableDatabases, lb_username, tf_username, lb_password, pf_password);
		hbox_Buttons.getChildren().addAll(bt_connectToDatabase, bt_Cancel);
		ldbVBox.getChildren().addAll(vbox_chooseDB, hbox_Buttons);	
		
		ldbVBox.setPadding(new Insets(15));
		ldbVBox.setSpacing(25);
		
		//Das Fenster zentriert anzeigen
		ldbStage.setTitle("Mit Datenbank verbinden");
		ldbStage.centerOnScreen();
		ldbStage.show();
		
		bt_connectToDatabase.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				
				Main.databaseConnection = DatabaseFunctions.connectToDatabase(Main.serverConnection, 
						cb_AvailableDatabases.getValue(), tf_username.getText(), pf_password.getText());
				if(Main.databaseConnection != null)
				{
					Alert al_DBCreationSuccessful = new Alert(AlertType.INFORMATION, "Verbindung zur Datenbank "+cb_AvailableDatabases.getValue()+""
							+ " erfolgreich.", ButtonType.OK);
					al_DBCreationSuccessful.setTitle("Hinweis");
					al_DBCreationSuccessful.showAndWait();
					ldbStage.hide();
					FunctionsWindow.createFunctionsWindow();
				}
			
				
			}
			
		});
		
		bt_Cancel.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				ldbStage.hide();
			}
			
		});
		
		
		
	}
}
