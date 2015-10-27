package diagnosis.recommendation.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import com.hp.hpl.jena.ontology.OntModel;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class R34 implements ActionListener{

	private static R34 instance;
	private JFrame frmR3;
	private JLabel lblEnterADisease;
	private JButton buttonDiseaseHierachy;
	private JButton buttonDiseaseSymptoms;
	private JButton btnGraphDiseaseHierarchy;
	private JButton btnGraphDiseaseSymptoms;
	private JLabel lblResults;
	private JTextArea textAreaResults;
	private JList list;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneResults;
	private JButton btnGoBack;
	private DefaultListModel model;
	private OntologyClassQuery query;
	private OntModel ontModel;
	

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R34 window = new R34();
					window.frmR3.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private R34() {
		initialize();
	}
	
	public static R34 getInstance() {
		
		if(instance == null)
			instance = new R34();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmR3 = new JFrame();
		frmR3.setTitle("R3, R4");
		frmR3.setBounds(100, 100, 367, 783);
		frmR3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmR3.getContentPane().setLayout(null);
		
		lblEnterADisease = new JLabel("Enter a Disease Name");
		lblEnterADisease.setBounds(10, 11, 127, 14);
		frmR3.getContentPane().add(lblEnterADisease);
		
		buttonDiseaseSymptoms = new JButton("Get Symptoms of Disease (Text View)");
		buttonDiseaseSymptoms.setBounds(42, 327, 271, 23);
		frmR3.getContentPane().add(buttonDiseaseSymptoms);
		
		lblResults = new JLabel("Results");
		lblResults.setBounds(10, 411, 60, 14);
		frmR3.getContentPane().add(lblResults);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 25, 265, 191);
		frmR3.getContentPane().add(list);
		
		scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 23, 290, 218);
		frmR3.getContentPane().add(scrollPane);
		
		btnGoBack = new JButton("<< Go Back");
		btnGoBack.setBounds(117, 701, 107, 36);
		frmR3.getContentPane().add(btnGoBack);
		
		buttonDiseaseHierachy = new JButton("Get Hierarchy of Disease (Text View)");
		buttonDiseaseHierachy.setBounds(42, 261, 271, 23);
		frmR3.getContentPane().add(buttonDiseaseHierachy);
		
		btnGraphDiseaseHierarchy = new JButton("Get Hierarchy of Disease (Graph View)");
		btnGraphDiseaseHierarchy.setBounds(42, 293, 271, 23);
		frmR3.getContentPane().add(btnGraphDiseaseHierarchy);
		
		btnGraphDiseaseSymptoms = new JButton("Get Symptoms of Disease (Graph View)");
		btnGraphDiseaseSymptoms.setBounds(42, 361, 271, 23);
		frmR3.getContentPane().add(btnGraphDiseaseSymptoms);
		
		textAreaResults = new JTextArea();
		textAreaResults.setBounds(10, 426, 303, 244);
		frmR3.getContentPane().add(textAreaResults);
		
		scrollPaneResults = new JScrollPane(textAreaResults);
		scrollPaneResults.setBounds(10, 426, 320, 264);
		frmR3.getContentPane().add(scrollPaneResults);
		
		model = new DefaultListModel();
		populateDiseasesList();
		
		frmR3.setVisible(true);
		buttonDiseaseHierachy.addActionListener(this);
		buttonDiseaseSymptoms.addActionListener(this);
		btnGoBack.addActionListener(this);
		btnGraphDiseaseHierarchy.addActionListener(this);
		btnGraphDiseaseSymptoms.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonDiseaseHierachy) {
			
			textAreaResults.setText("");
			
			try {
				
				query.showHierarchy(System.out, null, ontModel);
				List<String> hierarchy = query.getHierarchy();
				
				int diseaseIndex = 0;
				
				for(int i = 0; i < hierarchy.size(); i++) {
					
					if(hierarchy.get(i).trim().equalsIgnoreCase((String) list.getSelectedValue())) {
						
						diseaseIndex = i;
						break;
					}
				}
	
				if(diseaseIndex != 0) {
					
					for(int i = diseaseIndex - 21; i <= diseaseIndex + 21; i++) {
						
						textAreaResults.append(hierarchy.get(i));
						textAreaResults.append("\n");
					}
				}
				
			}
			
			catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == buttonDiseaseSymptoms) {
			
			textAreaResults.setText("");
			String disease = (String) list.getSelectedValue();
			List<String> symptoms = query.getSymptomsOfDisease(System.out, ontModel, disease);
			
			for(int i = 0; i < symptoms.size(); i++) {
				
				textAreaResults.append(symptoms.get(i));
				textAreaResults.append("\n");
			}
		}
		
		else if(e.getSource() == btnGraphDiseaseHierarchy) {
			
			try {
				
				query.showHierarchy(System.out, null, ontModel);
				List<String> hierarchy = query.getHierarchy();
				
				int diseaseIndex = 0;
				
				for(int i = 0; i < hierarchy.size(); i++) {
					
					if(hierarchy.get(i).trim().equalsIgnoreCase((String) list.getSelectedValue())) {
						
						diseaseIndex = i;
						break;
					}
				}
	
				if(diseaseIndex != 0) {
					
					for(int i = diseaseIndex - 21; i <= diseaseIndex + 21; i++) {
						
					}
				}
				
			}
			
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
		else if(e.getSource() == btnGraphDiseaseSymptoms) {
			
			Graph <String, String> g = new DirectedSparseMultigraph<String, String>();
			
			JungView view = new JungView();
			
			String disease = (String) list.getSelectedValue();
			g.addVertex(disease);
			List<String> symptoms = query.getSymptomsOfDisease(System.out, ontModel, disease);
			
			for(int i = 0; i < symptoms.size(); i++) {
				
				g.addVertex(symptoms.get(i));
				g.addEdge(disease + " has_symptom " + symptoms.get(i), disease, symptoms.get(i), EdgeType.DIRECTED);
			}
			
			view.setJungParameters(g);
			List<String> diseases = new LinkedList<String>();
			diseases.add(disease);
			view.setVertexColourTransformer(diseases);
			view.setVertexShapeTransformer("RECTANGLE");
		}
		
		else if(e.getSource() == btnGoBack) {
			
			InvestigationQuestions window = InvestigationQuestions.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			frmR3.setVisible(false);
		}
	}
	
	private void populateDiseasesList() {
		
		model.addElement(new String("adult respiratory distress syndrome"));
		model.addElement(new String("anemia"));
		model.addElement(new String("asthma"));
		model.addElement(new String("hypercalcemia"));
		model.addElement(new String("diabetes mellitus type 1"));
		model.addElement(new String("diabetes mellitus type 2"));
		model.addElement(new String("hypertension"));
		model.addElement(new String("renal failure"));
		
		list.setModel(model);
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

	public JFrame getFrmR3() {
		return frmR3;
	}

}
