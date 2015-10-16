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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.rest.brms.RuleProcessor;


//@SuppressWarnings({"unchecked", "rawtypes"})
@Path("/quoteService")
public class QuotingResourceService {
	private static final Logger log = Logger.getLogger(QuotingResourceService.class);
	private static Long quoteId = 23671L;
	private static RuleProcessor rulesProsessor = new RuleProcessor();

	@GET
	@Path("/init")
	@Produces(MediaType.APPLICATION_JSON)
	public Response initializeQuote() {
		log.info("inside initializeQuote");
		
		QuoteRequest request = new QuoteRequest();
	
		try{
			Long quoteId = generateQuoteId();
			
			request.setQuote(new Quote(quoteId));
			List<Question> questions = rulesProsessor.initializeQuote(quoteId);
			
			Map<String, Question> quoteQuestionMap = new HashMap<String, Question>();
			quoteQuestionMap = buildQuestionByGroup(questions, null);

			request.setQuestionMap(quoteQuestionMap);
			request.setAnswerMap(null);	// reset answers: we do not send answers back up
			
			return sendResponse(200, request);
			
		} catch(Exception e){
			log.error("Exception inside initializeQuote, exception", e);
			return Response.serverError().entity(new ErrorResponse("Exception in initializeQuote, error: " + e.getMessage() + "\n" + e.getStackTrace())).build();
		}
		
	}

	// org.codehaus.jackson.map.JsonMapping jackson 1.9 - included in resteasy-jaxrs-3.0.9.Final.jar
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateQuote(QuoteRequest request) {

		log.info("inside updateQuote");
		log.info("quote: " + request.getQuote());
		//log.info("received parameter property: " + request.getProperty());
		log.info("received answers: " + request.getAnswerMap());
		
		try{
			List<Question> questionLst = rulesProsessor.updateQuote(request);
					
			//log.info("returned updated questions: " + questionLst);
			Map<String, Question> questionMap = buildQuestionByGroup(questionLst, null);
					
			//log.info("returned applicantQuestMap: " + applicantQuestMap);
			request.setQuestionMap(questionMap);
			request.setAnswerMap(null);	// reset answers: we do not send answers back up
	
			log.info("retured updated request");
					
		} catch (Exception e) {
			log.error("updateQuote, exception",e);
			return Response.serverError().entity(new ErrorResponse("Exception in updateQuote, error: " + e.getMessage() + "\n" + e.getStackTrace())).build();
			
		}

		return sendResponse(200, request);
		
	}

	@Path("/eligibility")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doEligiblity(QuoteRequest wrapper) {
		log.info("Inside doEligiblity");
		List sendList = new ArrayList();
		//sendList.add(wrapper.getApplicant());
		//sendList.add(wrapper.getProperty());
		//sendList.add(wrapper.getProperty().getAddress());
		
		// we need a quote to calculate the status and risk
		//quoteSession.insert(new Quote(1234));

		
		rulesProsessor.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());

		return sendResponse(200, wrapper);
		
	}

	@Path("/rate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rateQuote(QuoteRequest request) {
		log.info("Inside rateQuote");
		
		try {
			
			boolean success = rulesProsessor.rateQuote(request);
			
			if (success == false){
				log.info("Unable to rate quote. Still have issues to resolve");
			}
			
		} catch (Exception e) {
			log.error("rateQuote, exception",e);
			return Response.serverError().entity(new ErrorResponse("Exception in rateQuote, error: " + e.getMessage() + "\n" + e.getStackTrace())).build();
			
		} 
		
		return sendResponse(200, request);	

	}


	@Path("/devSettings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDevelopmentSettings() {

		String appEnvironment = System.getProperty("insurance.appEnvironment");
		Map<String, String> devSettings = new HashMap<String, String>();
		devSettings.put("appEnvironment", appEnvironment);
		return sendResponse(200, devSettings);	
		
	}
	
	private Response sendResponse(int status, Object result){
	
		return Response.status(status).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209601").entity(result).build();
	}
	
	/**
	 * Prepare a key/value pair for questions in groups eg. key for property is p.street eg. key for
	 * applicant is a.firstName
	 * 
	 * @param questionLst
	 * @param groupss
	 * @return
	 */
	private Map<String, Question> buildQuestionByGroup(List<Question> questionLst, String groups) {

		log.info("Inside buildQuestionByGroup");
		
		Map<String, Question> questMap = new HashMap<String, Question>();
		
		if (questionLst == null || questionLst.size() == 0){
			log.info("invalid quiestion list");
			return questMap;
		}
		
		for (Question q : questionLst) {

			String key = q.getQuestionId();
			//log.info("add key["+key+"]");
			questMap.put(key, q);
		}

		return questMap;
	}
	
	private synchronized Long generateQuoteId() {
		
		return quoteId++;
	}

}
