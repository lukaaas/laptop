package application;

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBOX_Import_UserTipps 
{
	public static VBox vbox_Import_UserTipps()
	{
		VBox vbox_BP_Import_UserTipps = new VBox();
		vbox_BP_Import_UserTipps.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox_BP_Import_UserTipps.getStyleClass().add("vbox");
		
		Label lb_header = new Label("Wählen Sie die .CSV-Dateien aus, aus denen User und Tipps importiert werden sollen:");
	
		VBox vbox_chooseUserCSV = new VBox();
		Label lb_chooseUserCSV = new Label("Wählen Sie die CSV-Datei für den Import der User:");
		HBox hbox_directoryUser = new HBox();
		Button bt_chooseDirectoryUser = new Button("Durchsuchen...");
		TextField tf_directoryUser = new TextField();
		hbox_directoryUser.getChildren().addAll(bt_chooseDirectoryUser, tf_directoryUser);
		vbox_chooseUserCSV.getChildren().addAll(lb_chooseUserCSV, hbox_directoryUser);
		
		VBox vbox_chooseTippsCSV = new VBox();
		Label lb_chooseTippsCSV = new Label("Wählen Sie die CSV-Datei für den Import der Tipps:");
		HBox hbox_directoryTipps = new HBox();
		Button bt_chooseDirectoryTipps = new Button("Durchsuchen...");
		TextField tf_directoryTipps = new TextField();
		hbox_directoryTipps.getChildren().addAll(bt_chooseDirectoryTipps, tf_directoryTipps);
		vbox_chooseTippsCSV.getChildren().addAll(lb_chooseTippsCSV, hbox_directoryTipps);
		
		Button bt_executeImport = new Button("Daten importieren");
		
		vbox_BP_Import_UserTipps.getChildren().addAll(lb_header, vbox_chooseUserCSV, vbox_chooseTippsCSV, bt_executeImport);

		vbox_BP_Import_UserTipps.setMinSize(800, 400);
		
		
		bt_chooseDirectoryUser.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryUser.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_chooseDirectoryTipps.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryTipps.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_executeImport.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				try
				{
					DatabaseFunctions.importFromCSV(tf_directoryUser.getText(), "user");
					DatabaseFunctions.importFromCSV(tf_directoryTipps.getText(), "tipps");
					
					Alert al_importSuccessful = new Alert(AlertType.INFORMATION, "Import erfolgreich.", ButtonType.OK);
					al_importSuccessful.setTitle("Hinweis");
					al_importSuccessful.showAndWait();
				} 
				catch (Exception e2)
				{
					Alert al_importFailed = new Alert(Alert.AlertType.ERROR, "Import fehlgeschlagen.", ButtonType.OK);
					al_importFailed.setTitle("Fehler");
					al_importFailed.showAndWait();
				}	
			}
		});
		
		
		return vbox_BP_Import_UserTipps;
		
	}
}
