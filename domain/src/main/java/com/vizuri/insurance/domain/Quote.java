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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quote {
	private Long id;
	private double amount;
	private double riskRate;
	private String status;
	private List reason = new ArrayList();	// Arrays.asList("Property too old", "Roof too old", "NEEDS_APPROVAL", "ELIGIBLE")

	public Quote(){
		
	}

	public Quote(Long id){
		setId(id);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRiskRate() {
		return riskRate;
	}

	public void setRiskRate(double riskRate) {
		this.riskRate = riskRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List getReason() {
		return reason;
	}

	public void setReason(List reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Quote [id=" + id + ", amount=" + amount + ", riskRate="
				+ riskRate + ", status=" + status + ", reason=" + reason + "]";
	}

}
