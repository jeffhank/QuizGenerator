package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setStyle("-fx-background-color: #FFFFFF;");
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Label titleLabel = new Label("Quiz Generator");
			titleLabel.setStyle("-fx-font: 30 arial;");
			root.setTop(titleLabel);
			root.setAlignment(titleLabel, Pos.CENTER);
			
			GridPane topicGrid = new GridPane();
			Label topicTitle = new Label("Select Topics");
			topicTitle.setStyle("-fx-font: 18 arial;");
			topicGrid.add(topicTitle, 0, 0);
			for(int i=1; i<4; i++) {
			  CheckBox tBox = new CheckBox("Topic " + i);
			  topicGrid.add(tBox,0,i);
			}
			topicGrid.setVgap(10.0);
	    root.setLeft(topicGrid);
			topicGrid.setAlignment(Pos.CENTER_LEFT);
			
			Button addQuestionsButton = new Button("Add Questions");
			addQuestionsButton.setId("aqb");
			root.setCenter(addQuestionsButton);
			
			GridPane numQuestionsPane = new GridPane();
			Label numQuestionsLabel = new Label("How many questions?");
	     ObservableList<String> options = 
	          FXCollections.observableArrayList(
	              "1",
	              "2",
	              "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9"
	          );
			ComboBox numQuestions = new ComboBox(options);
			numQuestionsPane.add(numQuestionsLabel, 0, 0);
			numQuestionsPane.add(numQuestions, 0, 1);
			numQuestionsPane.setVgap(10.0);
			root.setRight(numQuestionsPane);
			numQuestionsPane.setAlignment(Pos.CENTER_RIGHT);
			
			Button generateButton = new Button("Generate Quiz");
			root.setBottom(generateButton);
			root.setAlignment(generateButton, Pos.BOTTOM_CENTER);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
