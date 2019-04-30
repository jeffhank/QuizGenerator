package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionPane extends BorderPane {

	private QuizApplication application;

	public QuestionPane(QuizApplication application) {
		this.application = application;
		setup();
	}

	private void setup() {
		Text title = new Text("Question");
		this.setTop(title);
	}
}
