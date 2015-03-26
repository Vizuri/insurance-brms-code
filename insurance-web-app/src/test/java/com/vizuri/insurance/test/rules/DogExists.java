package com.vizuri.insurance.test.rules;

import java.util.Collection;

import org.junit.Test;

import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class DogExists {
	
	@Test
	public void dogExistsTest(){
		try {
			RuleProcessor rp = new RuleProcessor();
			
			Property prop = new Property();
			//prop.setDogExists(true);
			Question q23 = new Question();
			q23.setId(23);
			Question q24 = new Question();
			q24.setId(24);
			
			Collection coll = rp.fireRules(RuleProcessor.AGENDA_QUESTION_DISPLAY	,prop,q23,q24);
			
			System.out.println("q23"+q23);
			System.out.println("q24"+q24);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
