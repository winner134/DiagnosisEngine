package medRules.test;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "Human_Disease")
public class Disease implements Serializable {

	private int disID;
	
	private String diseaseName;
	
	private List<String> diseaseSynonym;
	
	private String diseaseID;
	
	private String diseaseDescription;
	
	private List<Test> test;

	private boolean testListFinal;
	
	public Disease() {
		
		test = new LinkedList<Test>();
		diseaseSynonym = new LinkedList<String>();
	}
	
	public Disease(String dName, String dID) {
		
		diseaseName = dName;
		diseaseID = dID;
		test = new LinkedList<Test>();
		diseaseSynonym = new LinkedList<String>();
	}

	@Column(name = "Disease_Name", nullable = false, unique = true)
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	
	
	@Id
	@Column(name = "DIS_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dis_id")
	@TableGenerator(name = "dis_id", table = "dis_pk_table", 
			pkColumnName = "dis_pk", pkColumnValue = "dis_value",
			allocationSize = 1)
	public int getDisID() {
		return disID;
	}

	public void setDisID(int disID) {
		this.disID = disID;
	}

	@Transient
	public String getDiseaseID() {
		return diseaseID;
	}
	
	public void setDiseaseID(String diseaseID) {
		this.diseaseID = diseaseID;
	}
	
	@Transient
	public String getDiseaseDescription() {
		return diseaseDescription;
	}

	public void setDiseaseDescription(String diseaseDescription) {
		this.diseaseDescription = diseaseDescription;
	}
	
	@Transient
	public List<String> getDiseaseSynonym() {
		return diseaseSynonym;
	}

	public void setDiseaseSynonym(List<String> diseaseSynonym) {
		this.diseaseSynonym = diseaseSynonym;
	}
	
	@ManyToMany
	@JoinTable(name = "DISEASE_TEST_TABLE", 
			joinColumns = {@JoinColumn(name = "disID")}, 
			inverseJoinColumns = {@JoinColumn(name = "testID")})
	public List<Test> getTest() {
		return test;
	}
	
	public void setTest(List<Test> test) {
		this.test = test;
	}

	@Transient
	public boolean isTestListFinal() {
		return testListFinal;
	}

	public void setTestListFinal(boolean testListFinal) {
		this.testListFinal = testListFinal;
	}

	public String toString() {
		
		return diseaseID + " - " + diseaseName + ": " + diseaseDescription;

	}
	
	public void printTestList() {
		
		for(int i = 0; i < test.size(); i++) {
			
			System.out.println("Test " + test.get(i).getTestSequence() + ": " + test.get(i).getTestName());
		}
	}
	
	public boolean hasTest(String tName) {
		
		for(int i = 0; i < test.size(); i++) {
			
			if(test.get(i).getTestName().equalsIgnoreCase(tName))
				return true;
		}
		
		return false;
	}
	
	public int isEquivalentDisease(String dName) {
		
		if(diseaseName.equalsIgnoreCase(dName))
			return 1;
		
		for(int i = 0; i < diseaseSynonym.size(); i++) {
			
			if(diseaseSynonym.get(i).equalsIgnoreCase(dName))
				return 1;
		}
		
		return 0;
	}

	
}
