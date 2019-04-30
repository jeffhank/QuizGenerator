package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class QuestionPane extends BorderPane {

  private QuizApplication application;

  public QuestionPane(QuizApplication application) {
    this.application = application;
    setup();
  }

  public void setup() {
    Label titleLabel = new Label("Question 3 of 12");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 35 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);
    
    GridPane imageGrid = new GridPane();
    
    Image image = new Image("/IMG_1793.PNG", 200, 200, false, false);
    ImageView myImageView = new ImageView();
    myImageView.setImage(image);
    myImageView.setPreserveRatio(true);
    imageGrid.add(myImageView, 0, 0);
    imageGrid.setPadding(new Insets(12));
    this.setLeft(imageGrid);
    this.setAlignment(imageGrid, Pos.TOP_CENTER);
    
    GridPane questionAndAnswersGrid = new GridPane();
    questionAndAnswersGrid.setPadding(new Insets(12));
    Label question = new Label("Is this project horrible?");
    question.setWrapText(true);
    question.setStyle("-fx-font: 24 arial;");
    questionAndAnswersGrid.add(question, 0, 0);
    ToggleGroup questionGroup = new ToggleGroup();
    for (int i = 1; i < 6; i++) {
      RadioButton answerBox = new RadioButton("Answer " + i);
      answerBox.setToggleGroup(questionGroup);
      questionAndAnswersGrid.add(answerBox, 0, i);
    }
    questionAndAnswersGrid.setVgap(20.0);
    this.setCenter(questionAndAnswersGrid);
    questionAndAnswersGrid.setAlignment(Pos.TOP_LEFT);
    
    Button nextButton = new Button("Next");
    this.setRight(nextButton);
    this.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
  }
}
