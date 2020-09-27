package application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StageWindow {

	public static void createFunctionsWindow() {
		Stage primaryStage = new Stage();
		BorderPane root = new BorderPane();
		primaryStage.setTitle("Funktionen");
		Scene scene = new Scene(root, 1000, 500);
	
		

		VBox vbox = new VBox();
		vbox.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		vbox.getStyleClass().add("etappenplan");
		

		Text text = new Text(DatabaseFunctions.getStageTableString());
		text.setFont(Font.font("monospace"));
		vbox.getChildren().add(text);

		root.getChildren().add(vbox);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Etappenplan");
		primaryStage.show();

	}
}
