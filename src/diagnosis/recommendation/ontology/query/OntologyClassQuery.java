package diagnosis.recommendation.ontology.query;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;

import java.io.PrintStream;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextArea;


public class OntologyClassQuery {

	//protected OntModel symptom_disease_model;
    private Map<AnonId,String> m_anonIDs = new HashMap<AnonId, String>();
    private int m_anonCount = 0;
    private List<DI> diseases = new LinkedList<DI>();
    private int counter;
    private int stopSympOfDiseasesQuery = 0;
    private static final String LANG = "en";
    private List<String> symp;
    private OntClass pathologyClass;
    private List<String> hierarchy;
    private JComponent gui;
    private DefaultListModel model;
    
    /** Show the sub-class hierarchy encoded by the given model */
    public void showHierarchy( PrintStream out, JComponent gui, OntModel m ) {
    	
    	hierarchy = new LinkedList<String>();
    	this.gui = gui;
    	model = new DefaultListModel();
    	
        // create an iterator over the root classes that are not anonymous class expressions
        Iterator<OntClass> i = m.listHierarchyRootClasses()
                      .filterDrop( new Filter<OntClass>() {
                                    @Override
                                    public boolean accept( OntClass r ) {
                                        return r.isAnon();
                                    }} );

        while (i.hasNext()) {
        	
        	showClass( out, i.next(), new ArrayList<OntClass>(), 0 );	
        }
    }
    
    /** Search for a class in the given model. The search parameter "className" is compared to the label and synonyms of classes in the model*/ 
    /**
     * @param csName - if selector = 1: this is the label (name) of the class to be searched for (className)
     * 			     - if selector = 2: this is the label (name) of the symptom class for which a disease class is to be found (symptomName)  
     * @param selector - if selector = 1: Search for a class in the given model. The search parameter "className" is compared to the label and synonyms of 
     * 					 classes in the model
     *                 - if selector = 2: Search for a disease class for which the given symptom is a symptom of   
     */
    public void searchHierarchyForClass(PrintStream out, OntModel m, String csName, int selector) {
    	
    	// create an iterator over the root classes that are not anonymous class expressions
        Iterator<OntClass> i = searchClasses(m);
        
        int foundClass = 0;
        
        while (i.hasNext() && foundClass == 0) {
        	
        	if(selector == 1)
        		foundClass = checkClass( out, i.next(), new ArrayList<OntClass>(), 0, csName );
        	else if(selector == 2) {
        		checkDiseaseClass(out, i.next(), new ArrayList<OntClass>(), 0, csName ); 
        	}
        }
    }
    
    private Iterator<OntClass> searchClasses(OntModel m) {
    	
    	// create an iterator over the root classes that are not anonymous class expressions
        Iterator<OntClass> i = m.listHierarchyRootClasses()
                      .filterDrop( new Filter<OntClass>() {
                                    @Override
                                    public boolean accept( OntClass r ) {
                                        return r.isAnon();
                                    }} );
        
        return i;
    }

    /** Present a class, then recurse down to the sub-classes.
     *  Use occurs check to prevent getting stuck in a loop
     */
    protected void showClass( PrintStream out, OntClass cls, List<OntClass> occurs, int depth ) {
        
    	String hierarchyString = this.indentString(depth);
    	hierarchyString = hierarchyString + cls.getLabel(LANG);
    	hierarchy.add(hierarchyString);
    	renderClassDescription( out, cls, depth );
        out.println();
        
        searchSubClasses(out, cls, occurs, depth, 1, null);
    }
    
    private void searchSubClasses(PrintStream out, OntClass cls, List<OntClass> occurs, int depth, int selector, String name) {
    	
    	// recurse to the next level down
        if (cls.canAs( OntClass.class )  &&  !occurs.contains( cls )) {
            for (Iterator<OntClass> i = cls.listSubClasses( true );  i.hasNext(); ) {
                OntClass sub = i.next();

                // we push this expression on the occurs list before we recurse
                occurs.add( cls );
                if(selector == 1)
                	showClass( out, sub, occurs, depth + 1 );
                else if(selector == 2)
                	checkClass( out, sub, occurs, depth + 1, name);
                else if(selector == 3)
                	checkDiseaseClass( out, sub, occurs, depth + 1, name);
                else if(selector == 4)
                	checkSymptomsOfDisease(out, sub, occurs, depth + 1, name);
                
                occurs.remove( cls );
            }
        }
    }
    
    /** Look for a class in the in the hierarchy of cls with the class name className */
    private int checkClass( PrintStream out, OntClass cls, List<OntClass> occurs, int depth, String className ) {
        
    	ExtendedIterator<RDFNode> clsLabels = cls.listLabels(LANG);
    	
    	while (clsLabels.hasNext()) {
    	
    		if(clsLabels.next().asLiteral().getString().equalsIgnoreCase(className)) {
	    	
    			renderClassDescription( out, cls, depth );
    			out.println();
    			pathologyClass = cls;
    			return 1;
    		}
    	
    	}
    	
    	searchSubClasses(out, cls, occurs, depth, 2, className);
        
        return 0;
    }
    
    /** Look for disease classes ,in the hierarchy of OntClass cls, that have the symptom symptomName */ 
    private void checkDiseaseClass( PrintStream out, OntClass cls, List<OntClass> occurs, int depth, String symptomName) {
    
    	//out.println("\nClass = " + cls.getLabel(LANG));
    	
    	try{
    		
    		ExtendedIterator<OntClass> disjoint = cls.listDisjointWith();
    		ExtendedIterator<RDFNode> disjointLabel = null;
    		String symptom = null;
    		
    		while (disjoint.hasNext()) {
		    			
    			disjointLabel = disjoint.next().listLabels(LANG);

		    	while (disjointLabel.hasNext()) {
		    		symptom = disjointLabel.next().asLiteral().toString().replaceAll("@" + LANG, "");
		    		//out.println("Range = " + propR);
		    		if(symptom.equalsIgnoreCase(symptomName))
		    			addToDiseases(cls.getLabel(LANG));	
		    	}	
			}    		
    	}
    	
    	catch(Exception e) {
    		//e.printStackTrace();
    	}
    	
    	finally{
	        
    		searchSubClasses(out, cls, occurs, depth, 3, symptomName);
    	}
    }

    /**
     * <p>Render a description of the given class to the given output stream.</p>
     * @param out A print stream to write to
     * @param c The class to render
     */
    public void renderClassDescription( PrintStream out, OntClass c, int depth ) {
        indent( out, depth);

        if (c.isRestriction()) {
            renderRestriction( out, c.as( Restriction.class ) );
        }
        else {
            if (!c.isAnon()) {
                out.print( "Class " );
                renderURI( out, c.getModel(), c.getURI() );
                out.print( ' ' );
                renderClassLabel(out, c);
            }
            else if (c.isAnon()) {
                renderAnonymous( out, c, "class" );
            }
        }
    }
    
    /** Look for diseases that the list of symptoms List<String> symptoms and return a list of
     * these disease names
     * 
     * @param symptoms - list of symptoms
     * @param m - ontology model to look in for diseases
     * @param cls - ontology class hierarchy to look in for diseases
     * @return - list of diseases showing the given list of symptoms
     */
    public List<String> linkSymptomsToDiseases(PrintStream out, OntModel m, OntClass cls, List<String> symptoms) {
    	
    	counter = 0;
    	List<String> dis = new LinkedList<String>();
    	
    	for (int i = 0; i < symptoms.size(); i++) {
    		
    		if(m != null && cls == null)
    			this.searchHierarchyForClass(out, m, symptoms.get(i), 2);
    		else if(m == null && cls != null)
    			this.searchSubClasses(out, cls, new ArrayList<OntClass>(), 0, 3, symptoms.get(i));
    		counter++;
    	}
    	
    	/*out.println("DISEASE LIST SIZE = " + diseases.size());
    	for(int i = 0; i < diseases.size(); i++) {
    		
    		out.println(diseases.get(i).getDisease());
    	}*/
    	
    	List<List<DI>> diseaseLists = new LinkedList<List<DI>>();
    	List <DI> list = new LinkedList<DI>();
    	int j_int = 0;
    	
    	for (int i = 0; i <= this.findMaxIndex(); i++) {
 
    		list = new LinkedList<DI>();
    		
    		for (int j = j_int; j < diseases.size(); j++) {

    			if(diseases.get(j).getIndex() == i) {
   
    				list.add(diseases.get(j));
    				j_int++;
    			}
    		}
    		
    		//out.println("SIZE OF LIST " + i + " = " + list.size());
    		diseaseLists.add(list);
    	}
    	
    	for(int i = 0; i < diseaseLists.size(); i++) {
    		
    		for(int j = 0; j < diseaseLists.get(i).size(); j++) {
    			
    			if(searchDiseaseLists(diseaseLists, diseaseLists.get(i).get(j).getDisease()) == 1)
    				addToList(dis, diseaseLists.get(i).get(j).getDisease());
    		}
    	}
    	
    	diseases = new LinkedList<DI>();
    	
    	return dis;
    }
    
    /** Return the symptoms for the given disease */
    public List<String> getSymptomsOfDisease(PrintStream out, OntModel m, String disease) {
    	
    	symp = new LinkedList<String>(); 	
    	Iterator<OntClass> i = this.searchClasses(m);
    	
    	do {
    		
    		checkSymptomsOfDisease(out, i.next(), new ArrayList<OntClass>(), 0, disease);
    	
    	} while(i.hasNext() && stopSympOfDiseasesQuery == 0);
    	
    	stopSympOfDiseasesQuery = 0;
    	
    	return symp;
    }
    
    /** 1 - Look for diseases in OntModel m that have the given knownSymptoms
     *  2 - Allow user to select a disease from the list of diseases having knownSymptoms
     *  3 - Find missing symptoms for the selected disease. Note that
     *      missingSymptoms = all the symptoms of the selected disease - knownSymptoms
     */
    public List<String> getMissingSymptoms(PrintStream out, OntModel m, List<String> knownSymptoms){
    	
    	List<String> diseases = this.linkSymptomsToDiseases(out, m, null, knownSymptoms);
    	List<String> allSymptoms = null;
    	List<String> missingSymptoms = new LinkedList<String>();
    	int chooseDisease = selectDisease(diseases) - 1;
    	
    	for(int i = 0; i < diseases.size(); i++) {
    		
    		if(i == chooseDisease) {
    			
    			allSymptoms = this.getSymptomsOfDisease(out, m, diseases.get(i));
    			break;
    		}
    	}
    	
    	for(int i = 0; i < allSymptoms.size(); i++) {
    		
    		int found = 0;
    		
    		for(int j = 0; j < knownSymptoms.size(); j++) {
    			
    			if(knownSymptoms.get(j).equalsIgnoreCase(allSymptoms.get(i))) {
    				found = 1;
    			}
    		}
    		
    		if(found == 0 && !missingSymptoms.contains(allSymptoms.get(i)))
    			missingSymptoms.add(allSymptoms.get(i));
    	}
    	
    	return missingSymptoms;
    }
    
    
    public List<String> getMissingSymptoms(PrintStream out, OntModel m, List<String> knownSymptoms, String disease) {
    	
    	List<String> allSymptoms = this.getSymptomsOfDisease(out, m, disease);
    	List<String> missingSymptoms = new LinkedList<String>();
    	
    	for(int i = 0; i < allSymptoms.size(); i++) {
    		
    		int found = 0;
    		
    		for(int j = 0; j < knownSymptoms.size(); j++) {
    			
    			if(knownSymptoms.get(j).equalsIgnoreCase(allSymptoms.get(i))) {
    				found = 1;
    			}
    		}
    		
    		if(found == 0 && !missingSymptoms.contains(allSymptoms.get(i)))
    			missingSymptoms.add(allSymptoms.get(i));
    	}
    	
    	return missingSymptoms;
    	
    }
    
    public List<String> linkSymptomsToDiseasesOfPathology(PrintStream out, OntModel m, List<String> symptoms, String pathology) {
    	
    	List<String> dis = new LinkedList<String>();
    	this.searchHierarchyForClass(out, m, pathology, 1);
    	dis = this.linkSymptomsToDiseases(out, null, pathologyClass, symptoms);
    	pathologyClass = null;
    	
    	return dis;
    }
    
    private int selectDisease(List<String> diseases) {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	for(int i = 0; i < diseases.size(); i++) {
    		
    		System.out.println((i+1) + " - " + diseases.get(i));
    	}
    	
    	System.out.println("\n" + "Enter number of disease you would like to find the missing symptoms for: ");
    	int choice = scan.nextInt();
    	
    	return choice;
    }
    
    private void checkSymptomsOfDisease(PrintStream out, OntClass ontClass, List<OntClass> occurs, int depth, String disease) {
    	
    	ExtendedIterator<RDFNode> clsLabels = ontClass.listLabels(LANG);
    	Iterator<OntClass> clsDisjoint;
    	String sympt;
    	
    	try{
	    	while (clsLabels.hasNext()) {
	    	
	    		if(clsLabels.next().asLiteral().getString().equalsIgnoreCase(disease)) {
	    			
	    			clsDisjoint = ontClass.listDisjointWith();
	    			while(clsDisjoint.hasNext()) {
	    	    		sympt = clsDisjoint.next().getLabel(LANG);
	    				if(!symp.contains(sympt))
	    					symp.add(sympt);
	    	    	}
	    			
	    			stopSympOfDiseasesQuery = 1;
	    		}	
	    	}
	    	
	    	this.searchSubClasses(out, ontClass, occurs, depth, 4, disease);
    	}
    	
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
    	
	}

	private int searchDiseaseLists(List<List<DI>> dl, String disease) {
    	
    	int counter = 0;
    	
    	for (int i = 0; i < dl.size(); i++) {
    	
    		for (int j = 0; j < dl.get(i).size(); j++) {
    			
    			if(disease.equalsIgnoreCase(dl.get(i).get(j).getDisease()))
    				counter++;
    		}
    		
    	}
    	
    	if(counter == dl.size())
    		return 1;
    	else
    		return 0;
    }
    
    private int findMaxIndex() {
    	
    	int max = 0;
    	
    	for(int i = 0; i < diseases.size(); i++) {
    		
    		if(diseases.get(i).getIndex() > max)
    			max = diseases.get(i).getIndex();
    	}
    	
    	return max;
    }
    
    private void addToDiseases(String d) {
    	
    	int diseaseExistsinList = 0;
    	
    	for (int i = 0; i < diseases.size(); i++) {
    		
    		if(diseases.get(i).getDisease().equalsIgnoreCase(d) && diseases.get(i).getIndex() == counter) {
    			 diseaseExistsinList = 1;
    			 break;
    		}
    	}
    	
    	if(diseaseExistsinList == 0 && d != null)
    		diseases.add(new DI(d, counter));
    }
    
    private void addToList(List<String> l, String d) {
    	
    	int diseaseExistsinList = 0;
    	
    	for(int i = 0; i < l.size(); i++) {
    		
    		if(l.get(i).equalsIgnoreCase(d)) {
    			diseaseExistsinList = 1;
   			 	break;
    		}
    	}
    	
    	if(diseaseExistsinList == 0)
    		l.add(d);
    }

    public OntClass getPathologyClass() {
		return pathologyClass;
	}
    
	public List<String> getHierarchy() {
		return hierarchy;
	}


	/**
     * <p>Handle the case of rendering a restriction.</p>
     * @param out The print stream to write to
     * @param r The restriction to render
     */
    protected void renderRestriction( PrintStream out, Restriction r ) {
        if (!r.isAnon()) {
        	
            out.print( "Restriction " );
            renderURI( out, r.getModel(), r.getURI() );
        }
        else {
            renderAnonymous( out, r, "restriction" );
        }

        out.print( " on property " );
        renderURI( out, r.getModel(), r.getOnProperty().getURI() );
    }

    /** Render a URI */
    protected void renderURI( PrintStream out, PrefixMapping prefixes, String uri ) {
        out.print( prefixes.shortForm( uri ) );
    }
    
    /** Render the class label */
    protected void renderClassLabel(PrintStream out, OntClass c) {
    	
    	out.println(c.getLabel("en"));
    	
    	if(gui != null) {
    		
    		if(gui instanceof JTextArea) {
    			
    			JTextArea textArea = (JTextArea) gui;
    			textArea.setText("");
    			textArea.append(c.getLabel("en"));
    		}
    		
    		else if(gui instanceof JList) {
    			
    			JList list = (JList) gui;
    			model.addElement(new String(c.getLabel("en")));
    			list.setModel(model);
    		}
    	}
    }
    
    /** Render an anonymous class or restriction */
    protected void renderAnonymous( PrintStream out, Resource anon, String name ) {
        String anonID = m_anonIDs.get( anon.getId() );
        if (anonID == null) {
            anonID = "a-" + m_anonCount++;
            m_anonIDs.put( anon.getId(), anonID );
        }

        out.print( "Anonymous ");
        out.print( name );
        out.print( " with ID " );
        out.print( anonID );
    }

    /** Generate the indentation */
    protected void indent( PrintStream out, int depth ) {
        for (int i = 0;  i < depth; i++) {
            out.print( "  " );
        }
    }
    
    protected String indentString(int depth) {
    	
    	String ind = new String("");
    	
    	for (int i = 0;  i < depth; i++) {
    		
    		ind = ind + "  ";
        }
    	
    	return ind;
    }
}
