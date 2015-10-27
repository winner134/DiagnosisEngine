package diagnosis.recommendation.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class TestDataSelection implements ActionListener{

	private JFrame frmTestDataSelection;
	private JLabel lblSelectTestData;
	private JList listTestFiles;
	private JButton btnProceedToResults;
	private JButton buttonBack;
	private JScrollPane scrollPaneTestFiles;
	private static TestDataSelection INSTANCE = null;
	private DefaultListModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestDataSelection window = new TestDataSelection();
					window.frmTestDataSelection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private TestDataSelection() {
		initialize();
	}
	
	public static TestDataSelection getInstance() {
		
		if(INSTANCE == null) {	
			INSTANCE = new TestDataSelection();
		}
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestDataSelection = new JFrame();
		frmTestDataSelection.setTitle("Test Data Selection");
		frmTestDataSelection.setBounds(100, 100, 450, 300);
		frmTestDataSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestDataSelection.getContentPane().setLayout(null);
		
		lblSelectTestData = new JLabel("Select Test Data File:");
		lblSelectTestData.setBounds(10, 11, 136, 14);
		frmTestDataSelection.getContentPane().add(lblSelectTestData);
		
		listTestFiles = new JList();
		listTestFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTestFiles.setBounds(10, 33, 283, 129);
		frmTestDataSelection.getContentPane().add(listTestFiles);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(21, 189, 144, 32);
		frmTestDataSelection.getContentPane().add(buttonBack);
		
		btnProceedToResults = new JButton("Proceed to Results >>");
		btnProceedToResults.setBounds(177, 189, 161, 32);
		frmTestDataSelection.getContentPane().add(btnProceedToResults);
		
		scrollPaneTestFiles = new JScrollPane(listTestFiles);
		scrollPaneTestFiles.setBounds(10, 33, 302, 146);
		frmTestDataSelection.getContentPane().add(scrollPaneTestFiles);
		
		frmTestDataSelection.setVisible(true);
		btnProceedToResults.addActionListener(this);
		buttonBack.addActionListener(this);
		
		model = new DefaultListModel();
		
		populateFilesList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnProceedToResults) {
			
			TestDataOption options = TestDataOption.getInstance();
			options.getFrmTestFileOptions().setVisible(true);
			this.frmTestDataSelection.setVisible(false);
		}
		
		else if(e.getSource() == buttonBack) {
			
			TrainingTest test = TrainingTest.getInstance();
			test.getFrmClassificationOption().setVisible(true);
			this.frmTestDataSelection.setVisible(false);
		}
	}
	
	public void populateFilesList() {
		
		model.addElement(new String("diabetes test data"));
		model.addElement(new String("calcemia data"));
		model.addElement(new String("anemia data"));
		model.addElement(new String("blood pressure test data"));
		
		listTestFiles.setModel(model);
	}

	public JFrame getFrmTestDataSelection() {
		return frmTestDataSelection;
	}
	
	public String getListSelection() {
		
		return listTestFiles.getSelectedValue().toString();
	}
	
}
