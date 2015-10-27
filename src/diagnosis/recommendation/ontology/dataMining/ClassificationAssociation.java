package diagnosis.recommendation.ontology.dataMining;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

import weka.associations.Apriori;
import weka.associations.Associator;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class ClassificationAssociation {

	  /** the classifier used internally */
	  protected Classifier m_Classifier = null;
	  
	  protected Associator m_Associator = null;
	  
	 /** the training file */
	  protected String m_TrainingFile = null;

	  /** the training instances */
	  protected Instances m_Training = null;
	  
	  /**
	   * sets the file to use for training
	   */
	  public void setTraining(String name) throws Exception {
	    m_TrainingFile = name;
	    m_Training     = new Instances(
	                        new BufferedReader(new FileReader(m_TrainingFile)));
	    m_Training.setClassIndex(m_Training.numAttributes() - 1);
	    
	    if(m_Training == null)
	    	System.out.println("No Training Data");
	  }
	  
	  /**
	   * sets the classifier to use
	   * @param name        the classname of the classifier
	   * @param options     the options for the classifier
	   */
	  public void setClassifier(int classifier) throws Exception {
		  
		  if(classifier == 1)
			  m_Classifier = new J48();
		  else if(classifier == 2) 
			  m_Classifier = new JRip();
		  else if(classifier == 3)
			  m_Classifier = new NaiveBayes();
		  else if(classifier == 4)
			  m_Classifier = new NBTree();
	  }
	  
	  public void setAssociator() throws Exception {
		  
		  Apriori associator = new Apriori();
		  associator.setNumRules(100);
		  m_Associator = associator;
	  }
	  
	  public String executeClassifier() {
		  
		  try {
			
			m_Classifier.buildClassifier(m_Training);
			Evaluation eval = new Evaluation(m_Training);
			eval.crossValidateModel(m_Classifier, m_Training, 20, m_Training.getRandomNumberGenerator(1));
			//System.out.println(eval.toSummaryString());
			//System.out.println(eval.toMatrixString());
			//System.out.println(m_Classifier.toString());
			this.getClassifierTree();
			
			return eval.toSummaryString() + "\n" + eval.toMatrixString() + "\n" + m_Classifier.toString();
		  } 
		  
		  catch (Exception e) {
			  
			e.printStackTrace();
		  }
		  
		  return null;
	  }
	  
	  public String executeAssociation() {
		  
		  try {
			  
			  Discretize discrete = new Discretize();
			  discrete.setInputFormat(m_Training);
			  Instances f_Training = Filter.useFilter(m_Training, discrete);
			  m_Associator.buildAssociations(f_Training);
			  System.out.println(m_Associator.toString());
			  return m_Associator.toString();
		  } 
		  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  
		  return null;
		  
	  }
	  
	  public LinkedList<Double> classifyTestData(String test) {
		  
		  LinkedList<Double> predictions = new LinkedList<Double>();
		  
		  try{
			  
			  m_Classifier.buildClassifier(m_Training);
			  // evaluate classifier and print some statistics
			  Instances m_Test = new Instances(new BufferedReader(new FileReader(test)));
			  m_Test.setClassIndex(m_Test.numAttributes() - 1);
			  //System.out.println(m_Classifier.toString());
	
			  if(m_Training == null)
				  System.out.println("No Training Data");
			  
			  else {
				  
				  for(int i = 0; i < m_Test.numInstances(); i++) {
					  
					  predictions.add(m_Classifier.classifyInstance(m_Test.instance(i)));

				  }
			  }
			  
		  }
		  
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		  
		  return predictions;
	  }
	  
	  public void getClassifierTree() {
		  
		  try {
			
			  if(m_Classifier instanceof J48) {
				  
				  J48 classifier = (J48) m_Classifier;
				  classifier.buildClassifier(m_Training);
				  
				     // display classifier
				     final javax.swing.JFrame jf = new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
				     Toolkit toolkit =  Toolkit.getDefaultToolkit ();
				     Dimension dim = toolkit.getScreenSize();
				     jf.setSize(dim.width, dim.height);
				     jf.getContentPane().setLayout(new BorderLayout());
				     TreeVisualizer tv = new TreeVisualizer(null, classifier.graph(), new PlaceNode2());
				     tv.fitToScreen();
				     jf.getContentPane().add(tv, BorderLayout.CENTER);
				     jf.addWindowListener(new java.awt.event.WindowAdapter() {
				       public void windowClosing(java.awt.event.WindowEvent e) {
				         jf.dispose();
				       }
				     });
				 
				     jf.setVisible(true);
				     tv.fitToScreen();

			  }

		  } 
		  
		  catch (Exception e) {
			  
			e.printStackTrace();
		}
	  }

}
