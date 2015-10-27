package diagnosis.recommendation.dataFormatting;
import java.util.LinkedList;


public class DoubleListToArray {
	
	public double[] convertToDoubleArray(LinkedList<Double> list) {
		
		double[] arr = new double[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			
			arr[i] = list.get(i);
		}
		
		return arr;
	}

}
