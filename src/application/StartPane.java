package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StartPane extends BorderPane implements QScene {

  private QuizApplication application;
  private List<CheckBox> topicBoxes;

  public StartPane(QuizApplication application) {
    this.application = application;
    this.topicBoxes = new ArrayList<>();
    setupLayout();
  }

  private void setupLayout() {
    Label titleLabel = new Label("Quiz Generator");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    GridPane numQuestionsPane = new GridPane();
    GridPane topicGrid = new GridPane();
    GridPane buttonGrid = new GridPane();

    topicGrid.setVisible(false);
    topicGrid.setPadding(new Insets(12, 12, 12, 12));
    Label topicTitle = new Label("Select Topics");
    topicTitle.setStyle("-fx-font: 18 arial;");
    topicGrid.add(topicTitle, 0, 0);
    topicGrid.setVgap(10.0);
    this.setLeft(topicGrid);
    topicGrid.setAlignment(Pos.CENTER_LEFT);

    Label fileSelectedLabel = new Label("No file selected");
    fileSelectedLabel.setPadding(new Insets(12));
    fileSelectedLabel.setStyle("-fx-font: 16 arial;");

    Button addQuestionsButton = new Button("Add Questions");
    Button addNewQuestionButton = new Button("Add A New Question");

    Button generateButton = new Button("Generate Quiz");
    
    addQuestionsButton.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select Questions");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Questions JSON " + "file", "*.json"));
      File chosenFile = fileChooser.showOpenDialog(application.getPrimaryStage());
      if (chosenFile != null) {
        fileSelectedLabel.setText(chosenFile.getName());
        try {
          JsonLoader loader = new JsonLoader(chosenFile.getPath());
          HashMap<String, List<Question>> parsedDb = loader.getParsedDb();
          application.setQuestionDb(parsedDb);

          // Update the topic list
          String[] topicSet = parsedDb.keySet().toArray(new String[parsedDb.size()]);

          // Sort the topic list alphabetically
          Arrays.sort(topicSet);
          for (int i = 0; i < topicSet.length; i++) {
            CheckBox tBox = new CheckBox(topicSet[i]);
            tBox.setSelected(true);
            topicGrid.add(tBox, 0, i + 1);
            topicBoxes.add(tBox);
          }

          topicGrid.setVisible(true);
          numQuestionsPane.setVisible(true);
          generateButton.setVisible(true);
          addNewQuestionButton.setVisible(true);
        } catch (IOException ex) {
          Alert ioAlert = new Alert(Alert.AlertType.ERROR);
          ioAlert.setTitle("Error Reading Question DB file");
          ioAlert.setContentText("Could not read question DB.");
          ex.printStackTrace();
        } catch (ParseException ex) {
          Alert ioAlert = new Alert(Alert.AlertType.ERROR);
          ioAlert.setTitle("Error Reading Question DB json");
          ioAlert.setContentText("Error parsing the question database json file.");
          ex.printStackTrace();
        }
      }
    });

    generateButton.setVisible(false);
    addNewQuestionButton.setVisible(false);
    generateButton.setOnAction(event -> {
      // Get the topics chosen by the user
      application.setSelectedTopics(setTopics());
      application.switchScreen(AppScreen.QUESTION_SCREEN);
    });

    generateButton.setId("gb");
    addNewQuestionButton.setOnAction(event -> {
        application.switchScreen(AppScreen.NEWQUESTION_SCREEN);
      });
    buttonGrid.add(addQuestionsButton, 0, 0);
    buttonGrid.add(addNewQuestionButton, 0, 1);
    buttonGrid.add(fileSelectedLabel, 1, 0);
    buttonGrid.add(generateButton, 0, 2);
    buttonGrid.setVgap(10.0);
    this.setCenter(buttonGrid);
    buttonGrid.setAlignment(Pos.CENTER);

    numQuestionsPane.setVisible(false);
    Label numQuestionsLabel = new Label("How many questions?");
    TextField numQuestions = new TextField();

    numQuestionsPane.add(numQuestionsLabel, 0, 0);
    numQuestionsPane.add(numQuestions, 0, 1);
    numQuestionsPane.setVgap(10.0);
    numQuestionsPane.setPadding(new Insets(12));
    this.setRight(numQuestionsPane);
    numQuestionsPane.setAlignment(Pos.CENTER_RIGHT);

    this.setStyle("-fx-background-color: #FFFFFF;");
  }

  public List<String> setTopics() {
    List<String> ret = new ArrayList<>();
    for (CheckBox topicBox : topicBoxes) {
      if (topicBox.isSelected())
        ret.add(topicBox.getText());
    }
    return ret;
  }

  @Override
  public void onShown() {
    // Do nothing
  }
}
