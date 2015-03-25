package com.vizuri.insurance.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;

@XmlRootElement
public class TransferWrapper {
	@XmlElement
	private Applicant applicant;
	
	@XmlElement
	private List<Question> questions = new ArrayList<Question>();
	
	
	@XmlElement
	private Map<String,Question> applicantQuestMap = new HashMap();
	
/*	@XmlElement 
	private Map<String,Question> propertyQuestMap = new HashMap();*/
	@XmlElement
	private Property property;
	
	@XmlElement
	private Quote quote;
	
	public Quote getQuote() {
		return quote;
	}
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	/*public Map<String, Question> getPropertyQuestMap() {
		return propertyQuestMap;
	}
	public void setPropertyQuestMap(Map<String, Question> propertyQuestMap) {
		this.propertyQuestMap = propertyQuestMap;
	}*/
	public Map<String, Question> getApplicantQuestMap() {
		return applicantQuestMap;
	}
	public void setApplicantQuestMap(Map<String, Question> applicantQuestMap) {
		this.applicantQuestMap = applicantQuestMap;
	}

	
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	
	@Override
	public String toString() {
		return "TransferWrapper [applicant=" + applicant + ", questions=" + questions + ", property=" + property + "]";
	}
	
	
}
