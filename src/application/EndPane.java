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

public class EndPane extends BorderPane {

	private QuizApplication application;

	public EndPane(QuizApplication application) {
		this.application = application;
		setupLayout();
	}

	private void setupLayout() {
		Label titleLabel = new Label("Final Score: ");
		titleLabel.setPadding(new Insets(12));
		titleLabel.setStyle("-fx-font: 30 arial;");
		this.setTop(titleLabel);
		BorderPane.setAlignment(titleLabel, Pos.CENTER);

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
		exitWithoutSaveButton.setOnAction(a -> {
			Optional<ButtonType> result = alert1.showAndWait();
			if (result.get() == ButtonType.OK) {
				System.exit(0);
			}
		});

		restartQuizButton.setOnAction(a -> application.switchScreen(AppScreen.START_SCREEN));
		saveAndExitButton.setOnAction(a -> application.switchScreen(AppScreen.SAVE_SCREEN));
		buttonGrid.add(restartQuizButton, 0, 0);
		buttonGrid.add(exitWithoutSaveButton, 1, 0);
		buttonGrid.add(saveAndExitButton, 2, 0);
		this.setCenter(buttonGrid);
		buttonGrid.setAlignment(Pos.CENTER);

	}
}
