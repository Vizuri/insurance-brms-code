package com.vizuri.insurance.test.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Test;

import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.rest.QuoteRequest;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class InvalidYearBuilt {


  private static final Logger logger = Logger.getLogger(InvalidYearBuilt.class);
  @Test
  public void testYearGreaterThanCurrent() {
    TestUtils.setUpProps();
    RuleProcessor rp = new RuleProcessor();
    Property prop = new Property();

    prop.setYearBuilt(2016);

    List sendList = new ArrayList();
    sendList.add(prop);



    Collection coll = rp.fireRules(RuleProcessor.AGENDA_QUOTE_ERROR_CHECK, sendList.toArray());

    QuoteRequest wrapper = TestUtils.getWrapperValuesFromFacgts(coll);
    logger.info(wrapper.getQuoteMessages());
  }
}
