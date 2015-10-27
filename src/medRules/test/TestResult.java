package medRules.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Test_Result")
public class TestResult implements Serializable {
	
	private int id;
	private double amount;
	private String unit;
	private String description;
	private String result;
	private boolean normal;
	private boolean diagnosis;
	private Test test;
	
	@ManyToOne
	@JoinColumn(name = "Test_Joint")
	public Test getTest() {
		return test;
	}
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "TRID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNormal() {
		return normal;
	}
	
	public void setNormal(boolean normal) {
		this.normal = normal;
	}
	
	public boolean isDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(boolean diagnosis) {
		this.diagnosis = diagnosis;
		
		if(diagnosis == true) {
			
		}
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
