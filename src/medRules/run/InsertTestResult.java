package medRules.run;

import java.util.List;

import medRules.data.RetrieveTestData;
import medRules.data.UpdateTestResultData;
import medRules.test.TestResult;

public class InsertTestResult {
	
	public static void main(String[] args) {
		
		RetrieveTestData rt = new RetrieveTestData();
		
		List<TestResult> tr = rt.getTestResults();
		TestResult updatedTestResult = null;
		
		for(int i = 0; i < tr.size(); i++) {
			
			if(tr.get(i).getTest().getTestName().equalsIgnoreCase("Fasting plasma glucose")) {
				tr.get(i).setAmount(130);
				updatedTestResult = tr.get(i);
				break;
			}
		}
		
		UpdateTestResultData update = new UpdateTestResultData();
		update.updateTestResultRecord(updatedTestResult);
	}

}
