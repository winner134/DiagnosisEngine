package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class D1Algorithm implements ActionListener{

	private JFrame frmDClassification;
	private JLabel lblSelectClassificationAlgorithm;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnNB;
	private JRadioButton rdbtnJ48;
	private JRadioButton rdbtnJRIP;
	private JButton buttonBack;
	private JRadioButton rdbtnNBTree;
	private JButton btnProceed;
	private static D1Algorithm INSTANCE = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D1Algorithm window = new D1Algorithm();
					window.frmDClassification.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private D1Algorithm() {
		initialize();
	}
	
	public static D1Algorithm getInstance() {
		
		if(INSTANCE == null) {
			
			INSTANCE = new D1Algorithm();
		}
		
		return INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmDClassification = new JFrame();
		frmDClassification.setTitle("D1 - Classification Algorithm Selection");
		frmDClassification.setBounds(100, 100, 390, 255);
		frmDClassification.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDClassification.getContentPane().setLayout(null);
		
		lblSelectClassificationAlgorithm = new JLabel("Select Classification Algorithm:");
		lblSelectClassificationAlgorithm.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectClassificationAlgorithm.setBounds(10, 11, 243, 14);
		frmDClassification.getContentPane().add(lblSelectClassificationAlgorithm);
		
		rdbtnJ48 = new JRadioButton("J48  (Decision Tree Classification)");
		rdbtnJ48.setBounds(10, 32, 222, 23);
		frmDClassification.getContentPane().add(rdbtnJ48);
		
		rdbtnJRIP = new JRadioButton("JRIP (Rule-based Classification)");
		rdbtnJRIP.setBounds(10, 56, 222, 23);
		frmDClassification.getContentPane().add(rdbtnJRIP);
		
		rdbtnNB = new JRadioButton("Naive Bayes (Probabilistic Classification)");
		rdbtnNB.setBounds(10, 84, 257, 23);
		frmDClassification.getContentPane().add(rdbtnNB);
		
		rdbtnNBTree = new JRadioButton("NBTree (Probabilistic Decision Tree Classification)");
		rdbtnNBTree.setBounds(10, 110, 358, 23);
		frmDClassification.getContentPane().add(rdbtnNBTree);
		
		buttonBack = new JButton("<< Go Back");
		buttonBack.setBounds(27, 167, 154, 29);
		frmDClassification.getContentPane().add(buttonBack);
		
		btnProceed = new JButton("Proceed >>");
		btnProceed.setBounds(189, 167, 155, 29);
		frmDClassification.getContentPane().add(btnProceed);
		
		buttonGroup.add(rdbtnJ48);
		buttonGroup.add(rdbtnJRIP);
		buttonGroup.add(rdbtnNB);
		buttonGroup.add(rdbtnNBTree);
		buttonBack.addActionListener(this);
		btnProceed.addActionListener(this);
		frmDClassification.setVisible(true);
	}
	
	public int getSelectedAlgorithm() {
		
		if(rdbtnJ48.isSelected())
			return 1;
		else if(rdbtnJRIP.isSelected())
			return 2;
		else if(rdbtnNB.isSelected())
			return 3;
		else if(rdbtnNBTree.isSelected())
			return 4;
		else
			return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btnProceed) {
			
			TrainingTest options = TrainingTest.getInstance();
			options.getFrmClassificationOption().setVisible(true);
			this.frmDClassification.setVisible(false);
		}
		
		else if(e.getSource() == buttonBack) {
			
			DMRelations relations = DMRelations.getInstance();
			relations.getFrmDifferentialDiagnosisRecommendation().setVisible(true);
			this.frmDClassification.setVisible(false);
		}
	}

	public JFrame getFrmDClassification() {
		return frmDClassification;
	}
	
}
