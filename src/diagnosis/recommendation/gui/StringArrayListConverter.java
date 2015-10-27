package diagnosis.recommendation.gui;

import java.util.LinkedList;
import java.util.List;

public class StringArrayListConverter {

	private static StringArrayListConverter instance;
	
	private StringArrayListConverter() {
		
	}
	
	public static StringArrayListConverter getInstance() {
		
		if(instance == null)
			instance = new StringArrayListConverter();
		
		return instance;
	}
	
	public List<String> convertToList(String[] stringArray) {
		
		List<String> stringList = new LinkedList<String>();
		
		for(int i = 0; i < stringArray.length; i++) {
			
			stringList.add(stringArray[i]);
		}
		
		return stringList;
	}
	
	public String[] convertObjectArrayToStringArray(Object[] oArray) {
		
		String[] sArray = new String[oArray.length];
		
		for (int i = 0; i < oArray.length; i++) {
			
			sArray[i] = (String) oArray[i];
		}
		
		return sArray;
	}
}
