package com.vizuri.insurance.test.rules;

import static org.drools.scorecards.ScorecardCompiler.DrlType.EXTERNAL_OBJECT_MODEL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dmg.pmml.pmml_4_2.descr.PMML;
import org.drools.core.common.DefaultFactHandle;
import org.drools.pmml.pmml_4_2.PMML4Compiler;
import org.drools.scorecards.ScorecardCompiler;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;

import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.domain.Quote;
import com.vizuri.insurance.rest.TransferWrapper;
import com.vizuri.insurance.rest.brms.AgendaListener;
import com.vizuri.insurance.rest.brms.RuleListener;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class RiskRate10 {

	// @Test
	public void test() {
		RuleProcessor rp = new RuleProcessor();
		Property p = new Property();
		p.setYearBuilt(1971);

		Applicant app = new Applicant();
		app.setFraud(false);

		Question q = new Question();
		q.setId(18);

		TransferWrapper wrapper = (TransferWrapper) TestUtils.fromJson(
				"/test.js", TransferWrapper.class);

		List<Object> sendList = new ArrayList<Object>(wrapper.getQuestions());
		sendList.add(wrapper.getApplicant());
		wrapper.getProperty().setAge(31);
		sendList.add(wrapper.getProperty());
		sendList.add(wrapper.getProperty().getAddress());

		rp.fireRules(null, sendList.toArray());
		System.out.println("wrapper.getProperty() : "
				+ wrapper.getProperty());

	}

	@Test
	public void testDRLExecution() throws Exception {
		PMML pmmlDocument = null;
		String drl = null;
		ScorecardCompiler scorecardCompiler = new ScorecardCompiler(
				EXTERNAL_OBJECT_MODEL);
		;
		if (scorecardCompiler.compileFromExcel(RiskRate10.class
				.getResourceAsStream("/scoremodel_externalmodel.xls"))) {
			pmmlDocument = scorecardCompiler.getPMMLDocument();
			assertNotNull(pmmlDocument);

			PMML4Compiler.dumpModel(pmmlDocument, System.out);
			drl = scorecardCompiler.getDRL();
			assertTrue(drl != null && !drl.isEmpty());
			// System.out.println(drl);
		} else {
			fail("failed to parse scoremodel Excel.");
		}

		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write(ks.getResources().newByteArrayResource(drl.getBytes())
				.setSourcePath("test_dscorecard_rules.drl")
				.setResourceType(ResourceType.DRL));
		KieBuilder kieBuilder = ks.newKieBuilder(kfs);
		Results res = kieBuilder.buildAll().getResults();
		System.err.print(res.getMessages());

		
	}

	@Test
	public void testDRLExecutionWithProperty() throws Exception {
		PMML pmmlDocument = null;
		String drl = null;
		ScorecardCompiler scorecardCompiler = new ScorecardCompiler(
				EXTERNAL_OBJECT_MODEL);
		;
		if (scorecardCompiler.compileFromExcel(RiskRate10.class
				.getResourceAsStream("/property_externalmodel.xls"))) {
			pmmlDocument = scorecardCompiler.getPMMLDocument();
			assertNotNull(pmmlDocument);

			PMML4Compiler.dumpModel(pmmlDocument, System.out);
			drl = scorecardCompiler.getDRL();
			assertTrue(drl != null && !drl.isEmpty());
			// System.out.println(drl);
		} else {
			fail("failed to parse scoremodel Excel.");
		}

		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write(ks.getResources().newByteArrayResource(drl.getBytes())
				.setSourcePath("test_dscorecard_rules.drl")
				.setResourceType(ResourceType.DRL));
		KieBuilder kieBuilder = ks.newKieBuilder(kfs);
		Results res = kieBuilder.buildAll().getResults();
		System.err.print(res.getMessages());
	
	}

	@Test
	public void testScore() {
		KieSession kieSession = null;
		
		try {
			AgendaListener agendaListener = new AgendaListener();
			RuleListener ruleListener = new RuleListener();
			KieServices kieServices = KieServices.Factory.get();
			kieSession = kieServices.getKieClasspathContainer().newKieSession();

			kieSession.addEventListener(agendaListener);
			kieSession.addEventListener(ruleListener);

			Quote q = new Quote();
			Property p = new Property();
			
			p.setYearBuilt(2006);
			p.setAgeOfRoof(1);	// will not be used since the srocecard only evaluate fields with type double
			
			System.out.println("p : " + p);

			kieSession.insert(p);
			kieSession.insert(q);

			kieSession.getAgenda().getAgendaGroup("score").setFocus();

			kieSession.fireAllRules();

			Collection factHandles = kieSession.getFactHandles();

			System.out.println("factHandles :" + factHandles.size());
			for (Object object : factHandles) {
				DefaultFactHandle fact = (DefaultFactHandle) object;
				System.out.println("bo : " + fact.getObject());
			}

			

			//System.out.println("scor3e :" + p.getRiskRate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{kieSession.dispose();}catch(Exception e){}
		}

	}
}
