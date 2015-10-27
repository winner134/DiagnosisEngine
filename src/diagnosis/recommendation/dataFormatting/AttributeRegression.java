package diagnosis.recommendation.dataFormatting;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import flanagan.analysis.Regression;


public class AttributeRegression {

	private static FileReader v1_File;
	private static BufferedReader attribute1Reader;
	private static FileReader v2_File;
	private static BufferedReader attribute2Reader;
	private final static String ATTR_BEGIN = "@attribute";
	private final static String DATA_BEGIN = "@data";
	private final static String REAL_VAR = "real";
	private final static String NOMINAL_VAR = "{";
	private final static String NUMERIC_VAR = "numeric";
	private final static int REAL = 1;
	private final static int NOMINAL = 2;
	private final static int NUMERIC = 3;
	private static LinkedList<Double> attribute1;
	private static LinkedList<Double> attribute2;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 4) {
			
			computeTwoAttributeLinearRegression(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		}
	}
	
	public static void computeTwoAttributeLinearRegression(String filePathAttr1, String filePathAttr2, int columnIndexAttr1, int columnIndexAttr2) {
		
		try{
			
			v1_File = new FileReader(filePathAttr1);
			attribute1Reader = new BufferedReader(v1_File);
			v2_File = new FileReader(filePathAttr2);
			attribute2Reader = new BufferedReader(v2_File);
			attribute1 = new LinkedList<Double>();
			attribute2 = new LinkedList<Double>();
			
			String attr1 = attribute1Reader.readLine();
			
			while(!attr1.contains(ATTR_BEGIN)) {
				
				attr1 = attribute1Reader.readLine();
			}
			
			String attr2 = attribute2Reader.readLine();
			
			while(!attr2.contains(ATTR_BEGIN)) {
				
				attr2 = attribute2Reader.readLine();
			}
			
			int attr1Type;
			if(columnIndexAttr1 == 1) {
				
				attr1Type = getAttrType(attr1);
			}
			
			else {
				
				for(int i = 1; i < columnIndexAttr1; i++) {
					
					attr1 = attribute1Reader.readLine();
				}
				
				attr1Type = getAttrType(attr1);
			}
			
			int attr2Type;
			if(columnIndexAttr2 == 1) {
				
				attr2Type = getAttrType(attr2);
			}
			
			else {
				
				for(int i = 1; i < columnIndexAttr2; i++) {
					
					attr2 = attribute2Reader.readLine();
				}
				
				attr2Type = getAttrType(attr2);
			}
			
				
			while(!attr1.equalsIgnoreCase(DATA_BEGIN)) {
				attr1 = attribute1Reader.readLine();
			}
				
			attr1 = attribute1Reader.readLine();
				
			while(attr1 != null) {
					
				String[] readAttr1 = attr1.split(",");
				attribute1.add(Double.parseDouble(readAttr1[columnIndexAttr1-1]));
				attr1 = attribute1Reader.readLine();
			}
			
				
			while(!attr2.equalsIgnoreCase(DATA_BEGIN)) {
				attr2 = attribute2Reader.readLine();
			}
				
			attr2 = attribute2Reader.readLine();
				
			while(attr2 != null) {
					
				String[] readAttr2 = attr2.split(",");
				attribute2.add(Double.parseDouble(readAttr2[columnIndexAttr2-1]));
				attr2 = attribute2Reader.readLine();
			}
			
			DoubleListToArray converter = new DoubleListToArray();
			
			double[] varX = converter.convertToDoubleArray(attribute1);
			double[] varY = converter.convertToDoubleArray(attribute2);
			
			double phiCoeff = calculatePhiCoefficient(varX, varY);
			double pearsonCoeff = calculatePearsonCoefficient(varX, varY);
			
			System.out.println("Phi Correlation Coefficient = " + phiCoeff);
			System.out.println("Pearson Correlation Coefficient = " + pearsonCoeff);
			
			Regression reg = new Regression(varX, varY);
			reg.linearPlot();
		}
		
		catch(IOException e) {
			
		}
		
		catch(Exception e1) {
			
		}
	}
	
	private static int getAttrType(String attr) {
		
		if(attr.contains(REAL_VAR))
			return REAL;
		else if(attr.contains(NUMERIC_VAR))
			return NUMERIC;
		else if(attr.contains(NOMINAL_VAR))
			return NOMINAL;
		else
			return -1;
	}
	
	public static double calculatePhiCoefficient(double[] varX, double[] varY) {
		
		double num10 = 0.0;
		double num11 = 0.0;
		double num01 = 0.0;
		double num00 = 0.0;
		double num0 = 0.0;
		double num1 = 0.0;
		double numY0 = 0.0;
		double numY1 = 0.0;
		
		for(int i = 0; i < varX.length; i++) {
			
			if(varX[i] == 0.0 && varY[i] == 0.0)
				num00 = num00 + 1.0;
			else if(varX[i] == 0.0 && varY[i] == 1.0)
				num01 = num01 + 1.0;
			else if(varX[i] == 1.0 && varY[i] == 0.0)
				num10 = num10 + 1.0;
			else if(varX[i] == 1.0 && varY[i] == 1.0)
				num11 = num11 + 1.0;
			
			if(varX[i] == 0.0)
				num0 = num0 + 1.0;
			else if(varX[i] == 1.0)
				num1 = num1 + 1.0;
			
			if(varY[i] == 0.0)
				numY0 = numY0 + 1.0;
			else if(varY[i] == 1.0)
				numY1 = numY1 + 1.0;
		}
		
		return ((num11*num00) - (num10 * num01))/(Math.sqrt(num1*num0*numY0*numY1));
	}
	
	public static double calculatePearsonCoefficient(double[] varX, double[] varY) {
		
		double sumXY = 0.0;
		double sumX = 0.0;
		double sumY = 0.0;
		double sumXSquared = 0.0;
		double sumYSquared = 0.0;
		int n = varX.length;
		
		for(int i = 0; i < n; i++) {
			
			sumX = sumX + varX[i];
			sumY = sumY + varY[i];
			sumXY = sumXY + (varX[i] * varY[i]);
			sumXSquared = sumXSquared + (varX[i] * varX[i]);
			sumYSquared = sumYSquared + (varY[i] * varY[i]);
		}
		
		return (((n*sumXY) - (sumX * sumY)) / (Math.sqrt((n*sumXSquared) - (sumX * sumX)) * Math.sqrt((n*sumYSquared) - (sumY * sumY))));
	}

}
