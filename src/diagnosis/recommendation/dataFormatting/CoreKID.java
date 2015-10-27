package diagnosis.recommendation.dataFormatting;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eobjects.sassy.SasColumnType;
import org.eobjects.sassy.SasReader;
import org.eobjects.sassy.SasReaderCallback;


public class CoreKID {
	
	private List<Double> ageYears;
	//private List<Double> ageMonths;
	private List<Double> ageDays;
	private List<Diagnosis> diagnosis;
	private List<Double> gender;
	private int rowCount;
	private FileWriter fstream;
	private BufferedWriter out;
	
	public CoreKID() {
		
		ageYears = new LinkedList<Double>();
		//ageMonths = new LinkedList<Double>();
		ageDays = new LinkedList<Double>();
		diagnosis = new LinkedList<Diagnosis>();
		gender = new LinkedList<Double>();
		rowCount = 0;
		

		try {
			fstream = new FileWriter("C:/NIS 2009/adult_demographic_diagnosis1.arff",true);
			out = new BufferedWriter(fstream);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void readCoreKID() {
		
		SasReader reader = new SasReader(new File("C:/NIS 2009/nis_2009_core.sas7bdat"));
		reader.read(new SasReaderCallback() {
		        public void column(int index, String name, String label, SasColumnType type, int length) {
		                System.out.println("Column no. " + index + ": " + label);
		        }
		        
		        public boolean readData() { return true; }
	
		        public boolean row(int rowNumber, Object[] rowData) {        
		        		
		                //System.out.println("Row no. " + rowNumber + ": " + Arrays.toString(rowData));
		        		
						try {
	 
			                String row = Arrays.toString(rowData);
			                String[] columns = row.split(",");
			                ReadExcelFile decode = new ReadExcelFile();
			                out.write("\n");
			                
			                for(int i = 0; i < columns.length; i++) {
			                	
			                	if(i == 0) {
			                		
			                		String age = columns[i];
			                		ageYears.add(Double.parseDouble(age.replace('[', ' ')));
			                		//System.out.println("Age in Years = " + columns[i]);
			                		out.write(ageYears.get(rowCount).toString() + ", ");

			                	}
			                	
			                	/*if(i == 3) {
			                		
			                		ageMonths.add(Double.parseDouble(columns[i]));
			                		//System.out.println("Age in Days = " + columns[i]);
			                		out.write(ageMonths.get(rowCount).toString() + ", ");
			                	}*/
			                	
			                	if(i == 1) {
			                		
			                		ageDays.add(Double.parseDouble(columns[i]));
			                		//System.out.println("Age in Months = " + columns[i]);
			                		out.write(ageDays.get(rowCount).toString() + ", ");
			                	}
			                	
			                	diagnosis.add(new Diagnosis());
			                	
			                	if(i == 19 && !columns[i].trim().equalsIgnoreCase("")) {

			                		String diag = decode.decodeICD9Diagnosis(columns[i].trim());
									diagnosis.get(rowCount).addDiagnosis(diag);
									//System.out.println("DIAG = " + diag);

									out.write(diagnosis.get(rowCount).getDiagnosis(0) + ", ");
			                	}
			                	
			                	if(i == 78) {
			                		
			                		gender.add(Double.parseDouble(columns[i]));
			                		//System.out.println("GENDER = " + columns[i]);
			                		out.write(gender.get(rowCount).toString());
			                		out.newLine();
			                	}	
			                	
			                }
			                
			                rowCount++;			                
            
		        	} 
						
					catch (IOException e1) {
						
						e1.printStackTrace();
					}
		                
		                return true;
		        }
		});
		
		 try {
			out.close();
		 } 
		 
		 catch (IOException e) {

			e.printStackTrace();
		 }
	}

	public List<Double> getAgeYears() {
		return ageYears;
	}

	/*public List<Double> getAgeMonths() {
		return ageMonths;
	}*/

	public List<Double> getAgeDays() {
		return ageDays;
	}

	public List<Diagnosis> getDiagnosis() {
		return diagnosis;
	}

	public List<Double> getGender() {
		return gender;
	}

}
