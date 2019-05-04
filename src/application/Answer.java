//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Answer.java
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

/**
 * 
 * This class represents an answer choice for a question
 *
 */
public class Answer {
  private boolean isCorrect;
  private String choice;

  /**
   * Constructor
   * 
   * @param isCorrect
   *          is a boolean in which true means the answer is correct, false for
   *          the other way
   * @param choice
   *          is the String of the answer choice
   */
  public Answer(boolean isCorrect, String choice) {
    this.isCorrect = isCorrect;
    this.choice = choice;
  }

  /**
   * getter method for isCorrect
   * 
   * @return isCorrect
   */
  public boolean getIsCorrect() {
    return this.isCorrect;
  }

  /**
   * getter method for choice
   * 
   * @return choice
   */
  public String getChoice() {
    return this.choice;
  }
}
