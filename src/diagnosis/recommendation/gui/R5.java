package diagnosis.recommendation.gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.hp.hpl.jena.ontology.OntModel;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class R5 implements ActionListener {

	private static R5 instance;
	private JFrame frmR5;
	private JLabel labelSymptoms;
	private JLabel labelDiseaseName;
	private JButton buttonMissingSymptoms;
	private JButton buttonGraphMissingSymptoms;
	private JScrollPane scrollPaneSymptoms;
	private JLabel labelResults; 
	private JTextArea textAreaResults;
	private JList symptomsList;
	private JList diseasesList;
	private JScrollPane scrollPaneDiseases;
	private JButton button;
	private JButton btnGetDiseases;
	private JList resultsList;
	private JScrollPane scrollPaneResults;
	private DefaultListModel symptomsModel;
	private DefaultListModel diseasesModel;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private StringArrayListConverter converter;
	

	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R5 window = new R5();
					window.frmR5.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private R5() {
		initialize();
		
		converter = StringArrayListConverter.getInstance();
	}
	
	public static R5 getInstance() {
		
		if(instance == null)
			instance = new R5();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmR5 = new JFrame();
		frmR5.setTitle("R5");
		frmR5.setBounds(100, 100, 361, 838);
		frmR5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmR5.getContentPane().setLayout(null);
		
		labelSymptoms = new JLabel("Select Symptoms");
		labelSymptoms.setEnabled(true);
		labelSymptoms.setBounds(10, 11, 106, 14);
		frmR5.getContentPane().add(labelSymptoms);
		
		labelDiseaseName = new JLabel("Select a Disease Name");
		labelDiseaseName.setEnabled(false);
		labelDiseaseName.setBounds(10, 273, 240, 14);
		frmR5.getContentPane().add(labelDiseaseName);
		
		buttonGraphMissingSymptoms = new JButton("Get Missing Symptoms of Disease (Graph View)");
		buttonGraphMissingSymptoms.setEnabled(false);
		buttonGraphMissingSymptoms.setBounds(17, 489, 318, 38);
		frmR5.getContentPane().add(buttonGraphMissingSymptoms);
		
		button = new JButton("<< Go Back");
		button.setBounds(101, 770, 121, 23);
		frmR5.getContentPane().add(button);
		
		btnGetDiseases = new JButton("Get Diseases");
		btnGetDiseases.setBounds(78, 216, 157, 34);
		frmR5.getContentPane().add(btnGetDiseases);
		
		labelResults = new JLabel("Results");
		labelResults.setEnabled(false);
		labelResults.setBounds(10, 538, 290, 14);
		frmR5.getContentPane().add(labelResults);
		
		diseasesList = new JList();
		diseasesList.setEnabled(false);
		diseasesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		diseasesList.setBounds(10, 286, 280, 137);
		frmR5.getContentPane().add(diseasesList);
		
		scrollPaneDiseases = new JScrollPane(diseasesList);
		scrollPaneDiseases.setBounds(10, 285, 290, 150);
		frmR5.getContentPane().add(scrollPaneDiseases);
		
		textAreaResults = new JTextArea();
		textAreaResults.setBounds(10, 550, 290, 205);
		frmR5.getContentPane().add(textAreaResults);
		textAreaResults.setForeground(Color.BLUE);
		textAreaResults.setEditable(false);
		textAreaResults.setEnabled(false);
		
		buttonMissingSymptoms = new JButton("Get Missing Symptoms of Disease (Text View)");
		buttonMissingSymptoms.setEnabled(false);
		buttonMissingSymptoms.setBounds(17, 446, 318, 38);
		frmR5.getContentPane().add(buttonMissingSymptoms);
		
		scrollPaneResults = new JScrollPane(textAreaResults);
		scrollPaneResults.setBounds(10, 550, 302, 219);
		frmR5.getContentPane().add(scrollPaneResults);
		
		symptomsList = new JList();
		symptomsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				diseasesModel.clear();
				textAreaResults.setEnabled(false);
				diseasesList.setEnabled(false);
				buttonMissingSymptoms.setEnabled(false);
				buttonGraphMissingSymptoms.setEnabled(false);
				labelDiseaseName.setEnabled(false);
				labelResults.setEnabled(false);
				textAreaResults.setText("");
			}
		});
		symptomsList.setBounds(10, 24, 302, 160);
		frmR5.getContentPane().add(symptomsList);
		
		scrollPaneSymptoms = new JScrollPane(symptomsList);
		scrollPaneSymptoms.setBounds(10, 23, 325, 182);
		frmR5.getContentPane().add(scrollPaneSymptoms);
		
		diseasesModel = new DefaultListModel();
		symptomsModel = new DefaultListModel();
		
		populateSymptomsModel();
		diseasesList.setModel(diseasesModel);
		
		frmR5.setVisible(true);
		btnGetDiseases.addActionListener(this);
		buttonMissingSymptoms.addActionListener(this);
		button.addActionListener(this);
		buttonGraphMissingSymptoms.addActionListener(this);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnGetDiseases) {

			diseasesList.removeAll();
			textAreaResults.setEnabled(true);
			diseasesList.setEnabled(true);
			buttonMissingSymptoms.setEnabled(true);
			buttonGraphMissingSymptoms.setEnabled(true);
			labelDiseaseName.setEnabled(true);
			labelResults.setEnabled(true);
			
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			
			List<String> diseases;
			diseases = query.linkSymptomsToDiseases(System.out, ontModel, null, converter.convertToList(selectedSymptoms));
			
			for (int i = 0; i < diseases.size(); i++) {
				
				diseasesModel.addElement(diseases.get(i));
			}
			
		}
		
		else if(e.getSource() == buttonMissingSymptoms) {
			
			textAreaResults.setText("");

			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			List<String> knownSymptoms = converter.convertToList(selectedSymptoms);
			
			List<String> missingSymptoms = query.getMissingSymptoms(System.out, ontModel, knownSymptoms, (String) diseasesList.getSelectedValue());
			
			for (int i = 0; i < missingSymptoms.size(); i++) {
				
				textAreaResults.append(missingSymptoms.get(i));
				textAreaResults.append("\n");
			}
		}
		
		else if(e.getSource() == buttonGraphMissingSymptoms) {
			
			//Graph(V, E)
			Graph<String, String> g = new DirectedSparseMultigraph<String, String>();
			
			JungView view = new JungView();
			
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			List<String> knownSymptoms = converter.convertToList(selectedSymptoms);
			
			List<String> missingSymptoms = query.getMissingSymptoms(System.out, ontModel, knownSymptoms, (String) diseasesList.getSelectedValue());
			
			g.addVertex((String) diseasesList.getSelectedValue());
			
			for(int i = 0; i < knownSymptoms.size(); i++) {
				
				g.addVertex(knownSymptoms.get(i));
				g.addEdge((String) diseasesList.getSelectedValue() + " has_symptom " + knownSymptoms.get(i), (String) diseasesList.getSelectedValue(), knownSymptoms.get(i), EdgeType.DIRECTED);
			}
			
			for (int i = 0; i < missingSymptoms.size(); i++) {
				
				g.addVertex(missingSymptoms.get(i));
				g.addEdge((String) diseasesList.getSelectedValue() + " has_symptom " + missingSymptoms.get(i), (String) diseasesList.getSelectedValue(), missingSymptoms.get(i), EdgeType.DIRECTED);
			}
			
			view.setJungParameters(g);
			view.setVertexShapeTransformer("RECTANGLE");
			view.setVertexColourTransformer((String) diseasesList.getSelectedValue(), knownSymptoms, missingSymptoms);
			
		}
		
		else if(e.getSource() == button) {
			
			InvestigationQuestions window = InvestigationQuestions.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			frmR5.setVisible(false);
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

	public JFrame getFrmR5() {
		return frmR5;
	}

	private void populateSymptomsModel() {
		
		symptomsModel.addElement(new String("abdominal discomfort"));
		symptomsModel.addElement(new String("abdominal pain"));
		symptomsModel.addElement(new String("alteration of apetite"));
		symptomsModel.addElement(new String("chest pain"));
		symptomsModel.addElement(new String("confusion"));
		symptomsModel.addElement(new String("constipation"));
		symptomsModel.addElement(new String("cough"));
		symptomsModel.addElement(new String("cyanosis"));
		symptomsModel.addElement(new String("depression"));
		symptomsModel.addElement(new String("diarrhea"));
		symptomsModel.addElement(new String("dizziness"));
		symptomsModel.addElement(new String("drowsiness"));
		symptomsModel.addElement(new String("edema"));
		symptomsModel.addElement(new String("excessive secretion of urine"));
		symptomsModel.addElement(new String("fatigue"));
		symptomsModel.addElement(new String("flushing"));
		symptomsModel.addElement(new String("frequent urination"));
		symptomsModel.addElement(new String("headache"));
		symptomsModel.addElement(new String("hypoxemia"));
		symptomsModel.addElement(new String("itching"));
		symptomsModel.addElement(new String("jaundice"));
		symptomsModel.addElement(new String("lethargy"));
		symptomsModel.addElement(new String("muscle cramp"));
		symptomsModel.addElement(new String("nausea"));
		symptomsModel.addElement(new String("nocturia"));
		symptomsModel.addElement(new String("nosebleed"));
		symptomsModel.addElement(new String("objective vertigo"));
		symptomsModel.addElement(new String("pain"));
		symptomsModel.addElement(new String("paleness"));
		symptomsModel.addElement(new String("painful urine discharge"));
		symptomsModel.addElement(new String("palpitation"));
		symptomsModel.addElement(new String("pulmonary edema"));
		symptomsModel.addElement(new String("rapid breathing"));
		symptomsModel.addElement(new String("shortness of breath"));
		symptomsModel.addElement(new String("subjective vertigo"));
		symptomsModel.addElement(new String("thirst"));
		symptomsModel.addElement(new String("tachycardia"));
		symptomsModel.addElement(new String("urgency of urination"));
		symptomsModel.addElement(new String("urinary retention"));
		symptomsModel.addElement(new String("vertigo"));
		symptomsModel.addElement(new String("vomiting"));
		symptomsModel.addElement(new String("weight loss"));
		symptomsModel.addElement(new String("whezzing"));
		
		symptomsList.setModel(symptomsModel);
	}

}
