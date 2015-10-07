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
import com.vizuri.insurance.domain.QuoteMessage;


public class TransferWrapper {

	private Applicant applicant;

	private List<Question> questions = new ArrayList<Question>();

	private Map<String, Question> applicantQuestMap = new HashMap();

	private List<QuoteMessage> quoteMessages = new ArrayList<QuoteMessage>();

	private Property property;

	private Quote quote;

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	/*
	 * public Map<String, Question> getPropertyQuestMap() { return propertyQuestMap; } public void
	 * setPropertyQuestMap(Map<String, Question> propertyQuestMap) { this.propertyQuestMap =
	 * propertyQuestMap; }
	 */
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

	public List<QuoteMessage> getQuoteMessages() {
		return quoteMessages;
	}

	public void setQuoteMessages(List<QuoteMessage> quoteMessages) {
		this.quoteMessages = quoteMessages;
	}

	@Override
	public String toString() {
		return "TransferWrapper [applicant=" + applicant + ", questions="
				+ questions + ", applicantQuestMap=" + applicantQuestMap
				+ ", quoteMessages=" + quoteMessages + ", property=" + property
				+ ", quote=" + quote + "]";
	}

}
