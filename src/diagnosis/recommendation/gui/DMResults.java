package diagnosis.recommendation.gui;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;


public class DMResults implements ActionListener{

	private JFrame frmOntologyDrivenData;
	private JLabel lblResults;
	private JButton buttonBack;
	private static DMResults instance = null;
	private JTextArea textAreaResults;
	private JScrollPane scrollPaneResults;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMResults window = new DMResults();
					window.frmOntologyDrivenData.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private DMResults() {

		initialize();
	}
	
	public static DMResults getInstance() {
		
		if(instance == null)
			instance = new DMResults();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmOntologyDrivenData = new JFrame();
		frmOntologyDrivenData.setTitle("Ontology Driven Data Mining DDx Results");
		frmOntologyDrivenData.setBounds(100, 100, 600, 488);
		frmOntologyDrivenData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOntologyDrivenData.getContentPane().setLayout(null);
		
		lblResults = new JLabel("Results");
		lblResults.setBounds(10, 11, 63, 14);
		frmOntologyDrivenData.getContentPane().add(lblResults);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(209, 404, 133, 35);
		frmOntologyDrivenData.getContentPane().add(buttonBack);
		
		textAreaResults = new JTextArea();
		textAreaResults.setEditable(false);
		textAreaResults.setBounds(10, 26, 547, 354);
		frmOntologyDrivenData.getContentPane().add(textAreaResults);
		
		scrollPaneResults = new JScrollPane(textAreaResults);
		scrollPaneResults.setBounds(10, 23, 564, 370);
		frmOntologyDrivenData.getContentPane().add(scrollPaneResults);
		
		buttonBack.addActionListener(this);
		frmOntologyDrivenData.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == buttonBack) {
			
			DiagnosisGUI diag = DiagnosisGUI.getInstance();
			diag.getFrmSemanticDiagonsisRecommender().setVisible(true);
			this.frmOntologyDrivenData.setVisible(false);
		}
	}

	public JTextArea getTextAreaResults() {
		return textAreaResults;
	}
	
	public JFrame getFrmOntologyDrivenData() {
		return frmOntologyDrivenData;
	}

	public void printToTextArea(String text) {
		
		textAreaResults.setText("");
		
		textAreaResults.append(text);
	}
}
