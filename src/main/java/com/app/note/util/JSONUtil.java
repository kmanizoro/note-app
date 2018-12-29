package com.app.note.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

	public static String getJSONValueAsString(Object obj) throws AppException{
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuffer buffer = new StringBuffer();
		try{
			buffer.append(objectMapper.writeValueAsString(obj));
		}catch(JsonParseException jse){
			throw new AppException(jse);
		}catch(Exception exception){
			throw new AppException(exception);
		}
		return buffer.toString();
	}
	
}
