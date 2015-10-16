package com.vizuri.insurance.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kie.api.definition.type.PropertyReactive;

@PropertyReactive	//to use modify() in the brms rules more effectively - not recommended at this time due to issues
public class Answer implements Serializable {
	private static final long serialVersionUID = -2811773784154293610L;
	
	
	private String questionId;
	private String strValue;
	private boolean updatedValue;
	private Date lastUpdated;
	private final String dateFormat = "yyyy-MM-dd";
	
	public Answer() {
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String srtValue) {
		this.lastUpdated = new Date();
		this.strValue = srtValue;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getNumValue() {
		if (strValue != null && !strValue.isEmpty()){
			try{
				return Integer.parseInt(strValue);
			}
			catch(Exception e){
				return -1;
			}
		}
		return -1;
	}

	public void setNumValue(int numValue) {
		setStrValue(Integer.toString(numValue));
	}
	
	public float getDecValue() {
		if (strValue != null && !strValue.isEmpty()){
			try{
				return Float.parseFloat(strValue);
			}
			catch(Exception e){
				return -1.0f;
			}
		}
		return 0.0f;
	}

	public void setDecValue(float decValue) {
		setStrValue(Float.toString(decValue));
	}
	
	// pass in Yes/yes, No/no and true/false
	public boolean getBoolValue() {
		if (strValue != null && !strValue.isEmpty()){
			if (strValue.equalsIgnoreCase("Yes")){
				return true;
			} else if (strValue.equalsIgnoreCase("No")){
				return false;
			} else{
				return Boolean.parseBoolean(strValue);
			}
		}
		return false;
	}
	
	public void setBoolValue(boolean boolValue) {
		setStrValue(Boolean.toString(boolValue));
	}
//	
//	public Date getDateValue() {
//		if (strValue != null && strValue.length() == dateFormat.length()){
//			
//			try{
//				return new SimpleDateFormat(dateFormat).parse(strValue);
//			}
//			catch(Exception e){
//				return null;
//			}
//			
//		}
//		return null;
//	}
//	
//	// use date format "2015-08-18":yyyy-MM-dd
//	public void setDateValue(Date date) {
//		
//		if (date != null){
//			setStrValue(new SimpleDateFormat(dateFormat).format(date));
//		}
//		else{
//			setStrValue("");
//		}
//	}

	public boolean isUpdatedValue() {
		return updatedValue;
	}

	public void setUpdatedValue(boolean updatedValue) {
		this.updatedValue = updatedValue;
	}
	
//	public List<String> getMultipleValues(){
//		if (strValue != null){
//			return new ArrayList<String>(Arrays.asList(strValue.split(",")));
//		} else{
//			return new ArrayList<String>();
//		}
//	}
//	
//	public void setAddMultipleValue(String valueList) {
//		List<String> options = getMultipleValues();
//		
//		if (options != null && valueList != null) {
//			for (String possibleValue : valueList.split(",")) {
//				if (!options.contains(possibleValue)){
//					options.add(possibleValue);
//				}
//			}
//			setStrValue(convertToString(options, ","));
//		}
//			
//	}
	
	private String convertToString(List<String> list, String delim) {
	    StringBuilder sb = new StringBuilder();
	    String loopDelim = "";

	    for(String s : list) {
	        sb.append(loopDelim);
	        sb.append(s);            
	        loopDelim = delim;
	    }

	    return sb.toString();
	}
	
	// check if any of the input values are found
//	public boolean getIncludesAnyValues(String valueList) {
//		List<String> options = getMultipleValues();
//		
//		if (options != null && valueList != null) {
//			for (String possibleValue : valueList.split(",")) {
//				if (options.contains(possibleValue)){
//					return true;
//				}
//			}
//		}
//		
//		return false;		
//	}
	
	// need to check that none of the input values are found in the list
//	public boolean getExcludeAllValues(String valueList) {
//		List<String> options = getMultipleValues();
//			
//		if (options != null && valueList != null) {
//			for (String possibleValue : valueList.split(",")) {
//				if (options.contains(possibleValue)){
//					return false;
//				}
//			}
//		}
//		return true;		
//	}

	@Override
	public String toString() {
		return "Answer [questionId=" + questionId + ", strValue=" + strValue
				+ ", updatedValue=" + updatedValue + ", lastUpdated="
				+ lastUpdated + ", dateFormat=" + dateFormat + "]";
	}
	
}
