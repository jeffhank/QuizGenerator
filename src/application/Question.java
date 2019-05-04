//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Question.java
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

import java.util.ArrayList;

/**
 * 
 * This class represents an question in the quiz
 *
 */
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
   * method that adds an answer to the Question
   * 
   * @param a is the answer to add
   */
  public void addAnswer(Answer a) {
    choiceArray.add(a);
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