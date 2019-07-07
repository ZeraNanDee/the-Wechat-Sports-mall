package com.example.demo.entity;

public class wxStep {

	private int stepid;
	private int userid;
	private int stepnum;
	private double ticket;
	
	public wxStep( int stepnum) {//用于更新步数表
		super();
		this.stepnum = stepnum;
	}

	public wxStep( int stepnum, double ticket) {//用于更新步数表
		super();
		this.stepnum = stepnum;
		this.ticket = ticket;
	}
	
	public wxStep(int userid, int stepnum, double ticket) {//用于更新步数表
		super();
		this.userid = userid;
		this.stepnum = stepnum;
		this.ticket = ticket;
	}
	
	
	public wxStep(int stepid, int userid, int stepnum, double ticket) {
		super();
		this.stepid = stepid;
		this.userid = userid;
		this.stepnum = stepnum;
		this.ticket = ticket;
	}

	/**
	 * @return the stepid
	 */
	public int getStepid() {
		return stepid;
	}

	/**
	 * @param stepid the stepid to set
	 */
	public void setStepid(int stepid) {
		this.stepid = stepid;
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
	 * @return the stepnum
	 */
	public int getStepnum() {
		return stepnum;
	}

	/**
	 * @param stepnum the stepnum to set
	 */
	public void setStepnum(int stepnum) {
		this.stepnum = stepnum;
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
