package com.vizuri.insurance.test.rules;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.drools.core.common.DefaultFactHandle;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.domain.QuoteMessage;
import com.vizuri.insurance.rest.TransferWrapper;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class TestUtils {
	private static TestUtils u = new TestUtils();
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
	
	public static Object fromJson(String fileName,Class clazz){
		
		if(! fileName.startsWith("/")){
			//fileName += "/"+fileName;
		}
		RuleProcessor rp = new RuleProcessor();
		
		//System.out.println(testUrl);
		InputStream fios = rp.getClass().getResourceAsStream(fileName);//
		
		//new FileInputStream("/Users/ashakya/insurance/workspace/vizuri_brms-insurance-code/insurance-web-app/src/main/resources/test.js");
		InputStreamReader fread = new InputStreamReader(fios);
		JsonReader reader = new JsonReader(fread);
		GsonBuilder gbuilder = new GsonBuilder();
		gbuilder.addDeserializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				
				return false;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				// TODO Auto-generated method stub
				return clazz.equals(Calendar.class) || clazz.equals(Calendar.class);
			}
		});
		Object wrapper = gbuilder.create().fromJson(reader,clazz);
		
		
		return wrapper;
		
	
	}
}
