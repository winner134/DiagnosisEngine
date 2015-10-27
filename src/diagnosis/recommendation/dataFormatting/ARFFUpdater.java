package diagnosis.recommendation.dataFormatting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class ARFFUpdater {
	
	private static FileWriter attribute;
	private static BufferedWriter attributeWriter;
	private static FileReader reader;
	private static BufferedReader attributeReader;
	private static FileReader reader1;
	private static BufferedReader attributeReader1;
	private static LinkedList<String> diagnosis;
	private final static String ATTR = "prim_diagnosis";
	private final static String DATA_BEGIN = "@data";
	private final static int DIAG_POS = 3;
	//private final static int MONTHS_POS = 2;
	private final static int DAYS_POS = 2;
	private final static int GEN_POS = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 1)
			updateARFF(args[0]);
		
	}
	
	public static void updateARFF(String fileName) {
		
		try {
			
			attribute = new FileWriter(fileName.replaceAll(".arff", "") + "_corrected.arff", true);
			attributeWriter = new BufferedWriter(attribute);
			reader = new FileReader(fileName);
			attributeReader = new BufferedReader(reader);
			reader1 = new FileReader(fileName);
			attributeReader1 = new BufferedReader(reader1);
			diagnosis = new LinkedList<String>();
			
			String line = attributeReader.readLine();
			
			while(!line.contains(ATTR)) {
				
				attributeWriter.append(line);
				attributeWriter.newLine();
				
				line = attributeReader.readLine();
				
			}
					
			attributeWriter.append(line);
			attributeWriter.append("     {");
			
			line = attributeReader.readLine();
			
			while(!line.contains(DATA_BEGIN)) {
				line = attributeReader.readLine();
			}
			
			line = attributeReader.readLine();
			while(line != null) {
				
				String[] columns = line.split(",");
				if(!diagnosis.contains(columns[DIAG_POS-1]))
					diagnosis.add(columns[DIAG_POS-1]);
				line = attributeReader.readLine();
				line = attributeReader.readLine();

			}
			
			for(int i = 0; i < diagnosis.size(); i++) {
				
				attributeWriter.append("\"" + diagnosis.get(i).trim() + "\"");
				if(i != diagnosis.size() - 1)
					attributeWriter.append(", ");
			}
			
			attributeWriter.append("}");
			attributeWriter.newLine();
			
			String line1 = attributeReader1.readLine();
			
			while(!line1.contains(ATTR)) {
				line1 = attributeReader1.readLine();
			}
			
			line1 = attributeReader1.readLine();
			
			while(!line1.contains(DATA_BEGIN)) {
				
				attributeWriter.append(line1);
				attributeWriter.newLine();
				line1 = attributeReader1.readLine();
			}
			
			attributeWriter.append(line1);
			attributeWriter.newLine();
			
			line1 = attributeReader1.readLine();
			
			while(line1 != null) {
				
				String[] columns = line1.split(",");
				columns[DIAG_POS-1] = "\"" + columns[DIAG_POS-1].trim() + "\"";
				//if(columns[MONTHS_POS-1].trim().equalsIgnoreCase("NaN"))
				//	columns[MONTHS_POS-1] = "0.0";
				if(columns[DAYS_POS-1].trim().equalsIgnoreCase("NaN"))
					columns[DAYS_POS-1] = "0.0";
				
				for(int i = 0; i < columns.length - 2; i++) {
					
					attributeWriter.append(columns[i]);
					attributeWriter.append(", ");
				}
				
				attributeWriter.append(columns[GEN_POS-1]);
				attributeWriter.append(", ");
				attributeWriter.append(columns[DIAG_POS-1]);
				
				attributeWriter.newLine();
				line1 = attributeReader1.readLine();
				line1 = attributeReader1.readLine();
			}
			
			attributeWriter.close();
			attributeReader.close();
			attributeReader1.close();
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		catch(Exception e1) {
			
			e1.printStackTrace();
		}
		
	}

}
