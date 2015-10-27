package diagnosis.recommendation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

import com.hp.hpl.jena.ontology.OntModel;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;

public class DDR implements ActionListener{

	private static DDR instance; 
	private JFrame frmDdMode;
	private JLabel lblSymptoms;
	private JLabel lblResults;
	private JButton btnPossibleDiagnosis;
	private JButton btnRules;
	private JLabel lblDiagnosisRecommendation;
	private JTextArea textAreaDiagLog;
	private JButton buttonBack;
	private JList symptomsList;
	private JScrollPane scrollPaneSymptoms;
	private JScrollPane scrollPaneDiagnosis;
	private StringArrayListConverter converter;
	private JList list;
	private JScrollPane scrollPanePossibleDiagnosis;
	private DefaultListModel model;
	private DefaultListModel possibleDiseasesmodel;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private FileWriter attribute;
	private BufferedWriter attributeWriter;
	private AgeGender ag;
	
	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DDR window = new DDR();
					window.frmDdMode.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private DDR() {
		
		initialize();
		
		converter = StringArrayListConverter.getInstance();
	}
	
	public static DDR getInstance() {
		
		if(instance == null)
			instance = new DDR();
			
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmDdMode = new JFrame();
		frmDdMode.setTitle("DDx Rule Engine (Drools Expert/Apache Derby/Hibernate) Recommendation");
		frmDdMode.setBounds(100, 100, 715, 663);
		frmDdMode.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDdMode.getContentPane().setLayout(null);
		
		lblSymptoms = new JLabel("Select Symptoms");
		lblSymptoms.setBounds(10, 11, 120, 14);
		frmDdMode.getContentPane().add(lblSymptoms);
		
		lblResults = new JLabel("List of Possible Diagnoses");
		lblResults.setEnabled(false);
		lblResults.setBounds(280, 11, 160, 14);
		frmDdMode.getContentPane().add(lblResults);
		
		btnPossibleDiagnosis = new JButton("Show Possible Diagnosis");
		btnPossibleDiagnosis.setBounds(10, 234, 238, 37);
		frmDdMode.getContentPane().add(btnPossibleDiagnosis);
		
		btnRules = new JButton("Provide Patient Data for Selected Disease (data for rule-based firing)");
		btnRules.setEnabled(false);
		btnRules.setBounds(266, 254, 424, 37);
		frmDdMode.getContentPane().add(btnRules);
		
		lblDiagnosisRecommendation = new JLabel("Diagnosis Recommendation");
		lblDiagnosisRecommendation.setEnabled(false);
		lblDiagnosisRecommendation.setBounds(10, 312, 220, 14);
		frmDdMode.getContentPane().add(lblDiagnosisRecommendation);
		
		textAreaDiagLog = new JTextArea();
		textAreaDiagLog.setEnabled(false);
		textAreaDiagLog.setEditable(false);
		textAreaDiagLog.setBounds(10, 325, 489, 203);
		frmDdMode.getContentPane().add(textAreaDiagLog);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(286, 572, 104, 37);
		frmDdMode.getContentPane().add(buttonBack);
		
		symptomsList = new JList();
		symptomsList.setBounds(10, 25, 220, 182);
		frmDdMode.getContentPane().add(symptomsList);
		
		scrollPaneSymptoms = new JScrollPane(symptomsList);
		scrollPaneSymptoms.setBounds(10, 25, 238, 195);
		frmDdMode.getContentPane().add(scrollPaneSymptoms);
		
		list = new JList();
		list.setEnabled(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(280, 24, 292, 199);
		frmDdMode.getContentPane().add(list);
		
		scrollPaneDiagnosis = new JScrollPane(textAreaDiagLog);
		scrollPaneDiagnosis.setBounds(10, 326, 509, 219);
		frmDdMode.getContentPane().add(scrollPaneDiagnosis);
		
		scrollPanePossibleDiagnosis = new JScrollPane(list);
		scrollPanePossibleDiagnosis.setBounds(280, 23, 311, 220);
		frmDdMode.getContentPane().add(scrollPanePossibleDiagnosis);
		
		model = new DefaultListModel();
		possibleDiseasesmodel = new DefaultListModel();
		populateSymptomsList();
		
		btnPossibleDiagnosis.addActionListener(this);
		buttonBack.addActionListener(this);
		btnRules.addActionListener(this);
		
		frmDdMode.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnPossibleDiagnosis) {
			
			lblResults.setVisible(true);
			lblDiagnosisRecommendation.setEnabled(true);
			btnRules.setEnabled(true);
			textAreaDiagLog.setEnabled(true);
			list.setEnabled(true);
			
			possibleDiseasesmodel.removeAllElements();
			
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			List<String> diseases = null;
			List<String> symptomsSelection = converter.convertToList(selectedSymptoms);
			diseases = query.linkSymptomsToDiseases(System.out, ontModel, null, symptomsSelection);
			
			for(int i = 0; i < diseases.size(); i++) {
				
				possibleDiseasesmodel.addElement(diseases.get(i));
			}
			
			list.setModel(possibleDiseasesmodel);
		}
		
		else if(e.getSource() == btnRules) {
			
			RuleWindow window = RuleWindow.getInstance();
			window.getFrmRuleFiring().setVisible(true);
			window.updateView((String) list.getSelectedValue()); 
			//window.clearTextFields();
		}
		
		else if(e.getSource() == buttonBack) {
			
			DiagnosisGUI window = DiagnosisGUI.getInstance();
			window.getFrmSemanticDiagonsisRecommender().setVisible(true);
			frmDdMode.setVisible(false);
		}
	}
	
	private void populateSymptomsList() {
		
		model.addElement(new String("abdominal discomfort"));
		model.addElement(new String("abdominal pain"));
		model.addElement(new String("alteration of apetite"));
		model.addElement(new String("chest pain"));
		model.addElement(new String("confusion"));
		model.addElement(new String("constipation"));
		model.addElement(new String("cough"));
		model.addElement(new String("cyanosis"));
		model.addElement(new String("depression"));
		model.addElement(new String("diarrhea"));
		model.addElement(new String("dizziness"));
		model.addElement(new String("drowsiness"));
		model.addElement(new String("edema"));
		model.addElement(new String("excessive secretion of urine"));
		model.addElement(new String("fatigue"));
		model.addElement(new String("flushing"));
		model.addElement(new String("frequent urination"));
		model.addElement(new String("headache"));
		model.addElement(new String("hypoxemia"));
		model.addElement(new String("itching"));
		model.addElement(new String("jaundice"));
		model.addElement(new String("lethargy"));
		model.addElement(new String("muscle cramp"));
		model.addElement(new String("nausea"));
		model.addElement(new String("nocturia"));
		model.addElement(new String("nosebleed"));
		model.addElement(new String("objective vertigo"));
		model.addElement(new String("pain"));
		model.addElement(new String("paleness"));
		model.addElement(new String("painful urine discharge"));
		model.addElement(new String("palpitation"));
		model.addElement(new String("pulmonary edema"));
		model.addElement(new String("rapid breathing"));
		model.addElement(new String("shortness of breath"));
		model.addElement(new String("subjective vertigo"));
		model.addElement(new String("thirst"));
		model.addElement(new String("tachycardia"));
		model.addElement(new String("urgency of urination"));
		model.addElement(new String("urinary retention"));
		model.addElement(new String("vertigo"));
		model.addElement(new String("vomiting"));
		model.addElement(new String("weight loss"));
		model.addElement(new String("whezzing"));
		
		symptomsList.setModel(model);
	}

	public void printToDiagLog(String observation) {
		
		textAreaDiagLog.append(observation);
		
		try {
			checkDiagLogString(observation);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void checkDiagLogString(String observation) throws Exception {
		
		String filePath = null;
		RuleWindow w = RuleWindow.getInstance();
		
		if(observation.contains("high") || observation.contains("High") || observation.contains("low") || observation.contains("Low") || observation.contains("positive")) {
			
			if(observation.contains("anemia")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getCBC().getText() + ", " + w.getHemoglobinLevels().getText() + ", " + "\"" + "ANEMIA NOS-" + "\"");
			}
			
			else if(observation.contains("hypertension")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getSystolicBP().getText() + ", " + w.getDystolicBP().getText() + ", " + "\"" + "HYPERTENSION NOS" + "\"");
			}
			
			else if(observation.contains("hypotension")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getSystolicBP().getText() + ", " + w.getDystolicBP().getText() + ", " + "\"" + "ORTHOSTATIC HYPOTENSION" + "\"");
			}
			
			else if(observation.contains("calcemia") || observation.contains("hypercalcemia")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getCalciumLevels().getText() + ", " + "\"" + "HYPERCALCEMIA (Begin 1997)" + "\"");
			}
			
			else if(observation.contains("hypocalcemia")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getCalciumLevels().getText() + ", " + "\"" + "HYPOCALCEMIA (Begin 1997)" + "\"");
			}
			
			else if(observation.contains("diabetes")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getSugarLevels().getText() + ", " + "\"" + "DIABETES UNCOMPL TYPE II" + "\"");
			}
		}
		
		else if(observation.contains("Normal") || observation.contains("normal") || observation.contains("negative")) {
			
			if(observation.contains("anemia")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getCBC().getText() + ", " + w.getHemoglobinLevels().getText() + ", " + "\"" + "Normal" + "\"");
			}
			
			else if(observation.contains("hypertension")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getSystolicBP().getText() + ", " + w.getDystolicBP().getText() + ", " + "\"" + "Normal" + "\"");
			}
			
			else if(observation.contains("calcemia") || observation.contains("hypercalcemia")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getCalciumLevels().getText() + ", " + "\"" + "Normal" + "\"");
			}
			
			else if(observation.contains("diabetes")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2.arff";
				writeAgeGender(filePath);
				attributeWriter.append(w.getSugarLevels().getText() + ", " + "\"" + "Normal" + "\"");
			}
		}
		
		if(attributeWriter != null) {
			attributeWriter.newLine();
			attributeWriter.close();
		}
		
	}
	
	private void setRecordFilePath(String filePath) {
		
		try {
			attribute = new FileWriter(filePath, true);
			attributeWriter = new BufferedWriter(attribute);
		} 
		
		catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	private void writeAgeGender(String filePath) {
		
		setRecordFilePath(filePath);
		
		try {
			attributeWriter.append(ag.getTextFieldAge().getText() + ".0, 0.0, " + ag.getTextFieldGender().getText() + ".0, ");
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public OntologyClassQuery getQueryModel() {
		return query;
	}

	public void setQueryModel(OntologyClassQuery query) {
		this.query = query;
	}

	public OntModel getOntModel() {
		return ontModel;
	}

	public void setOntModel(OntModel ontModel) {
		this.ontModel = ontModel;
	}

	public JFrame getFrmDdMode() {
		return frmDdMode;
	}

	public DefaultListModel getPossibleDiseasesmodel() {
		return possibleDiseasesmodel;
	}

	public AgeGender getAg() {
		return ag;
	}

	public void setAg(AgeGender ag) {
		this.ag = ag;
	}
	
}
