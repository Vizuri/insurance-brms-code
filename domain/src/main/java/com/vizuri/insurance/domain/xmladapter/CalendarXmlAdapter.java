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



package com.vizuri.insurance.domain.xmladapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CalendarXmlAdapter extends XmlAdapter<String, Calendar> {
  SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
 
  @Override
  public Calendar unmarshal(String v) throws Exception {
    if (v == null || v.trim().isEmpty()) {
      return null;
    }

    Calendar cal = null;

    try {

      Date dt = form.parse(v);
      cal = Calendar.getInstance();
      cal.setTime(dt);
    } catch (Exception e) {
      
    }
    return cal;
  }

  @Override
  public String marshal(Calendar v) throws Exception {
    if (v != null) {
      return form.format(v.getTime());
    }

    return null;
  }

}
