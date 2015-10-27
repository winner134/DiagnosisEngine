package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JTextField;
import javax.swing.JButton;

import weka.classifiers.Classifier;

import diagnosis.recommendation.dataFormatting.TestRecordWriter;
import diagnosis.recommendation.ontology.dataMining.ClassificationAssociation;

public class NewTestData implements ActionListener{

	private JFrame frmNewTestRecord;
	private JTextField textFieldAge;
	private JTextField textFieldGender;
	private JTextField textFieldRBC;
	private JTextField textFieldHemoglobin;
	private JButton btnGoBack;
	private JButton btnAnotherRecord;
	private JButton btnProceed;
	private JLabel lblAge;
	private JLabel lblGender;
	private JLabel lblRBC;
	private JLabel lblEnterHemoglobin;
	private JLabel lblNewLabel;
	private JLabel lblNewCalcemiaTest;
	private JLabel lblCalcium;
	private JTextField textFieldCalcium;
	private JLabel lblDiabetes;
	private JLabel lblFPG;
	private JTextField textFieldFPG;
	private JLabel lblHypertension;
	private JLabel lblSystolic;
	private JTextField textFieldSystolic;
	private JLabel lblDiastolic;
	private JTextField textFieldDiastolic;
	private static NewTestData INSTANCE = null;
	private FileWriter attribute;
	private BufferedWriter attributeWriter;
	private final int J48  = 1;
	private final int JRIP = 2;
	private final int NB = 3;
	private final int NBTree = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewTestData window = new NewTestData();
					window.frmNewTestRecord.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private NewTestData() {
		initialize();
	}
	
	public static NewTestData getInstance() {
		
		if(INSTANCE == null)
			INSTANCE = new NewTestData();
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNewTestRecord = new JFrame();
		frmNewTestRecord.setTitle("New Test Record Form");
		frmNewTestRecord.setBounds(100, 100, 536, 372);
		frmNewTestRecord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNewTestRecord.getContentPane().setLayout(null);
		
		btnGoBack = new JButton("<< Go Back");
		btnGoBack.setBounds(10, 286, 138, 37);
		frmNewTestRecord.getContentPane().add(btnGoBack);
		
		btnAnotherRecord = new JButton("Enter Another Record");
		btnAnotherRecord.setBounds(162, 286, 194, 37);
		frmNewTestRecord.getContentPane().add(btnAnotherRecord);
		
		btnProceed = new JButton("Proceed >>");
		btnProceed.setBounds(366, 286, 149, 37);
		frmNewTestRecord.getContentPane().add(btnProceed);
		
		frmNewTestRecord.setVisible(true);
		btnGoBack.addActionListener(this);
		btnAnotherRecord.addActionListener(this);
		btnProceed.addActionListener(this);
		
	}
	
	public void anemiaTestRecordSetup() {
		
		lblNewLabel = new JLabel("New Anemia Test Record:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 11, 215, 14);
		frmNewTestRecord.getContentPane().add(lblNewLabel);
		
		setupAgeGender();

		lblRBC = new JLabel("Enter rbc (percentage of red blood cells in the blood): ");
		lblRBC.setBounds(10, 106, 329, 14);
		frmNewTestRecord.getContentPane().add(lblRBC);
		
		textFieldRBC = new JTextField();
		textFieldRBC.setColumns(10);
		textFieldRBC.setBounds(318, 103, 164, 20);
		frmNewTestRecord.getContentPane().add(textFieldRBC);
		
		lblEnterHemoglobin = new JLabel("Enter hemoglobin levels (mg/dL):");
		lblEnterHemoglobin.setBounds(10, 134, 223, 14);
		frmNewTestRecord.getContentPane().add(lblEnterHemoglobin);
		
		textFieldHemoglobin = new JTextField();
		textFieldHemoglobin.setColumns(10);
		textFieldHemoglobin.setBounds(220, 131, 164, 20);
		frmNewTestRecord.getContentPane().add(textFieldHemoglobin);
	}
	
	public void setupAgeGender() {
		
		lblAge = new JLabel("Enter Age:");
		lblAge.setBounds(10, 36, 80, 14);
		frmNewTestRecord.getContentPane().add(lblAge);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(71, 33, 164, 20);
		frmNewTestRecord.getContentPane().add(textFieldAge);
		textFieldAge.setColumns(10);
		
		lblGender = new JLabel("Enter Gender (0 - Male, 1- Female): ");
		lblGender.setBounds(10, 73, 211, 14);
		frmNewTestRecord.getContentPane().add(lblGender);
		
		textFieldGender = new JTextField();
		textFieldGender.setColumns(10);
		textFieldGender.setBounds(213, 70, 164, 20);
		frmNewTestRecord.getContentPane().add(textFieldGender);
	}
	
	public void destroyAgeGender() {
		
		frmNewTestRecord.getContentPane().remove(lblAge);
		frmNewTestRecord.getContentPane().remove(lblGender);
		frmNewTestRecord.getContentPane().remove(textFieldAge);
		frmNewTestRecord.getContentPane().remove(textFieldGender);
	}
	
	public void destroyAnemiaSetup() {
		
		frmNewTestRecord.getContentPane().remove(lblNewLabel);
		frmNewTestRecord.getContentPane().remove(lblRBC);
		frmNewTestRecord.getContentPane().remove(lblEnterHemoglobin);
		frmNewTestRecord.getContentPane().remove(textFieldHemoglobin);
		frmNewTestRecord.getContentPane().remove(textFieldRBC);
		
		destroyAgeGender();
	}
	
	public void calcemiaTestRecordSetup() {
		
		lblNewCalcemiaTest = new JLabel("New Calcemia Test Record:");
		lblNewCalcemiaTest.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewCalcemiaTest.setBounds(10, 11, 207, 14);
		frmNewTestRecord.getContentPane().add(lblNewCalcemiaTest);
		
		setupAgeGender();	

		lblCalcium = new JLabel("Enter Calcium Levels (mg/dL): ");
		lblCalcium.setBounds(10, 100, 205, 14);
		frmNewTestRecord.getContentPane().add(lblCalcium);
		
		textFieldCalcium = new JTextField();
		textFieldCalcium.setBounds(191, 97, 172, 20);
		frmNewTestRecord.getContentPane().add(textFieldCalcium);
		textFieldCalcium.setColumns(10);
	}
	
	public void destroyCalcemiaSetup() {
		
		destroyAgeGender();
		frmNewTestRecord.getContentPane().remove(lblNewCalcemiaTest);
		frmNewTestRecord.getContentPane().remove(lblCalcium);
		frmNewTestRecord.getContentPane().remove(textFieldCalcium);
	}
	
	public void diabetesTestRecordSetup() {
		
		lblDiabetes = new JLabel("New Diabetes Test Record");
		lblDiabetes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDiabetes.setBounds(10, 11, 215, 14);
		frmNewTestRecord.getContentPane().add(lblDiabetes);
		
		setupAgeGender();
		
		lblFPG = new JLabel("Enter Fasting Plasma Glucose Levels (mg/dL):");
		lblFPG.setBounds(10, 113, 281, 14);
		frmNewTestRecord.getContentPane().add(lblFPG);
		
		textFieldFPG = new JTextField();
		textFieldFPG.setBounds(271, 110, 176, 20);
		frmNewTestRecord.getContentPane().add(textFieldFPG);
		textFieldFPG.setColumns(10);
	
	}
	
	public void destroyDiabetesSetup() {
		
		destroyAgeGender();
		frmNewTestRecord.getContentPane().remove(lblDiabetes);
		frmNewTestRecord.getContentPane().remove(lblFPG);
		frmNewTestRecord.getContentPane().remove(textFieldFPG);
	}
	
	public void hypertensionTestRecordSetup() {

		lblHypertension = new JLabel("New Hypertension/Hypotension Test Record:");
		lblHypertension.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHypertension.setBounds(10, 11, 320, 14);
		frmNewTestRecord.getContentPane().add(lblHypertension);
		
		setupAgeGender();
		
		lblSystolic = new JLabel("Enter Systolic Blood Pressure:");
		lblSystolic.setBounds(10, 117, 189, 14);
		frmNewTestRecord.getContentPane().add(lblSystolic);
		
		textFieldSystolic = new JTextField();
		textFieldSystolic.setBounds(190, 114, 177, 20);
		frmNewTestRecord.getContentPane().add(textFieldSystolic);
		textFieldSystolic.setColumns(10);
		
		lblDiastolic = new JLabel("Enter Diastolic Blood Pressure:");
		lblDiastolic.setBounds(10, 157, 177, 14);
		frmNewTestRecord.getContentPane().add(lblDiastolic);
		
		textFieldDiastolic = new JTextField();
		textFieldDiastolic.setBounds(190, 154, 177, 20);
		frmNewTestRecord.getContentPane().add(textFieldDiastolic);
		textFieldDiastolic.setColumns(10);
	}
	
	public void destroyHypertensionSetup() {
		
		destroyAgeGender();
		frmNewTestRecord.getContentPane().remove(lblHypertension);
		frmNewTestRecord.getContentPane().remove(lblSystolic);
		frmNewTestRecord.getContentPane().remove(textFieldSystolic);
		frmNewTestRecord.getContentPane().remove(lblDiastolic);
		frmNewTestRecord.getContentPane().remove(textFieldDiastolic);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == btnAnotherRecord) {
	
			updateTestRecords();
			refreshTestRecordScreen();			
		}
		
		else if(e.getSource() == btnGoBack) {
			
			TestDataOption option = TestDataOption.getInstance();
			option.getFrmTestFileOptions().setVisible(true);
			this.getFrmNewTestRecord().setVisible(false);
			destroyTestRecordGUISetup();
		}
		
		else if(e.getSource() == btnProceed) {
			
			updateTestRecords();
			DMResults results = DMResults.getInstance();
			results.getFrmOntologyDrivenData().setVisible(true);
			this.getFrmNewTestRecord().setVisible(false);
			
			classifyTestData();
		}
	}
	
	public void classifyTestData() {
		
		ClassificationAssociation classifier = new ClassificationAssociation();
		
		D1Algorithm alg = D1Algorithm.getInstance();
		int selection = alg.getSelectedAlgorithm();
		
		try{
			if(selection == J48) {
				
				classifier.setClassifier(1);
				setTrainingFilePath(classifier);
				setResultsPathExecute(classifier);
			}
			
			else if(selection ==  JRIP) {
				
				classifier.setClassifier(2);
				setTrainingFilePath(classifier);
				setResultsPathExecute(classifier);
			}
			
			else if(selection == NB) {
				
				classifier.setClassifier(3);
				setTrainingFilePath(classifier);
				setResultsPathExecute(classifier);
			}
			
			else if(selection == NBTree) {
				
				classifier.setClassifier(4);
				setTrainingFilePath(classifier);
				setResultsPathExecute(classifier);
			}
		}
		
		catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	protected void setTrainingFilePath(ClassificationAssociation classifier) {
		
		TestDataSelection selection = TestDataSelection.getInstance();
		
		try {
			if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
				
				classifier.setTraining("C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
				
				classifier.setTraining("C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {
	
				classifier.setTraining("C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
				
				classifier.setTraining("C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3.arff");
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void setResultsPathExecute(ClassificationAssociation classifier) {
		
		TestDataSelection selection = TestDataSelection.getInstance();
		LinkedList<Double> predictions = new LinkedList<Double>();
		TestRecordWriter writer = new TestRecordWriter();
		DMResults results = DMResults.getInstance();
		
		try {
			
			if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
				
				predictions = classifier.classifyTestData("C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2_test.arff");

				writer.writePredictionsToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2_test.arff", "C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2_prediction.arff", predictions);
				
				results.printToTextArea("See classification results for the test records in file: C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2_prediction.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
				
				predictions = classifier.classifyTestData("C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia_test.arff");
				
				writer.writePredictionsToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia_test.arff", "C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia_prediction.arff", predictions);
				
				results.printToTextArea("See classification results for the test records in file C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia_prediction.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {
	
				predictions = classifier.classifyTestData("C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2_test.arff");

				writer.writePredictionsToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2_test.arff", "C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2_prediction.arff", predictions);
				
				results.printToTextArea("See classification results for the test records in file C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2_prediction.arff");
			}
			
			else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
				
				predictions = classifier.classifyTestData("C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3_test.arff");
				
				writer.writePredictionsToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3_test.arff", "C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3_prediction.arff", predictions);
				
				results.printToTextArea("See classification results for the test records in file C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3_prediction.arff");
			}
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateTestRecords() {
		
		TestDataSelection selection = TestDataSelection.getInstance();
		
		if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
			
			writeRecordToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2_test.arff", textFieldFPG.getText(), null);
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
			
			writeRecordToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia_test.arff", textFieldCalcium.getText(), null);
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {

			writeRecordToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2_test.arff", textFieldRBC.getText(), textFieldHemoglobin.getText());
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
			
			writeRecordToFile("C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3_test.arff", textFieldSystolic.getText(), textFieldDiastolic.getText());
		}
	}
	
	private void destroyTestRecordGUISetup() {
	
		TestDataSelection selection = TestDataSelection.getInstance();
		
		if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
			
			this.destroyDiabetesSetup();
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
			
			this.destroyCalcemiaSetup();
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {

			this.destroyAnemiaSetup();
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
			
			this.destroyHypertensionSetup();
		}
	}
	
	private void writeRecordToFile(String filePath, String testData1, String testData2) {
		
		try {
			attribute = new FileWriter(filePath, true);
			attributeWriter = new BufferedWriter(attribute);
			
			attributeWriter.append(textFieldAge.getText() + ".0, " + "0.0" + ", " + textFieldGender.getText() + ".0"); 
			
			if(testData1 != null) {
				
				attributeWriter.append(", " + testData1);
				
				if(testData2 != null)		
					attributeWriter.append(", " + testData2 + ", ?");
				else
					attributeWriter.append(", ?");
			}
			
			else
				attributeWriter.append(", ?");
			
			attributeWriter.newLine();
			attributeWriter.close();
			
		} 
		
		catch (IOException e) {

			e.printStackTrace();
		}	
	}
	
	private void refreshTestRecordScreen() {
		
		TestDataSelection selection = TestDataSelection.getInstance();
		textFieldAge.setText(null);
		textFieldGender.setText(null);
		
		if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
			
			textFieldFPG.setText(null);
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
			
			textFieldCalcium.setText(null);
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {
			
			textFieldRBC.setText(null);
			textFieldHemoglobin.setText(null);
		}
		
		else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
			
			textFieldSystolic.setText(null);
			textFieldDiastolic.setText(null);
		}
	}

	public JFrame getFrmNewTestRecord() {
		return frmNewTestRecord;
	}
	
}
