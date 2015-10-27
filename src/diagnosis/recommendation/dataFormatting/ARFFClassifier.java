package diagnosis.recommendation.dataFormatting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class ARFFClassifier {

	private static FileWriter attribute;
	private static BufferedWriter attributeWriter;
	private static FileReader reader;
	private static BufferedReader attributeReader;
	private final static String DATA_BEGIN = "@data";
	private final static String DIAG = "DIABETES UNCOMPL TYPE II";
	private final static String ALT_DIAG = "Normal";
	private final static int DIAG_POS = 4;
	private static int diagCount = 0;
	private static int altCount = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 1)
			updateClass(args[0]);
	}
	
	public static void updateClass(String fileName) {
		
		try {
			attribute = new FileWriter(fileName.replaceAll(".arff", "") + "_diagnosis.arff", true);
			attributeWriter = new BufferedWriter(attribute);
			reader = new FileReader(fileName);
			attributeReader = new BufferedReader(reader);
			
			String line = attributeReader.readLine();
			
			while(!line.contains(DATA_BEGIN)) {
				
				attributeWriter.append(line);
				attributeWriter.newLine();
				
				line = attributeReader.readLine();
				
			}
			
			attributeWriter.append(line);
			attributeWriter.newLine();
			line = attributeReader.readLine();

			while(line != null) {
				
				String[] columns = line.split(",");

				if(columns[DIAG_POS-1].replaceAll("\"", "").trim().contains(DIAG)) {
					
					for(int i = 0; i < columns.length - 1; i++) {
						
						attributeWriter.append(columns[i]);
						attributeWriter.append(", ");
					}
					
					attributeWriter.append(columns[DIAG_POS-1]);
					attributeWriter.newLine();
					diagCount++;
				}
				
				else {
					
					if(altCount <= diagCount) {
						
						for(int i = 0; i < columns.length - 1; i++) {
							
							attributeWriter.append(columns[i]);
							attributeWriter.append(", ");
						}
						
						attributeWriter.append("\"" + ALT_DIAG + "\"");
						attributeWriter.newLine();
						altCount++;
					}
				}	
				
				line = attributeReader.readLine();
			}
			
			attributeWriter.flush();
			attributeWriter.close();
			attributeReader.close();

		}
		
		catch(IOException e) {
			
			e.printStackTrace();
		}
		
		catch(Exception e1) {
			
			e1.printStackTrace();
		}
		
	}
}
