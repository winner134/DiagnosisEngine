package diagnosis.recommendation.dataFormatting;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class PredicitionAccuracy {
	
	private static FileReader p_File;
	private static BufferedReader predictionReader;
	private static FileReader v_File;
	private static BufferedReader validationReader;
	private final static String DATA_BEGIN = "@data";
	private static LinkedList<Record> prediction;
	private static LinkedList<Record> validation;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 3) {
			compare(args[0], args[1]);
			System.out.println("Accuracy = " + computeAccuracy(Integer.parseInt(args[2])));
		}
	}
	
	public static void compare(String predicitionFile, String validationFile) {
		
		try{
			
			p_File = new FileReader(predicitionFile);
			predictionReader = new BufferedReader(p_File);
			v_File = new FileReader(validationFile);
			validationReader = new BufferedReader(v_File);
			prediction = new LinkedList<Record>();
			validation = new LinkedList<Record>();
		
			while(!predictionReader.readLine().trim().equalsIgnoreCase(DATA_BEGIN));
			while(!validationReader.readLine().trim().equalsIgnoreCase(DATA_BEGIN));
			
			String predictionRecord = predictionReader.readLine();
			
			while(predictionRecord != null) {
				
				Record r = new Record();
				String[] columns = predictionRecord.split(",");
				
				if(columns.length > 0) {
					
					for(int i = 0; i < columns.length; i++) {
						r.add(columns[i]);
					}
	
					prediction.add(r);
				}
				
				predictionRecord = predictionReader.readLine();

			}
			
			String validationRecord = validationReader.readLine();
			
			while(validationRecord != null) {
				
				Record r = new Record();
				String[] columns = validationRecord.split(",");
				
				if(columns.length > 0) {
					
					for(int i = 0; i < columns.length; i++) {
						
						r.add(columns[i]);
					}
					
					validation.add(r);
				}
				
				validationRecord = validationReader.readLine();
			}
			
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
		
		catch(Exception e1) {		
			e1.printStackTrace();
		}
	}
	
	public static double computeAccuracy(int columnIndex) {
		
		int matchCount = 0;
		
		if(prediction.size() != validation.size()) {
			
			System.out.println("Invalid Comparison - Records do not have the same number of columns");
			return -1;
		}
		
		for(int i = 0; i < prediction.size(); i++) {
		
			if(prediction.get(i).get(columnIndex-1).replaceAll("'", "").trim().equalsIgnoreCase(validation.get(i).get(columnIndex-1).replaceAll("\"", "").trim()))
				matchCount++;
		}

		return (matchCount/(prediction.size() * 1.0)) * 100;
	}
}
