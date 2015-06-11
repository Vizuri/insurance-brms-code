/*
 *	Copyright 2015 Vizuri, a business division of AEM Corporation
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */


package com.vizuri.insurance.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@Stateless
//@Stateless
//import org.jboss.logging.Logger;"
import org.jboss.logging.Logger;

import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.rest.brms.RuleProcessor;
@Path("/insurance")
@RequestScoped
public class InsuranceRestService {
	  

	   private static final Logger log = Logger.getLogger(InsuranceRestService.class);
	    @Inject
	   private Validator validator;
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/createApplicant")
	    public Applicant createApplicant(Applicant applicant) {
	    	
	    	
	    	
	    	
	    	
	    	log.infov("inside createApplicant"
	    			+ " : {0}",applicant);
	        return applicant;
	        
	        
	        
	    	
	    	
	    }
	    
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/createProperty")
	    public Property createProperty(Property property) {
	    	
	    	
	    	
	    	
	    	
	    	log.infov("inside createProperty {0}",property);
	        return property;
	        
	        
	        
	    	
	    	
	    }
	    
	    
	   
	    
	    
	    
	    @GET
	    @Path("/questionsByGroup")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getQuestions(@QueryParam("group") String group){
	    	log.info("inside getQuestions");
	    	Question q = new Question();
	    	
	    	q.setGroup("applicant");
	    	q.setMappedObject("Applicant");
	    	q.setMappedProperty("firstName");
	    	q.setDescription("First Name");
	    	q.setAnswerType("text");
	    	Question q2 = new Question();
	    	
	    	q2.setGroup("applicant");
	    	q2.setMappedObject("Applicant");
	    	q2.setMappedProperty("lastName");
	    	q2.setDescription("Last Name");
	    	q.setAnswerType("text");
	    	
	    	List li = new ArrayList<Question>();
	    	li.add(q);
	    	li.add(q2);
	    	
	    	RuleProcessor rule = new RuleProcessor();
	    	//rule.getAllQuestions();
	    	
	    	Map m = new HashMap();
	    	m.put("questions", rule.getAllQuestions());
	    	
	    	return Response
	                .status(200)
	                .header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	                .header("Access-Control-Allow-Credentials", "true")
	                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	                .header("Access-Control-Max-Age", "1209601")
	                .entity(m)
	                .build();
	    	//return ;
	    }
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/fireRulesApplicant")
	    
	    public Response fireRulesApplicant(TransferWrapper wrapper){
	    	log.info("Inside fireRulesApplicant");
	    	RuleProcessor process = new RuleProcessor();
	    //	process.fireApplicationRules(wrapper.getApplicant(), wrapper.getQuestions());
	    	
	    	return Response
	                .status(200)
	                .header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	                .header("Access-Control-Allow-Credentials", "true")
	                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	                .header("Access-Control-Max-Age", "1209600")
	                .entity(wrapper)
	                .build();
	    }
	    
	    public static void main(String[] args) {
	   
		}
	    
	    
}
