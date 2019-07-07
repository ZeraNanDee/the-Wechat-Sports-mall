package com.example.demo.entity;

public class wxSign {
	private int signid;
	private int userid;
	private double ticket;
	
	
	public wxSign(int userid, double ticket) {//插入今日签到的数据
		super();
		this.userid = userid;
		this.ticket = ticket;
	}
	/**
	 * @return the signid
	 */
	public int getSignid() {
		return signid;
	}
	/**
	 * @param signid the signid to set
	 */
	public void setSignid(int signid) {
		this.signid = signid;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the ticket
	 */
	public double getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(double ticket) {
		this.ticket = ticket;
	}

}
