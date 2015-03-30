	package com.vizuri.insurance.domain;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.vizuri.insurance.domain.xmladapter.BooleanXmlAdapter;
import com.vizuri.insurance.domain.xmladapter.CalendarXmlAdapter;

@SuppressWarnings("restriction")
@XmlRootElement
public class Property {
	
	
	private Address address;
	
	@XmlJavaTypeAdapter(value = CalendarXmlAdapter.class)
	private Calendar policyBeginDate;
	
	@XmlJavaTypeAdapter(value = CalendarXmlAdapter.class)
	private Calendar purchaseDate;
	
	private int yearBuilt;
	private int livingArea;
	private int ageOfRoof;
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean electrictSystemRenovated;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean homeSafetyDeviceInstalled;
	
	private List<String> homeSafetyDevices;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean previousClaims;
	
	private List<Claim> claims;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean dogExists;
	
	private Map<Integer, String> dogs;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean childCareBusinessExists;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean childCareLiabilityCoverageRequired;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean childCareLiabilityAlreadyExists;
	
	private Integer applicantId;
	private Integer policyId;
	private Integer quoteId;
	private String status;
	private int age;
	private int riskRate;
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Calendar getPolicyBeginDate() {
		return policyBeginDate;
	}
	public void setPolicyBeginDate(Calendar policyBeginDate) {
		this.policyBeginDate = policyBeginDate;
	}
	public Calendar getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public int getLivingArea() {
		return livingArea;
	}
	public void setLivingArea(int livingArea) {
		this.livingArea = livingArea;
	}
	public int getAgeOfRoof() {
		return ageOfRoof;
	}
	public void setAgeOfRoof(int ageOfRoof) {
		this.ageOfRoof = ageOfRoof;
	}
	public Boolean getElectrictSystemRenovated() {
		return electrictSystemRenovated;
	}
	public void setElectrictSystemRenovated(Boolean electrictSystemRenovated) {
		this.electrictSystemRenovated = electrictSystemRenovated;
	}
	public Boolean getHomeSafetyDeviceInstalled() {
		return homeSafetyDeviceInstalled;
	}
	public void setHomeSafetyDeviceInstalled(Boolean homeSafetyDeviceInstalled) {
		this.homeSafetyDeviceInstalled = homeSafetyDeviceInstalled;
	}
	public List<String> getHomeSafetyDevices() {
		return homeSafetyDevices;
	}
	public void setHomeSafetyDevices(List<String> homeSafetyDevices) {
		this.homeSafetyDevices = homeSafetyDevices;
	}
	public Boolean getPreviousClaims() {
		return previousClaims;
	}
	public void setPreviousClaims(Boolean previousClaims) {
		this.previousClaims = previousClaims;
	}
	public List<Claim> getClaims() {
		return claims;
	}
	public void setClaims(List<Claim> claims) {
		this.claims = claims;
	}
	public Boolean getDogExists() {
		return dogExists;
	}
	public void setDogExists(Boolean dogExists) {
		this.dogExists = dogExists;
	}
	public Map<Integer, String> getDogs() {
		return dogs;
	}
	public void setDogs(Map<Integer, String> dogs) {
		this.dogs = dogs;
	}
	public Boolean getChildCareBusinessExists() {
		return childCareBusinessExists;
	}
	public void setChildCareBusinessExists(Boolean childCareBusinessExists) {
		this.childCareBusinessExists = childCareBusinessExists;
	}
	public Boolean getChildCareLiabilityCoverageRequired() {
		return childCareLiabilityCoverageRequired;
	}
	public void setChildCareLiabilityCoverageRequired(
			Boolean childCareLiabilityCoverageRequired) {
		this.childCareLiabilityCoverageRequired = childCareLiabilityCoverageRequired;
	}
	public Boolean getChildCareLiabilityAlreadyExists() {
		return childCareLiabilityAlreadyExists;
	}
	public void setChildCareLiabilityAlreadyExists(
			Boolean childCareLiabilityAlreadyExists) {
		this.childCareLiabilityAlreadyExists = childCareLiabilityAlreadyExists;
	}
	public Integer getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public Integer getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Integer quoteId) {
		this.quoteId = quoteId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getAge() {
		if(getYearBuilt() != 0){
			Calendar calendar = Calendar.getInstance();
			age = calendar.get(Calendar.YEAR) - getYearBuilt();
			return age;
		}
		return 0;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	
	public int getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(int riskRate) {
		this.riskRate = riskRate;
	}
	
	@Override
	public String toString() {
		return "Property [address=" + address + ", policyBeginDate="
				+ policyBeginDate + ", purchaseDate=" + purchaseDate
				+ ", yearBuilt=" + yearBuilt + ", livingArea=" + livingArea
				+ ", ageOfRoof=" + ageOfRoof + ", electrictSystemRenovated="
				+ electrictSystemRenovated + ", homeSafetyDeviceInstalled="
				+ homeSafetyDeviceInstalled + ", homeSafetyDevices="
				+ homeSafetyDevices + ", previousClaims=" + previousClaims
				+ ", claims=" + claims + ", dogExists=" + dogExists + ", dogs="
				+ dogs + ", childCareBusinessExists=" + childCareBusinessExists
				+ ", childCareLiabilityCoverageRequired="
				+ childCareLiabilityCoverageRequired
				+ ", childCareLiabilityAlreadyExists="
				+ childCareLiabilityAlreadyExists + ", applicantId="
				+ applicantId + ", policyId=" + policyId + ", quoteId="
				+ quoteId + ", status=" + status + ", age=" + age + ", riskRate=" + riskRate +"]";
	}
	
	
	
	
}
