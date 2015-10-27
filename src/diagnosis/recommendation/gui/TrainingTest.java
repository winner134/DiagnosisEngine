package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainingTest implements ActionListener{

	private JFrame frmClassificationOption;
	private JLabel lblSelectClassificationOption;
	private JRadioButton rdbtnUseTrainingData;
	private JRadioButton rdbtnClassification;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnProceed;
	private JButton buttonBack;
	private static TrainingTest INSTANCE = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainingTest window = new TrainingTest();
					window.frmClassificationOption.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private TrainingTest() {
		initialize();
	}
	
	public static TrainingTest getInstance() {
		
		if(INSTANCE == null)
			INSTANCE = new TrainingTest();
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClassificationOption = new JFrame();
		frmClassificationOption.setTitle("Classification Option");
		frmClassificationOption.setBounds(100, 100, 563, 213);
		frmClassificationOption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClassificationOption.getContentPane().setLayout(null);
		
		lblSelectClassificationOption = new JLabel("Select Classification Option:");
		lblSelectClassificationOption.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectClassificationOption.setBounds(10, 11, 253, 14);
		frmClassificationOption.getContentPane().add(lblSelectClassificationOption);
		
		rdbtnUseTrainingData = new JRadioButton("Use Training Data to Find Trends in the Data");
		rdbtnUseTrainingData.setBounds(10, 43, 334, 23);
		frmClassificationOption.getContentPane().add(rdbtnUseTrainingData);
		
		rdbtnClassification = new JRadioButton("Perform Classification - Predict the Value of the Class Variable for given Test Data");
		rdbtnClassification.setBounds(10, 69, 518, 23);
		frmClassificationOption.getContentPane().add(rdbtnClassification);
		
		btnProceed = new JButton("Proceed >>");
		btnProceed.setBounds(273, 110, 138, 35);
		frmClassificationOption.getContentPane().add(btnProceed);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(125, 110, 138, 35);
		frmClassificationOption.getContentPane().add(buttonBack);
		
		frmClassificationOption.setVisible(true);
		btnProceed.addActionListener(this);
		buttonBack.addActionListener(this);
		buttonGroup.add(rdbtnClassification);
		buttonGroup.add(rdbtnUseTrainingData);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnProceed) {
			
			if(rdbtnUseTrainingData.isSelected()) {
				
				DMDataSelection data = DMDataSelection.getInstance();
				data.getFrmSelectionOfData().setVisible(true);
				this.frmClassificationOption.setVisible(false);
				data.setAlgorithmSelection(true);
			}
			
			else if(rdbtnClassification.isSelected()) {
				
				TestDataSelection data1 = TestDataSelection.getInstance();
				data1.getFrmTestDataSelection().setVisible(true);
				this.frmClassificationOption.setVisible(false);
			}
		}
		
		else if(e.getSource() == buttonBack) {
			
			D1Algorithm alg = D1Algorithm.getInstance();
			alg.getFrmDClassification().setVisible(true);
			this.frmClassificationOption.setVisible(false);
		}
	}

	public JFrame getFrmClassificationOption() {
		return frmClassificationOption;
	}
	
	
}
