package diagnosis.recommendation.dataFormatting;
import java.util.LinkedList;


public class Record {

	private LinkedList<String> column;
	
	public Record() {
		
		column = new LinkedList<String>();
	}
	
	public String get(int index) {
		
		return column.get(index);
	}
	
	public void add(String column) {
		
		this.column.add(column);
	}

	public LinkedList<String> getColumn() {
		return column;
	}
	
	
}
