package application;

import application.QuizApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class SavePane extends BorderPane implements QScene {

  private QuizApplication application;
  private File destFile;

  public SavePane(QuizApplication application) {
    this.application = application;
    setupLayout();
  }

  /**
   * Method to setup our screen
   */
  private void setupLayout() {
    // Label and styling to set up saving the quiz
    Label titleLabel = new Label("Save your quiz");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    GridPane grid = new GridPane();
    Button saveAndExit = new Button("Save and exit");
    Button browse = new Button("Browse...");
    TextField filename = new TextField();
    saveAndExit.setDisable(true);

    // Make the filename field read-only but also normal looking
    filename.setEditable(false);
    filename.setMouseTransparent(true);
    filename.setFocusTraversable(false);

    grid.add(new Label("Filename to save to: "), 0, 0);
    grid.add(browse, 2, 0);
    grid.add(filename, 1, 0);
    grid.add(saveAndExit, 1, 1);

    Alert alert2 = new Alert(AlertType.INFORMATION);
    alert2.setTitle("Saving and exiting");
    alert2.setContentText("You are saving to your given file and exiting. Thanks for taking the quiz. Goodbye!");
    // Action event to cause the save and exit
    saveAndExit.setOnAction(a -> {
      try {
        JsonSaver dbSaver = new JsonSaver(application.getQuestionDb());
        dbSaver.saveQuestionSetJson(destFile.getPath());

        Alert success = new Alert(AlertType.INFORMATION);
        success.setTitle("Success");
        success.setContentText("Successfully saved the JSON file");
        success.show();
      } catch (IOException ex) {
        Alert ioAlert = new Alert(AlertType.ERROR);
        ioAlert.setTitle("Error saving JSON file");
        ioAlert.setContentText("There was an error when writing the JSON file.");
        ioAlert.show();
      }
    });

    browse.setOnAction(a -> {
      FileChooser saveDialog = new FileChooser();
      saveDialog.setTitle("Save Question Database");
      saveDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*" +
              ".json"));

      File dFile = saveDialog.showSaveDialog(application.getPrimaryStage());
      if (dFile != null) {
        destFile = dFile;
        filename.setText(destFile.getPath());
        saveAndExit.setDisable(false);
      }
    });
    // Adding the elements to the screen
    this.setCenter(grid);
    grid.setAlignment(Pos.CENTER);

  }

  @Override
  public void onShown() { }
}
