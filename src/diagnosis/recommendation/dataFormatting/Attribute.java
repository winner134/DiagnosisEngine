package diagnosis.recommendation.dataFormatting;

public class Attribute {

	private Integer integerAttribute;
	private Double doubleAttribute;
	
	public Integer getIntegerAttribute() {
		return integerAttribute;
	}
	public void setIntegerAttribute(Integer integerAttribute) {
		this.integerAttribute = integerAttribute;
	}
	public Double getDoubleAttribute() {
		return doubleAttribute;
	}
	public void setDoubleAttribute(Double doubleAttribute) {
		this.doubleAttribute = doubleAttribute;
	}
	
	public void addAttribute(int attr) {
		
		integerAttribute = attr;
	}
	
	public void attrAttr(double attr) {
		
		doubleAttribute = attr;
	}
	
}
