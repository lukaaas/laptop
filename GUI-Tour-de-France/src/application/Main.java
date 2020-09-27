package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application
{
	public static Connection serverConnection;
	public static Connection databaseConnection;
	
	@Override
	public void start(Stage primaryStage) 
	{
		
		
		
		try {				
			
			Label headerLabel = new Label("Datenbankserver auswählen");
			
			ToggleGroup datenbanken = new ToggleGroup();
			
			RadioButton rb_liveDB = new RadioButton("Live Datenbankserver");
			rb_liveDB.setToggleGroup(datenbanken);
			
			RadioButton rb_testDB = new RadioButton("Test Datenbankserver");
			rb_testDB.setToggleGroup(datenbanken);
			
			RadioButton rb_manuell = new RadioButton("Manuelle Eingabe");
			rb_manuell.setToggleGroup(datenbanken);
			
			Button bt_starten = new Button("Verbinden");
			bt_starten.setDisable(true);
			
			
			//VBox für die Labels und Eingabefelder für IP-Adresse, Port, Benutzername und Passwort
			VBox vbox_eingaben = new VBox();
			Label lb_Benutzername = new Label("Benutzername:");
			TextField tf_Benutzername = new TextField();
			Label lb_Passwort = new Label("Passwort");
			PasswordField pf_Passwort = new PasswordField();
			Label lb_IPAdresse = new Label("IP-Adresse");
			TextField tf_IPAdresse = new TextField();
			Label lb_Port = new Label("Port");
			TextField tf_Port = new TextField();
			
			vbox_eingaben.getChildren().addAll(lb_Benutzername, tf_Benutzername, lb_Passwort, pf_Passwort, lb_IPAdresse, tf_IPAdresse,
											lb_Port, tf_Port);
			vbox_eingaben.setVisible(false);
			
			VBox vbox_Serverauswahl = new VBox(headerLabel,rb_liveDB,rb_testDB,rb_manuell, vbox_eingaben,bt_starten);
			vbox_Serverauswahl.setPadding(new Insets(15));
			vbox_Serverauswahl.setSpacing(25);
		
			
			Scene scene = new Scene(vbox_Serverauswahl);
			
			primaryStage.setTitle("Datenbankserver auswählen");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			rb_liveDB.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					//Labels und Eingabefelder sichtbar machen, entsprechende Felder deaktivieren und mit Standardtext füllen
					vbox_eingaben.setVisible(true);
					for(int i=7;i>3;i--)
					{
						vbox_eingaben.getChildren().get(i).setDisable(true);
						
						tf_IPAdresse.setText("193.175.198.25");
						tf_Port.setText("3306");
						bt_starten.setDisable(false);
					}
					
				}
			});
			
			rb_testDB.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					//Labels und Eingabefelder sichtbar machen, entsprechende Felder deaktivieren und mit Standardtext füllen
					vbox_eingaben.setVisible(true);
					for(int i=7;i>3;i--)
					{
						vbox_eingaben.getChildren().get(i).setDisable(true);
					}
					tf_IPAdresse.setText("127.0.0.1");
					tf_Port.setText("3306");
					bt_starten.setDisable(false);
				}
			});
			
			rb_manuell.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					//Labels und Eingabefelder sichtbar machen, entsprechende Felder deaktivieren und mit Standardtext füllen
					vbox_eingaben.setVisible(true);
					for(int i=7;i>3;i--)
					{
						vbox_eingaben.getChildren().get(i).setDisable(false);
						tf_IPAdresse.clear();
						tf_Port.clear();
						bt_starten.setDisable(false);
					}
				}
			});
		
			bt_starten.setOnAction(new EventHandler<ActionEvent>() 
			{
				public void handle(ActionEvent e)
				{
					//Checken ob alle Felder ausgefüllt sind
					if(tf_Benutzername.getText().isEmpty() || tf_IPAdresse.getText().isEmpty() || tf_Port.getText().isEmpty())
					{
						Alert alarm = new Alert(Alert.AlertType.WARNING,"Alle Felder müssen ausgefüllt sein.", ButtonType.OK);
						alarm.setTitle("Fehler!");
						alarm.show();
						
					}
					else
					{
						//Verbindung zum Server mit den entsprechenden Eingaben herstellen und in der Variable serverConnection speichern
						serverConnection = DatabaseFunctions.connectToServer("jdbc:mysql://"+tf_IPAdresse.getText()+":"+
													tf_Port.getText(), tf_Benutzername.getText(), pf_Passwort.getText());
						
						
					}
					
					//Wenn eine Verbindung hergestellt wurde, öffne das nächste Fenster 
					if(serverConnection != null)
					{
						Alert al_ConnectionSuccessful = new Alert(AlertType.INFORMATION, "Die Verbindung wurde hergestellt.", ButtonType.OK);
						al_ConnectionSuccessful.setTitle("Hinweis");
						al_ConnectionSuccessful.showAndWait();
						primaryStage.hide();
						DatabaseWindow.createDatabaseWindow();
					}
					
				
					
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try 
		{
			launch(args);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(databaseConnection != null)
					databaseConnection.close();
				if(serverConnection != null)
					serverConnection.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
				
		}
	}
	
}
