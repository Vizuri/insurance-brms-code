package com.vizuri.insurance.test.rules;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vizuri.insurance.domain.Address;
import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class QuoteElibility {

  @Test
  public void testEligibility() {
    RuleProcessor rp = new RuleProcessor();

    List lst = rp.getAllQuestions();


    Applicant app = new Applicant();
    app.setFirstName("first");
    app.setLastName("Shakya");
    app.setEmail("email@email.com");
    app.setFraud(true);

    Property p = new Property();
    p.setAddress(new Address());
    p.getAddress().setStreet("street");
    p.getAddress().setCity("city");
    p.getAddress().setState("ST");
    p.getAddress().setZip("20120");
    p.setAge(1920);
    // p.setLivingArea(2012);
    p.setAgeOfRoof(85);
    //
    List sendTo = new ArrayList(lst);
    sendTo.add(app);
    sendTo.add(p);

    rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendTo.toArray());
  }
}
