package medRules.test;


import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name="Medical_Tests")
public class Test implements Serializable{
	
	private int testID;
	private String testName;
	private int testSequence;
	private List<String> testSynonym;
	private String testDescription;
	private List<TestResult> testNumbers;
	private List<Disease> diseases;
	

	public Test() {
		
		diseases = new LinkedList<Disease>();
		testNumbers = new LinkedList<TestResult>();
		testSynonym = new LinkedList<String>();
	}
	
	@ManyToMany
	@JoinTable(name = "DISEASE_TEST_TABLE",
			joinColumns = {@JoinColumn(name = "testID")}, 
			inverseJoinColumns = {@JoinColumn(name = "disID")})
	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}
	
	@Transient
	public List<String> getTestSynonym() {
		return testSynonym;
	}

	public void setTestSynonym(List<String> testSynonym) {
		this.testSynonym = testSynonym;
	}

	@Column(nullable = false, name = "Sequence_Number")
	public int getTestSequence() {
		return testSequence;
	}

	public void setTestSequence(int testSequence) {
		this.testSequence = testSequence;
	}

	public void setTestNumbers(List<TestResult> testNumbers) {
		this.testNumbers = testNumbers;
	}
	
	@OneToMany(targetEntity = TestResult.class, mappedBy = "test",
			cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<TestResult> getTestNumbers() {
		return testNumbers;
	}
	
	/** Tested PK - Works without unique not null */
	@Column(name="Medical_Test_Name", unique = true, nullable = false)
	public String getTestName() {
		return testName;
	}
	
	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	/** New PK */
	@Id
	@Column(name = "Test_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "test_id")
	@TableGenerator(name = "test_id", table = "test_pk_table", 
			pkColumnName = "test_pk", pkColumnValue = "test_value",
			allocationSize = 1)
	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	@Column(length = 700)
	public String getTestDescription() {
		return testDescription;
	}
	
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	
	public void addTestSynonym(String syn) {	
		testSynonym.add(syn);
	}
	
}
