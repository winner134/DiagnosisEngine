package diagnosis.recommendation.ontology.reasoner.pellet;

import org.mindswap.pellet.jena.PelletInfGraph;



public class PelletInference {
	
	 PelletInfGraph pi;
	
	public PelletInference(PelletInfGraph pellet) {
		
		pi = pellet;
	}
	
	public boolean isConsistent() {
		
		return pi.isConsistent();
	}
	
	public PelletInfGraph getPi() {
		return pi;
	}

	public void classify() {
		
		pi.classify();
	}
	
	public void realize() {
		
		pi.realize();
	}
}
