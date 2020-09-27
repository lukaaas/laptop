package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DatabaseWindow 
{
	public static void createDatabaseWindow()
	{
		Stage dbwStage = new Stage();
		VBox dbwVBox = new VBox();
		Scene dbwScene = new Scene(dbwVBox);
		dbwStage.setScene(dbwScene);
		
				
		
		Label lb_headerLabel = new Label("Was möchten Sie tun?");
		Button bt_createNewDatabase = new Button("Neue Datenbank erstellen");
		Button bt_connectToDatabase = new Button("Mit bestehender Datenbank verbinden");
		
		Label lb_StatusFooter = new Label();
		try 
		{
			lb_StatusFooter.setText("Status: Verbunden mit "+Main.serverConnection.getMetaData().getURL());
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		//Neue VBox für die "Überschrift", sowie für die beiden Buttons
		VBox vbox_Buttons = new VBox();
		vbox_Buttons.getChildren().addAll(lb_headerLabel, bt_createNewDatabase, bt_connectToDatabase, lb_StatusFooter);
		vbox_Buttons.setPadding(new Insets(15));
		vbox_Buttons.setSpacing(25);
		
		//Hinzufügen der "Buttonbox" zur dbwVBox (und damit zur Scene)
		dbwVBox.getChildren().add(vbox_Buttons);
		
		//Das Fenster zentriert anzeigen
		dbwStage.setTitle("Datenbankoptionen");
		dbwStage.centerOnScreen();
		dbwStage.show();
		
		
		bt_createNewDatabase.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				DatabaseFunctions.createNewDatabase(Main.serverConnection);
			}
			
		});
		
		bt_connectToDatabase.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				ConnectToDatabaseWindow.connectToDatabaseWindow();
			}
			
		});
	}
	
	
}
