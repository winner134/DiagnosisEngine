package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.mindswap.pellet.jena.PelletInfGraph;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;
import diagnosis.recommendation.ontology.reasoner.pellet.PelletInference;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DiagnosisGUI implements ActionListener{

	private static DiagnosisGUI instance;
	private JFrame frmSemanticDiagonsisRecommender;
	private JButton btnInvestigation;
	private JButton btnRecommendation;
	private JButton btnRecommendation1;
	private JPanel panel;
	private JButton btnDashboard;
	private JLabel lblNewLabel;
	private JButton btnSettings;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private static final int DIRECT_INFERENCE = 1;
	private static final int INDIRECT_INFERENCE = 2;
	private int inference_mode = DIRECT_INFERENCE;

	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiagnosisGUI window = new DiagnosisGUI();
					window.frmSemanticDiagonsisRecommender.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private DiagnosisGUI() {
		
		initialize(); 
	}
	
	public static DiagnosisGUI getInstance(){
		
		if(instance == null)
			instance = new  DiagnosisGUI();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSemanticDiagonsisRecommender = new JFrame();
		frmSemanticDiagonsisRecommender.setTitle("Semantic Diagonsis Recommender GUI");
		frmSemanticDiagonsisRecommender.setBounds(100, 100, 539, 652);
		frmSemanticDiagonsisRecommender.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSemanticDiagonsisRecommender.getContentPane().setLayout(null);
		
		btnInvestigation = new JButton("Differential Diagnosis Investigation");
		btnInvestigation.setBounds(67, 393, 378, 58);
		frmSemanticDiagonsisRecommender.getContentPane().add(btnInvestigation);
		
		btnRecommendation1 = new JButton("Differential Diagnosis Proximity Driven Recommendation");
		btnRecommendation1.setBounds(67, 531, 378, 58);
		frmSemanticDiagonsisRecommender.getContentPane().add(btnRecommendation1);
		
		panel = new JPanel();
		panel.setBounds(172, 69, 170, 170);
		frmSemanticDiagonsisRecommender.getContentPane().add(panel);
		
		btnDashboard = new JButton("Differential Diagnosis Dashboard");
		btnDashboard.setBounds(67, 324, 378, 58);
		frmSemanticDiagonsisRecommender.getContentPane().add(btnDashboard);
		
		lblNewLabel = new JLabel("Semantic Web-Based DDx Recommendation System");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(67, 24, 446, 34);
		frmSemanticDiagonsisRecommender.getContentPane().add(lblNewLabel);
		
		btnSettings = new JButton("Inferential Settings");
		btnSettings.setBounds(67, 262, 378, 51);
		frmSemanticDiagonsisRecommender.getContentPane().add(btnSettings);
		
		btnRecommendation = new JButton("Differential Diagnosis Evidence Driven Recommendation");
		btnRecommendation.setBounds(67, 462, 378, 58);
		frmSemanticDiagonsisRecommender.getContentPane().add(btnRecommendation);
		
		frmSemanticDiagonsisRecommender.setVisible(true);
		btnInvestigation.addActionListener(this);
		btnRecommendation.addActionListener(this);
		btnRecommendation1.addActionListener(this);
		btnDashboard.addActionListener(this);
		btnSettings.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnInvestigation) {
			
			InvestigationQuestions window = InvestigationQuestions.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.setInference_mode(inference_mode);
			window.getFrmDiagnosisInvestigation().setVisible(true);
			frmSemanticDiagonsisRecommender.setVisible(false);
		}
		
		else if(e.getSource() == btnRecommendation) {
			
			DDR window = DDR.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmDdMode().setVisible(true);
			frmSemanticDiagonsisRecommender.setVisible(false);
		}
		
		else if(e.getSource() == btnDashboard) {
			
		}
		
		else if(e.getSource() == btnSettings) {
			
			Settings window = Settings.getInstance();
			frmSemanticDiagonsisRecommender.setVisible(false);
			window.getFrmSettings().setVisible(true);
		}
		
		else if(e.getSource() == btnRecommendation1) {
			
			DMRelations window = DMRelations.getInstance();
			frmSemanticDiagonsisRecommender.setVisible(false);
			window.getFrmDifferentialDiagnosisRecommendation().setVisible(true);
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

	public JFrame getFrmSemanticDiagonsisRecommender() {
		return frmSemanticDiagonsisRecommender;
	}

	public int getInference_mode() {
		return inference_mode;
	}

	public void setInference_mode(int inference_mode) {
		
		if(this.inference_mode != inference_mode) {
			
			this.inference_mode = inference_mode;
			
			if(this.inference_mode == DIRECT_INFERENCE) {
				
				ontModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM);
				ontModel.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl");
				ontModel.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl" );
				 
				ontModel.read("file:/C:/ActionRecognition/Ontology/DSO.owl");
			}
			
			else if(this.inference_mode == INDIRECT_INFERENCE) {
				
				 ontModel = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC);
				 ontModel.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl");
				 ontModel.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl" );
				 
				 ontModel.read("file:/C:/ActionRecognition/Ontology/DSO.owl");

				 // get the underlying Pellet graph
				 PelletInference pellet = new PelletInference((PelletInfGraph) ontModel.getGraph());

				 //progressBar.setIndeterminate(true);
				 boolean consistent = pellet.isConsistent();
				 System.out.println("Consistency = " + consistent);

				 // Trigger classification
				 pellet.classify();
				 // Trigger realization
				 pellet.realize();
			}
		}	
	}
	
}

