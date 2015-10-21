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

package com.vizuri.insurance.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuoteMessage {

	private String group;
	private String property;
	private String message;
	private Integer priority;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "QuoteMessage [group=" + group + ", property=" + property
				+ ", message=" + message + ", priority=" + priority
				+ ", messageStatus=" + messageStatus + "]";
	}

}
