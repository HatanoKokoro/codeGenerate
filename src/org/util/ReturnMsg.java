package org.util;

public class ReturnMsg {
	
	private boolean success;
	private String msg;
	private String resource;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public ReturnMsg(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	public ReturnMsg() {
		super();
	}

}
