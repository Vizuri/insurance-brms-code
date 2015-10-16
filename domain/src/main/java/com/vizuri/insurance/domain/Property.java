/*
 * Copyright 2015 Vizuri, a business division of AEM Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vizuri.insurance.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Property {

	private Address address;
	private Date policyBeginDate;
	private Date purchaseDate;

	private int yearBuilt;
	private int livingArea;
	private int ageOfRoof;
	
	private Boolean electrictSystemRenovated;

	private Boolean homeSafetyDeviceInstalled;

	private List<String> homeSafetyDevices;

	private Boolean previousClaims;

	private List<Claim> claims;

	private Boolean dogExists;

	private List<Dog> dogs;

	private Boolean childCareBusinessExists;

	private Boolean childCareLiabilityCoverageRequired;

	private Boolean childCareLiabilityAlreadyExists;

	private Integer applicantId;
	private Integer policyId;
	private Integer quoteId;
	
	private double age;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getPolicyBeginDate() {
		return policyBeginDate;
	}

	public void setPolicyBeginDate(Date policyBeginDate) {
		this.policyBeginDate = policyBeginDate;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(int yearBuilt) {
		if (this.yearBuilt != yearBuilt) {
			this.yearBuilt = yearBuilt;
		}
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

	public List<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(List<Dog> dogs) {
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

	public double getAge() {
		if (getYearBuilt() != 0) {
			Calendar calendar = Calendar.getInstance();
			age = calendar.get(Calendar.YEAR) - getYearBuilt();
			return age;
		}
		return 0;
	}

	public void setAge(double age) {
		this.age = age;
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
				+ quoteId + ", age=" + age + "]";
	}

}
