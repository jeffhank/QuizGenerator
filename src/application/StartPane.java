package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class StartPane extends BorderPane {

  private QuizApplication application;

  public StartPane(QuizApplication application) {
    this.application = application;
    setupLayout();
  }

  private void setupLayout() {
    Label titleLabel = new Label("Quiz Generator");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    GridPane topicGrid = new GridPane();
    topicGrid.setPadding(new Insets(12, 12, 12, 12));
    Label topicTitle = new Label("Select Topics");
    topicTitle.setStyle("-fx-font: 18 arial;");
    topicGrid.add(topicTitle, 0, 0);
    for (int i = 1; i < 4; i++) {
      CheckBox tBox = new CheckBox("Topic " + i);
      topicGrid.add(tBox, 0, i);
    }
    topicGrid.setVgap(10.0);
    this.setLeft(topicGrid);
    topicGrid.setAlignment(Pos.CENTER_LEFT);

    GridPane buttonGrid = new GridPane();
    Button addQuestionsButton = new Button("Add Questions");
    Button generateButton = new Button("Generate Quiz");

    generateButton.setOnAction(a -> application.switchScreen(AppScreen.END_SCREEN));
    generateButton.setId("gb");
    buttonGrid.add(addQuestionsButton, 0, 0);
    buttonGrid.add(generateButton, 0, 1);
    buttonGrid.setVgap(10.0);
    this.setCenter(buttonGrid);
    buttonGrid.setAlignment(Pos.CENTER);

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
    ComboBox<String> numQuestions = new ComboBox<>(options);
    numQuestionsPane.add(numQuestionsLabel, 0, 0);
    numQuestionsPane.add(numQuestions, 0, 1);
    numQuestionsPane.setVgap(10.0);
    numQuestionsPane.setPadding(new Insets(12));
    this.setRight(numQuestionsPane);
    numQuestionsPane.setAlignment(Pos.CENTER_RIGHT);

    this.setStyle("-fx-background-color: #FFFFFF;");
  }
}
