package application;

import java.util.Optional;

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

public class NewQuestionPane extends BorderPane implements QScene {

	private QuizApplication application;

	public NewQuestionPane(QuizApplication application) {
		this.application = application;
		setupLayout();
	}

	private void setupLayout() {
		Label titleLabel = new Label("Add A New Question");
		titleLabel.setPadding(new Insets(12));
		titleLabel.setStyle("-fx-font: 30 arial;");
		this.setTop(titleLabel);
		BorderPane.setAlignment(titleLabel, Pos.CENTER);

		GridPane grid = new GridPane();
		TextField question = new TextField();

		grid.add(new Label("Enter question text: "), 0, 0);
		grid.add(question, 1, 0);

		this.setCenter(grid);
		grid.setAlignment(Pos.CENTER);

	}

	@Override
	public void onShown() {
		// TODO Auto-generated method stub

	}
}