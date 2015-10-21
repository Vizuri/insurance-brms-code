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
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;



public class RuleListener implements RuleRuntimeEventListener {
	private static final Logger log = Logger.getLogger(RuleListener.class);

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		//log.info("objectInserted");

		Object factObject = event.getObject();
		log.info("inserted Object : " + factObject);
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		//log.info("objectUpdated");

		Object factObject = event.getObject();

		log.info("updated Object : " + factObject);


	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		//log.info("objectDeleted");

		Object factObject = event.getOldObject();

		log.info("deleted Object : " + factObject);
	}


}
