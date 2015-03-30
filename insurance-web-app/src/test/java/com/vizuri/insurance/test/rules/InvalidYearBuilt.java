package com.vizuri.insurance.test.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.rest.TransferWrapper;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class InvalidYearBuilt {
	
	
	
	
	@Test
	public void testYearGreaterThanCurrent(){
		TestUtils.setUpProps();
		RuleProcessor rp = new RuleProcessor();
		Property prop = new Property();
		
		prop.setYearBuilt(2016);
		
		List sendList = new ArrayList();
		sendList.add(prop);
	
		
		
		Collection coll = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());
		
		TransferWrapper wrapper = TestUtils.getWrapperValuesFromFacgts(coll);
		System.out.println(wrapper.getQuoteMessages());
	}
}
