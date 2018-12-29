package com.app.note.util;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger("Exception-");
	
	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private Throwable throwable;
	
	public AppException(){
		this.msg = getStackTraceString(getStackTrace(),getCause());
		this.throwable = getCause();
		logException();
	}
	
	public AppException(Exception ex){
		if(AppUtil.isNotNull(ex)){
			this.msg = getStackTraceString(ex.getStackTrace(),ex);
			this.throwable = ex.getCause();
		}else{
			this.msg = getStackTraceString(getStackTrace(),getCause());
			this.throwable = getCause();
		}
		logException();
	}
	
	public AppException(String msg){
		this.msg = msg+"\n"+getStackTraceString(getStackTrace(),getCause());;
		this.throwable = getCause();
		logException();
	}
	
	public AppException(Throwable t){
		if(AppUtil.isNotNull(t)){
			this.msg = getStackTraceString(t.getStackTrace(),getCause());
			this.throwable = t.getCause();
		}else{
			this.msg = getStackTraceString(getStackTrace(),getCause());
			this.throwable = getCause();
		}
		logException();
	}
	
	public AppException(String code,String msg){
		this.msg = code+": "+ msg +"\n"+getStackTraceString(getStackTrace(),getCause());
		this.throwable = getCause();
		logException();
	}
	
	public AppException(String code,String msg,Throwable t){
		this.msg = code+": "+ msg +"\n"+getStackTraceString(getStackTrace(),getCause());
		this.throwable = t.getCause();
		logException();
	}
	
	public void logException(){
		logger.error(msg,throwable);
	}
	
	private String getStackTraceString(StackTraceElement[] stackTraceElement,Throwable t) {
	    StringBuffer sb = new StringBuffer(500);
	    
	    sb.append("LogId: LOG"+System.currentTimeMillis()+" Date: ["+new Date().toString()+"] ");
	    if(t!=null){sb.append("Error Message: "+t.getCause());}
	    sb.append("\nError Info:\n");
	    if(stackTraceElement!=null){
	    	for (int i = 0; i < stackTraceElement.length; i++) {
	  	      sb.append("\t at " + stackTraceElement[i].toString() + "\n");
	  	    }
	    }
	    return sb.toString();
	}
	
}
