package diagnosis.recommendation.gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

import com.hp.hpl.jena.ontology.OntModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import diagnosis.recommendation.ontology.query.OntologyClassQuery;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;


public class R2 implements ActionListener {

	private static R2 instance;
	private JFrame frmR1;
	private JLabel lblSymptoms;
	private JButton button;
	private JTextArea textAreaResults;
	private JLabel lblResults;
	private JList symptomsList;
	private DefaultListModel model;
	private JScrollPane scrollPane;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private StringArrayListConverter converter;
	private JButton buttonBack;
	private JButton btnJungView;
	private JScrollPane scrollPaneResults;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R2 window = new R2();
					window.frmR1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private R2() {
		initialize();
		
		converter = StringArrayListConverter.getInstance();
	}
	
	public static R2 getInstance() {
	
		if(instance == null)
			instance = new R2();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmR1 = new JFrame();
		frmR1.setTitle("R1, R2");
		frmR1.setBounds(100, 100, 311, 693);
		frmR1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmR1.getContentPane().setLayout(null);
		
		lblSymptoms = new JLabel("Select Symptoms");
		lblSymptoms.setBounds(23, 11, 143, 14);
		frmR1.getContentPane().add(lblSymptoms);
		
		button = new JButton("Get Diseases of Symptoms (Text View)");
		button.setBounds(14, 178, 271, 36);
		frmR1.getContentPane().add(button);
		
		textAreaResults = new JTextArea();
		textAreaResults.setEditable(false);
		textAreaResults.setForeground(Color.BLUE);
		textAreaResults.setBounds(14, 300, 216, 273);
		frmR1.getContentPane().add(textAreaResults);
		
		lblResults = new JLabel("Results");
		lblResults.setBounds(10, 280, 70, 14);
		frmR1.getContentPane().add(lblResults);
		
		symptomsList = new JList();
		symptomsList.setBounds(23, 25, 197, 142);
		frmR1.getContentPane().add(symptomsList);
		
		scrollPane = new JScrollPane(symptomsList);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(23, 25, 216, 142);
		frmR1.getContentPane().add(scrollPane);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(57, 606, 136, 38);
		frmR1.getContentPane().add(buttonBack);
		
		btnJungView = new JButton("Get Diseaseas of Symptoms (Graph View)");
		btnJungView.setBounds(14, 221, 271, 36);
		frmR1.getContentPane().add(btnJungView);
		
		scrollPaneResults = new JScrollPane(textAreaResults);
		scrollPaneResults.setBounds(14, 300, 238, 292);
		frmR1.getContentPane().add(scrollPaneResults);
		
		model = new DefaultListModel();
		populateSymptomsList();
		
		buttonBack.addActionListener(this);
		button.addActionListener(this);
		btnJungView.addActionListener(this);
		frmR1.setVisible(true);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button) {
			
			textAreaResults.setText("");
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			
			List<String> diseases;
			diseases = query.linkSymptomsToDiseases(System.out, ontModel, null, converter.convertToList(selectedSymptoms));
			
			for(int i = 0; i < diseases.size(); i++) {
				
				textAreaResults.append(diseases.get(i));
				textAreaResults.append("\n");
			}
		}
		
		else if(e.getSource() == btnJungView) {
			
			//Graph(V, E)
			Graph<String, String> g = new DirectedSparseMultigraph<String, String>();
			
			JungView view = new JungView();
			
			textAreaResults.setText("");
			String[] selectedSymptoms = converter.convertObjectArrayToStringArray(symptomsList.getSelectedValues());
			
			List<String> diseases;
			diseases = query.linkSymptomsToDiseases(System.out, ontModel, null, converter.convertToList(selectedSymptoms));
			
			for(int i = 0; i < diseases.size(); i++) {
				
				g.addVertex(diseases.get(i));
				//view.setVertexColourTransformer("RED");
			}
			
			for(int i = 0; i < selectedSymptoms.length; i++) {
				
				g.addVertex(selectedSymptoms[i]);
				//view.setVertexColourTransformer("YELLOW");
			}
			
			for(int i = 0; i < diseases.size(); i++) {
				
				for(int j = 0; j < selectedSymptoms.length; j++) {
					
					g.addEdge(diseases.get(i) + " has_symptom " + selectedSymptoms[j], selectedSymptoms[j], diseases.get(i), EdgeType.DIRECTED);
				}
			}
			
			view.setJungParameters(g);
			view.setVertexColourTransformer(diseases);
			view.setVertexShapeTransformer("RECTANGLE");
		}
		
		else if(e.getSource() == buttonBack) {
			
			InvestigationQuestions window = InvestigationQuestions.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			frmR1.setVisible(false);
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

	public JFrame getFrmR1() {
		return frmR1;
	}

}
