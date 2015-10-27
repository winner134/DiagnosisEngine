import diagnosis.recommendation.ontology.dataMining.ClassificationAssociation;


public class TestClassifier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		try {
			
			ClassificationAssociation associator = new ClassificationAssociation();
			associator.setAssociator();
			associator.setTraining("C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/weather.arff");
			associator.executeAssociation();
		} 
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
