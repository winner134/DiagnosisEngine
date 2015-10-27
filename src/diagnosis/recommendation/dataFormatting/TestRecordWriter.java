package diagnosis.recommendation.dataFormatting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

public class TestRecordWriter {

	private FileWriter attribute;
	private BufferedWriter attributeWriter;
	private FileReader reader;
	private BufferedReader attributeReader;
	private final static String DATA_BEGIN = "@data";
	private final static String DIAGNOSIS = "prim_diagnosis";
	private final static String ATTR = "@attribute";
	private int DIAG_POS = 0;
	private String[] diagnosisValues = null;
	
	public void writePredictionsToFile(String testFile, String predictionFile, LinkedList<Double> predictions) {
		
		try {
			attribute = new FileWriter(predictionFile, true);
			attributeWriter = new BufferedWriter(attribute);
			reader = new FileReader(testFile);
			attributeReader = new BufferedReader(reader);
			
			String line = attributeReader.readLine();
			adjustDiagPos(line);
			
			while(!line.contains(DIAGNOSIS)) {
				
				attributeWriter.append(line);
				attributeWriter.newLine();
				
				line = attributeReader.readLine();
				adjustDiagPos(line);
			}
			
			attributeWriter.append(line);
			attributeWriter.newLine();
			line = line.replaceAll(ATTR, "");
			line = line.replaceAll(DIAGNOSIS, "");
			line = line.replace('{', ' ');
			line = line.replace('}', ' ');
			line = line.replaceAll("'", "").trim();
			diagnosisValues = line.split(",");
			line = attributeReader.readLine();
			attributeWriter.append(line);
			attributeWriter.newLine();
			line = attributeReader.readLine();
			
			if(diagnosisValues != null && DIAG_POS > 0) {
				
				int counter = 0;
				
				while(line != null) {
							
					String[] columns = line.split(",");
					
					
					for(int i = 0; i < DIAG_POS - 1; i++) {
						
						attributeWriter.append(columns[i] + ", ");
					}
					
					for(int i = 0; i < diagnosisValues.length; i++) {
						
						if(predictions.get(counter) == i)
							attributeWriter.append(diagnosisValues[i]);
					}
					
					counter = counter + 1;
					attributeWriter.newLine();
					line = attributeReader.readLine();
				}
				
				attributeWriter.close();
				attributeReader.close();
			}
			
			else {
				
				System.out.println("INVALID TEST DATA");
			}
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void adjustDiagPos(String line) {
		
		if(line.contains(ATTR))
			DIAG_POS++;
	}
}
