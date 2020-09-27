package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBOX_Ranking_RankingErstellen 
{
	public static VBox vbox_Ranking_RankingErstellen()
	{
		VBox vbox = new VBox();
		vbox.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox.getStyleClass().add("vbox");
		
		Label lb_header = new Label("Wählen Sie eine Etappe, um das neue Ranking zu berechnen");
		Button bt_createRanking = new Button("Ranking erstellen");
		
		
		Label lb_chooseEtappennummer = new Label("Wählen Sie die Etappe:");
		ComboBox<Integer> cb_Etappennummern = new ComboBox<>();
		for(int i=1;i<22;i++)
		{
			cb_Etappennummern.getItems().add(i);
		}
		
		HBox hbox_chooseEtappennummer = new HBox(lb_chooseEtappennummer, cb_Etappennummern);
		vbox.getChildren().addAll(lb_header, hbox_chooseEtappennummer, bt_createRanking);
		
		
		
		bt_createRanking.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				DatabaseFunctions.createRanking(cb_Etappennummern.getValue());
			}
		});
		
		return vbox;
	}
	
}
