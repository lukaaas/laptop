package application;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBOX_Ergebnisse_ErgebnisseAusgeben 
{
	public static VBox vbox_ergebnisseAusgeben()
	{
		VBox vbox = new VBox();
		vbox.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox.getStyleClass().add("vbox");
		
		//Textbox zur Ausgabe der Ergebnisse
		TextArea ta_results = new TextArea();
		ta_results.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		ta_results.getStyleClass().add("textarea");
		ta_results.setEditable(false);
		ta_results.setMinSize(300, 300);
		
		//Button für die Ergebnisanzeige
		Button bt_ergebnisAnzeigen = new Button("Ergebnis anzeigen");
		
		//Combobox zur Auswahl der Etappe
		
		Label lb_chooseEtappennummer = new Label("Wählen Sie die Etappe:");
		ComboBox<Integer> cb_Etappennummern = new ComboBox<>();
		
		try 
		{
			java.sql.Statement stm = Main.databaseConnection.createStatement();
			TreeSet<Integer> liste = new TreeSet<Integer>();
			for(int i=1;i<22;i++)
			{
				ResultSet rs_Etappennummern = stm.executeQuery("SELECT fahrerplatz1 FROM etappen WHERE etappennummer="+i+";");
				rs_Etappennummern.next();
				rs_Etappennummern.getString(1);
				if(rs_Etappennummern.wasNull() == true)
				{
					break;
				}
				else
				{
					cb_Etappennummern.getItems().add(i);
				}
			}
			
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		HBox hbox_chooseEtappennummer = new HBox(lb_chooseEtappennummer, cb_Etappennummern);
		hbox_chooseEtappennummer.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		hbox_chooseEtappennummer.getStyleClass().add("hbox");
		vbox.getChildren().addAll(hbox_chooseEtappennummer, ta_results, bt_ergebnisAnzeigen);
	
		bt_ergebnisAnzeigen.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				
				try 
				{
					LinkedList<String> liste = DatabaseFunctions.printEtappenResults(cb_Etappennummern.getValue());
						ta_results.insertText(0, "Siegerteam:\t\t"+liste.get(4)+"\n");
						ta_results.insertText(0, "Siegerzeit:\t\t"+liste.get(3)+"\n");
						ta_results.insertText(0, "Etappensieger:\t\t"+liste.get(2)+"\n");
						ta_results.insertText(0, "Datum:\t\t\t"+liste.get(1)+"\n");
						ta_results.insertText(0, "Etappennummer:\t"+liste.get(0)+"\n");
				} 
				catch (NullPointerException e1)
				{
				Alert al_keineEtappe = new Alert(AlertType.WARNING, "Wählen Sie eine Etappe", ButtonType.OK);
				al_keineEtappe.setTitle("Warnung");
				al_keineEtappe.showAndWait();
				}
			
			}
		});
		
	
		return vbox;
		
		
	}
}
