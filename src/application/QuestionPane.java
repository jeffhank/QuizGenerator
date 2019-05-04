//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           QuestionPane.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class represents the question screen that appears for each question in the quiz. I will
 * contain the question text, question number, an image, and answer choices for the question.
 * Upon answering, the user will be shown whether they were correct or not and then will be
 * allowed to click a next button to move on to the next question.
 */
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
  private List<Question> questions;

  /**
   * initlializes all private members
   *
   * @param application object to be instantiated by this pane
   */
  public QuestionPane(QuizApplication application) {
    this.application = application;
    this.currentQuestionIndex = 0;
    this.imageGrid = new GridPane();
    this.nextButton = new Button();
    this.correctAnswers = 0;
    this.questions = new ArrayList<>();
    setupComponents();
    setupEventHandlers();
  }

  /**
   * populates the HashMap data structure with question based on the topics selected by the user
   */
  public void setupQuestionDb() {
    questions.clear();
    HashMap<String, List<Question>> questionDb = application.getQuestionDb();
    List<String> topicsWanted = application.getSelectedTopics();
    assert(topicsWanted.size() == questionDb.keySet().size());
    System.out.println(topicsWanted);

    int questionsWanted = application.getQuestionsWanted();
    System.out.println(questionsWanted);
    Random rand = new Random();
    System.out.println(questionDb);

    // Calculate total number of questions so we don't overshoot the number of questions wanted
    int total = 0;
    for (String topic : questionDb.keySet()) {
      if(topicsWanted.contains(topic))
        total += questionDb.get(topic).size();
    }
    
    System.out.println(total);

    if (questionsWanted > total) {
      questionsWanted = total;
    }

    for (int i = 0; i < questionsWanted; i++) { //randomly adds each question to the data structure
      for(String topic: topicsWanted) {
        for(Question q: questionDb.get(topic)) {
          if(questions.size() <= questionsWanted)
            questions.add(q);
        }
      }
    }
    questions.remove(questions.size() - 1);
    ArrayList<Question> noDuplicates = new ArrayList<Question>();
    for(Question question: questions) {
      if(!noDuplicates.contains(question))
        noDuplicates.add(question);
    }
    if(noDuplicates.size() > questionsWanted) {
      for(int i=0; i<=noDuplicates.size() - questionsWanted; i++) { 
        noDuplicates.remove(i);
      }
    }
    questions = noDuplicates;
  }

  /**
   * sets up the layout of the QuestionPane
   */
  public void setupComponents() {
    this.setStyle("-fx-background-color: #FFFFFF;");
    titleLabel = new Label();
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

    //when next button is clicked, the next question pane is displayed 
    nextButton.setOnAction(v -> updateQuestionView(++currentQuestionIndex));
    nextButton.setDisable(true);

    this.setRight(nextButton);
    BorderPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
  }

  /**
   * updates the screen to display the next question pane in the quiz
   *
   * @param questionIndex question number to be represented by this question pane
   */
  private void updateQuestionView(int questionIndex) {
    nextButton.setDisable(true);
    questionGrid.getChildren().removeIf(child -> child instanceof ImageView);
    System.out.println(questionIndex);


    System.out.println(questions);

    if (questionIndex > questions.size() - 1) {
      System.out.println(correctAnswers);
      application.setCorrectAnswers(correctAnswers);
      application.setTotalQuestions(questions.size());
      currentQuestionIndex = 0;
      correctAnswers = 0;
      application.switchScreen(AppScreen.END_SCREEN); //ends quiz if user answered all questions

      return;
    }
    questionGrid.getChildren().removeIf(childComponent -> childComponent instanceof RadioButton);
    Question newQuestion = questions.get(questionIndex);
    this.titleLabel.setText(String.format("Question %d out of %d", questionIndex + 1,
            questions.size()));
    imageGrid.setPadding(new Insets(12));
    this.setLeft(imageGrid);
    this.setAlignment(imageGrid, Pos.TOP_CENTER); //positions the image
    System.out.println(newQuestion.getImage());
    if (newQuestion.getImage().equals("none")) {
      imageGrid.getChildren().clear();
    } else {
      imageGrid.getChildren().clear();
      Image image = new Image("resources/" + newQuestion.getImage(), 200, 200, false, false);
      myImageView = new ImageView();
      myImageView.setImage(image);
      myImageView.setPreserveRatio(true);
      imageGrid.add(myImageView, 0, 0);
    }
    this.questionLabel.setText(newQuestion.getQuestionText()); //adds question text at the top 

    List<Answer> answers = newQuestion.getChoiceArray(); // list of answer choices for this question
    for (int i = 0; i < answers.size(); i++) {
      Answer answer = answers.get(i);
      RadioButton answerButton = new RadioButton(answer.getChoice());
      answerButton.setToggleGroup(answerGroup);
      int row = i + 1;
      questionGrid.add(answerButton, 0, i + 1);

      answerButton.setOnAction(a -> {
        questionGrid.getChildren().removeIf(child -> child instanceof ImageView);
        if (answer.getIsCorrect()) { //displays checkmark indicating answer was correct
          correctAnswers++;
          Image correct = new Image("resources/correct.jpg", 30, 30, false, false);
          ImageView incorrectOrCorrect = new ImageView();
          incorrectOrCorrect.setImage(correct);
          incorrectOrCorrect.setPreserveRatio(true);
          questionGrid.add(incorrectOrCorrect, 1, row);
        } else { //if answer is wrong, displays red X for the user
          System.out.println("false");
          Image incorrect = new Image("resources/incorrect.png", 20, 20, false, false);
          ImageView incorrectOrCorrect = new ImageView();
          incorrectOrCorrect.setImage(incorrect);
          incorrectOrCorrect.setPreserveRatio(true);
          questionGrid.add(incorrectOrCorrect, 1, row);
        }

        //disables answer choices when user has chosen an answer, allows user to click next button
        answerGroup.getToggles().forEach(toggle -> {
          Node node = (Node) toggle;
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
    setupQuestionDb();
    updateQuestionView(currentQuestionIndex);
  }
}