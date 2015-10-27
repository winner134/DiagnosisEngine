package diagnosis.recommendation.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import com.hp.hpl.jena.ontology.OntModel;

import diagnosis.recommendation.ontology.query.OntologyClassQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class InvestigationQuestions implements ActionListener {

	private static InvestigationQuestions instance;
	private JFrame frmDiagnosisInvestigation;
	private JLabel lblNewLabel;
	private JLabel lblR1Question;
	private JButton btnR1;
	private JLabel lblR3Question; 
	private JButton btnR3;
	private JLabel lblR4Question;
	private JButton btnR4;
	private JLabel lblR5Question;
	private JButton btnR5;
	private JLabel lblNewLabel_1;
	private JButton btnR6;
	private JButton btnBack;
	private OntologyClassQuery query;
	private OntModel ontModel;
	private int inference_mode;
	
	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvestigationQuestions window = new InvestigationQuestions();
					window.frmDiagnosisInvestigation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private InvestigationQuestions() {
		initialize();
	}
	
	public static InvestigationQuestions getInstance() {
		
		if(instance == null)
			instance = new InvestigationQuestions();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmDiagnosisInvestigation = new JFrame();
		frmDiagnosisInvestigation.setResizable(false);
		frmDiagnosisInvestigation.setTitle("Diagnosis Investigation using the DSO Crawler");
		frmDiagnosisInvestigation.setBounds(100, 100, 832, 336);
		frmDiagnosisInvestigation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDiagnosisInvestigation.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("DDx Questions");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(0, 0, 127, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblNewLabel);
		
		lblR1Question = new JLabel("R1/R2: What are the possible diseases a patient may have if he/she displays one or more symptoms?");
		lblR1Question.setBounds(0, 46, 661, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblR1Question);
		
		btnR1 = new JButton("Investigate R1/R2");
		btnR1.setBounds(671, 42, 145, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnR1);
		
		lblR3Question = new JLabel("R3: What are the symptoms of a given disease?");
		lblR3Question.setBounds(0, 86, 413, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblR3Question);
		
		btnR3 = new JButton("Investigate R3");
		btnR3.setBounds(671, 82, 145, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnR3);
		
		lblR4Question = new JLabel("R4: What are the related sibiling and parent diseases of a given disease?");
		lblR4Question.setBounds(0, 121, 413, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblR4Question);
		
		btnR4 = new JButton("Investigate R4");
		btnR4.setBounds(671, 117, 145, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnR4);
		
		lblR5Question = new JLabel("R5: What are the missing symptoms of a disease given one or more of its symptoms?");
		lblR5Question.setBounds(0, 156, 504, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblR5Question);
		
		btnR5 = new JButton("Investigate R5");
		btnR5.setBounds(671, 152, 145, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnR5);
		
		lblNewLabel_1 = new JLabel("R6: What are the possible diseases, under a certain pathology, a patient may have if he/she displays one or more symptoms?");
		lblNewLabel_1.setBounds(0, 191, 816, 14);
		frmDiagnosisInvestigation.getContentPane().add(lblNewLabel_1);
		
		btnR6 = new JButton("Investigate R6");
		btnR6.setBounds(671, 216, 145, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnR6);
		
		btnBack = new JButton("<< Go Back");
		btnBack.setBounds(373, 274, 105, 23);
		frmDiagnosisInvestigation.getContentPane().add(btnBack);
		
		frmDiagnosisInvestigation.setVisible(true);
		btnR6.addActionListener(this);
		btnR5.addActionListener(this);
		btnR4.addActionListener(this);
		btnR3.addActionListener(this);
		btnR1.addActionListener(this);
		btnBack.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnR1) {
			
			R2 window = R2.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmR1().setVisible(true);
		}
		
		else if(e.getSource() == btnR3) {
			
			R34 window = R34.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmR3().setVisible(true);
		}
		
		else if(e.getSource() == btnR4) {
			
			R34 window = R34.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmR3().setVisible(true);
		}
		
		else if(e.getSource() == btnR5) {
			
			R5 window = R5.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmR5().setVisible(true);
		}
		
		else if(e.getSource() == btnR6) {
			
			R6 window = R6.getInstance();
			window.setOntModel(ontModel);
			window.setQueryModel(query);
			window.getFrmR6().setVisible(true);
		}
		
		else if(e.getSource() == btnBack) {
			
			DiagnosisGUI window = DiagnosisGUI.getInstance();
			window.getFrmSemanticDiagonsisRecommender().setVisible(true);
			frmDiagnosisInvestigation.setVisible(false);
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

	public JFrame getFrmDiagnosisInvestigation() {
		return frmDiagnosisInvestigation;
	}

	public int getInference_mode() {
		return inference_mode;
	}

	public void setInference_mode(int inference_mode) {
		this.inference_mode = inference_mode;
	}
	
}
