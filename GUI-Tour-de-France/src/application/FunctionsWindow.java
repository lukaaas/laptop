package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FunctionsWindow
{
	public static void createFunctionsWindow()
	{
		Stage primaryStage = new Stage();
		BorderPane root = new BorderPane();
		primaryStage.setTitle("Funktionen");
		Scene scene = new Scene(root,1200,750);
		Image image = new Image("file:background.jpg");
        ImageView view = new ImageView();
        view.setImage(image);
        view.setFitHeight(750);
        view.setFitWidth(1200);
        root.getChildren().add(view);
        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
	
        
   
	

		
		//Neue VBox für die Menübar
		VBox vbox = new VBox();
		vbox.setMinWidth(1200);
		
		
		//Neue Menübar anlegen
		MenuBar menuBar = new MenuBar();
		menuBar.setMinWidth(1200);
		
		//Erster Menüpunkt: Tabellen
		Menu mn_Tables = new Menu("Tabellen");
		//Auswahlpunkt: Tabellen anlegen
		MenuItem mn_Tables_TabellenAnlegen = new MenuItem("Tabellen anlegen");
		mn_Tables.getItems().add(mn_Tables_TabellenAnlegen);
		
		//Zweiter Menüpunkt: Import
		Menu mn_Import = new Menu("Import");
		//Auswahlpunkt für User/Tipps-Import
		MenuItem submenu_import_UserUndTipps = new MenuItem("User und Tipps importieren");
		submenu_import_UserUndTipps.setDisable(true);
		//Auswahlpunkt für Fahrer/Teams/Etappen
		MenuItem submenu_import_FahrerTeamsEtappen = new MenuItem("Fahrer, Teams und Etappen importieren");
		//Die Auswahlpunkte dem Menü hinzufügen
		mn_Import.getItems().addAll(submenu_import_UserUndTipps, submenu_import_FahrerTeamsEtappen);
		
		//Dritter Menüpunkt: Ergebnisse
		Menu mn_Ergebnisse = new Menu("Ergebnisse");
		//Auswahlpunkt für die Eingabe von Ergebnissen
		MenuItem submenu_ErgebnisseEingeben = new MenuItem("Ergebnisse eingeben");
		//Auswahlpunkt für die Ausgabe der Ergebnisse
		MenuItem submenu_ErgebnisseAusgeben = new MenuItem("Ergebnisse ausgeben");
		//Die Auswahlpunkte dem Menü hinzufügen
		mn_Ergebnisse.getItems().addAll(submenu_ErgebnisseEingeben, submenu_ErgebnisseAusgeben);
		
		//Vierter Menüpunkt: Ranking
		Menu mn_Ranking = new Menu("Ranking");
		//Auswahlpunkt für das Erstellen des Rankings
		MenuItem submenu_RankingErstellen = new MenuItem("Ranking erstellen");
		//Auswahlpunkt für das Ausgeben des Rankings
		MenuItem mn_RankingAusgeben = new MenuItem("Ranking ausgeben");
		//Auswahlpunkte dem Menü hinzufügen
		mn_Ranking.getItems().addAll(submenu_RankingErstellen, mn_RankingAusgeben);
		
		//Fünfter Menüpunkt: Teamwertung
		Menu mn_Teamwertung = new Menu("Teamwertung");
		//Auswahlpunkt zur Ausgabe der Teamwertung
		MenuItem mn_TeamwertungAusgeben = new MenuItem("Teamwertung ausgeben");
		//Auswahlpunkt dem Menü hinzufügen
		mn_Teamwertung.getItems().add(mn_TeamwertungAusgeben);

		
		//Sechster Menüpunkt: Etappenplan
		Menu mn_Etappenplan = new Menu("Etappenplan");
		//Auswahlpunkt zur Ausgabe des Etappenplans
		MenuItem submenu_EtappenplanAusgeben = new MenuItem("Etappenplan ausgeben");
		//Auswahlpunkt dem Menü hinzufügen 
		mn_Etappenplan.getItems().add(submenu_EtappenplanAusgeben);
		
		//Alle Fünf Hauptmenüs der Menübar hinzufügen
		menuBar.getMenus().addAll(mn_Tables, mn_Import, mn_Ergebnisse, mn_Ranking, mn_Teamwertung, mn_Etappenplan);
		
		
		//Die Menübar der VBox hinzufügen
		vbox.getChildren().add(menuBar);
		
		
		root.getChildren().add(vbox);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Datenbankfunktionen");
		primaryStage.show();
		
		
		//Funktion zum Reiter "Tabellen Anlegen"
		mn_Tables_TabellenAnlegen.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				DatabaseFunctions.createNewTables();
			}
		});
		
		//Funktion zum Reiter "User und Tipps importieren"
		submenu_import_UserUndTipps.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				if(vbox.getChildren().size() >=2)
					vbox.getChildren().remove(1);
				vbox.getChildren().add(VBOX_Import_UserTipps.vbox_Import_UserTipps());
				
			}
		});
		
		submenu_import_FahrerTeamsEtappen.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				submenu_import_UserUndTipps.setDisable(false);
				if(vbox.getChildren().size() >=2)
					vbox.getChildren().remove(1);
				vbox.getChildren().add(VBOX_Import_EtappenFahrerTeams.vbox_Import_EtappenFahrerTeams());
			}
		});
		
		submenu_ErgebnisseEingeben.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				if(vbox.getChildren().size() >=2)
					vbox.getChildren().remove(1);
				vbox.getChildren().add(VBOX_Ergebnisse_ErgebnisseEingeben.vbox_Ergebnisse_ErgebnisseEingeben());
			}
		});
		
		submenu_ErgebnisseAusgeben.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				if(vbox.getChildren().size() >=2)
					vbox.getChildren().remove(1);
				vbox.getChildren().add(VBOX_Ergebnisse_ErgebnisseAusgeben.vbox_ergebnisseAusgeben());
			}
		});
		
		submenu_RankingErstellen.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				if(vbox.getChildren().size() >=2)
					vbox.getChildren().remove(1);
				vbox.getChildren().add(VBOX_Ranking_RankingErstellen.vbox_Ranking_RankingErstellen());
			}
		});
		
		mn_Etappenplan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StageWindow.createFunctionsWindow();
			}
		});
		
		
	}
	
	
}
