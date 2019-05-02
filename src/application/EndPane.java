package application;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
/**
 * 
 * This class represents the end window of the quiz. After all questions are answered the user
 * is taken to this screen to save and exit, exit without saving, or restart the quiz. Also 
 * displays the final score for the user. 
 *
 */
public class EndPane extends BorderPane implements QScene {

	private QuizApplication application;
	/**
	 * constructor for the end pane that initializes the QuizApplication and calls setupLayout()
	 * @param QuizApplication to be used for this screen
	 */
	public EndPane(QuizApplication application) {
		this.application = application;
		setupLayout();
	}
	
	/**
     * sets up the layout for the end window screen 
     */
	private void setupLayout() {

		GridPane buttonGrid = new GridPane();
		Button restartQuizButton = new Button("Restart quiz");
		Button exitWithoutSaveButton = new Button("Exit without saving");
		Button saveAndExitButton = new Button("Save and exit");

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Exiting without saving");
		alert.setContentText("You are exiting without saving. Thanks for taking the quiz. Goodbye!");

		Alert alert1 = new Alert(AlertType.INFORMATION);
		alert1.setTitle("Exiting without saving");
		alert1.setContentText("You are exiting without saving. Thanks for taking the quiz. Goodbye!");
		exitWithoutSaveButton.setOnAction(a -> { //shows alert pop up when user exits without save
			Optional<ButtonType> result = alert1.showAndWait();
			if (result.get() == ButtonType.OK) {
				System.exit(0);
			}
		});
		
		//switches screen to start pane when the restart quiz button is pressed
		restartQuizButton.setOnAction(a -> application.switchScreen(AppScreen.START_SCREEN));
		//switches screen to save screen when the user tries to save
		saveAndExitButton.setOnAction(a -> application.switchScreen(AppScreen.SAVE_SCREEN));
		buttonGrid.add(restartQuizButton, 0, 0);
		buttonGrid.add(exitWithoutSaveButton, 1, 0);
		buttonGrid.add(saveAndExitButton, 2, 0);
		this.setCenter(buttonGrid);
		buttonGrid.setAlignment(Pos.CENTER);
	}

  @Override
  /**
   * displays the final score label when this end pane is shown during the application. Also
   * sets the style for the score label
   */
  public void onShown() {
    Label titleLabel = new Label(
        "Final Score: " + application.getQuestionsCorrect() + "/" + application.getTotalQuestions() + " ("
            +  ((double)application.getQuestionsCorrect() / application.getTotalQuestions()) * 100 + "%)");

    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 30 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);
    
  }
}
