package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionPane extends BorderPane implements QScene {

  private QuizApplication application;
  private GridPane imageGrid;
  private Label titleLabel;
  private Label questionLabel;
  private GridPane questionGrid;
  private ImageView myImageView;
  private ToggleGroup answerGroup;
  private Button nextButton;
  private int currentQuestionIndex;
  private int correctAnswers;

  public QuestionPane(QuizApplication application) {
    this.application = application;
    this.currentQuestionIndex = 0;
    this.imageGrid = new GridPane();
    this.nextButton = new Button();
    this.correctAnswers = 0;
    setupComponents();
    setupEventHandlers();
  }

  public void setupComponents() {
    this.setStyle("-fx-background-color: #FFFFFF;");
    titleLabel = new Label("Question 1 of 12");
    titleLabel.setPadding(new Insets(12));
    titleLabel.setStyle("-fx-font: 35 arial;");
    this.setTop(titleLabel);
    BorderPane.setAlignment(titleLabel, Pos.CENTER);

    this.answerGroup = new ToggleGroup();

//    HashMap<String, List<Question>> database = application.

    questionGrid = new GridPane();
    questionGrid.setPadding(new Insets(12));
    questionLabel = new Label();
    questionLabel.setWrapText(true);
    questionLabel.setStyle("-fx-font: 24 arial;");
    questionGrid.add(questionLabel, 0, 0);
    questionGrid.setVgap(30.0);
    this.setCenter(questionGrid);
    questionGrid.setAlignment(Pos.TOP_LEFT);
    nextButton = new Button("Next");

    nextButton.setOnAction(v -> updateQuestionView(++currentQuestionIndex));
    nextButton.setDisable(true);

    this.setRight(nextButton);
    BorderPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
  }

  private void updateQuestionView(int questionIndex) {
    nextButton.setDisable(true);
    questionGrid.getChildren().removeIf(child -> child instanceof ImageView);
    System.out.println(questionIndex);

    HashMap<String, List<Question>> questionDb = application.getQuestionDb();
    HashMap<String, List<Question>> filteredQuestionDb = new HashMap<>();
    List<String> topicsWanted = application.getSelectedTopics();

    for (String topic : topicsWanted) {
      filteredQuestionDb.put(topic, questionDb.get(topic));
    }

    List<Question> questions = filteredQuestionDb.values().stream()
            .flatMap(List::stream).collect(Collectors.toList());
    System.out.println(questions);

    if(questionIndex > questions.size() - 1) {
      System.out.println(correctAnswers);
      application.setCorrectAnswers(correctAnswers);
      application.setTotalQuestions(questions.size());
      currentQuestionIndex = 0;
      correctAnswers = 0;
      application.switchScreen(AppScreen.END_SCREEN);
      
      
      return;
    }
    questionGrid.getChildren().removeIf(childComponent -> childComponent instanceof RadioButton);
    Question newQuestion = questions.get(questionIndex);
    this.titleLabel.setText(String.format("Question %d out of %d", questionIndex + 1,
            questions.size()));
    imageGrid.setPadding(new Insets(12));
    this.setLeft(imageGrid);
    this.setAlignment(imageGrid, Pos.TOP_CENTER);
    System.out.println(newQuestion.getImage());
    if(newQuestion.getImage().equals("none")) {
      imageGrid.getChildren().clear();
    }
    else {
      imageGrid.getChildren().clear();
      Image image = new Image("resources/" + newQuestion.getImage(), 200, 200, false, false);
      myImageView = new ImageView();
      myImageView.setImage(image);
      myImageView.setPreserveRatio(true);
      imageGrid.add(myImageView, 0, 0);
    }
    this.questionLabel.setText(newQuestion.getQuestionText());

    List<Answer> answers = newQuestion.getChoiceArray();
    for (int i = 0; i < answers.size(); i++) {
      Answer answer = answers.get(i);
      RadioButton answerButton = new RadioButton(answer.getChoice());
      answerButton.setToggleGroup(answerGroup);
      int row = i + 1;
      questionGrid.add(answerButton, 0, i + 1);
      
      answerButton.setOnAction(a -> {
        questionGrid.getChildren().removeIf(child -> child instanceof ImageView);
        if(answer.getIsCorrect() == true) {
          correctAnswers++;
          Image correct = new Image("resources/correct.jpg", 30, 30, false, false);
          ImageView incorrectOrCorrect = new ImageView();
          incorrectOrCorrect.setImage(correct);
          incorrectOrCorrect.setPreserveRatio(true);
          questionGrid.add(incorrectOrCorrect, 1, row);
        }
        else {
          System.out.println("false");
          Image incorrect = new Image("resources/incorrect.png", 20, 20, false, false);
          ImageView incorrectOrCorrect = new ImageView();
          incorrectOrCorrect.setImage(incorrect);
          incorrectOrCorrect.setPreserveRatio(true);
          questionGrid.add(incorrectOrCorrect, 1, row);
        }
        
        answerGroup.getToggles().forEach(toggle -> {
          Node node = (Node) toggle ;
          node.setDisable(true);
          nextButton.setDisable(false);
      });
      });
    }
  }

  
  private void setupEventHandlers() {

  }

  @Override
  public void onShown() {
    updateQuestionView(currentQuestionIndex);
  }
}