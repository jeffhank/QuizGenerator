//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           QuizApplication.java
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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * This class is a master class for the quiz application, containing and managing
 * all of the 
 *
 */
public class QuizApplication extends Application {
  private int correctAnswers;
  private List<Pair<QScene, Scene>> screens;
  final int WINDOW_WIDTH = 800;
  final int WINDOW_HEIGHT = 600;
  private Stage primaryStage;
  private HashMap<String, List<Question>> questionDb;

  private List<String> selectedTopics;
  private int totalQuestions;
  private int questionsWanted;
  private Scene startScene;
  private Scene questionScene;

  /**
   * @param primaryStage is the java fx Stage that runs the program
   */
  @Override
  public void start(Stage primaryStage) {

    // Store this stage value, so we can use it to switch screens later on
    this.primaryStage = primaryStage;

    // Set up all our different scenes
    // First, setup our start scene
    screens = new ArrayList<>();

    StartPane startPane = new StartPane(this);
    startScene = new Scene(startPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    // CSS styling
    startScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(startPane, startScene));

    QuestionPane questionPane = new QuestionPane(this);
    questionScene = new Scene(questionPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    // CSS Styling
    questionScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(questionPane, questionScene));

    EndPane endPane = new EndPane(this);
    Scene endScene = new Scene(endPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    // CSS Styling
    endScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(endPane, endScene));

    SavePane savePane = new SavePane(this);
    Scene saveScene = new Scene(savePane, WINDOW_WIDTH, WINDOW_HEIGHT);
    // CSS Styling
    saveScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(savePane, saveScene));

    NewQuestionPane newQuestionPane = new NewQuestionPane(this);
    Scene newQuestionScene = new Scene(newQuestionPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    // CSS Styling
    newQuestionScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(newQuestionPane, newQuestionScene));

    switchScreen(AppScreen.START_SCREEN);
  }

  // This function uses the primaryStage we stored earlier to switch scenes. This has the
  // advantage that every Pane object which also contains a reference to this class (such as all
  // the panes created so far for this project) can just call switchScreen() anywhere in the code.
  public void switchScreen(AppScreen screen) {
    int screenIndex = -1;
    switch (screen) {
      case START_SCREEN:
        screenIndex = 0;
        break;
      case QUESTION_SCREEN:
        screenIndex = 1;
        break;
      case END_SCREEN:
        screenIndex = 2;
        break;
      case SAVE_SCREEN:
        screenIndex = 3;
        break;
      case NEWQUESTION_SCREEN:
        screenIndex = 4;
        break;
    }
    Pair<QScene, Scene> sceneToShow = screens.get(screenIndex);
    primaryStage.setScene(sceneToShow.getValue());

    // Trigger the screen shown callback. This is useful because the onShow() method gives you an
    // opportunity to initialize components before running dynamic code.
    sceneToShow.getKey().onShown();

    primaryStage.show();
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public HashMap<String, List<Question>> getQuestionDb() {
    return questionDb;
  }

  public void setQuestionDb(HashMap<String, List<Question>> questionDb) {
    this.questionDb = questionDb;
  }
  
  public void addToQuestionDb(String topic, Question newQuestion) {
    if(questionDb.containsKey(topic)) {
      List<Question> questions = questionDb.get(topic);
      questions.add(newQuestion);
      questionDb.put(topic, questions);
    }
    else {
      List<Question> questions = new ArrayList<>();
      questions.add(newQuestion);
      questionDb.put(topic, questions);
    }
  }
  public StartPane getStartScene() {
    return (StartPane) startScene.getRoot();
  }
  
  
  public List<String> getSelectedTopics() {
    return selectedTopics;
  }

  public int getTotalQuestions() {
    return totalQuestions;
  }

  public void setTotalQuestions(int totalQuestions) {
    this.totalQuestions = totalQuestions;
  }

  public int getQuestionsCorrect() {
    return correctAnswers;
  }

  public void setCorrectAnswers(int correct) {
    this.correctAnswers = correct;
  }

  public void setSelectedTopics(List<String> selectedTopics) {
    this.selectedTopics = selectedTopics;
  }

  public void setQuestionsWanted(int questionsWanted) {
    this.questionsWanted = questionsWanted;
  }

  public int getQuestionsWanted() {
    return questionsWanted;
  }
}
