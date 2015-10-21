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


package com.vizuri.insurance.rest.brms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.common.DefaultFactHandle;
import org.jboss.logging.Logger;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import com.vizuri.insurance.domain.Address;
import com.vizuri.insurance.domain.Answer;
//import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Claim;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;
import com.vizuri.insurance.domain.ScoreCardInput;
import com.vizuri.insurance.rest.SessionCache;
import com.vizuri.insurance.rest.QuoteRequest;

public class RuleProcessor {


	private KieContainer kContainer = RuleProcessor.Factory.get();// kServices.getKieClasspathContainer();
	private static final Logger log = Logger.getLogger(RuleProcessor.class);
	private static SessionCache cache = new SessionCache();
	
	private static class Factory {
		private static KieContainer kContainer;
		static {
			try {


				// if the local maven repository is not in the default ${user.home}/.m2
				// need to provide the custom settings.xml
				// pass property value
				// -Dkie.maven.settings.custom={custom.settings.location.full.path}

				KieServices kServices = KieServices.Factory.get();

				ReleaseId releaseId = kServices.newReleaseId("com.vizuri.insurance", "rules", "1.0-SNAPSHOT");

				//kContainer = kServices.newKieContainer(releaseId, Factory.class.getClassLoader());
				
				kContainer = kServices.newKieContainer(releaseId);

				KieScanner kScanner = kServices.newKieScanner(kContainer);


				// Start the KieScanner polling the maven repository every 10 seconds

				kScanner.start(10000L);
				
				
			} catch (Exception e) {

				log.error("",e);
			}
		}

		public static KieContainer get() {
			return kContainer;
		}
	}

	public RuleProcessor() {

	}

	public static final String AGENDA_QUESTION_GROUP = "question-group";	// construction
	public static final String AGENDA_QUESTION_DISPLAY = "question-display";// customization
	
	public static final String AGENDA_SYNC_ANSWERS = "sync-answers";	// update answers
	public static final String AGENDA_QUOTE_ERROR_CHECK = "quote-error-check";	// check for errors
	public static final String AGENDA_PREPARE_SCORCARD = "prepare-scorecard-input";
	
	public static final String AGENDA_ELIGIBLITY = "eligibility";
	public static final String AGENDA_CALCULATION = "calculation";
	
	
	public static final String AGENDA_MAIN = "MAIN";
	// public static final String AGENDA_MAIN_GROUP = "question-group";
	AgendaListener agendaListener = new AgendaListener();
	RuleListener ruleListener = new RuleListener();

	public KieSession createNewQuoteSession(boolean addListeners){		
		KieSession quoteSession = kContainer.newKieSession();
		
		if (addListeners){
			quoteSession.addEventListener(agendaListener);
			quoteSession.addEventListener(ruleListener);
		}
		
		return quoteSession;
	}
	
	public List<Question> initializeQuote(Long quoteId){
	
		log.info("Inside initializeQuote["+quoteId+"]");
		List<Question> questions = new ArrayList<Question>();
		
		try{
					
			// remove it if it already existed
			cache.removeQuoteSession(quoteId);
			
			KieSession quoteSession = createNewQuoteSession(false);
					
			log.info("Fire AGENDA_QUESTION_GROUP");
			quoteSession.getAgenda().getAgendaGroup(AGENDA_QUESTION_GROUP).setFocus();
			quoteSession.fireAllRules();
			
			QueryResults queryResults = quoteSession.getQueryResults("getQuestions");
			
			for (QueryResultsRow row : queryResults) {			
				questions.add((Question) row.get("$question"));	
			}
			
//			Collection<FactHandle> coll = quoteSession.getFactHandles();		
//			Collections.sort(questions, new Comparator<Question>() {
//
//				@Override
//				public int compare(Question o1, Question o2) {
//
//					return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
//				}
//			});
//
//			for (FactHandle object : coll) {
//				DefaultFactHandle fact = (DefaultFactHandle) object;
//				if (false == (fact.getObject() instanceof Question)) {
//					continue;
//				}
//
//				Question q = (Question) fact.getObject();
//				questions.add(q);
//
//			}
			
			cache.saveQuoteSession(quoteId, quoteSession);				
			
		} catch (Exception e) {
			log.error("Exception in initializeQuote["+quoteId+"]",e);
			throw e;
		}	
		return questions;
	
	}
	
	public List<Question> updateQuote(QuoteRequest request) throws Exception{
		
		log.info("Inside updateQuote");
		
		Quote quote = request.getQuote();	
		
		try{
			
			if (quote == null || quote.getId() == null){
				throw new Exception("Invalid Quote");		
			}
			
			// reset any previous error messages
			request.getQuoteMessages().clear();
					
			KieSession quoteSession = cache.getQuoteSession(quote.getId());	
			
			if (quoteSession == null){
				throw new Exception("Invalid kie session");		
			}
			
			//updateExistingQuoteFact(quoteSession, request.getApplicant());
			//updateExistingQuoteFact(quoteSession, request.getProperty());
			//updateExistingQuoteFact(quoteSession, request.getProperty().getAddress());
			
			updateAnswers(quoteSession, request);
					
			log.info("Fire AGENDA_QUOTE_ERROR_CHECK");
			quoteSession.getAgenda().getAgendaGroup(AGENDA_QUOTE_ERROR_CHECK).setFocus();
			quoteSession.fireAllRules();
			
			QueryResults queryResults = quoteSession.getQueryResults("getQuestionErrors");
			
			for (QueryResultsRow row : queryResults) {			
				request.getQuoteMessages().add((QuoteMessage) row.get("$error"));	
			}
			
//			Collection<FactHandle> coll = quoteSession.getFactHandles();
//	
//			// get a list of new errors
//			for (Object object : coll) {
//				DefaultFactHandle fact = (DefaultFactHandle) object;
//	
//				if (fact.getObject() instanceof QuoteMessage) {
//					QuoteMessage msg = (QuoteMessage) fact.getObject();
//					request.getQuoteMessages().add(msg);
//	
//				}
//			}
							
			// Now check which questions needs to be visible
			log.info("Fire AGENDA_QUESTION_DISPLAY");
			quoteSession.getAgenda().getAgendaGroup(AGENDA_QUESTION_DISPLAY).setFocus();
			quoteSession.fireAllRules();
			
			List<Question> questions = new ArrayList<Question>();
			
			queryResults = quoteSession.getQueryResults("getQuestions");
			
			for (QueryResultsRow row : queryResults) {			
				questions.add((Question) row.get("$question"));	
			}
			
			// modified list of questions
			return questions;
			
		} catch(Exception e){
			log.error("Exception in updateQuote",e);
			throw e;
		}
		
	}
	
	public boolean rateQuote(QuoteRequest request) throws Exception{
			
		log.info("Inside rateQuote");
		
		Quote quote = request.getQuote();	
		
		try{
			
			if (quote == null || quote.getId() == null){
				throw new Exception("Invalid Quote");		
			}
			
			// reset any previous error messages
			request.getQuoteMessages().clear();
					
			KieSession quoteSession = cache.getQuoteSession(quote.getId());	
			
			if (quoteSession == null){
				throw new Exception("Invalid kie session");		
			}
			
			log.info("Fire AGENDA_QUOTE_ERROR_CHECK");
			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK).setFocus();
			quoteSession.fireAllRules();
			
			QueryResults queryResults = quoteSession.getQueryResults("getQuestionErrors");
				
			// now if we have no more error messages we can go ahead and do a rating on the quote
			if (queryResults.size() > 0) {
				log.info("Cannot rate, still have errors");
				//throw new Exception("Cannot rate, still have errors");
				
				for (QueryResultsRow row : queryResults) {			
					request.getQuoteMessages().add((QuoteMessage) row.get("$error"));	
				}
				
				// Now check which questions needs to be visible
				log.info("Fire AGENDA_QUESTION_DISPLAY");
				quoteSession.getAgenda().getAgendaGroup(AGENDA_QUESTION_DISPLAY).setFocus();
				quoteSession.fireAllRules();
				
				Map<String, Question> questionMap = new HashMap<String, Question>();
				Question q;
				
				queryResults = quoteSession.getQueryResults("getQuestions");
				
				for (QueryResultsRow row : queryResults) {	
					q = (Question) row.get("$question");
					questionMap.put(q.getQuestionId(), q);
					
				}
				
				request.setQuestionMap(questionMap);
				return false;	// was unable to rate
			}
				
			log.info("No more errors so calculate elegibility");
			
			List<Object> returnedFacts = getExistingQuoteFact(quoteSession, Quote.class);
			if (returnedFacts != null && returnedFacts.size() == 0){
				log.info("insert Quote: " + quote);
				// we need a quote to calculate the status and risk
				quoteSession.insert(quote);
			}
			
			// now lets test the score card
			ScoreCardInput sc = new ScoreCardInput();
			sc.setUpdated(true);
			
			quoteSession.insert(sc);
			log.info("Fire AGENDA_PREPARE_SCORCARD");
			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_PREPARE_SCORCARD).setFocus();
			quoteSession.fireAllRules();
			
			// have to do this to force the scorecard to calculate
//			log.info("Fire AGENDA_MAIN");
//			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_MAIN).setFocus();
//			quoteSession.fireAllRules();
			
			returnedFacts = getExistingQuoteFact(quoteSession, Quote.class);			
			request.setQuote((Quote) returnedFacts.get(0));
			log.info("Quote risk rate: " +  request.getQuote().getRiskRate());

			
//			Collection<?> riskResult = quoteSession.getObjects(new ClassObjectFilter(Quote.class));
//			
//			for (Object risk : riskResult) {
//				log.info("Have risk results");
//				request.setQuote((Quote) risk);
//				
//				log.info("Quote risk rate: " +  request.getQuote().getRiskRate());
//			}
			
			log.info("Fire AGENDA_ELIGIBLITY");	// calculate status
			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_ELIGIBLITY).setFocus();
			quoteSession.fireAllRules();
			
			// now calculate the quote amount based on the risk rate
			
			log.info("Fire AGENDA_CALCULATION");	// calculate amount
			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_CALCULATION).setFocus();
			quoteSession.fireAllRules();
			
			returnedFacts = getExistingQuoteFact(quoteSession, Quote.class);			
			request.setQuote((Quote) returnedFacts.get(0));
			log.info("Final Quote: " +  request.getQuote());
			
			return true;	// was able to rate
		
			
		} catch(Exception e){
			log.error("Exception in rateQuote",e);
			throw e;
		}	
			
	}
	
	/**
	 * 
	 * @param agendaGroup can be null,
	 * @param object
	 * @return
	 */
	public Collection<FactHandle> fireRules(String agendaGroup, Object... objects) {

		log.info("Inside fireRules["+agendaGroup+"]");
		KieSession kieSession = null;
		try {
			kieSession = kContainer.newKieSession();
			//kieSession.addEventListener(agendaListener);
			//kieSession.addEventListener(ruleListener);
			
			if (objects != null) {
				for (Object obj : objects) {
					log.info("insert object: " + obj);
					kieSession.insert(obj);
				}
			}

			if (agendaGroup != null) {
				log.info("Fire agenda group:" + agendaGroup);
				kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
			}

			kieSession.fireAllRules();
			return kieSession.getFactHandles();
			

		} catch (Exception e) {
			log.error("",e);
		} finally {
			try {
				kieSession.dispose();
			} catch (Exception e) {
				log.error("",e);
			}
		}

		return null;
	}

	public List<Question> getAllQuestions() {

		Collection<FactHandle> coll = fireRules(AGENDA_QUESTION_GROUP);

		List<Question> questions = new ArrayList<Question>();
		Collections.sort(questions, new Comparator<Question>() {

			@Override
			public int compare(Question o1, Question o2) {

				return Integer.valueOf(o1.getOrder()).compareTo(Integer.valueOf(o2.getOrder()));
			}
		});

		for (FactHandle object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle) object;
			if (false == (fact.getObject() instanceof Question)) {
				continue;
			}

			Question q = (Question) fact.getObject();
			questions.add(q);

		}
		return questions;

	}



//	@SuppressWarnings({"rawtypes", "unchecked"})
//	public List<Question> fireQuestionRule(String agenda, List objLst) {
//
//		log.info("Inside fireQuestionRule["+ agenda+"]");
//		
//		Collection coll = fireRules(agenda, objLst.toArray());
//		
//		Map questTrack = new HashMap<Integer, Question>();
//		
//		for (Object object : coll) {
//			DefaultFactHandle fact = (DefaultFactHandle) object;
//			if (false == (fact.getObject() instanceof Question)) {
//				continue;
//			}
//
//			Question q = (Question) fact.getObject();
//			log.info("add object: " + q);
//			questTrack.put(q.getOrder(), q);
//
//		}
//
//		List<Question> questionRetList = new ArrayList<Question>();
//		questionRetList.addAll(questTrack.values());
//		
//		return questionRetList;
//	}
	
	private void updateExistingQuoteFact(KieSession quoteSession,Object updatedFact) {
		
		log.info("Inside updateExistingQuoteFact");
		FactHandle factHandle;
		
		List<Object> facts = getExistingQuoteFact(quoteSession, updatedFact.getClass());
		
		log.info("Found facts: " + facts);
		if (facts != null && facts.size() > 0){
		
			factHandle = quoteSession.getFactHandle(facts.get(0));
			log.info("update factHandle: " + factHandle);
			quoteSession.update(factHandle, updatedFact);
		}
		else{
			log.info("insert fact: " + updatedFact);
			quoteSession.insert(updatedFact);
		}
	}
	
	private List<Object> getExistingQuoteFact(KieSession quoteSession, Class<?> factClass) {
		
		log.info("Inside getExistingQuoteFact");
		
		Collection<?> results = quoteSession.getObjects(new ClassObjectFilter(factClass));
		List<Object> facts = new ArrayList<Object>();
		
		if (results != null){
			
			log.info("have results size["+results.size()+"]");
			for (Object fact : results) {
						
				log.info("found fact: " + fact);	
				facts.add(fact);
			}
		}
		
		return facts;
	}
	
	private void updateAnswers(KieSession quoteSession, QuoteRequest request){
		
		log.info("Inside updateAnswers");
		
		Map<String, Answer> answerMap = request.getAnswerMap();
		if (answerMap != null && answerMap.size() > 0){
			
			Collection<Answer> answers = answerMap.values();
			for (Answer a : answers) {	
				
				a.setUpdatedValue(true);	// need this so that we can remove old answers
				log.info("insert Answer: " + a);
				quoteSession.insert(a);
			}
			
			
			log.info("Fire AGENDA_SYNC_ANSWERS");
			quoteSession.getAgenda().getAgendaGroup(AGENDA_SYNC_ANSWERS).setFocus();
			quoteSession.fireAllRules();
		}
	}

}
