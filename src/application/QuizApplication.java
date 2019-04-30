package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class QuizApplication extends Application {

  List<Pair<Pane, Scene>> screens;
  int showingScreenIndex;
  final int WINDOW_WIDTH = 800;
  final int WINDOW_HEIGHT = 600;
  private Stage primaryStage;

  @Override
  public void start(Stage primaryStage) {
    // Store this stage value, so we can use it to switch screens later on
    this.primaryStage = primaryStage;

    // Set up all our different scenes
    // First, setup our start scene
    screens = new ArrayList<>();

    Pane startPane = new StartPane(this);
    Scene startScene = new Scene(startPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    startScene.getStylesheets().add(getClass().getResource("start_pane.css").toExternalForm());
    screens.add(new Pair<>(startPane, startScene));

    Pane questionPane = new QuestionPane(this);
    Scene questionScene = new Scene(questionPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    screens.add(new Pair<>(questionPane, questionScene));

    Pane endPane = new QuestionPane(this);
    Scene endScene = new Scene(endPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    screens.add(new Pair<>(endPane, endScene));

    switchScreen(AppScreen.START_SCREEN);
  }

  public void switchScreen(AppScreen screen) {
    switch (screen) {
      case START_SCREEN:
        primaryStage.setScene(screens.get(0).getValue());
        break;
      case QUESTION_SCREEN:
        primaryStage.setScene(screens.get(1).getValue());
        break;
      case END_SCREEN:
        primaryStage.setScene(screens.get(2).getValue());
        break;
    }
    primaryStage.show();
  }
}
