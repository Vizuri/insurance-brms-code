package com.vizuri.insurance.test.rules;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vizuri.insurance.domain.Applicant;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.domain.Question;
import com.vizuri.insurance.rest.TransferWrapper;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class RiskRate10 {

  @Test
  public void test() {
    RuleProcessor rp = new RuleProcessor();
    Property p = new Property();
    p.setYearBuilt(1971);

    Applicant app = new Applicant();
    app.setFraud(false);

    Question q = new Question();
    q.setId(18);

    TransferWrapper wrapper =
        (TransferWrapper) TestUtils.fromJson("/test.js", TransferWrapper.class);

    List sendList = new ArrayList(wrapper.getQuestions());
    sendList.add(wrapper.getApplicant());
    wrapper.getProperty().setRiskRate(0);
    sendList.add(wrapper.getProperty());
    sendList.add(wrapper.getProperty().getAddress());

    rp.fireRules(RuleProcessor.AGENDA_RISK_RULE_GROUP, sendList.toArray());


  }
}
