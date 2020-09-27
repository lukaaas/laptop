package application;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBOX_Ergebnisse_ErgebnisseEingeben 
{
	public static VBox vbox_Ergebnisse_ErgebnisseEingeben()
	{
		VBox vbox = new VBox();
		
		vbox.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox.getStyleClass().add("vbox");
		Label lb_header = new Label("Wählen Sie die CSV-Dateien für Gesamt-, Berg- und Punktwertung aus:");
		
		//Sachen für Gesamtwertung
		VBox vbox_Gesamtwertung = new VBox();
		Label lb_directoryGesamtwertung = new Label("CSV-Datei für die Gesamtwertung");
		HBox hbox_Gesamtwertung = new HBox();
		Button bt_gesamtwertung = new Button("Durchsuchen");
		TextField tf_directoryGesamtwertung = new TextField();
		hbox_Gesamtwertung.getChildren().addAll(bt_gesamtwertung, tf_directoryGesamtwertung);
		vbox_Gesamtwertung.getChildren().addAll(lb_directoryGesamtwertung, hbox_Gesamtwertung);
		
		
		//Sachen für Bergwertung
		VBox vbox_Bergwertung = new VBox();
		Label lb_directoryBergwertung = new Label("CSV-Datei für die Bergwertung");
		HBox hbox_Bergwertung = new HBox();
		Button bt_Bergwertung = new Button("Durchsuchen");
		TextField tf_directoryBergwertung = new TextField();
		hbox_Bergwertung.getChildren().addAll(bt_Bergwertung, tf_directoryBergwertung);
		vbox_Bergwertung.getChildren().addAll(lb_directoryBergwertung, hbox_Bergwertung);
		
		//Sachen für Punktwertung
		VBox vbox_Punktwertung = new VBox();
		Label lb_directoryPunktwertung = new Label("CSV-Datei für die Punktwertung");
		HBox hbox_Punktwertung = new HBox();
		Button bt_Punktwertung = new Button("Durchsuchen");
		TextField tf_directoryPunktwertung = new TextField();
		hbox_Punktwertung.getChildren().addAll(bt_Punktwertung, tf_directoryPunktwertung);
		vbox_Punktwertung.getChildren().addAll(lb_directoryPunktwertung, hbox_Punktwertung);
		
		//Alles der vbox hinzufügen
		vbox.getChildren().addAll(lb_header,vbox_Gesamtwertung, vbox_Bergwertung, vbox_Punktwertung);
		
		//Label und Combobox, um die Etappennummer anzugeben
		Label lb_chooseEtappennummer = new Label("Wählen Sie die Nummer der Etappe, von der die Ergebnisse stammen:");
		ComboBox<Integer> cb_etappenNummern = new ComboBox<Integer>();
		for(int i=1; i<22;i++)
		{
			cb_etappenNummern.getItems().add(i);
		}
		vbox.getChildren().addAll(lb_chooseEtappennummer, cb_etappenNummern);
		
		//Button, um den Import zu starten 
		Button bt_executeImportErgebnisse = new Button("Ergebnisse importieren");
		Button bt_updateTables = new Button("Tabellen aktualisieren");
		vbox.getChildren().addAll(bt_executeImportErgebnisse, bt_updateTables);
		
		bt_gesamtwertung.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryGesamtwertung.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_Bergwertung.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryBergwertung.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_Punktwertung.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryPunktwertung.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_executeImportErgebnisse.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				try
				{
					DatabaseFunctions.importResultsFromCSV(tf_directoryGesamtwertung.getText(), "gesamtwertung");
					DatabaseFunctions.importResultsFromCSV(tf_directoryBergwertung.getText(), "bergwertung");
					DatabaseFunctions.importResultsFromCSV(tf_directoryPunktwertung.getText(), "punktwertung");
				
				}
				catch(Exception e2)
				{
					Alert al_FilesNotFound = new Alert(AlertType.WARNING, "Geben Sie die CSV-Dateien an und stellen Sie sicher, dass die Dateipfade korrekt sind.", ButtonType.OK);
					al_FilesNotFound.setTitle("Hinweis");
					al_FilesNotFound.showAndWait();
				}
				
				Alert al_importSuccessful = new Alert(AlertType.INFORMATION, "Import der Ergebnisse erfolgreich.", ButtonType.OK);
				al_importSuccessful.setTitle("Hinweis");
				al_importSuccessful.showAndWait();
				
			}
		});
		
		bt_updateTables.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				
				try 
				{
					DatabaseFunctions.updateTables(cb_etappenNummern.getValue());
				} 
				catch(NullPointerException e1)
				{
					Alert al_noNumberChosen = new Alert(AlertType.WARNING, "Wählen Sie eine Etappennummer.", ButtonType.OK);
					al_noNumberChosen.setTitle("Hinweis");
					al_noNumberChosen.showAndWait();
				}
				
				Alert al_updateSuccessful = new Alert(AlertType.INFORMATION, "Aktualisierung der Tabellen erfolgreich.", ButtonType.OK);
				al_updateSuccessful.setTitle("Hinweis");
				al_updateSuccessful.showAndWait();
				
			}
		});
		
		
		vbox.setMinSize(1000, 400);
		return vbox;
		
	}
}
