package com.vizuri.insurance.rest;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.kie.api.runtime.KieSession;

public class SessionCache {
	
	private final static transient Logger log = Logger.getLogger(SessionCache.class);
	
	private static Map<Long, KieSession> sessionMap = new HashMap<Long,KieSession>();
		
	public void saveQuoteSession(Long quoteId, KieSession quoteSession) {
		log.info("Storing quoteSession for quote id: " + quoteId);
		sessionMap.put(quoteId, quoteSession);
	}

	
	public KieSession getQuoteSession(Long quoteId) {
		log.info("Retrieving quote session for quoteId: " + quoteId);
		return sessionMap.get(quoteId);
	}

	
	public void removeQuoteSession(Long quoteId) {
		log.info("HashMap:Removing quote session for quoteId: " + quoteId);
		
		KieSession quoteSession = sessionMap.get(quoteId);
		
		if (quoteSession != null){
			quoteSession.destroy();
		}
	
		sessionMap.remove(quoteId);
	}

	
	public void clearAllQuoteSessions() {
		log.info("Clearing all quote sessions...");
		sessionMap.clear();
	}

}
