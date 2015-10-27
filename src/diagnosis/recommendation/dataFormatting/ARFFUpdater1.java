package diagnosis.recommendation.dataFormatting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class ARFFUpdater1 {
	
	private static FileWriter attribute;
	private static BufferedWriter attributeWriter;
	private static FileReader reader;
	private static BufferedReader attributeReader;
	private static LinkedList<String> diagnosis;
	private final static String ATTR = "prim_diagnosis";
	private final static int DIAG_POS = 4;
	private final static String DATA_BEGIN = "@data";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 1)
			printDiagList(args[0]);
	}
	
	public static void printDiagList(String fileName) {
		
		try {
			attribute = new FileWriter(fileName.replaceAll(".arff", "") + "_list.arff", true);
			attributeWriter = new BufferedWriter(attribute);
			reader = new FileReader(fileName);
			attributeReader = new BufferedReader(reader);
			
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
			}
			
			for(int i = 0; i < diagnosis.size(); i++) {
				
				attributeWriter.append(diagnosis.get(i).trim());
				System.out.print(diagnosis.get(i).trim());
				if(i != diagnosis.size() - 1) {
					attributeWriter.append(", ");
					System.out.print(", ");
				}
			}
			
			attributeWriter.append("}");
			attributeWriter.newLine();
						
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
