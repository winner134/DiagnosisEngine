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

import diagnosis.recommendation.ontology.dataMining.ClassificationAssociation;

public class DMDataSelection implements ActionListener {

	private JFrame frmSelectionOfData;
	private JLabel labelFile;
	private JList listFiles;
	private JButton buttonBack;
	private JButton buttonResults;
	private JScrollPane scrollPaneFilesList;
	private static DMDataSelection INSTANCE = null;
	private boolean algorithmSelection = false;
	private DefaultListModel model;
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
					DMDataSelection window = new DMDataSelection();
					window.frmSelectionOfData.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DMDataSelection() {
		initialize();
	}
	
	public static DMDataSelection getInstance() {
				
		if(INSTANCE == null) {
			
			INSTANCE = new DMDataSelection();
		}
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSelectionOfData = new JFrame();
		frmSelectionOfData.setTitle("Selection of Data File in ARFF Format");
		frmSelectionOfData.setBounds(100, 100, 393, 280);
		frmSelectionOfData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSelectionOfData.getContentPane().setLayout(null);
		
		labelFile = new JLabel("Select Data File:");
		labelFile.setBounds(10, 11, 136, 14);
		frmSelectionOfData.getContentPane().add(labelFile);
		
		listFiles = new JList();
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFiles.setBounds(10, 28, 283, 129);
		frmSelectionOfData.getContentPane().add(listFiles);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(31, 187, 144, 32);
		frmSelectionOfData.getContentPane().add(buttonBack);
		
		buttonResults = new JButton("Proceed to Results");
		buttonResults.setBounds(185, 187, 153, 32);
		frmSelectionOfData.getContentPane().add(buttonResults);
		
		scrollPaneFilesList = new JScrollPane();
		scrollPaneFilesList.setBounds(10, 23, 298, 153);
		frmSelectionOfData.getContentPane().add(scrollPaneFilesList);
		
		this.frmSelectionOfData.setVisible(true);
		buttonBack.addActionListener(this);
		buttonResults.addActionListener(this);
		
		model = new DefaultListModel();
		
		populateFilesList();
	}

	public JFrame getFrmSelectionOfData() {
		return frmSelectionOfData;
	}

	public boolean isAlgorithmSelection() {
		return algorithmSelection;
	}

	public void setAlgorithmSelection(boolean algorithmSelection) {
		this.algorithmSelection = algorithmSelection;
	}
	
	public void populateFilesList() {
		
		model.addElement(new String("diabetes.arff"));
		model.addElement(new String("diabetes 2.arff"));
		model.addElement(new String("calcemia.arff"));
		model.addElement(new String("anemia.arff"));
		model.addElement(new String("blood pressure.arff"));
		
		listFiles.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == buttonBack) {
			
			if(this.isAlgorithmSelection()) {
				
				D1Algorithm d1 = D1Algorithm.getInstance();
				d1.getFrmDClassification().setVisible(true);
				this.getFrmSelectionOfData().setVisible(false);
				this.setAlgorithmSelection(false);
			}
			
			else {
				
				DMRelations relations = DMRelations.getInstance();
				this.getFrmSelectionOfData().setVisible(false);
				relations.getFrmDifferentialDiagnosisRecommendation().setVisible(true);
			}
		}
		
		else if(e.getSource() == buttonResults) {
			
			DMResults results = DMResults.getInstance();
			String filePath = null;
			
			if(listFiles.getSelectedValue().toString().equalsIgnoreCase("diabetes.arff")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_diabetes2.arff";
			}
			
			else if(listFiles.getSelectedValue().toString().equalsIgnoreCase("diabetes 2.arff")) {
				
				filePath = "C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/diabetes.arff";
			}
			
			else if(listFiles.getSelectedValue().toString().equalsIgnoreCase("calcemia.arff")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_calcemia.arff";
			}
			
			else if(listFiles.getSelectedValue().toString().equalsIgnoreCase("anemia.arff")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_anemia2.arff";
			}
			
			else if(listFiles.getSelectedValue().toString().equalsIgnoreCase("blood pressure.arff")) {
				
				filePath = "C:/NIS 2009/adult_demographic_diagnosis_corrected_hyperhypoTension3.arff";
			}
			
			ClassificationAssociation classifier = new ClassificationAssociation();
			
			if(this.isAlgorithmSelection()) {
				
				D1Algorithm alg = D1Algorithm.getInstance();
				int selection = alg.getSelectedAlgorithm();
				
				if(selection == J48) {
					
					try {
						classifier.setTraining(filePath);
						classifier.setClassifier(1);
						String classification = classifier.executeClassifier();
						results.printToTextArea(classification);
					}
					
					catch(Exception e1) {
						
						e1.printStackTrace();
					}
				}
				
				else if(selection == JRIP) {
					
					try {
						classifier.setTraining(filePath);
						classifier.setClassifier(2);
						String classification = classifier.executeClassifier();
						results.printToTextArea(classification);
					}
					
					catch(Exception e2) {
						
						e2.printStackTrace();
					}
				}
				
				else if(selection == NB) {
					
					try {
						classifier.setTraining(filePath);
						classifier.setClassifier(3);
						String classification = classifier.executeClassifier();
						results.printToTextArea(classification);
					}
					
					catch(Exception e3) {
						
						e3.printStackTrace();
					}
				}
				
				else if(selection == NBTree) {
					
					try {
						classifier.setTraining(filePath);
						classifier.setClassifier(3);
						String classification = classifier.executeClassifier();
						results.printToTextArea(classification);
					}
					
					catch(Exception e3) {
						
						e3.printStackTrace();
					}
				}
			}
			
			else {
				
				try {
					classifier.setTraining(filePath);
					classifier.setAssociator();
					String classification = classifier.executeAssociation();
					results.printToTextArea(classification);
				}
				
				catch(Exception e3) {
					
					e3.printStackTrace();
				}
			}
			
			results.getFrmOntologyDrivenData().setVisible(true);
			this.getFrmSelectionOfData().setVisible(false);
		}
	}
	
}
