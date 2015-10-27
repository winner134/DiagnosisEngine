

import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import diagnosis.recommendation.gui.DiagnosisGUI;
import diagnosis.recommendation.gui.InvestigationQuestions;
import diagnosis.recommendation.gui.R2;
import diagnosis.recommendation.gui.R34;
import diagnosis.recommendation.gui.R5;
import diagnosis.recommendation.gui.R6;
import diagnosis.recommendation.ontology.query.OntologyClassQuery;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 try{
			  
			 OntModel m  = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM);
			 m.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl");
			 m.getDocumentManager().addAltEntry(null, "c:/ActionRecognition/Ontology/DSO.owl" );
				 
			 m.read("file:/C:/ActionRecognition/Ontology/DSO.owl");
			 
			 OntologyClassQuery q = new OntologyClassQuery();
			 
			 DiagnosisGUI window = DiagnosisGUI.getInstance();
			 window.setQueryModel(q);
			 window.setOntModel(m);

		 }
		 
		 catch(Exception e) {
			 e.printStackTrace();
		 }
	}

}