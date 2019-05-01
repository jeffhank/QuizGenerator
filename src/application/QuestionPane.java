package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionPane extends BorderPane {

  private QuizApplication application;

  private Label titleLabel;
  private Label questionLabel;
  private GridPane questionGrid;
  private ImageView myImageView;
  private ToggleGroup answerGroup;

  private int currentQuestionIndex;

  public QuestionPane(QuizApplication application) {
    this.application = application;
    this.currentQuestionIndex = 0;
    setupComponents();
    setupEventHandlers();
  }

  public void setupComponents() {
    titleLabel = new Label("Question 1 of 12");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 35 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    GridPane imageGrid = new GridPane();
    this.answerGroup = new ToggleGroup();

//    HashMap<String, List<Question>> database = application.
    Image image = new Image("/IMG_1793.PNG", 200, 200, false, false);
    myImageView = new ImageView();
    myImageView.setImage(image);
    myImageView.setPreserveRatio(true);
    imageGrid.add(myImageView, 0, 0);
    imageGrid.setPadding(new Insets(12));
    this.setLeft(imageGrid);
    BorderPane.setAlignment(imageGrid, Pos.TOP_CENTER);

    questionGrid = new GridPane();
    questionGrid.setPadding(new Insets(12));
    questionLabel = new Label("Is this project horrible?");
    questionLabel.setWrapText(true);
    questionLabel.setStyle("-fx-font: 24 arial;");
    questionGrid.add(questionLabel, 0, 0);
    questionGrid.setVgap(20.0);
    this.setCenter(questionGrid);
    questionGrid.setAlignment(Pos.TOP_LEFT);

    Button nextButton = new Button("Next");

    Button backButton = new Button("Back");
    nextButton.setOnAction(v -> updateQuestionView(++currentQuestionIndex));
    backButton.setOnAction(v -> updateQuestionView(--currentQuestionIndex));

    this.setRight(nextButton);
    this.setLeft(backButton);
    BorderPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
    BorderPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
  }

  private void updateQuestionView(int questionIndex) {
    List<Question> questions = application.getQuestionDb().values().stream()
            .flatMap(List::stream).collect(Collectors.toList());

    questionGrid.getChildren().removeIf(childComponent -> childComponent instanceof RadioButton);
    Question newQuestion = questions.get(questionIndex);
    this.titleLabel.setText(String.format("Question %d out of %d", questionIndex + 1,
            questions.size()));

    System.out.println(newQuestion.getImage());
    Image newImage = new Image(newQuestion.getImage(), 200, 200, false,
            false);
    this.myImageView.setImage(newImage);
    this.questionLabel.setText(newQuestion.getQuestionText());

    List<Answer> answers = newQuestion.getChoiceArray();
    for (int i = 0; i < answers.size(); i++) {
      Answer answer = answers.get(i);
      RadioButton answerButton = new RadioButton(answer.getChoice());
      answerButton.setToggleGroup(answerGroup);
      questionGrid.add(answerButton, 0, i + 1);
    }
  }

  private void setupEventHandlers() {

  }
}
