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

import java.util.Optional;

public class SavePane extends BorderPane {

  private QuizApplication application;

  public SavePane(QuizApplication application) {
    this.application = application;
    setupLayout();
  }

  private void setupLayout() {
    Label titleLabel = new Label("Save your quiz");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    GridPane grid = new GridPane();
    Button saveAndExit = new Button("Save and exit");
    TextField filename = new TextField();
    grid.add(new Label("Enter filename to save to: "), 0, 0);
    grid.add(filename, 1, 0);
    grid.add(saveAndExit, 0, 1);

    Alert alert2 = new Alert(AlertType.INFORMATION);
    alert2.setTitle("Saving and exiting");
    alert2.setContentText("You are saving to your given file and exiting. Thanks for taking the quiz. Goodbye!");
    saveAndExit.setOnAction(a -> {
      if (!filename.getText().equals("")) {
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
          System.exit(0);
        }
      }
    });

    this.setCenter(grid);
    grid.setAlignment(Pos.CENTER);

  }
}
