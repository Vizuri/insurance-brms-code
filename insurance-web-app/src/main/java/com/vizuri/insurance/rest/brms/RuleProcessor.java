/*
 * Copyright 2015 Vizuri, a business division of AEM Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


package com.vizuri.insurance.rest.brms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.common.DefaultFactHandle;
import org.jboss.logging.Logger;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.vizuri.insurance.domain.Question;

public class RuleProcessor {
 

  private KieContainer kContainer = RuleProcessor.Factory.get();// kServices.getKieClasspathContainer();
  private static final Logger logger =Logger.getLogger(RuleProcessor.class);
  private static class Factory {
    private static KieContainer kContainer;
    static {
      try {


        // if the local maven repository is not in the default ${user.home}/.m2
        // need to provide the custom settings.xml
        // pass property value
        // -Dkie.maven.settings.custom={custom.settings.location.full.path}

        KieServices kServices = KieServices.Factory.get();

        ReleaseId releaseId = kServices.newReleaseId("com.vizuri", "Insurance", "1.0-SNAPSHOT");

        kContainer = kServices.newKieContainer(releaseId);

        KieScanner kScanner = kServices.newKieScanner(kContainer);


        // Start the KieScanner polling the maven repository every 10 seconds

        kScanner.start(10000L);
      } catch (Exception e) {
        
        logger.error("",e);
      }
    }

    public static KieContainer get() {
      return kContainer;
    }
  }

  public RuleProcessor() {

  }

  public static final String AGENDA_QUESTION_GROUP = "question-group";
  public static final String AGENDA_QUESTION_DISPLAY = "question-display";
  public static final String AGENDA_ELIGIBLITY = "eligibility";
  public static final String AGENDA_CALCULATION = "calculation";
  public static final String AGENDA_QUOTE_ERROR_CHECK = "quote-error-check";
  public static final String AGENDA_RISK_RULE_GROUP = "riskRuleGroup";
  // public static final String AGENDA_MAIN_GROUP = "question-group";
  AgendaListener agendaListener = new AgendaListener();
  RuleListener ruleListener = new RuleListener();

  /**
   * 
   * @param agendaGroup can be null,
   * @param object
   * @return
   */
  public Collection fireRules(String agendaGroup, Object... object) {

    KieSession kieSession = null;
    try {
      kieSession = kContainer.newKieSession();
      kieSession.addEventListener(agendaListener);
      kieSession.addEventListener(ruleListener);
      if (object != null) {
        for (Object obj : object) {
          kieSession.insert(obj);
        }
      }

      if (agendaGroup != null) {
         kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
      }


      kieSession.fireAllRules();
      Collection coll = kieSession.getFactHandles();

      if (true) {
        return coll;
      }

    } catch (Exception e) {
      logger.error("",e);
    } finally {
      try {
        kieSession.dispose();
      } catch (Exception e) {
        logger.error("",e);
      }
    }

    return null;
  }

  public List<Question> getAllQuestions() {

    Collection coll = fireRules(AGENDA_QUESTION_GROUP);

    List<Question> quests = new ArrayList<Question>();
    Collections.sort(quests, new Comparator<Question>() {

      @Override
      public int compare(Question o1, Question o2) {

        return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
      }
    });

    for (Object object : coll) {
      DefaultFactHandle fact = (DefaultFactHandle) object;
      if (false == (fact.getObject() instanceof Question)) {
        continue;
      }

      Question q = (Question) fact.getObject();
      quests.add(q);

    }
    return quests;

  }



  @SuppressWarnings({"rawtypes", "unchecked"})
  public Map fireQuestionRule(String agenda, List objLst) {


    Map m = new HashMap();

    Collection coll = fireRules(agenda, objLst.toArray());
    Map questTrack = new HashMap<Integer, Question>();
    for (Object object : coll) {
      DefaultFactHandle fact = (DefaultFactHandle) object;
      if (false == (fact.getObject() instanceof Question)) {
        continue;
      }

      Question q = (Question) fact.getObject();

      questTrack.put(q.getId(), q);


    }

    List<Question> questionRetList = new ArrayList<Question>();
    questionRetList.addAll(questTrack.values());


    m.put("questions", questionRetList);
    return m;
  }

 

}
