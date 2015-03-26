package com.vizuri.insurance.rest.brms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.common.DefaultFactHandle;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.vizuri.insurance.domain.Question;

public class RuleProcessor {
	//private KieServices kServices = KieServices.Factory.get();
	
	private KieContainer kContainer = RuleProcessor.Factory.get();//kServices.getKieClasspathContainer();
	
    private static class Factory {
    	private static KieContainer kContainer;
    	static{
    		try {
    			//kie.maven.settings.custom=/Users/ashakya/insurance/m2repo/mac-settings.xml
    			
				//String M2_HOME="/Users/ashakya/insurance/m2repo";
				//System.setProperty("M2_HOME", M2_HOME);
				//System.setProperty("kie.maven.settings.custom","/Users/ashakya/insurance/m2repo/mac-settings.xml");
				//System.setProperty("org.guvnor.m2repo.dir","/Users/ashakya/insurance/jboss/jboss-eap-6.1/bin/repositories");
				KieServices kServices = KieServices.Factory.get();

				ReleaseId releaseId = kServices.newReleaseId( "com.vizuri", "Insurance", "1.0-SNAPSHOT");

				kContainer = kServices.newKieContainer( releaseId );

				KieScanner kScanner = kServices.newKieScanner( kContainer );


				// Start the KieScanner polling the maven repository every 10 seconds

				kScanner.start( 10000L );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		public static KieContainer get() {
            return kContainer;
        }
    }
	public RuleProcessor() {
		
	}

	public static final String  AGENDA_QUESTION_GROUP = "question-group";
	public static final String  AGENDA_QUESTION_DISPLAY = "question-display";
	public static final String AGENDA_ELIGIBLITY = "eligibility";
	public static final String AGENDA_CALCULATION = "calculation";
	//public static final String  AGENDA_MAIN_GROUP = "question-group";
	AgendaListener agendaListener  = new AgendaListener();
	RuleListener ruleListener = new RuleListener();
	/**
	 * 
	 * @param agendaGroup can be null, 
	 * @param object
	 * @return
	 */
	public Collection fireRules(String agendaGroup,Object... object){
		
		KieSession kieSession = null;
		try {
			kieSession = kContainer.newKieSession();
			
			if(object != null){
				for (Object obj : object) {
					kieSession.insert(obj);
				}
			}
			
			if(agendaGroup != null){
				kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
			}
			kieSession.addEventListener(agendaListener);
			kieSession.addEventListener(ruleListener);
			
			kieSession.fireAllRules();
			Collection coll = kieSession.getFactHandles();
			
			if(true){
				return coll;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				kieSession.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public List<Question> getAllQuestions(){
		
		Collection coll = fireRules(AGENDA_QUESTION_GROUP);
		
		List<Question> quests = new ArrayList<Question>();
		Collections.sort(quests, new Comparator<Question>() {

			@Override
			public int compare(Question o1, Question o2) {
				
				return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
			}
		});
		
		for (Object object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle)object;
			if( false == (fact.getObject() instanceof Question )){
				continue;
			}
			
			Question q = (Question) fact.getObject();
			quests.add(q);
			
		}
		return quests;
		
	}
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map fireApplicationRules(Applicant applicant, List<Question> questions){
		
		
		Map m = new HashMap();
		List objLst = new ArrayList(questions);
		objLst.add(applicant);
		
		
		
		Map questTrack = new HashMap<Integer,Question>();
		
		for (Question question : questions) {
			questTrack.put(question.getId(),question);
		}
		Collection coll = fireRules(AGENDA_QUESTION_DISPLAY,objLst.toArray());
		
		for (Object object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle)object;
			if( false == (fact.getObject() instanceof Question )){
				continue;
			}
			
			Question q = (Question) fact.getObject();
	
			questTrack.put(q.getId(),q);
		
			
		}
		
		List<Question> questionRetList = new ArrayList<Question>();
		questionRetList.addAll(questTrack.values());
		
		m.put("applicant", applicant);
		m.put("questions",questionRetList);
		return m;
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map fireQuestionRule(String agenda,List objLst){
		
		
		Map m = new HashMap();
		
		Collection coll = fireRules(agenda,objLst.toArray());
		Map questTrack = new HashMap<Integer,Question>();
		for (Object object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle)object;
			if( false == (fact.getObject() instanceof Question )){
				continue;
			}
			
			Question q = (Question) fact.getObject();
	
			questTrack.put(q.getId(),q);
		
			
		}
		
		List<Question> questionRetList = new ArrayList<Question>();
		questionRetList.addAll(questTrack.values());
		
		
		m.put("questions",questionRetList);
		return m;
	}
	
	/*public static void main(String[] args) {
		RuleProcessor r = new   RuleProcessor();
		//List<Question> quests = r.getAllQuestions();
		Applicant a = new Applicant();
		a.setFirstName("Vip");
		
		Question q = new Question();
		q.setId(5);
		System.out.println("map before : "+q);
		Map map = r.fireApplicationRules(a, Arrays.asList(q));
		System.out.println("map : "+map);
		
	}*/
	public static void main(String[] args) {
		
	}
	
}
