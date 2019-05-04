//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           EndPane.java
// Course:          Computer Science 400, Spring 2019
//
// Author:          ateam56
// Lecturer's Name: Debra Deppler
// Due:             05/03/2019 by 12am
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         None
// Online Sources:  None
//

package application;

import application.AppScreen;
import application.QScene;
import application.QuizApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * This class represents the end window of the quiz. After all questions are answered the user
 * is taken to this screen to save and exit, exit without saving, or restart the quiz. Also
 * displays the final score for the user.
 */
public class EndPane extends BorderPane implements QScene {

    private QuizApplication application;

    /**
     * constructor for the end pane that initializes the QuizApplication and calls setupLayout()
     *
     * @param application to be used for this screen
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

    /**
     * displays the final score label when this end pane is shown during the application. Also
     * sets the style for the score label
     */
    @Override
    public void onShown() {
        int questionsCorrect = application.getQuestionsCorrect();
        int totalQuestions = application.getTotalQuestions();

        Label titleLabel = new Label(String.format("Final score: %d/%d (%.1f%%)", questionsCorrect,
                totalQuestions, (double) questionsCorrect / totalQuestions * 100));
        titleLabel.setPadding(new Insets(12));
        titleLabel.setStyle("-fx-font: 30 arial;");
        this.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

    }
}
