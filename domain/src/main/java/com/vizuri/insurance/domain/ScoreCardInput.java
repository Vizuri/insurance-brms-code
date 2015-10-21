package com.vizuri.insurance.domain;

public class ScoreCardInput {

	private String quoteId;
	
	private boolean fraud;
	private boolean filedForBankrupcy;
	private double propertyAge;
	private double propertyRoofAge;
	private boolean childCareBusinessExists;
	
	private boolean updated = false;
	
	public ScoreCardInput(){
		
	}
	
	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public boolean isFraud() {
		return fraud;
	}
	
	public void setFraud(boolean fraud) {
		this.fraud = fraud;
	}
	
	public boolean isFiledForBankrupcy() {
		return filedForBankrupcy;
	}
	
	public void setFiledForBankrupcy(boolean filedForBankrupcy) {
		this.filedForBankrupcy = filedForBankrupcy;
	}
	
	public double getPropertyAge() {
		return propertyAge;
	}
	public void setPropertyAge(double propertyAge) {
		this.propertyAge = propertyAge;
	}
	public double getPropertyRoofAge() {
		return propertyRoofAge;
	}
	public void setPropertyRoofAge(double propertyRoofAge) {
		this.propertyRoofAge = propertyRoofAge;
	}
	public boolean isChildCareBusinessExists() {
		return childCareBusinessExists;
	}
	public void setChildCareBusinessExists(boolean childCareBusinessExists) {
		this.childCareBusinessExists = childCareBusinessExists;
	}
	@Override
	public String toString() {
		return "ScoreCardInput [fraud=" + fraud + ", filedForBankrupcy="
				+ filedForBankrupcy + ", propertyAge=" + propertyAge
				+ ", propertyRoofAge=" + propertyRoofAge
				+ ", childCareBusinessExists=" + childCareBusinessExists + "]";
	}
	
}
