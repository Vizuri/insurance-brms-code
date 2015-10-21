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

import com.vizuri.insurance.domain.Answer;
//import com.vizuri.insurance.domain.Applicant;
//import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;


public class QuoteRequest {

	private Quote quote;
	private Map<String, Question> questionMap = new HashMap<String, Question>();
	private Map<String, Answer> answerMap = new HashMap<String, Answer>();

	private List<QuoteMessage> quoteMessages = new ArrayList<QuoteMessage>();

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public Map<String, Question> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<String, Question> questionMap) {
		this.questionMap = questionMap;
	}

	public Map<String, Answer> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, Answer> answerMap) {
		this.answerMap = answerMap;
	}

//	public Applicant getApplicant() {
//		return applicant;
//	}
//
//	public void setApplicant(Applicant applicant) {
//		this.applicant = applicant;
//	}

//	public List<Question> getQuestions() {
//		return questions;
//	}
//
//	public void setQuestions(List<Question> questions) {
//		this.questions = questions;
//	}

//	public Property getProperty() {
//		return property;
//	}
//
//	public void setProperty(Property property) {
//		this.property = property;
//	}

	public List<QuoteMessage> getQuoteMessages() {
		return quoteMessages;
	}

	public void setQuoteMessages(List<QuoteMessage> quoteMessages) {
		this.quoteMessages = quoteMessages;
	}

	@Override
	public String toString() {
		return "QuoteRequest [questionMap=" + questionMap + ", answerMap="
				+ answerMap + ", quoteMessages=" + quoteMessages
				+ ", quote=" + quote + "]";
	}

}
