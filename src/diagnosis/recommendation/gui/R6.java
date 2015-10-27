package diagnosis.recommendation.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import com.hp.hpl.jena.ontology.OntModel;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class R6 implements ActionListener{

	private static R6 instance;
	private JFrame frmR6;
	private JLabel lblSelectSymptoms;
	private JLabel lblEnterADisease;
	private JButton btnGetDiseases;
	private JButton btnGraphDiseases;
	private JLabel lblResults;
	private JTextArea textAreaResults;
	private JList symptomsList;
	private JList diseasesList;
	private JScrollPane scrollPaneSymptoms;
	private JScrollPane scrollPaneDiseases;
	private JScrollPane scrollPaneResults;
	private JButton btnNewButton;
	private DefaultListModel symptomsModel;
	private DefaultListModel diseasesModel;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private StringArrayListConverter converter;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R6 window = new R6();
					window.frmR6.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private R6() {
		initialize();
		
		converter = StringArrayListConverter.getInstance();
	}
	
	public static R6 getInstance() {
		
		if(instance == null)
			instance = new R6();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmR6 = new JFrame();
		frmR6.setTitle("R6");
		frmR6.setBounds(100, 100, 318, 757);
		frmR6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmR6.getContentPane().setLayout(null);
		
		lblSelectSymptoms = new JLabel("Select Symptoms");
		lblSelectSymptoms.setEnabled(true);
		lblSelectSymptoms.setBounds(10, 11, 111, 14);
		frmR6.getContentPane().add(lblSelectSymptoms);
		
		lblEnterADisease = new JLabel("Select a Disease Pathology Name");
		lblEnterADisease.setBounds(10, 145, 235, 14);
		frmR6.getContentPane().add(lblEnterADisease);
		
		btnGetDiseases = new JButton("Get Diseases (Text View)");
		btnGetDiseases.setBounds(39, 323, 217, 37);
		frmR6.getContentPane().add(btnGetDiseases);
		
		lblResults = new JLabel("Results");
		lblResults.setBounds(10, 425, 46, 14);
		frmR6.getContentPane().add(lblResults);
		
		symptomsList = new JList();
		symptomsList.setBounds(10, 21, 267, 100);
		frmR6.getContentPane().add(symptomsList);
		
		diseasesList = new JList();
		diseasesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		diseasesList.setBounds(10, 157, 267, 137);
		frmR6.getContentPane().add(diseasesList);
		
		scrollPaneSymptoms = new JScrollPane(symptomsList);
		scrollPaneSymptoms.setBounds(10, 23, 282, 111);
		frmR6.getContentPane().add(scrollPaneSymptoms);
		
		scrollPaneDiseases = new JScrollPane(diseasesList);
		scrollPaneDiseases.setBounds(10, 157, 282, 155);
		frmR6.getContentPane().add(scrollPaneDiseases);
		
		btnNewButton = new JButton("<< Go Back");
		btnNewButton.setBounds(85, 670, 139, 38);
		frmR6.getContentPane().add(btnNewButton);
		
		btnGraphDiseases = new JButton("Get Diseases (Graph View)");
		btnGraphDiseases.setBounds(39, 369, 217, 37);
		frmR6.getContentPane().add(btnGraphDiseases);
		
		textAreaResults = new JTextArea();
		textAreaResults.setBounds(10, 436, 267, 211);
		frmR6.getContentPane().add(textAreaResults);
		
		JScrollPane scrollPaneResults = new JScrollPane(textAreaResults);
		scrollPaneResults.setBounds(10, 437, 282, 222);
		frmR6.getContentPane().add(scrollPaneResults);
		
		diseasesModel = new DefaultListModel();
		symptomsModel = new DefaultListModel();
		
		populateSymptomsModel();
		populateDiseasePathologiesModel();
		diseasesList.setModel(diseasesModel);
		btnNewButton.addActionListener(this);
		btnGetDiseases.addActionListener(this);
		btnGraphDiseases.addActionListener(this);
		frmR6.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnGetDiseases) {
			
			textAreaResults.setText("");
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			
			List<String> diseases;
			query.searchHierarchyForClass(System.out, ontModel, (String) diseasesList.getSelectedValue(), 1); 
			diseases = query.linkSymptomsToDiseases(System.out, null, query.getPathologyClass(), converter.convertToList(selectedSymptoms));
			
			for(int i = 0; i < diseases.size(); i++) {
				
				textAreaResults.append(diseases.get(i));
				textAreaResults.append("\n");
			}
		}
		
		else if(e.getSource() == btnGraphDiseases) {
			
			//Graph(V, E)
			Graph<String, String> g = new DirectedSparseMultigraph<String, String>();
			
			JungView view = new JungView();
			
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			
			List<String> diseases;
			query.searchHierarchyForClass(System.out, ontModel, (String) diseasesList.getSelectedValue(), 1); 
			diseases = query.linkSymptomsToDiseases(System.out, null, query.getPathologyClass(), converter.convertToList(selectedSymptoms));
			
			g.addVertex((String) diseasesList.getSelectedValue());
			
			List<String> parentEdges = new LinkedList<String>();
			
			for(int i = 0; i < diseases.size(); i++) {
				
				g.addVertex(diseases.get(i));
				g.addEdge(diseases.get(i)+ " has_parent " + (String) diseasesList.getSelectedValue(), diseases.get(i), 
						(String) diseasesList.getSelectedValue(), EdgeType.DIRECTED);
				
				parentEdges.add(diseases.get(i)+ " has_parent " + (String) diseasesList.getSelectedValue());
				
			}
			
			for(int i = 0; i < selectedSymptoms.length; i++) {
				
				g.addVertex(selectedSymptoms[i]);
			}
			
			for(int i = 0; i < diseases.size(); i++) {
				
				for(int j = 0; j < selectedSymptoms.length; j++) {
					
					g.addEdge(diseases.get(i) + " has_symptom " + selectedSymptoms[j], selectedSymptoms[j], diseases.get(i), EdgeType.DIRECTED);
				}
			}
			
			view.setJungParameters(g);
			view.setVertexColourTransformer((String) diseasesList.getSelectedValue(), diseases, selectedSymptoms);
			view.setVertexShapeTransformer("RECTANGLE");
			
			for(int i = 0; i < parentEdges.size(); i++) {
				
				view.setEdgeTransformer(parentEdges.get(i));
			}
		}
		
		else if(e.getSource() == btnNewButton) {
			
			InvestigationQuestions window = InvestigationQuestions.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			frmR6.setVisible(false);
		}
	}
	

	private void populateDiseasePathologiesModel() {
		
		diseasesModel.addElement(new String("disease of metabolism"));
		diseasesModel.addElement(new String("disease of anatomical entity"));
		diseasesModel.addElement(new String("\t hematopoietic system disease"));
		diseasesModel.addElement(new String("\t lymphatic system disease"));
		diseasesModel.addElement(new String("cardiovascular system disease"));
		diseasesModel.addElement(new String("\t vascular disease"));
		diseasesModel.addElement(new String("reproductive system disease"));
		diseasesModel.addElement(new String("integumentary system disease"));
		diseasesModel.addElement(new String("\t skin diseases"));
		diseasesModel.addElement(new String("\t hair diseases"));
		diseasesModel.addElement(new String("\t nail diseases"));
		diseasesModel.addElement(new String("musculoskeletal system disease"));
		diseasesModel.addElement(new String("urinary system disease"));
		diseasesModel.addElement(new String("\t ureteral disease"));
		diseasesModel.addElement(new String("\t bladder disease"));
		diseasesModel.addElement(new String("\t kidney disease"));
		diseasesModel.addElement(new String("\t urinary tract disease"));
		diseasesModel.addElement(new String("\t urethral disease"));
		diseasesModel.addElement(new String("immune system disease"));
		diseasesModel.addElement(new String("gastrointestinal system disease"));
		diseasesModel.addElement(new String("lung disease"));
		diseasesModel.addElement(new String("respiratory failure"));
		
		diseasesList.setModel(diseasesModel);
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

	public JFrame getFrmR6() {
		return frmR6;
	}
	
}
