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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.drools.core.ClassObjectFilter;
import org.drools.core.common.DefaultFactHandle;
import org.jboss.logging.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.vizuri.insurance.MyScoreCardOutput;
import com.vizuri.insurance.domain.Address;
import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Claim;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;
import com.vizuri.insurance.rest.brms.RuleProcessor;


@SuppressWarnings({"unchecked", "rawtypes"})
@Path("/quoteService")
public class QuotingResourceService {
	private static final Logger log = Logger.getLogger(QuotingResourceService.class);



	/**
	 * Prepare a key/value pair for questions in groups eg. key for property is p.street eg. key for
	 * applicant is a.firstName
	 * 
	 * @param questionLst
	 * @param groupss
	 * @return
	 */
	private Map<String, Question> buildQuestionByGroup(List<Question> questionLst, String groupss) {

		Map<String, Question> questMap = new HashMap<String, Question>();

		for (Question que : questionLst) {


			String group = que.getGroup();
			/*
			 * if(!que.getGroup().equalsIgnoreCase(group)){ continue; }
			 */

			String key = group.substring(0, 1).toLowerCase() + "." + que.getMappedProperty();
			questMap.put(key, que);
		}

		return questMap;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransferData() {
		log.info("inside getTransferData");
		TransferWrapper wrapper = new TransferWrapper();
		wrapper.setApplicant(new Applicant());
		wrapper.setProperty(new Property());
		wrapper.getProperty().setAddress(new Address());
		wrapper.getProperty().setClaims(new ArrayList<Claim>());

		RuleProcessor process = new RuleProcessor();
		List<Question> questionLst = process.getAllQuestions();

		wrapper.setQuestions(questionLst);


		Map<String, Question> applicantQuestMap = new HashMap<String, Question>();
		applicantQuestMap = buildQuestionByGroup(questionLst, null);


		wrapper.setApplicantQuestMap(applicantQuestMap);
		return sendResponse(200, wrapper);

	}

	// org.codehaus.jackson.map.JsonMapping jackson 1.9 - included in resteasy-jaxrs-3.0.9.Final.jar
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response handleQuestionDisplaysOnInputChanges(TransferWrapper wrapper) {

		log.info("inside handleQuestionDisplaysOnInputChanges");
		//log.info("input wrapper: " + wrapper);
		
		RuleProcessor rp = new RuleProcessor();
		// List<Question> questionListToSend = new
		// ArrayList<Question>(wrapper.getApplicantQuestMap().values());
		List<Question> questionLst = rp.getAllQuestions();

		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		log.info("received parameter property: " + wrapper.getProperty());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());

		wrapper.getQuoteMessages().clear();
		Collection collError = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());

		for (Object object : collError) {
			DefaultFactHandle fact = (DefaultFactHandle) object;


			if (fact.getObject() instanceof QuoteMessage) {
				QuoteMessage msg = (QuoteMessage) fact.getObject();
				wrapper.getQuoteMessages().add(msg);

			}
		}

		Map mp = rp.fireQuestionRule(RuleProcessor.AGENDA_QUESTION_DISPLAY, sendList);

		questionLst = (List) mp.get("questions");

		Map<String, Question> applicantQuestMap = new HashMap<String, Question>();
		applicantQuestMap = buildQuestionByGroup(questionLst, null);
		wrapper.setApplicantQuestMap(applicantQuestMap);

		//log.info("return parameter property: " + wrapper.getProperty());
		log.info("retured updated wrapper");
		return sendResponse(200, wrapper);
		
	}



	@Path("/eligibility")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doEligiblity(TransferWrapper wrapper) {
		log.info("Inside doEligiblity");
		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());
		
		// we need a quote to calculate the status and risk
		//quoteSession.insert(new Quote(1234));

		RuleProcessor rp = new RuleProcessor();
		/* Collection coll = */rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());

		return sendResponse(200, wrapper);
		
	}


	@Path("/quoteCalculate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doCalculation(TransferWrapper wrapper) {
		log.info("Inside doCalculation");
			
		RuleProcessor rp 		= new RuleProcessor();
		KieSession quoteSession = null;
		
		try {
			quoteSession = rp.createNewQuoteSession(true);
			
			// Insert Questions intu rule engine
//			if (wrapper.getQuestions() != null) {
//				for (Question q : wrapper.getQuestions()) {
//					quoteSession.insert(q);
//				}
//			}
			
			quoteSession.insert(wrapper.getApplicant());
			quoteSession.insert(wrapper.getProperty());
			quoteSession.insert(wrapper.getProperty().getAddress());
			
			wrapper.getQuoteMessages().clear();
			
			log.info("Fire AGENDA_QUOTE_ERROR_CHECK");
			quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK).setFocus();
			quoteSession.fireAllRules();
			
			Collection<?> errMsgs = quoteSession.getObjects(new ClassObjectFilter(QuoteMessage.class));
			
			for (Object msg : errMsgs) {				
				wrapper.getQuoteMessages().add((QuoteMessage) msg);					
			}
			
			// now if we have no more error messages we can go ahead and do a rating on the quote
			if (errMsgs.isEmpty()) {
				
				log.info("No more errors so calculate elegibility");
				
				// we need a quote to calculate the status and risk
				quoteSession.insert(new Quote(1234));
				
				// now lets test the score card
				//quoteSession.insert(new Quote());
				log.info("Fire AGENDA_MAIN");
				quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_MAIN).setFocus();
				quoteSession.fireAllRules();
				
				Collection<?> riskResult = quoteSession.getObjects(new ClassObjectFilter(Quote.class));
				
				for (Object risk : riskResult) {
					log.info("Have risk results");
					wrapper.setQuote((Quote) risk);
					
					log.info("Quote risk rate: " +  wrapper.getQuote().getRiskRate());
				}
				
				log.info("Fire AGENDA_ELIGIBLITY");
				quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_ELIGIBLITY).setFocus();
				quoteSession.fireAllRules();
				
				// now calculate the quote amount based on the risk rate
				
				log.info("Fire AGENDA_CALCULATION");
				quoteSession.getAgenda().getAgendaGroup(RuleProcessor.AGENDA_CALCULATION).setFocus();
				quoteSession.fireAllRules();
		
				Collection<?> finalQuote = quoteSession.getObjects(new ClassObjectFilter(Quote.class));
				
				for (Object quote : finalQuote) {
					log.info("Have a final quote");
					wrapper.setQuote((Quote) quote);

				}
			}	
			
//			Collection<?> scoreCardResult = quoteSession.getObjects(new ClassObjectFilter(MyScoreCardOutput.class));
//			
//			for (Object risk : scoreCardResult) {
//				log.info("Have score card results: " + ((MyScoreCardOutput) risk).getRiskRate());
//				//wrapper.setRisk((RateCalculationResult) risk);
//			}
					

		} catch (Exception e) {
			log.error("doCalculation, exception",e);
			return Response.serverError().entity("Exception unable to doCalculation").build();
		} finally {
			try {
				quoteSession.dispose();
			} catch (Exception e) {
				log.error("doCalculation:close kiesession, exception",e);
				return Response.serverError().entity("Exception closing kie session").build();
			}
		}
		
		return sendResponse(200, wrapper);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		log.info("Fire AGENDA_MAIN");
//		collError = rp.fireRules(RuleProcessor.AGENDA_MAIN, sendList.toArray());
//		
//		for (Object object : collError) {
//			DefaultFactHandle fact = (DefaultFactHandle) object;
//			
//			if (fact.getObject() instanceof RateCalculationResult) {
//				log.info("****** RISK ********* 2");
//				RateCalculationResult risk = (RateCalculationResult) fact.getObject();
//				wrapper.setRisk(risk);
//			}
//			else{
//				wrapper.setRisk(null);
//			}
//		}

//		List sendList = new ArrayList(wrapper.getQuestions());
//		sendList.add(wrapper.getApplicant());
//		sendList.add(wrapper.getProperty());
//		sendList.add(new RateCalculationResult());	// need this to set the risk rate
//		sendList.add(wrapper.getProperty().getAddress());
//		
//
//		wrapper.getQuoteMessages().clear();
//		log.info("Fire AGENDA_QUOTE_ERROR_CHECK");
//		Collection collError = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());
//
//		for (Object object : collError) {
//			DefaultFactHandle fact = (DefaultFactHandle) object;
//
//			if (fact.getObject() instanceof QuoteMessage) {
//				QuoteMessage msg = (QuoteMessage) fact.getObject();
//				wrapper.getQuoteMessages().add(msg);
//			}
//			
//		}
//		
//
//		
//		if (wrapper.getQuoteMessages().isEmpty()) {
//			
//			wrapper.getProperty().setRiskRate(0);
//			
//			// old way getting risk rate
//			//rp.fireRules(RuleProcessor.AGENDA_RISK_RULE_GROUP, sendList.toArray());
//
//			log.info("Fire AGENDA_ELIGIBLITY");
//			rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());
//
//			log.info("Fire AGENDA_CALCULATION");
//			Collection coll = rp.fireRules(RuleProcessor.AGENDA_CALCULATION, sendList.toArray());
//
//			for (Object object : coll) {
//				DefaultFactHandle fact = (DefaultFactHandle) object;
//				if (fact.getObject() instanceof Quote) {
//					wrapper.setQuote((Quote) fact.getObject());
//
//				}
//
//			}
//		}
//
//		return Response.status(200).header("Access-Control-Allow-Origin", "*")
//				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
//				.header("Access-Control-Allow-Credentials", "true")
//				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//				.header("Access-Control-Max-Age", "1209601").entity(wrapper).build();

	}


	@Path("/devSettings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDevelopmentSettings() {

		String appEnvironment = System.getProperty("insurance.appEnvironment");
		Map<String, String> devSettings = new HashMap<String, String>();
		devSettings.put("appEnvironment", appEnvironment);
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209601").entity(devSettings).build();
	}
	
	private Response sendResponse(int status, Object result){
	
		return Response.status(status).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209601").entity(result).build();
	}

}
