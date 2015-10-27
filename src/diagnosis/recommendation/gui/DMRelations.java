package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DMRelations implements ActionListener {

	private JFrame frmDifferentialDiagnosisRecommendation;
	private JLabel lblDdxDataMining;
	private JLabel lblD1;
	private JButton btnD1;
	private JLabel lblD2;
	private JButton btnD2;
	private JButton btnBack;
	private static DMRelations INSTANCE = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMRelations window = new DMRelations();
					window.frmDifferentialDiagnosisRecommendation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private DMRelations() {
		initialize();
	}
	
	public static DMRelations getInstance() {
		
		if(INSTANCE == null) {
			
			INSTANCE = new DMRelations();
		}
		
		return INSTANCE;
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		
		frmDifferentialDiagnosisRecommendation = new JFrame();
		frmDifferentialDiagnosisRecommendation.setTitle("Differential Diagnosis Recommendation using ODDM");
		frmDifferentialDiagnosisRecommendation.setBounds(100, 100, 1239, 300);
		frmDifferentialDiagnosisRecommendation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDifferentialDiagnosisRecommendation.getContentPane().setLayout(null);
		
		lblDdxDataMining = new JLabel("DDx Data Mining Queries");
		lblDdxDataMining.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDdxDataMining.setBounds(10, 11, 179, 14);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(lblDdxDataMining);
		
		lblD1 = new JLabel("D1: Find whether a patient has a certain disease or not based on the patient's demographic information, and lab test results.            (Classification using Test Data)");
		lblD1.setBounds(10, 44, 935, 14);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(lblD1);
		
		btnD1 = new JButton("Answer D1");
		btnD1.setBounds(10, 69, 160, 32);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(btnD1);
		
		lblD2 = new JLabel("D2: Find associations/rules describing the relation between a patient's demographic information and lab test results on one hand, and a certain possible diagnosis on the other hand.               (Association Rules)");
		lblD2.setBounds(10, 112, 1183, 14);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(lblD2);
		
		btnD2 = new JButton("Answer D2");
		btnD2.setBounds(10, 137, 160, 32);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(btnD2);
		
		btnBack = new JButton("<< Go Back");
		btnBack.setBounds(620, 199, 150, 39);
		frmDifferentialDiagnosisRecommendation.getContentPane().add(btnBack);
		
		btnBack.addActionListener(this);
		btnD1.addActionListener(this);
		btnD2.addActionListener(this);
		frmDifferentialDiagnosisRecommendation.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnD1) {
			
			D1Algorithm alg = D1Algorithm.getInstance();
			alg.getFrmDClassification().setVisible(true);
			this.frmDifferentialDiagnosisRecommendation.setVisible(false);
		}
		
		else if(e.getSource() == btnD2) {
			
			DMDataSelection dataSelection = DMDataSelection.getInstance();
			dataSelection.getFrmSelectionOfData().setVisible(true);
			this.frmDifferentialDiagnosisRecommendation.setVisible(false);
		}
		
		else if(e.getSource() == btnBack) {

			DiagnosisGUI diag = DiagnosisGUI.getInstance();
			diag.getFrmSemanticDiagonsisRecommender().setVisible(true);
			this.frmDifferentialDiagnosisRecommendation.setVisible(false);
		}
	}

	public JFrame getFrmDifferentialDiagnosisRecommendation() {
		return frmDifferentialDiagnosisRecommendation;
	}
	
	
}
