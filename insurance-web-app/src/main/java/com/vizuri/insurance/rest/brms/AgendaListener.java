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



import org.jboss.logging.Logger;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;

public class AgendaListener implements AgendaEventListener {
  private static final Logger log = Logger.getLogger(AgendaListener.class);



  @Override
  public void matchCreated(MatchCreatedEvent event) {
    // if (log.isInfoEnabled()) {

    log.info("matchCreated : " + event.getMatch().getRule());
    for(Object obj: event.getMatch().getObjects()){
    	log.info(obj);
    }
    log.info("matchCreated end:");
    // }

  }

  @Override
  public void matchCancelled(MatchCancelledEvent event) {

    // if (log.isInfoEnabled()) {
    log.info("matchCancelled : " + event.getMatch().getRule());
    // }
  }

  @Override
  public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
    // log.info("beforeRuleFlowGroupDeactivated : "+event.getRuleFlowGroup());
    // log.info("beforeRuleFlowGroupDeactivated");
  }

  @Override
  public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
    
    // log.info("beforeRuleFlowGroupDeactivated" +event.getRuleFlowGroup());


  }

  @Override
  public void beforeMatchFired(BeforeMatchFiredEvent event) {
    // if (log.isInfoEnabled()) {
    
    log.info("beforeMatchFired" + event.getMatch().getRule());
    //
    // }
  }

  @Override
  public void agendaGroupPushed(AgendaGroupPushedEvent event) {
    
    // log.info("agendaGroupPushed"+event.getAgendaGroup());
  }

  @Override
  public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
    // 
    // log.info("agendaagendaGroupPoppedGroupPushed "+event.getAgendaGroup());
  }

  @Override
  public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
    
    // log.info("afterRuleFlowGroupDeactivated" + event.getRuleFlowGroup());
  }

  @Override
  public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
    
    // log.info("afterRuleFlowGroupActivated" + event.getRuleFlowGroup());
  }

  // FactHandle handle = new DefaultFactHandle();

  @Override
  public void afterMatchFired(AfterMatchFiredEvent event) {
    // if (log.isInfoEnabled()) {
    
    log.info("afterMatchFired  : " + event.getMatch().getRule());
    log.info("event facts size: " + event.getMatch().getFactHandles().size());
    // }



  }

}
