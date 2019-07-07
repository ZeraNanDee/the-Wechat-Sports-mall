package com.example.demo.entity;

public class wxRank {
	private int userid;
	private String nickname;
	private String avatarUrl;
	private int stepnum;
	
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
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}
	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "wxRank [userid=" + userid + ", nickname=" + nickname + ", avatarUrl=" + avatarUrl + ", stepnum="
				+ stepnum + "]";
	}

}
