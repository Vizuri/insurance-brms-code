package com.vizuri.insurance.test.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.core.common.DefaultFactHandle;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.rest.TransferWrapper;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class RoofAgeTest {

	@Test
	public void test() throws Exception {
		RuleProcessor rp = new RuleProcessor();
		
		//System.out.println(testUrl);
		InputStream fios = this.getClass().getResourceAsStream("/test.js");//
		
		//new FileInputStream("/Users/ashakya/insurance/workspace/vizuri_brms-insurance-code/insurance-web-app/src/main/resources/test.js");
		InputStreamReader fread = new InputStreamReader(fios);
		JsonReader reader = new JsonReader(fread);
		
		Gson gson = new Gson();
		TransferWrapper wrapper = gson.fromJson(reader, TransferWrapper.class);
		Property prop = new Property();
		prop.setAgeOfRoof(100);
		
		List sendList = new ArrayList(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());
		
		System.out.println("wrapper : "+wrapper);
		Collection coll = rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());
		
		for (Object object : coll) {
			DefaultFactHandle fact = (DefaultFactHandle) object;
			System.out.println("object : "+fact.getObject());
		}
		
	}

}
