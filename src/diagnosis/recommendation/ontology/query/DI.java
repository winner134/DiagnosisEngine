package diagnosis.recommendation.ontology.query;

public class DI {

	private String disease;
	private int index;
	
	public DI(String disease, int index) {
		
		this.disease = disease;
		this.index = index;
		
	}
	
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
