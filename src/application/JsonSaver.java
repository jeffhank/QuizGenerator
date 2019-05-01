package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSaver {
	private Hashtable<String, ArrayList<Question>> hashFunction;

	public JsonSaver(Hashtable<String, ArrayList<Question>> hashFunction) {
		this.hashFunction = hashFunction;
	}

	public void saveQuestionSetJson(String jsonFilePath) {
		File file = new File(jsonFilePath);
		JSONObject curr = new JSONObject();
		Set keys = hashFunction.keySet();
		String[] keyArray = (String[]) keys.toArray();
		for (int i = 0; i < keyArray.length; i++) {
			for (int j = 0; j < hashFunction.get(keyArray[i]).size(); j++) {
				curr.put("meta-data", hashFunction.get(keyArray[i]).get(j).getMetaData());
				curr.put("questionText", hashFunction.get(keyArray[i]).get(j).getQuestionText());
				curr.put("topic", hashFunction.get(keyArray[i]).get(j).getTopic());
				curr.put("image", hashFunction.get(keyArray[i]).get(j).getImage());
				curr.put("choiceArray", hashFunction.get(keyArray[i]).get(j).getChoiceArray());
			}
		}
	}
}