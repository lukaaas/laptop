package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBOX_Import_EtappenFahrerTeams 
{
	
	public static VBox vbox_Import_EtappenFahrerTeams()
	{
		VBox vbox_Import_EtappenFahrerTeams = new VBox();
		VBox vbox_Import_EtappenFahrerTeams_TestData = new VBox();
		
		vbox_Import_EtappenFahrerTeams.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox_Import_EtappenFahrerTeams.getStyleClass().add("vbox");
		
		
		ToggleGroup tg_DataImport = new ToggleGroup();
		RadioButton rb_TestDataImport = new RadioButton("Daten importieren");
		
		
		rb_TestDataImport.setToggleGroup(tg_DataImport);
	
		vbox_Import_EtappenFahrerTeams.getChildren().addAll(rb_TestDataImport);
		
		
	
		// JavaFX FÜR DIE EINGABE VON TESTDATEN
		VBox vbox_chooseEtappenCSV = new VBox();
		Label lb_chooseEtappenCSV = new Label("Wählen Sie die CSV-Datei für den Import der Etappen:");
		HBox hbox_directoryEtappen = new HBox();
		Button bt_chooseDirectoryEtappen = new Button("Durchsuchen...");
		TextField tf_directoryEtappen = new TextField();
		hbox_directoryEtappen.getChildren().addAll(bt_chooseDirectoryEtappen, tf_directoryEtappen);
		vbox_chooseEtappenCSV.getChildren().addAll(lb_chooseEtappenCSV, hbox_directoryEtappen);
		
		VBox vbox_chooseFahrerCSV = new VBox();
		Label lb_chooseFahrerCSV = new Label("Wählen Sie die CSV-Datei für den Import der Fahrer:");
		HBox hbox_directoryFahrer = new HBox();
		Button bt_chooseDirectoryFahrer = new Button("Durchsuchen...");
		TextField tf_directoryFahrer = new TextField();
		hbox_directoryFahrer.getChildren().addAll(bt_chooseDirectoryFahrer, tf_directoryFahrer);
		vbox_chooseFahrerCSV.getChildren().addAll(lb_chooseFahrerCSV, hbox_directoryFahrer);
		
		VBox vbox_chooseTeamsCSV = new VBox();
		Label lb_chooseTeamsCSV = new Label("Wählen Sie die CSV-Datei für den Import der Teams:");
		HBox hbox_directoryTeams = new HBox();
		Button bt_chooseDirectoryTeams = new Button("Durchsuchen...");
		TextField tf_directoryTeams = new TextField();
		hbox_directoryTeams.getChildren().addAll(bt_chooseDirectoryTeams, tf_directoryTeams);
		vbox_chooseTeamsCSV.getChildren().addAll(lb_chooseTeamsCSV, hbox_directoryTeams);
		vbox_Import_EtappenFahrerTeams_TestData.getChildren().addAll(vbox_chooseEtappenCSV, vbox_chooseFahrerCSV, vbox_chooseTeamsCSV);
		
		vbox_Import_EtappenFahrerTeams.getChildren().add(vbox_Import_EtappenFahrerTeams_TestData);
		vbox_Import_EtappenFahrerTeams_TestData.setVisible(false);
		
		//Button um den Import zu starten
		Button bt_executeImport = new Button("Daten importieren");
		vbox_Import_EtappenFahrerTeams.getChildren().add(bt_executeImport);
		
		hbox_directoryEtappen.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		hbox_directoryEtappen.getStyleClass().add("hbox");
		hbox_directoryFahrer.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		hbox_directoryFahrer.getStyleClass().add("hbox");
		hbox_directoryTeams.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		hbox_directoryTeams.getStyleClass().add("hbox");
		
		
		rb_TestDataImport.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				vbox_Import_EtappenFahrerTeams_TestData.setVisible(true);
			}
		});
		
		bt_chooseDirectoryEtappen.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryEtappen.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_chooseDirectoryFahrer.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryFahrer.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_chooseDirectoryTeams.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				tf_directoryTeams.setText(DatabaseFunctions.openFileChooser());
			}
		});
		
		bt_executeImport.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				if(rb_TestDataImport.isSelected() == true)
				{
					try
					{
						if(tf_directoryEtappen.getText().isEmpty() == false)
							DatabaseFunctions.importFromCSV(tf_directoryEtappen.getText(), "etappen");
							
						if(tf_directoryFahrer.getText().isEmpty() == false)
							DatabaseFunctions.importFromCSV(tf_directoryFahrer.getText(), "fahrer");
						
						if(tf_directoryTeams.getText().isEmpty() == false)
							DatabaseFunctions.importFromCSV(tf_directoryTeams.getText(), "teams");
						
						
						Alert al_ImportSuccessful = new Alert(Alert.AlertType.INFORMATION, "Import erfolgreich.", ButtonType.OK);
						al_ImportSuccessful.setTitle("Hinweis");
						al_ImportSuccessful.show();
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		return vbox_Import_EtappenFahrerTeams;
		
	}
}
