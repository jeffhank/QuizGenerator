package application;

import java.util.ArrayList;

public class Question {
  private String metaData;
  private String questionText;
  private String topic;
  private String image;
  private ArrayList<Answer> choiceArray;

  /**
   * 
   * @param metaData
   *          : if the question is used or not
   * @param questionText
   *          : the text of the question being asked
   * @param topic
   *          : The topic of the question
   * @param image
   *          : String path of the image
   * @param choiceArray
   *          : An array of answers which display the answer choices for the user
   */
  public Question(String metaData, String questionText, String topic, String image, ArrayList<Answer> choiceArray) {
    this.metaData = metaData;
    this.questionText = questionText;
    this.topic = topic;
    this.image = image;
    this.choiceArray = choiceArray;
  }

  /**
   * getter method for metaData
   * 
   * @return metaData
   */
  public String getMetaData() {
    return metaData;
  }

  /**
   * getter method for questionText
   * 
   * @return questionText
   */
  public String getQuestionText() {
    return questionText;
  }

  /**
   * getter method for topic
   * 
   * @return topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * getter method for the image path
   * 
   * @return image
   */
  public String getImage() {
    return image;
  }

  /**
   * getter method for choiceArray
   * 
   * @return choiceArray
   */
  public ArrayList<Answer> getChoiceArray() {
    return choiceArray;
  }

  /**
   * returns the String format of the question class
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Answer a : choiceArray)
      builder.append(a.toString());
    String answerChoices = builder.toString();
    return String.format("[%s, %s, %s, %s, {%s}]", metaData, questionText, topic, image, answerChoices);
  }
}