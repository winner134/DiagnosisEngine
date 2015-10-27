package medRules.data;

import java.util.LinkedList;
import java.util.List;

import medRules.test.Disease;
import medRules.test.Test;
import medRules.test.TestResult;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class RetrieveTestData {
	
	private Session session;
	private Session session1;
	private Session session2;
	private SessionFactory factory;
	
	public RetrieveTestData() {
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		
		config.addAnnotatedClass(Disease.class);
		config.addAnnotatedClass(TestResult.class);
		config.addAnnotatedClass(Test.class);
		config.configure("hibernate.cfg.xml");
		
		factory = config.buildSessionFactory();
	
	}
	
	public List<Test> getTests() {
		
		List<Test> tests = new LinkedList<Test>();
		
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		int i = 1;
		while(session.get(Test.class, new Integer(i)) != null) {
			
			tests.add((Test) session.get(Test.class, new Integer(i)));
			i++;
		}
		
		session.getTransaction().commit();
		
		return tests;
	
	}
	
	public List<Disease> getDiseases() {
		
		List<Disease> dis = new LinkedList<Disease>();
		
		session1 = factory.getCurrentSession();
		
		session1.beginTransaction();
		
		int i = 1;
		while(session1.get(Disease.class, new Integer(i)) != null) {
			
			dis.add((Disease) session1.get(Disease.class, new Integer(i)));
			i++;
		}
		
		session1.getTransaction().commit();
		
		return dis;
	}
	
	public List<TestResult> getTestResults() {
		
		List<TestResult> tr = new LinkedList<TestResult>();
		
		session2 = factory.getCurrentSession();
		
		session2.beginTransaction();
		
		int i = 1;
		while(session2.get(TestResult.class, new Integer(i)) != null) {
			
			tr.add((TestResult) session2.get(TestResult.class, new Integer(i)));
			i++;
		}
		
		session2.getTransaction().commit();
		
		return tr;
	}

}
