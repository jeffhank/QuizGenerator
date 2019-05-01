package application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonLoader {

  private String jsonFilePath;
  private HashMap<String, List<Question>> parsedDb;
  /**
   * constructor
   * @param jsonFilePath the file path of the JSON file we are parsing
   * @throws IOException if finding the file warrants a problem
   * @throws ParseException if we have an issue with the parseFile()
   */
  public JsonLoader(String jsonFilePath) throws IOException, ParseException {
    this.jsonFilePath = jsonFilePath;
    this.parsedDb = parseFile();
  }
  /**
   * 
   * @return the HashMap of our questions
   * @throws IOException if there's a problem with the file reader
   * @throws ParseException if there is a problem with parsing the JSON file
   */
  private HashMap<String, List<Question>> parseFile() throws IOException, ParseException {
    // Load our file
    JSONObject fileObj = (JSONObject) new JSONParser().parse(new FileReader(jsonFilePath));

    // Create the data structure we will be adding to and returning
    HashMap<String, List<Question>> questionDb = new HashMap<>();

    // Get the array of questions
    JSONArray questions = (JSONArray) fileObj.get("questionArray");
    for (Object questionObj : questions) {
      JSONObject question = (JSONObject) questionObj;

      // For each topic, check if a bucket for that topic exists in our HashMap. If it
      // doesn't,
      // create it.
      String topic = (String) question.get("topic");
      if (!questionDb.containsKey(topic)) {
        questionDb.put(topic, new ArrayList<>());
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
      List<Question> topicQuestions = questionDb.get(topic);
      // Adds to the bucket
      topicQuestions.add(parsedQ);
    }

    return questionDb;
  }
  /**
   * 
   * @return the HashMap of all the questions
   */
  public HashMap<String, List<Question>> getParsedDb() {
    return parsedDb;
  }
}
