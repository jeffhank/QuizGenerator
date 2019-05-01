package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSaver {
  private HashMap<String, ArrayList<Question>> hashFunction;

  public JsonSaver(HashMap<String, ArrayList<Question>> hashFunction) {
    this.hashFunction = hashFunction;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void saveQuestionSetJson(String jsonFilePath) throws IOException {
    JSONObject curr = new JSONObject();
    Set keys = hashFunction.keySet();
    String[] keyArray = (String[]) keys.toArray();
    for (int i = 0; i < keyArray.length; i++) {
      for (int j = 0; j < hashFunction.get(keyArray[i]).size(); j++) {
        curr.put("meta-data", hashFunction.get(keyArray[i]).get(j).getMetaData());
        curr.put("questionText", hashFunction.get(keyArray[i]).get(j).getQuestionText());
        curr.put("topic", hashFunction.get(keyArray[i]).get(j).getTopic());
        curr.put("image", hashFunction.get(keyArray[i]).get(j).getImage());
        ArrayList choiceArray = hashFunction.get(keyArray[i]).get(j).getChoiceArray();
        JSONArray temp = new JSONArray();
        for (int k = 0; k < choiceArray.size(); k++) {
          JSONObject choiceArrayObject = new JSONObject();
          if (hashFunction.get(keyArray[i]).get(j).getChoiceArray().get(k).getIsCorrect()) {
            choiceArrayObject.put("isCorrect", "T");
          } else {
            choiceArrayObject.put("isCorrect", "F");
          }
          choiceArrayObject.put("choice", hashFunction.get(keyArray[i]).get(j).getChoiceArray().get(k).getChoice());
          temp.add(choiceArrayObject);
        }
        curr.put("choiceArray", temp);
      }
    }
    try (FileWriter file = new FileWriter(jsonFilePath)) {
      file.write(curr.toJSONString());
    }
  }
}