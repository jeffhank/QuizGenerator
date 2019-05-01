package application;

import java.util.ArrayList;

public class Question {
  private String metaData;
  private String questionText;
  private String topic;
  private String image;
  private ArrayList<Answer> choiceArray;

  public Question(String metaData, String questionText, String topic, String image, ArrayList<Answer> choiceArray) {
    this.metaData = metaData;
    this.questionText = questionText;
    this.topic = topic;
    this.image = image;
    this.choiceArray = choiceArray;
  }

  public String getMetaData() {
    return metaData;
  }

  public String getQuestionText() {
    return questionText;
  }

  public String getTopic() {
    return topic;
  }

  public String getImage() {
    return image;
  }

  public ArrayList<Answer> getChoiceArray() {
    return choiceArray;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Answer a : choiceArray)
      builder.append(a.toString());
    String answerChoices = builder.toString();
    return String.format("[%s, %s, %s, %s, {%s}]", metaData, questionText, topic, image,
            answerChoices);
  }
}