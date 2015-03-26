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
