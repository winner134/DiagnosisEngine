package medRules.data;

import medRules.test.Disease;
import medRules.test.Test;
import medRules.test.TestResult;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class UpdateTestResultData {
	
	private Session session;
	private SessionFactory factory;
	
	public UpdateTestResultData() {
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		
		config.addAnnotatedClass(Disease.class);
		config.addAnnotatedClass(TestResult.class);
		config.addAnnotatedClass(Test.class);
		config.configure("hibernate.cfg.xml");
		
		factory = config.buildSessionFactory();
		
	}
	
	public void updateTestResultRecord(TestResult tr) {
		
		session = factory.getCurrentSession();
		session.beginTransaction();	
		session.update(tr);
		session.getTransaction().commit();
	}

}
