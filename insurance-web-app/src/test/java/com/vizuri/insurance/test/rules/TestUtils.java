package com.vizuri.insurance.test.rules;

import java.util.Collection;

import org.drools.core.common.DefaultFactHandle;

import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;
import com.vizuri.insurance.rest.TransferWrapper;

public class TestUtils {
	
	public static void setUpProps(){
		//Dkie.maven.settings.custom={custom.settings.location.full.path}
		System.setProperty("kie.maven.settings.custom", "/Users/ashakya/insurance/m2repo/mac-settings.xml");
	}
	
	public static TransferWrapper getWrapperValuesFromFacgts(Collection coll){
		TransferWrapper wrapper = new TransferWrapper();
		for (Object object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle ) object;
			if(fact.getObject() instanceof Quote){
				wrapper.setQuote((Quote) fact.getObject());
				
			}
			
			if(fact.getObject() instanceof QuoteMessage){
				QuoteMessage msg = (QuoteMessage) fact.getObject();
				wrapper.getQuoteMessages().add(msg);
			
			}
		}	
		
		return wrapper;
	}
}
