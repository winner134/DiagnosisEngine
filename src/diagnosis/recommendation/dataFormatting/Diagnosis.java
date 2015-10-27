package diagnosis.recommendation.dataFormatting;
import java.util.LinkedList;


public class Diagnosis {
	
	private LinkedList<String> diagnosis;
	
	public Diagnosis() {
		
		diagnosis = new LinkedList<String>();
	}

	public void addDiagnosis(String diagnosis) {
		
		this.diagnosis.add(diagnosis);
	}
	
	public String getDiagnosis(int index) {
		
		return diagnosis.get(index);
	}
	
}
