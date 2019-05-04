//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           JsonLoader.java
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

import application.Answer;
import application.Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonLoader {

  private HashMap<String, List<Question>> questionDb;

  /**
   * constructor
   * @param jsonFilePath the file path of the JSON file we are parsing
   * @throws IOException if finding the file warrants a problem
   * @throws ParseException if we have an issue with the parseFile()
   */
  public JsonLoader(String jsonFilePath) throws IOException, ParseException {
    this.questionDb = new HashMap<>();
    parseFile(jsonFilePath, questionDb);
  }

  public JsonLoader(List<File> jsonPathList) throws IOException, ParseException {
    this.questionDb = new HashMap<>();
    for (File path : jsonPathList) {
      parseFile(path.getPath(), questionDb);
    }
  }

  /**
   * Parses a JSON file into a list of questions.
   * @param db database for parsed questions to be stored in
   * @throws IOException if there's a problem with the file reader
   * @throws ParseException if there is a problem with parsing the JSON file
   */
  private void parseFile(String filePath, HashMap<String, List<Question>> db) throws IOException,
          ParseException {
    // Load our file
    JSONObject fileObj = (JSONObject) new JSONParser().parse(new FileReader(filePath));

    // Get the array of questions
    JSONArray questions = (JSONArray) fileObj.get("questionArray");
    for (Object questionObj : questions) {
      JSONObject question = (JSONObject) questionObj;

      // For each topic, check if a bucket for that topic exists in our HashMap. If it
      // doesn't, create it.
      String topic = (String) question.get("topic");
      if (!db.containsKey(topic)) {
        db.put(topic, new ArrayList<>());
      }

      String metaData = (String) question.get("meta-data");
      String questionText = (String) question.get("questionText");
      String imageUrl = (String) question.get("image");
      
      JSONArray answers = (JSONArray) question.get("choiceArray");
      ArrayList<Answer> questionAnswers = new ArrayList<>();
      for (Object answerObj : answers) {
        JSONObject answer = (JSONObject) answerObj;

        // Check's to see whether or not the answer is correct or not
        boolean isCorrect = ((String) answer.get("isCorrect")).toLowerCase().equals("t");
        String choice = (String) answer.get("choice");

        // Adds the array of Answer objects
        questionAnswers.add(new Answer(isCorrect, choice));
      }
      // Creates a question object
      Question parsedQ = new Question(metaData, questionText, topic, imageUrl, questionAnswers);

      // Gets a bucket associated with the topic
      List<Question> topicQuestions = db.get(topic);

      // Adds to the bucket
      topicQuestions.add(parsedQ);
    }
  }
  /**
   * 
   * @return the HashMap of all the questions
   */
  public HashMap<String, List<Question>> getQuestionDb() {
    return questionDb;
  }
}
