package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import application.Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSaver {
  private HashMap<String, ArrayList<Question>> hashFunction;

  /**
   * constructor
   * 
   * @param hashFunction
   *          for what we convert to a JSON file
   */
  public JsonSaver(HashMap<String, ArrayList<Question>> hashFunction) {
    this.hashFunction = hashFunction;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  /**
   * 
   * @param jsonFilePath
   *          is the file path where the JSON file will be saved when the user
   *          wants to save it
   * @throws IOException
   *           if the file path could not be found or was invalid
   */
  public void saveQuestionSetJson(String jsonFilePath) throws IOException {
    JSONObject curr = new JSONObject(); // JSON object that will be made into a file
    Set keys = hashFunction.keySet();
    String[] keyArray = (String[]) keys.toArray(); // Convert all the keys into an array to find them easier
    for (int i = 0; i < keyArray.length; i++) { // First for loop that goes through all the keys
      for (int j = 0; j < hashFunction.get(keyArray[i]).size(); j++) { // Second for loop that goes through each bucket
                                                                       // for each key
        curr.put("meta-data", hashFunction.get(keyArray[i]).get(j).getMetaData()); // meta-data
        curr.put("questionText", hashFunction.get(keyArray[i]).get(j).getQuestionText()); // the text of the question
        curr.put("topic", hashFunction.get(keyArray[i]).get(j).getTopic()); // topic of the question
        curr.put("image", hashFunction.get(keyArray[i]).get(j).getImage()); // image locater string
        ArrayList choiceArray = hashFunction.get(keyArray[i]).get(j).getChoiceArray(); // ArrayList for the answers
        JSONArray temp = new JSONArray(); // JSON array that we will add to curr
        for (int k = 0; k < choiceArray.size(); k++) { // third for loop that goes through all the answer objects
          JSONObject choiceArrayObject = new JSONObject();
          if (hashFunction.get(keyArray[i]).get(j).getChoiceArray().get(k).getIsCorrect()) { // If answer is correct
            choiceArrayObject.put("isCorrect", "T");
          } else { // If answer is incorrect
            choiceArrayObject.put("isCorrect", "F");
          }
          // Text of the choice
          choiceArrayObject.put("choice", hashFunction.get(keyArray[i]).get(j).getChoiceArray().get(k).getChoice());
          temp.add(choiceArrayObject);
        }
        curr.put("choiceArray", temp);
      }
    }
    try (FileWriter file = new FileWriter(jsonFilePath)) { // Where we write the file to a JSON file
      file.write(curr.toJSONString());
    }
  }
}