package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import diagnosis.recommendation.ontology.dataMining.ClassificationAssociation;

public class TestDataOption implements ActionListener {

	private JFrame frmTestFileOptions;
	private JLabel lblWhatToDo;
	private JRadioButton rdbtnAddRecordsTo;
	private JRadioButton rdbtnClassify;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton buttonBack;
	private JButton btnProceed;
	private static TestDataOption INSTANCE = null;
	private final int J48  = 1;
	private final int JRIP = 2;
	private final int NB = 3;
	private final int NBTree = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestDataOption window = new TestDataOption();
					window.frmTestFileOptions.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private TestDataOption() {
		initialize();
	}
	
	public static TestDataOption getInstance() {
		
		if(INSTANCE == null)
			INSTANCE = new TestDataOption();
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestFileOptions = new JFrame();
		frmTestFileOptions.setTitle("Test File Options");
		frmTestFileOptions.setBounds(100, 100, 370, 189);
		frmTestFileOptions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestFileOptions.getContentPane().setLayout(null);
		
		lblWhatToDo = new JLabel("What to do with the Test Data File:");
		lblWhatToDo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWhatToDo.setBounds(10, 11, 280, 14);
		frmTestFileOptions.getContentPane().add(lblWhatToDo);
		
		rdbtnAddRecordsTo = new JRadioButton("Add Records to the Test Data File then Classify");
		rdbtnAddRecordsTo.setBounds(6, 32, 338, 23);
		frmTestFileOptions.getContentPane().add(rdbtnAddRecordsTo);
		
		rdbtnClassify = new JRadioButton("Classify the Current Data");
		rdbtnClassify.setBounds(6, 56, 258, 23);
		frmTestFileOptions.getContentPane().add(rdbtnClassify);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(22, 96, 144, 32);
		frmTestFileOptions.getContentPane().add(buttonBack);
		
		btnProceed = new JButton("Proceed >>");
		btnProceed.setBounds(176, 96, 161, 32);
		frmTestFileOptions.getContentPane().add(btnProceed);
		
		frmTestFileOptions.setVisible(true);
		btnProceed.addActionListener(this);
		buttonBack.addActionListener(this);
		buttonGroup.add(rdbtnAddRecordsTo);
		buttonGroup.add(rdbtnClassify);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		TestDataSelection selection = TestDataSelection.getInstance();
		
		if(e.getSource() == btnProceed) {
			
			if(rdbtnAddRecordsTo.isSelected()) {
				
				NewTestData newRecord = NewTestData.getInstance();
				
				if(selection.getListSelection().equalsIgnoreCase("diabetes test data")) {
					
					newRecord.diabetesTestRecordSetup();
				}
				
				else if(selection.getListSelection().equalsIgnoreCase("calcemia data")) {
					
					newRecord.calcemiaTestRecordSetup();
				}
				
				else if(selection.getListSelection().equalsIgnoreCase("anemia data")) {
					
					newRecord.anemiaTestRecordSetup();
				}
				
				else if(selection.getListSelection().equalsIgnoreCase("blood pressure test data")) {
					
					newRecord.hypertensionTestRecordSetup();
				}
				
				newRecord.getFrmNewTestRecord().setVisible(true);
				this.getFrmTestFileOptions().setVisible(false);
			}
			
			else if(rdbtnClassify.isSelected()) {
				
				DMResults results = DMResults.getInstance();
				results.getFrmOntologyDrivenData().setVisible(true);
				this.getFrmTestFileOptions().setVisible(false);
				
				D1Algorithm alg = D1Algorithm.getInstance();
				NewTestData test = NewTestData.getInstance();
				test.getFrmNewTestRecord().setVisible(false);
				test.classifyTestData();
				
			}	
		}
		
		else if(e.getSource() == buttonBack) {
			
			selection.getFrmTestDataSelection().setVisible(true);
			this.frmTestFileOptions.setVisible(false);
		}
	}

	public JFrame getFrmTestFileOptions() {
		return frmTestFileOptions;
	}
}
