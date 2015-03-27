package com.vizuri.insurance.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuoteMessage {
	
	private String group;
	private String property;
	private String message;
	
	private QuoteMessageStatus messageStatus;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QuoteMessageStatus getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(QuoteMessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Override
	public String toString() {
		return "QuoteMessage [group=" + group + ", property=" + property
				+ ", message=" + message + ", messageStatus=" + messageStatus
				+ "]";
	}

	
	
}
