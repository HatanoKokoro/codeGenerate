package org.util;

import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	public static String objectToString(Object obj){
		if(obj==null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		StringWriter ow = new StringWriter();
		JsonGenerator  gen;
		try{
			gen = new JsonFactory().createGenerator(ow);
			mapper.writeValue(gen, obj);
			gen.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		String result = ow.toString();
		result = result.replaceAll("\\\\","\\\\\\\\");
		return result;
	}
	
	public static Object stringToObject(String json,Class cls){
		if(json==null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try{
			obj = mapper.readValue(json, cls);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
}
