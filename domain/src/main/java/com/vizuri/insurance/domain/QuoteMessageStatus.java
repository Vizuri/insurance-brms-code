package com.vizuri.insurance.domain;

public enum QuoteMessageStatus{
	
	ERROR("ERROR"),
	INFO("INFO"),
	WARNING("WARNING");
	
	String msg;
	
	QuoteMessageStatus(String msg){
		this.msg = msg;
	}
	
	
	
}