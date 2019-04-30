package application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONLoader {
  
  public JSONLoader(String jsonFilePath) {

  }

  private void parseFile(String jsonFilePath) throws IOException, ParseException {
    // Load our file
    JSONObject fileObj = (JSONObject) new JSONParser().parse(new FileReader(jsonFilePath));

    Iterator questionIter = ((JSONArray) fileObj.get("questionArray")).iterator();
  }
}
