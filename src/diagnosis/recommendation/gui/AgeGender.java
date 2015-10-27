package diagnosis.recommendation.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import medRules.data.RetrieveTestData;
import medRules.data.UpdateTestResultData;
import medRules.test.Disease;
import medRules.test.DiseaseTestRules;
import medRules.test.Test;
import medRules.test.TestResult;

public class AgeGender implements ActionListener{

	private JFrame frmEnterYourAge;
	private JTextField textFieldAge;
	private JLabel lblEnterAge;
	private JTextField textFieldGender;
	private JLabel lblEnterGender;
	private JButton btnOk;
	private static AgeGender INSTANCE = null;
	private String disease;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgeGender window = new AgeGender();
					window.frmEnterYourAge.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private AgeGender() {
		initialize();
	}
	
	public static AgeGender getInstance() {
		
		if(INSTANCE == null) {
			INSTANCE = new AgeGender();
		}
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmEnterYourAge = new JFrame();
		frmEnterYourAge.setTitle("Enter your Age & Gender");
		frmEnterYourAge.setBounds(100, 100, 450, 193);
		frmEnterYourAge.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEnterYourAge.getContentPane().setLayout(null);
		
		lblEnterAge = new JLabel("Enter Age:");
		lblEnterAge.setBounds(10, 11, 106, 14);
		frmEnterYourAge.getContentPane().add(lblEnterAge);
		
		textFieldAge = new JTextField();
		textFieldAge.setBounds(111, 8, 154, 20);
		frmEnterYourAge.getContentPane().add(textFieldAge);
		textFieldAge.setColumns(10);
		
		lblEnterGender = new JLabel("Enter Gender (0 - Male, 1 - Female):");
		lblEnterGender.setBounds(10, 50, 224, 14);
		frmEnterYourAge.getContentPane().add(lblEnterGender);
		
		textFieldGender = new JTextField();
		textFieldGender.setBounds(226, 47, 154, 20);
		frmEnterYourAge.getContentPane().add(textFieldGender);
		textFieldGender.setColumns(10);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(155, 94, 109, 39);
		frmEnterYourAge.getContentPane().add(btnOk);
		
		frmEnterYourAge.setVisible(true);
		btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnOk) {
			
			this.frmEnterYourAge.setVisible(false);
			RuleWindow ruleWindow = RuleWindow.getInstance();
			ruleWindow.getFrmRuleFiring().setVisible(false);
			DDR ddrWindow = DDR.getInstance();
			
			List<TestResult> results;
			List<Disease> diseases;
			List<Test> tests;
			
			List<TestResult> selectedResults = new LinkedList<TestResult>();

			RetrieveTestData data = new RetrieveTestData();
			
			diseases = data.getDiseases();
			tests = data.getTests();
			results = data.getTestResults();
			
			if(disease.contains("diabetes")) {
				
				for(int i = 0; i < results.size(); i++) {
					
					if(results.get(i).getDescription().equalsIgnoreCase("glucose - FPG")) {
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getSugarLevels().getText()));
					}
					
					else if(results.get(i).getDescription().equalsIgnoreCase("glucose - OGTT")) {
						selectedResults.add(results.get(i));	
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getSugarLevels1().getText()));
					}
				}
			}
			
			else if(disease.equalsIgnoreCase("hypertension")) {
				
				for(int i = 0; i < results.size(); i++) {
					
					if(results.get(i).getDescription().equalsIgnoreCase("systolic")){			
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getSystolicBP().getText()));
					}
					
					else if(results.get(i).getDescription().equalsIgnoreCase("diastolic")) {
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getDystolicBP().getText()));
					}
				}
			}
			
			else if(disease.equalsIgnoreCase("anemia")) {
				
				for(int i = 0; i < results.size(); i++) {
					
					if(results.get(i).getDescription().contains("hemoglobin")) {
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getHemoglobinLevels().getText()));
					}
					
					else if(results.get(i).getDescription().contains("RBC")) {
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getCBC().getText()));
					}
				}
			}
			
			else if(disease.equalsIgnoreCase("hypercalcemia")) {
				
				for(int i = 0; i < results.size(); i++) {
					
					if(results.get(i).getDescription().equalsIgnoreCase("calcium levels")) {
						
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getCalciumLevels().getText()));
					}
				}
			}
			
			else if(disease.equalsIgnoreCase("adult respiratory distress syndrome")) {
				
				for(int i = 0; i < results.size(); i++) {
					
					if(results.get(i).getDescription().equalsIgnoreCase("ABG levels")) {
						
						selectedResults.add(results.get(i));
						results.get(i).setAmount(Double.parseDouble(ruleWindow.getPaO2Levels().getText()));
					}
				}
			}
			
			UpdateTestResultData update = new UpdateTestResultData();
			
			for(int i = 0; i < results.size(); i++) {
				
				update.updateTestResultRecord(results.get(i));
			}
			
			DiseaseTestRules dtRules = new DiseaseTestRules();
			
			dtRules.fireDiseaseTestRules(diseases, tests, results);
			
			UpdateTestResultData update1 = new UpdateTestResultData();
			
			for(int i = 0; i < results.size(); i++) {
				
				update1.updateTestResultRecord(results.get(i));
			}
			
			for(int i = 0; i < selectedResults.size(); i++) {
				
				ddrWindow.printToDiagLog("Test Description: " + selectedResults.get(i).getDescription() + "\n");
				ddrWindow.printToDiagLog("Test Result: " + selectedResults.get(i).getResult() + "\n\n");
				
				if(!selectedResults.get(i).isDiagnosis() && selectedResults.get(i).isNormal()) {
					
					ddrWindow.getPossibleDiseasesmodel().removeElement(disease);
				}
	
			}
			
		}
	}

	public void clearTextFields() {
	
		textFieldAge = null;
		textFieldGender = null;
	}
	
	public JTextField getTextFieldAge() {
		return textFieldAge;
	}

	public JTextField getTextFieldGender() {
		return textFieldGender;
	}

	public JFrame getFrmEnterYourAge() {
		return frmEnterYourAge;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}
	
}
