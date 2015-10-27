package diagnosis.recommendation.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;


public class Settings implements ActionListener{

	private static Settings instance;
	private JFrame frmSettings;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblInferenceMethod;
	private JRadioButton rdbtnDirect;
	private JRadioButton rdbtnIndirect;
	private JPanel panel;
	private JButton btnDone;
	private static final int DIRECT_INFERENCE = 1;
	private static final int INDIRECT_INFERENCE = 2;
	private int inference_mode;

	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings();
					window.frmSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	private Settings() {
		initialize();
	}
	
	public static Settings getInstance() {
		
		if(instance == null)
			instance = new Settings();
		
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSettings = new JFrame();
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 450, 300);
		frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSettings.getContentPane().setLayout(null);
		
		lblInferenceMethod = new JLabel("Inference Method:");
		lblInferenceMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInferenceMethod.setBounds(10, 11, 130, 14);
		frmSettings.getContentPane().add(lblInferenceMethod);
		
		rdbtnDirect = new JRadioButton("Direct Inferencing");
		buttonGroup.add(rdbtnDirect);
		rdbtnDirect.setBounds(6, 32, 177, 23);
		frmSettings.getContentPane().add(rdbtnDirect);
		
		rdbtnIndirect = new JRadioButton("Indirect Inferencing (using Pellet Reasoner)");
		buttonGroup.add(rdbtnIndirect);
		rdbtnIndirect.setBounds(6, 58, 274, 23);
		frmSettings.getContentPane().add(rdbtnIndirect);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 280, 97);
		frmSettings.getContentPane().add(panel);
		
		btnDone = new JButton("Done");
		btnDone.setBounds(164, 228, 89, 23);
		frmSettings.getContentPane().add(btnDone);
		
		btnDone.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnDone) {
			
			if(rdbtnDirect.isSelected())
				inference_mode = DIRECT_INFERENCE;
			else if(rdbtnIndirect.isSelected())
				inference_mode = INDIRECT_INFERENCE;
			
			DiagnosisGUI window = DiagnosisGUI.getInstance();
			window.setInference_mode(inference_mode);
			window.getFrmSemanticDiagonsisRecommender().setVisible(true);
			frmSettings.setVisible(false);
			
		}
	}

	public JFrame getFrmSettings() {
		return frmSettings;
	}
	
}

