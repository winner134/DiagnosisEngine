package medRules.run;


import medRules.test.Disease;
import medRules.test.Test;
import medRules.test.TestResult;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class PopulateTestDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		AnnotationConfiguration config = new AnnotationConfiguration();
	
		config.addAnnotatedClass(Disease.class);
		config.addAnnotatedClass(TestResult.class);
		config.addAnnotatedClass(Test.class);
		config.configure("hibernate.cfg.xml");

		new SchemaExport(config).create(true, true);
		
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		Disease diabetes = new Disease("Diabetes", "1");
		
		Test t1 = new Test();
		t1.setTestName("Fasting plasma glucose");
		t1.addTestSynonym("FPG");
		t1.setTestSequence(1);
		
		Test t2 = new Test();
		t2.setTestName("Oral glucose tolerance");
		t2.addTestSynonym("OGGT");
		t2.setTestSequence(1);
		t2.setTestDescription("The OGTT requires fasting for at least 8 hours before the test. " +
				"The plasma glucose level is measured immediately before and 2 hours after a person drinks a liquid containing 75 grams of glucose " +
				"dissolved in water. If the blood glucose level is between 140 and 199 mg/dL 2 hours after drinking the liquid, the person has a form " +
				"of pre-diabetes called impaired glucose tolerance (IGT). Having IGT, like having IFG, means a person has an increased risk of " +
				"developing type 2 diabetes but does not have it yet.");
		
		Test t3 = new Test();
		t3.setTestName("Random plasma glucose");
		t3.addTestSynonym("Casual plasma glucose test");
		t3.setTestSequence(1);

		diabetes.getTest().add(t1);
		diabetes.getTest().add(t2);
		diabetes.getTest().add(t3);
    
		t1.getDiseases().add(diabetes);
		t2.getDiseases().add(diabetes);
		t3.getDiseases().add(diabetes);
		
		TestResult tr1 = new TestResult();
		tr1.setTest(t1);
		tr1.setUnit("mg/dL");
		tr1.setDescription("glucose - FPG");
		
		TestResult tr2 = new TestResult();
		tr2.setTest(t2);
		tr2.setUnit("mg/dL");
		tr2.setDescription("glucose - OGGT");
		
		TestResult tr3 = new TestResult();
		tr3.setTest(t3);
		tr3.setUnit("mg/dL");
		tr3.setDescription("glucose - Random plasma glucose");
		
		
		session.save(diabetes);
		session.save(t1);
		session.save(t2);
		session.save(t3);
		session.save(tr1);
		session.save(tr2);
		session.save(tr3);
		
		session.getTransaction().commit();
		
		Session session1 = factory.getCurrentSession();
		
		session1.beginTransaction();
		
		Disease hypertension = new Disease("Hypertension", "2");
		
		Test t4 = new Test();
		t4.setTestName("Blood pressure");
		t4.setTestSequence(1);
		
		hypertension.getTest().add(t4);
		t4.getDiseases().add(hypertension);
		
		TestResult tr4 = new TestResult();
		tr4.setTest(t4);
		tr4.setUnit("mmHg");
		tr4.setDescription("systolic");
		
		TestResult tr5 = new TestResult();
		tr5.setTest(t4);
		tr5.setUnit("mmHg");
		tr5.setDescription("diastolic");
		
		session1.save(hypertension);
		session1.save(t4);
		session1.save(tr4);
		session1.save(tr5);
		
		session1.getTransaction().commit();
		
		Session session2 = factory.getCurrentSession();
		
		session2.beginTransaction();
		
		Disease ARDS = new Disease("Adult respiratory distress syndrome", "3");
		
		Test t5 = new Test();
		t5.setTestName("Arterial blood gas");
		t5.setTestSequence(1);
		
		ARDS.getTest().add(t5);
		t5.getDiseases().add(ARDS);
		
		TestResult tr6 = new TestResult();
		tr6.setTest(t5);
		tr6.setDescription("ABG levels");
		tr6.setUnit("PaO2");
		
		session2.save(ARDS);
		session2.save(t5);
		session2.save(tr6);
		
		session2.getTransaction().commit();
		
		Session session3 = factory.getCurrentSession();
		
		session3.beginTransaction();
		
		Disease anemia = new Disease("Anemia", "4");
		
		Test t6 = new Test();
		t6.setTestName("Complete blood count");
		t6.setTestSequence(1);
		
		anemia.getTest().add(t6);
		t6.getDiseases().add(anemia);
		
		TestResult tr7 = new TestResult();
		tr7.setTest(t6);
		tr7.setUnit("mg/dL");
		tr7.setDescription("hemoglobin, female");
		
		TestResult tr8 = new TestResult();
		tr8.setTest(t6);
		tr8.setUnit("mg/dL");
		tr8.setDescription("hemoglobin, male");
		
		TestResult tr9 = new TestResult();
		tr9.setTest(t6);
		tr9.setUnit("%");
		tr9.setDescription("% RBC, male");
		
		TestResult tr10 = new TestResult();
		tr10.setTest(t6);
		tr10.setUnit("%");
		tr10.setDescription("% RBC, female");
		
		
		session3.save(anemia);
		session3.save(t6);
		session3.save(tr7);
		session3.save(tr8);
		session3.save(tr9);
		session3.save(tr10);
		
		session3.getTransaction().commit();
		
		Session session4 = factory.getCurrentSession();
		
		session4.beginTransaction();
		
		Disease calcemia = new Disease("Calcemia", "5");
		
		Test t7 = new Test();
		t7.setTestName("blood test");
		t7.setTestSequence(1);
		
		t7.getDiseases().add(calcemia);
		calcemia.getTest().add(t7);
		

		TestResult tr11 = new TestResult();
		tr11.setTest(t7);
		tr11.setUnit("mg/dL");
		tr11.setDescription("calcium levels");
		
		session4.save(calcemia);
		session4.save(t7);
		session4.save(tr11);
		
		session4.getTransaction().commit();
		
	}

}
