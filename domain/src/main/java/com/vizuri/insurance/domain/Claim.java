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

import java.util.Date;

public class Claim {

  private Integer id;
  
  private Date claimDate;
  private double claimAmount;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getClaimDate() {
    return claimDate;
  }

  public void setClaimDate(Date claimDate) {
    this.claimDate = claimDate;
  }

  public double getClaimAmount() {
    return claimAmount;
  }

  public void setClaimAmount(double claimAmount) {
    this.claimAmount = claimAmount;
  }

  @Override
  public String toString() {
    return "Claim [id=" + id + ", claimDate=" + claimDate + ", claimAmount=" + claimAmount + "]";
  }


}
