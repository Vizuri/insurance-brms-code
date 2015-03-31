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

import org.drools.core.common.DefaultFactHandle;
import org.jboss.logging.Logger;

import com.vizuri.insurance.domain.Address;
import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Claim;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;
import com.vizuri.insurance.rest.brms.RuleProcessor;


@SuppressWarnings({"unchecked","rawtypes"})
@Path("/quoteService")
public class QuotingResourceService {
	private static final Logger log = Logger.getLogger(QuotingResourceService.class);

	
	
	/**
	 * Prepare a key/value pair for questions in groups
	 * eg. key for property is p.street
	 * eg. key for applicant is a.firstName
	 * @param questionLst
	 * @param groupss
	 * @return
	 */
	private Map<String, Question> buildQuestionByGroup(List<Question> questionLst,String groupss){
		
		Map<String, Question> questMap = new HashMap<String,Question>();
	       
	       for (Question que : questionLst) {
	    	   
	    	  
	    	   String group = que.getGroup();
	    	  /* if(!que.getGroup().equalsIgnoreCase(group)){
	    		   continue;
	    	   }*/
	    	   
	    	   String key = group.substring(0,1).toLowerCase()+"."+que.getMappedProperty();
	    	   questMap.put(key, que);
	       }
	       
	     return questMap; 
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransferData() {
		log.info("inside getTransferData");
       TransferWrapper wrap = new TransferWrapper();
       wrap.setApplicant(new Applicant());
       wrap.setProperty(new Property());
       wrap.getProperty().setAddress(new Address());
       wrap.getProperty().setClaims(new ArrayList<Claim>());
       
       RuleProcessor process = new RuleProcessor();
       List<Question> questionLst = process.getAllQuestions();
      
       wrap.setQuestions(questionLst);
       
       
       Map<String, Question> applicantQuestMap = new HashMap<String,Question>();
       applicantQuestMap = buildQuestionByGroup(questionLst, null);
       
      
       wrap.setApplicantQuestMap(applicantQuestMap);
       
       
       
   	return Response
            .status(200)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .header("Access-Control-Max-Age", "1209601")
            .entity(wrap)
            .build();
      
    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response handleQuestionDisplaysOnInputChanges(TransferWrapper wrapper){
		log.info("inside operate");
		///wrapper.getApplicantQuestMap().get("filedForBankruptcy").setEnabled(true);
		 log.infov("parameter wrapper : ",wrapper);
		RuleProcessor rp = new RuleProcessor();
		//List<Question> questionListToSend = new ArrayList<Question>(wrapper.getApplicantQuestMap().values());
		List<Question> questionLst = rp.getAllQuestions();
	
		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());
		
		wrapper.getQuoteMessages().clear();
		Collection collError = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());
		
		for (Object object : collError) {
			DefaultFactHandle fact = (DefaultFactHandle ) object;
			
			
			if(fact.getObject() instanceof QuoteMessage){
				QuoteMessage msg = (QuoteMessage) fact.getObject();
				wrapper.getQuoteMessages().add(msg);
			
			}
		}
	
		Map mp = rp.fireQuestionRule(RuleProcessor.AGENDA_QUESTION_DISPLAY, sendList);
		
		questionLst =(List) mp.get("questions");
		
	       Map<String, Question> applicantQuestMap = new HashMap<String,Question>();
		   applicantQuestMap = buildQuestionByGroup(questionLst, null);
	       wrapper.setApplicantQuestMap(applicantQuestMap);
	       
	       log.infov("retured wrapper : ",wrapper);
	  	return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209601")
	            .entity(wrapper)
	            .build();
	}
	
	
	
	@Path("/eligibility")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doEligiblity(TransferWrapper wrapper){
		log.info("Inside doEligiblity");
		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());
		
		RuleProcessor rp = new RuleProcessor();
		/*Collection coll  =*/rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());
		
		return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209601")
	            .entity(wrapper)
	            .build();
	}
	
	
	@Path("/quoteCalculate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response docalculation(TransferWrapper wrapper){
		log.info("Inside docalculation");
		
		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());
		RuleProcessor rp = new RuleProcessor();
		
		wrapper.getQuoteMessages().clear();
		Collection collError = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());
		
		for (Object object : collError) {
			DefaultFactHandle fact = (DefaultFactHandle ) object;
			
			
			if(fact.getObject() instanceof QuoteMessage){
				QuoteMessage msg = (QuoteMessage) fact.getObject();
				wrapper.getQuoteMessages().add(msg);
			
			}
		}
		if(wrapper.getQuoteMessages().isEmpty()){
			rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());
			
			Collection coll  = rp.fireRules(RuleProcessor.AGENDA_CALCULATION ,sendList.toArray());
			
			for (Object object : coll) {
				DefaultFactHandle fact = (DefaultFactHandle ) object;
				if(fact.getObject() instanceof Quote){
					wrapper.setQuote((Quote) fact.getObject());
					
				}
				
			
			}			
		}
		
		
		return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209601")
	            .entity(wrapper)
	            .build();
		
	}
	
	
	@Path("/devSettings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDevelopmentSettings(){
		
		String appEnvironment = System.getProperty("insurance.appEnvironment");
		Map<String,String> devSettings = new HashMap<String, String>();
		devSettings.put("appEnvironment", appEnvironment);
		return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209601")
	            .entity(devSettings)
	            .build();
	}
	
}
