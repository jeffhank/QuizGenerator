package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class NewQuestionPane extends BorderPane implements QScene {

	private QuizApplication application;
	private String questionString = "";
	private int numChoices = -1;
	private String topicString = "";
	private GridPane grid = new GridPane();
	private ArrayList<Answer> answerList = new ArrayList<Answer>();
  private int correctRow = -1;

	public NewQuestionPane(QuizApplication application) {
		this.application = application;
		setupLayout();
	}

	private void setupLayout() {
    this.setStyle("-fx-background-color: #FFFFFF;");
	  firstQuestions();
	}
	
	private void firstQuestions() {
	    grid.getChildren().clear();
	    this.getChildren().clear();
	    Label titleLabel = new Label("Add A New Question");
	    titleLabel.setPadding(new Insets(12));
	    titleLabel.setStyle("-fx-font: 30 arial;");
	    this.setTop(titleLabel);
	    BorderPane.setAlignment(titleLabel, Pos.CENTER);

	    TextField question = new TextField();
	    TextField numAnswerChoices = new TextField();
	    TextField topic = new TextField();
	    grid.add(new Label("Enter question text: "), 0, 0);
	    grid.add(question, 1, 0);
	    grid.add(new Label("How many answer choices do you want? (2-5)"), 0, 1);
	    grid.add(new Label("What is the topic of your question?"), 0, 2);
	    grid.add(numAnswerChoices, 1, 1);
	    grid.add(topic, 1, 2);
	    grid.setVgap(20.0);
	    grid.setHgap(10.0);
	    this.setCenter(grid);
	    grid.setAlignment(Pos.CENTER);
	    
	    Button nextButton = new Button("Next");
	    nextButton.setPrefWidth(200.0);
	    nextButton.setOnAction(a -> {
	      if(!question.getText().equals("") && !numAnswerChoices.getText().equals("") 
	          && !topic.getText().equals("") && !numAnswerChoices.getText().contentEquals("")) {
	        questionString = question.getText();
	        topicString = topic.getText();
	        try {
	          numChoices = Integer.parseInt(numAnswerChoices.getText());
	          if(numChoices > 1 && numChoices < 6)
	            askForAnswerChoices();
	          else {
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Invalid Input");
	            alert.setContentText("Please ensure the number of choices is between 2 and 5.");
	            alert.show();
	          }
	        }
	        catch(NumberFormatException nfe) {
	          Alert alert = new Alert(AlertType.INFORMATION);
	          alert.setTitle("Not a Number");
	          alert.setContentText("Please ensure you enter a number in the number of choices field.");
	          alert.show();
	        }
	        topicString = topic.getText();
	      }
	      else {
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Error");
          alert.setContentText("All fields must be filled.");
          alert.show();
	      }
	    });
	    this.setBottom(nextButton);
	    this.setAlignment(nextButton, Pos.BOTTOM_RIGHT);

	}
	
	public void askForAnswerChoices() {
	  grid.getChildren().clear();
	  this.getChildren().clear();
	  Button submitButton = new Button("Submit");
	  submitButton.setDisable(true);
	  Label instruction = new Label("Enter the choices and select the correct answer");
	  instruction.setStyle("-fx-font: 24 arial;");
	  instruction.setPadding(new Insets(12));
	  this.setTop(instruction);
	  this.setAlignment(instruction, Pos.TOP_CENTER);
	  ToggleGroup answerGroup = new ToggleGroup();
	  for(int i=0; i<numChoices; i++) {
	    grid.add(new TextField(), 0, i);
	    int currentRow = i;
	    RadioButton choiceButton = new RadioButton();
	    choiceButton.setToggleGroup(answerGroup);
	    choiceButton.setOnAction(a -> {
	      correctRow = currentRow;
	      submitButton.setDisable(false);
	    });
	    grid.add(choiceButton, 1, i);
	  }
	  this.setCenter(grid);
	  
	  Button backButton = new Button("Back");
	  backButton.setPrefWidth(200.0);
	  submitButton.setPrefWidth(200.0);
	  this.setLeft(backButton);
	  this.setRight(submitButton);
	  backButton.setOnAction(a -> firstQuestions());
	  Question newQuestion = new Question("unused", questionString, topicString, "none", answerList);
	  submitButton.setOnAction(a -> {
	    for(int i=0; i<grid.getChildren().size(); i++) {
	      if(grid.getChildren().get(i) instanceof TextField) {
	        TextField answerField = (TextField) grid.getChildren().get(i);
	        if(answerField.getText().equals("")) {
	          Alert alert = new Alert(AlertType.INFORMATION);
	          alert.setTitle("Error");
	          alert.setContentText("All fields must be filled.");
	          alert.show();
	          return;
	        }
	      }
	    }
	    
	    for(int i=0; i<grid.getChildren().size(); i++) {
	      if(grid.getChildren().get(i) instanceof TextField) {
	        TextField answerField = (TextField) grid.getChildren().get(i);
	        String answerString = answerField.getText();
	        if(grid.getRowIndex(grid.getChildren().get(i)) == correctRow) {
	          newQuestion.addAnswer(new Answer(true, answerString));
	        }
	        else {
	          newQuestion.addAnswer(new Answer(false, answerString));
	        }
	      }
	    }
	  });
	  this.setAlignment(backButton, Pos.BOTTOM_LEFT);
	  this.setAlignment(submitButton, Pos.BOTTOM_RIGHT);
	}
	@Override
	public void onShown() {
		// TODO Auto-generated method stub

	}
}