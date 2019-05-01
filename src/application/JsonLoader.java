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

  public JsonLoader(String jsonFilePath) throws IOException, ParseException {
    this.jsonFilePath = jsonFilePath;
    this.parsedDb = parseFile();
  }

  private HashMap<String, List<Question>> parseFile() throws IOException, ParseException {
    // Load our file
    JSONObject fileObj = (JSONObject) new JSONParser().parse(new FileReader(jsonFilePath));

    // Create the data structure we will be adding to and returning
    HashMap<String, List<Question>> questionDb = new HashMap<>();

    // Get the array of questions
    JSONArray questions = (JSONArray) fileObj.get("questionArray");
    for (Object questionObj : questions) {
      JSONObject question = (JSONObject) questionObj;

      // For each topic, check if a bucket for that topic exists in our HashMap. If it doesn't,
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

        boolean isCorrect = ((String) answer.get("isCorrect")).toLowerCase().equals("t");
        String choice = (String) answer.get("choice");

        questionAnswers.add(new Answer(isCorrect, choice));
      }

      Question parsedQ = new Question(metaData, questionText, topic, imageUrl, questionAnswers);
      List<Question> topicQuestions = questionDb.get(topic);
      topicQuestions.add(parsedQ);
    }

    return questionDb;
  }

  public HashMap<String, List<Question>> getParsedDb() {
    return parsedDb;
  }
}
