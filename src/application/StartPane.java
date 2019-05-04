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
  private List<CheckBox> topicBoxes; // Different topics of potential choices
  private HashMap<String, List<Question>> parsedDb; // List of questions
  private GridPane topicGrid = new GridPane();
  private GridPane numQuestionsPane;
  private Label totalQuestionsLabel = new Label();
  private int totalQuestionsLabelNumber = 0;

  public StartPane(QuizApplication application) {
    this.application = application;
    this.topicBoxes = new ArrayList<>();
    setupLayout();
  }

  private void setupLayout() {
    Label titleLabel = new Label("Quiz Generator");
    // CSS styling for the label
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    numQuestionsPane = new GridPane();
    GridPane buttonGrid = new GridPane();

    // The topic check boxes
    topicGrid.setVisible(false);
    topicGrid.setPadding(new Insets(12, 12, 12, 12));
    Label topicTitle = new Label("Select Topics");
    topicTitle.setStyle("-fx-font: 18 arial;");
    topicGrid.add(topicTitle, 0, 0);
    topicGrid.setVgap(10.0);
    this.setLeft(topicGrid);
    topicGrid.setAlignment(Pos.CENTER_LEFT);

    // Lets the user know whether or not a file is selected
    Label fileSelectedLabel = new Label("No file selected");
    fileSelectedLabel.setPadding(new Insets(12));
    fileSelectedLabel.setStyle("-fx-font: 16 arial;");

    Button addQuestionsButton = new Button("Add Questions");
    Button addNewQuestionButton = new Button("Add A New Question");

    Button generateButton = new Button("Generate Quiz");

    addQuestionsButton.setOnAction(event -> {
      // The action event to get the JSON file to the start pane
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select Questions");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Questions JSON " + "file", "*.json"));
      List<File> chosenFile = fileChooser.showOpenMultipleDialog(application.getPrimaryStage());
      // If the file actually has something and can be passed through as a JSON file
      if (chosenFile != null) {
        fileSelectedLabel
            .setText(String.format("%d file%s selected", chosenFile.size(), chosenFile.size() != 1 ? "s" : ""));
        try {
          JsonLoader loader = chosenFile.size() > 1 ? new JsonLoader(chosenFile)
              : new JsonLoader(chosenFile.get(0).getPath());
          parsedDb = loader.getQuestionDb();
          application.setQuestionDb(parsedDb);
          // Now that we have the JSON file we can update the topics
          updateTopicList();
          topicGrid.setVisible(true);
          numQuestionsPane.setVisible(true);
          generateButton.setVisible(true);
          addNewQuestionButton.setVisible(true);
          totalQuestionsLabelNumber = application.getQuestionDb().size();
          totalQuestionsLabel.setText("Total questions: " + application.getQuestionDb().size());
        } catch (IOException ex) {
          // If no file could be found or there was a problem reading the file
          Alert ioAlert = new Alert(Alert.AlertType.ERROR);
          ioAlert.setTitle("Error Reading Question DB file");
          ioAlert.setContentText("Could not read question DB.");
          ex.printStackTrace();
        } catch (ParseException ex) {
          // If the file could not be parced
          Alert ioAlert = new Alert(Alert.AlertType.ERROR);
          ioAlert.setTitle("Error Reading Question DB json");
          ioAlert.setContentText("Error parsing the question database json file.");
          ex.printStackTrace();
        }
      }

    });

    generateButton.setVisible(false);
    addNewQuestionButton.setVisible(false);
    Label numQuestionsLabel = new Label("How many questions?");
    TextField numQuestions = new TextField();

    generateButton.setOnAction(event -> {
      // Set the amount of quiz questions
      try {
        // Starting the quiz
        int questionsWanted = Integer.parseInt(numQuestions.getText()); // How many questions are on the quiz
        application.setSelectedTopics(setTopics()); // The topics selected
        if(questionsWanted > application.getQuestionDb().size() || questionsWanted < 1) {
          Alert invalidQNumber = new Alert(Alert.AlertType.ERROR);
          invalidQNumber.setTitle("Invalid input");
          invalidQNumber.setContentText(
              "Please enter a valid amount of questions.");
          invalidQNumber.show();
        }
        else {
          application.setQuestionsWanted(questionsWanted); // Passes how many questions are wanted through our method
          application.switchScreen(AppScreen.QUESTION_SCREEN);
        }
      } catch (NumberFormatException ex) { // If the user inputs the data in incorrectly
        Alert invalidQNumber = new Alert(Alert.AlertType.ERROR);
        invalidQNumber.setTitle("Invalid input");
        invalidQNumber.setContentText(
            "Error parsing amount of questions. The amount of " + "questions must be an integer greater than 0");
        invalidQNumber.show();
      }
      // Get the topics chosen by the user
    });

    generateButton.setId("gb");
    addNewQuestionButton.setOnAction(event -> {
      // Switching screens to a different pane
      application.switchScreen(AppScreen.NEWQUESTION_SCREEN);
    });
    // Adding all the buttons and different java fx elements to the screen
    buttonGrid.add(addQuestionsButton, 0, 0);
    buttonGrid.add(addNewQuestionButton, 0, 1);
    buttonGrid.add(fileSelectedLabel, 1, 0);
    buttonGrid.add(generateButton, 0, 2);
    buttonGrid.setVgap(10.0);
    this.setCenter(buttonGrid);
    buttonGrid.setAlignment(Pos.CENTER);

    numQuestionsPane.setVisible(false);
    numQuestionsPane.add(numQuestionsLabel, 0, 0);
    numQuestionsPane.add(numQuestions, 0, 1);
    numQuestionsPane.add(totalQuestionsLabel, 0, 2);
    numQuestionsPane.setVgap(10.0);
    numQuestionsPane.setPadding(new Insets(12));
    this.setRight(numQuestionsPane);
    numQuestionsPane.setAlignment(Pos.CENTER_RIGHT);

    this.setStyle("-fx-background-color: #FFFFFF;");
  }
  /**
   * Creating the list of topics
   * @return the list of topics
   */
  public List<String> setTopics() {
    List<String> ret = new ArrayList<>();
    for (CheckBox topicBox : topicBoxes) {
      if (topicBox.isSelected())
        ret.add(topicBox.getText());
    }
    return ret;
  }
  public Label getNumQuestionLabel() {
    return totalQuestionsLabel;
  }
  public int getNumQuestionsLabelInt() {
    return totalQuestionsLabelNumber;
  }
  /**
   * Updating the topic list when we get our JSON file loaded
   */
  public void updateTopicList() {
    topicBoxes.clear();
    topicGrid.getChildren().clear();
    String[] topicSet = parsedDb.keySet().toArray(new String[parsedDb.size()]);

    // Sort the topic list alphabetically
    Arrays.sort(topicSet);
    for (int i = 0; i < topicSet.length; i++) {
      CheckBox tBox = new CheckBox(topicSet[i]);
      tBox.setSelected(true);
      topicGrid.add(tBox, 0, i + 1);
      topicBoxes.add(tBox);
    }
  }

  @Override
  public void onShown() {
    //Do nothing
  }
}
