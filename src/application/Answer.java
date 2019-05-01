package application;

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
