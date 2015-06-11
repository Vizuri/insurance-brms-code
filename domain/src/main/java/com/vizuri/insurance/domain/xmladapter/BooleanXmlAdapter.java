/*
 *	Copyright 2015 Vizuri, a business division of AEM Corporation
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */

package com.vizuri.insurance.domain.xmladapter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@SuppressWarnings("restriction")
public class BooleanXmlAdapter extends XmlAdapter<String,Boolean>{
	
	@Override
	public Boolean unmarshal(String v) throws Exception {
		if(v == null || v.trim().isEmpty()){
			return null;
		}
		
		Boolean  bool =  null;
		
		if(v.equalsIgnoreCase("true") || v.equalsIgnoreCase("false")){
			bool = Boolean.valueOf(v);
		}
		
	
		
		
		return bool;
	}

	@Override
	public String marshal(Boolean v) throws Exception {
		if(v != null){
			return v.toString();
		}
		
		return null;
	}
	
	

}
