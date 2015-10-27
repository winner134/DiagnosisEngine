package diagnosis.recommendation.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import medRules.data.RetrieveTestData;
import medRules.data.UpdateTestResultData;
import medRules.test.Disease;
import medRules.test.DiseaseTestRules;
import medRules.test.Test;
import medRules.test.TestResult;


public class RuleWindow implements ActionListener{

	private JFrame frmRuleFiring;
	private JButton buttonGoBack;
	private JButton btnNarrowDiagnosis;
	private String disease = null;
	private JTextField sugarLevels;
	private JTextField sugarLevels1;
	private JTextField systolicBP;
	private JTextField dystolicBP;
	private JTextField PaO2Levels;
	private JTextField CBC;
	private JTextField hemoglobinLevels;
	private JTextField calciumLevels;
	private JLabel lblFPG;
	private JLabel lblUnit;
	private JLabel lblOr;
	private JLabel lblOGTT;
	private JLabel lblUnit1;
	private JLabel lblCBC;
	private JLabel lblUnit2;
	private JLabel lblAnd;
	private JLabel lblHemoglobin;
	private JLabel lblBP;
	private JLabel lblUnit3;
	private JLabel lblBP1;
	private JLabel lblUnit4;
	private JLabel lblCalcium;
	private JLabel lblUnit5;
	private JLabel lblABGT;
	private DDR window = DDR.getInstance();
	private static RuleWindow INSTANCE = null;
	
	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RuleWindow window = new RuleWindow();
					window.frmRuleFiring.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private RuleWindow() {
		initialize();
	}
	
	public static RuleWindow getInstance() {
		
		if(INSTANCE == null) {
			INSTANCE = new RuleWindow();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmRuleFiring = new JFrame();
		frmRuleFiring.setTitle("Diagnosis Rules Requiring Test Data to Fire (Clinical Pathways)");
		frmRuleFiring.setBounds(100, 100, 784, 363);
		frmRuleFiring.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRuleFiring.getContentPane().setLayout(null);
		
		buttonGoBack = new JButton("<< Go Back");
		buttonGoBack.setBounds(217, 291, 116, 23);
		frmRuleFiring.getContentPane().add(buttonGoBack);
		
		btnNarrowDiagnosis = new JButton("Narrow Diagnosis");
		btnNarrowDiagnosis.setBounds(343, 291, 149, 23);
		frmRuleFiring.getContentPane().add(btnNarrowDiagnosis);
		
		frmRuleFiring.setVisible(true);
		buttonGoBack.addActionListener(this);
		btnNarrowDiagnosis.addActionListener(this);
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public JFrame getFrmRuleFiring() {
		return frmRuleFiring;
	}
	
	public void updateView(String selectedDisease) {
		
		if(disease != null) {
		
			cleanWindow();
		}
		
		this.setDisease(selectedDisease);
		
		if(disease.equalsIgnoreCase("diabetes mellitus type 1") || disease.equalsIgnoreCase("diabetes mellitus type 2")) {	
			
			lblFPG = new JLabel("If a fasting plasma glucose test is perfomed to diagnos Diabetes, then what are the indicated glucose levels?");
			lblFPG.setBounds(10, 11, 730, 23);
			frmRuleFiring.getContentPane().add(lblFPG);	
			sugarLevels = new JTextField();
			sugarLevels.setBounds(10, 39, 169, 20);
			frmRuleFiring.getContentPane().add(sugarLevels);
			sugarLevels.setColumns(10);
			lblUnit = new JLabel("mg/dL");
			lblUnit.setBounds(189, 42, 58, 14);
			frmRuleFiring.getContentPane().add(lblUnit);
			
			lblOr = new JLabel("OR");
			lblOr.setFont(new Font("Arial", Font.BOLD, 18));
			lblOr.setBounds(344, 42, 70, 14);
			frmRuleFiring.getContentPane().add(lblOr);
			
			lblOGTT = new JLabel("If an oral glucose tolerance test is perfomed to diagnos Diabetes, then what are the indicated glucose levels?");
			lblOGTT.setBounds(10, 70, 682, 14);
			frmRuleFiring.getContentPane().add(lblOGTT);
			sugarLevels1 = new JTextField();
			sugarLevels1.setColumns(10);
			sugarLevels1.setBounds(10, 90, 169, 20);
			frmRuleFiring.getContentPane().add(sugarLevels1);
			lblUnit1 = new JLabel("mg/dL");
			lblUnit1.setBounds(189, 93, 58, 14);
			frmRuleFiring.getContentPane().add(lblUnit1);
		}
		
		else if(disease.equalsIgnoreCase("anemia")) {
			
			lblCBC = new JLabel("If a CBC (Complete Blood Count) test is performed to diagnos Anemia, then what is the percentage of red blood cells in the blood?");
			lblCBC.setBounds(10, 11, 730, 23);
			CBC = new JTextField();
			CBC.setColumns(10);
			CBC.setBounds(10, 39, 169, 20);
			lblUnit2 = new JLabel("%");
			lblUnit2.setBounds(189, 42, 58, 14);
			frmRuleFiring.getContentPane().add(lblCBC);
			frmRuleFiring.getContentPane().add(CBC);
			frmRuleFiring.getContentPane().add(lblUnit2);
			
			lblAnd = new JLabel("AND");
			lblAnd.setFont(new Font("Arial", Font.BOLD, 18));
			lblAnd.setBounds(344, 42, 70, 14);
			frmRuleFiring.getContentPane().add(lblAnd);
			
			lblHemoglobin = new JLabel("If a CBC (Complete Blood Count) test is performed to diagnos Anemia, the what is the hemoglobin level in the blood?");
			lblHemoglobin.setBounds(10, 70, 682, 14);
			hemoglobinLevels = new JTextField();
			hemoglobinLevels.setColumns(10);
			hemoglobinLevels.setBounds(10, 90, 169, 20);
			lblUnit1 = new JLabel("mg/dL");
			lblUnit1.setBounds(189, 93, 58, 14);
			frmRuleFiring.getContentPane().add(lblHemoglobin);
			frmRuleFiring.getContentPane().add(hemoglobinLevels);
			frmRuleFiring.getContentPane().add(lblUnit1);
			
		}
		
		else if(disease.equalsIgnoreCase("asthma")) {
			
		}
		
		else if(disease.equalsIgnoreCase("hypertension")) {
			
			lblBP = new JLabel("If a blood pressure test is performed to diagnos hypertension, then what is the indicated systolic blood pressure?");
			lblBP.setBounds(10, 11, 730, 23);
			systolicBP = new JTextField();
			systolicBP.setColumns(10);
			systolicBP.setBounds(10, 39, 169, 20);
			lblUnit3 = new JLabel("mmHg");
			lblUnit3.setBounds(189, 42, 58, 14);
			frmRuleFiring.getContentPane().add(lblBP);
			frmRuleFiring.getContentPane().add(systolicBP);
			frmRuleFiring.getContentPane().add(lblUnit3);
				
			lblAnd = new JLabel("AND");
			lblAnd.setFont(new Font("Arial", Font.BOLD, 18));
			lblAnd.setBounds(344, 42, 70, 14);
			frmRuleFiring.getContentPane().add(lblAnd);
			
			lblBP1 = new JLabel("If a blood pressure test is performed to diagnos hypertension, then what is the diastolic blood pressure?");
			lblBP1.setBounds(10, 70, 682, 14);
			dystolicBP = new JTextField();
			dystolicBP.setColumns(10);
			dystolicBP.setBounds(10, 90, 169, 20);
			lblUnit4 = new JLabel("mmHg");
			lblUnit4.setBounds(189, 93, 58, 14);
			frmRuleFiring.getContentPane().add(lblBP1);
			frmRuleFiring.getContentPane().add(dystolicBP);
			frmRuleFiring.getContentPane().add(lblUnit4);
		}
		
		else if(disease.equalsIgnoreCase("renal failure")) {
			
		}
		
		else if(disease.equalsIgnoreCase("hypercalcemia")) {
			
			lblCalcium = new JLabel("If a blood test is performed to diagnos calcemia, then what is the calcium level in the blood?");
			lblCalcium.setBounds(10, 11, 730, 23);
			calciumLevels = new JTextField();
			calciumLevels.setColumns(10);
			calciumLevels.setBounds(10, 39, 169, 20);
			lblUnit3 = new JLabel("mmHg");
			lblUnit3.setBounds(189, 42, 58, 14);
			frmRuleFiring.getContentPane().add(lblCalcium);
			frmRuleFiring.getContentPane().add(calciumLevels);
			frmRuleFiring.getContentPane().add(lblUnit3);
		}
		
		else if(disease.equalsIgnoreCase("adult respiratory distress syndrome")) {
			
			lblABGT = new JLabel("If an arterial blood gas test is performed to diagnos adult respiratory distress syndrome, then what is the PaO2 level?");
			lblABGT.setBounds(10, 11, 730, 23);
			PaO2Levels = new JTextField();
			PaO2Levels.setColumns(10);
			PaO2Levels.setBounds(10, 39, 169, 20);
			lblUnit5 = new JLabel("PaO2");
			lblUnit5.setBounds(189, 42, 58, 14);
			frmRuleFiring.getContentPane().add(lblABGT);
			frmRuleFiring.getContentPane().add(PaO2Levels);
			frmRuleFiring.getContentPane().add(lblUnit5);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnNarrowDiagnosis) {
					
			AgeGender ag = AgeGender.getInstance();
			ag.getFrmEnterYourAge().setVisible(true);
			ag.setDisease(disease);
			//ag.clearTextFields();
			window.setAg(ag);
		}
		
		else if(e.getSource() == buttonGoBack) {
			
			window.getFrmDdMode().setVisible(true);
			frmRuleFiring.setVisible(false);
		}
	}
	
	/*public void clearTextFields() {
		
		sugarLevels.setText(null);
		sugarLevels1.setText(null);
		systolicBP.setText(null);
		dystolicBP.setText(null);
		PaO2Levels.setText(null);
		CBC.setText(null);
		hemoglobinLevels.setText(null);
		calciumLevels.setText(null);
	}*/
	
	private void cleanWindow() {
		
		if(disease.equalsIgnoreCase("diabetes mellitus type 1") || disease.equalsIgnoreCase("diabetes mellitus type 2")) {	
			
			frmRuleFiring.getContentPane().remove(lblFPG);
			frmRuleFiring.getContentPane().remove(sugarLevels);
			frmRuleFiring.getContentPane().remove(lblUnit);
			frmRuleFiring.getContentPane().remove(lblOr);
			frmRuleFiring.getContentPane().remove(lblOGTT);
			frmRuleFiring.getContentPane().remove(sugarLevels1);
			frmRuleFiring.getContentPane().remove(lblUnit1);
		}
		
		else if(disease.equalsIgnoreCase("anemia")) {
			
			frmRuleFiring.getContentPane().remove(lblCBC);
			frmRuleFiring.getContentPane().remove(CBC);
			frmRuleFiring.getContentPane().remove(lblUnit2);
			frmRuleFiring.getContentPane().remove(lblAnd);
			frmRuleFiring.getContentPane().remove(lblHemoglobin);
			frmRuleFiring.getContentPane().remove(lblUnit1);
		}
		
		else if(disease.equalsIgnoreCase("asthma")) {
			
		}
		
		else if(disease.equalsIgnoreCase("hypertension")) {
			
			frmRuleFiring.getContentPane().remove(lblBP);
			frmRuleFiring.getContentPane().remove(systolicBP);
			frmRuleFiring.getContentPane().remove(lblUnit3);
			frmRuleFiring.getContentPane().remove(lblAnd);
			frmRuleFiring.getContentPane().remove(lblBP1);
			frmRuleFiring.getContentPane().remove(dystolicBP);
			frmRuleFiring.getContentPane().remove(lblUnit4);
		}
		
		else if(disease.equalsIgnoreCase("renal failure")) {
			
			
		}
		
		else if(disease.equalsIgnoreCase("hypercalcemia")) {
			
			frmRuleFiring.getContentPane().remove(lblCalcium);
			frmRuleFiring.getContentPane().remove(calciumLevels);
			frmRuleFiring.getContentPane().remove(lblUnit3);
		}
		
		else if(disease.equalsIgnoreCase("adult respiratory distress syndrome")) {
			
			frmRuleFiring.getContentPane().remove(lblABGT);
			frmRuleFiring.getContentPane().remove(PaO2Levels);
			frmRuleFiring.getContentPane().remove(lblUnit5);
		}
	}

	public JTextField getSugarLevels() {
		return sugarLevels;
	}

	public JTextField getSugarLevels1() {
		return sugarLevels1;
	}

	public JTextField getSystolicBP() {
		return systolicBP;
	}

	public JTextField getDystolicBP() {
		return dystolicBP;
	}

	public JTextField getPaO2Levels() {
		return PaO2Levels;
	}

	public JTextField getCBC() {
		return CBC;
	}

	public JTextField getHemoglobinLevels() {
		return hemoglobinLevels;
	}

	public JTextField getCalciumLevels() {
		return calciumLevels;
	}

}

