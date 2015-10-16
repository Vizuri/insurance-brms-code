package com.vizuri.insurance.test.rules;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.core.common.DefaultFactHandle;
import org.jboss.logging.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.vizuri.insurance.domain.Property;
import com.vizuri.insurance.rest.QuoteRequest;
import com.vizuri.insurance.rest.brms.RuleProcessor;

public class RoofAgeTest {
  private static final Logger logger = Logger.getLogger(RoofAgeTest.class);
  @Test
  public void test() throws Exception {
    RuleProcessor rp = new RuleProcessor();

   
    InputStream fios = this.getClass().getResourceAsStream("/test.js");//

    InputStreamReader fread = new InputStreamReader(fios);
    JsonReader reader = new JsonReader(fread);

    Gson gson = new Gson();
    QuoteRequest wrapper =
        (QuoteRequest) TestUtils.fromJson("/test.js", QuoteRequest.class);// gson.fromJson(reader,
                                                                                // TransferWrapper.class);
    Property prop = new Property();
    prop.setAgeOfRoof(100);

    List sendList = new ArrayList();
    //sendList.add(wrapper.getApplicant());
    //sendList.add(wrapper.getProperty());
   // sendList.add(wrapper.getProperty().getAddress());

    logger.info("wrapper : " + wrapper);
    Collection coll = rp.fireRules(RuleProcessor.AGENDA_ELIGIBLITY, sendList.toArray());

    for (Object object : coll) {
      DefaultFactHandle fact = (DefaultFactHandle) object;
      logger.info("object : " + fact.getObject());
    }

  }

}
