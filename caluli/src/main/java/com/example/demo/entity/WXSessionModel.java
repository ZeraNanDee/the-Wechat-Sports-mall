package com.example.demo.entity;

public class WXSessionModel {
	private String sessionkey;
	private String openid;
	public WXSessionModel() {
		
	}

	public String getSession_key() {
		return sessionkey;
	}
	public void setSession_key(String sessionkey) {
		this.sessionkey = sessionkey;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
}
