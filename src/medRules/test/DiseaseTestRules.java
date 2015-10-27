package medRules.test;
import java.util.List;


import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;


public class DiseaseTestRules {

	
	public void fireDiseaseTestRules(List<Disease> d, List<Test> t, List<TestResult> tr) {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbuilder.add( ResourceFactory.newClassPathResource( "medRules.drl", DiseaseTestRules.class ),
		ResourceType.DRL );
		
		if ( kbuilder.hasErrors() ) {
			System.err.println( kbuilder.getErrors().toString() );
		}
		
		kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		for(int i = 0; i < d.size(); i++) {
			ksession.insert(d.get(i));
		}
		
		for(int i = 0; i < t.size(); i++) {
			ksession.insert(t.get(i));
		}
		
		for(int i = 0; i < tr.size(); i++) {			
			ksession.insert(tr.get(i));
		}
		
		ksession.fireAllRules();
		ksession.dispose();
	}
}
