package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSaver {
  private HashMap<String, List<Question>> questionDB;

  /**
   * constructor
   * 
   * @param questionDB
   *          for what we convert to a JSON file
   */
  public JsonSaver(HashMap<String, List<Question>> questionDB) {
    this.questionDB = questionDB;
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
    JSONArray questionArr = new JSONArray();

    for (String key : questionDB.keySet()) {
      JSONObject curQuestionObj = new JSONObject();
      for (Question question : questionDB.get(key)) {
        curQuestionObj.put("meta-data", question.getMetaData());
        curQuestionObj.put("questionText", question.getQuestionText());
        curQuestionObj.put("topic", question.getTopic());
        curQuestionObj.put("image", question.getImage());

        // Create array object to store answer choices
        JSONArray choicesArr = new JSONArray();
        for (Answer answer : question.getChoiceArray()) {
          JSONObject answerObj = new JSONObject();
          answerObj.put("isCorrect", answer.getIsCorrect() ? "T" : "F");
          answerObj.put("choice", answer.getChoice());
          choicesArr.add(answerObj);
        }

        curQuestionObj.put("choiceArray", choicesArr);
        questionArr.add(curQuestionObj);
      }
    }
    curr.put("questionArray", questionArr);

    try (FileWriter file = new FileWriter(jsonFilePath)) { // Where we write the file to a JSON file
      file.write(curr.toJSONString());
    }
  }
}