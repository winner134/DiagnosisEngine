package diagnosis.recommendation.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.ejb.packaging.FileZippedJarVisitor;

import diagnosis.recommendation.ontology.dataMining.ClassificationAssociation;

public class DMData implements ActionListener {

	private JFrame frmDataMiningData;
	private JList filesList;
	private JRadioButton rdbtnFT;
	private JButton btnResults;
	private JButton buttonBack;
	private JRadioButton rdbtnJrip;
	private JRadioButton rdbtnBayesNet;
	private JRadioButton rdbtnJ48;
	private JLabel lblSelectClassificationAlgorithm;
	private JScrollPane scrollPane;
	private JLabel lblSelectDataFile;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnIbk;
	private static DMData instance;
	private DefaultListModel model;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMData window = new DMData();
					window.frmDataMiningData.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private DMData() {
		initialize();
	}
	
	public static DMData getInstance() {
		
		if(instance == null) {
			instance = new DMData();
		}
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmDataMiningData = new JFrame();
		frmDataMiningData.setTitle("Data Mining Data & Classification Algorithm Selection");
		frmDataMiningData.setBounds(100, 100, 523, 300);
		frmDataMiningData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataMiningData.getContentPane().setLayout(null);
		
		lblSelectDataFile = new JLabel("Select Data File:");
		lblSelectDataFile.setBounds(10, 11, 136, 14);
		frmDataMiningData.getContentPane().add(lblSelectDataFile);
		
		filesList = new JList();
		filesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filesList.setBounds(10, 22, 235, 118);
		frmDataMiningData.getContentPane().add(filesList);
		
		scrollPane = new JScrollPane(filesList);
		scrollPane.setBounds(10, 22, 253, 131);
		frmDataMiningData.getContentPane().add(scrollPane);
		
		lblSelectClassificationAlgorithm = new JLabel("Select Classification Algorithm: ");
		lblSelectClassificationAlgorithm.setBounds(273, 11, 185, 14);
		frmDataMiningData.getContentPane().add(lblSelectClassificationAlgorithm);
		
		rdbtnJ48 = new JRadioButton("J48 Decision Tree");
		rdbtnJ48.setBounds(269, 32, 163, 23);
		frmDataMiningData.getContentPane().add(rdbtnJ48);
		
		rdbtnBayesNet = new JRadioButton("Bayes Net");
		rdbtnBayesNet.setBounds(269, 58, 109, 23);
		frmDataMiningData.getContentPane().add(rdbtnBayesNet);
		
		rdbtnJrip = new JRadioButton("JRip");
		rdbtnJrip.setBounds(269, 83, 109, 23);
		frmDataMiningData.getContentPane().add(rdbtnJrip);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(110, 191, 125, 32);
		frmDataMiningData.getContentPane().add(buttonBack);
		
		btnResults = new JButton("Proceed to Results");
		btnResults.setBounds(245, 191, 153, 32);
		frmDataMiningData.getContentPane().add(btnResults);
		
		rdbtnFT = new JRadioButton("FT");
		rdbtnFT.setBounds(269, 109, 77, 23);
		frmDataMiningData.getContentPane().add(rdbtnFT);
		
		rdbtnIbk = new JRadioButton("IBK");
		rdbtnIbk.setBounds(269, 135, 109, 23);
		frmDataMiningData.getContentPane().add(rdbtnIbk);
		
		buttonGroup.add(rdbtnJ48);
		buttonGroup.add(rdbtnBayesNet);
		buttonGroup.add(rdbtnJrip);
		buttonGroup.add(rdbtnFT);
		buttonGroup.add(rdbtnIbk);
		
		btnResults.addActionListener(this);
		buttonBack.addActionListener(this);
		
		model = new DefaultListModel();
		
		populateFilesList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnResults) {
			
			try {
				
				FileInputStream calories = new FileInputStream(new File("C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/Calories.arff"));
				FileInputStream calcium = new FileInputStream(new File("C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/Calcium.arff"));
				FileInputStream brainSize = new FileInputStream(new File("C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/Brainsize.arff"));
				
				if(rdbtnJ48.isSelected() && ((String) filesList.getSelectedValue()).equalsIgnoreCase("diabetes.arff")) {
					
					
					ClassificationAssociation diabetesClassifier = new ClassificationAssociation();
					diabetesClassifier.setTraining("C:/ActionRecognition/RES/Data Mining/WEKA/os-weka1-Examples/diabetes.arff");
					//diabetesClassifier.setClassifier();
				}
			} 
			
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			catch(Exception e2) {
				
				e2.printStackTrace();
			}
		}
		
		else if(e.getSource() == buttonBack) {
			
		}
	}

	public JFrame getFrmDataMiningData() {
		return frmDataMiningData;
	}
	
	private void populateFilesList() {
		
		model.addElement(new String("diabetes.arff"));
		model.addElement(new String("calories.arff"));
		model.addElement(new String("calcium.arff"));
		model.addElement(new String("Brainsize.arff"));
		
		filesList.setModel(model);
	}
	
}
