package com.example.demo.entity;

public class wxUser {
	private int userid;
	private String openid;
	private int inviteId;
	private String nickName;
	private String city;
	private String country;
	private String avatarUrl;
	private int gender;
	private String language;
	private String province;
	private double balance;
	

	public wxUser(){}
	
	public wxUser(String avatarUrl, String nickName)//一一对应mapper中的数据库字段，返回对象为wxUser
	{
		System.out.println("有参构造函数："+avatarUrl+nickName);
		this.avatarUrl=avatarUrl;
		this.nickName=nickName;
	}

	public wxUser(int userid,double balance) //邀请后增加的余额
	{
		super();
		this.userid=userid;
		this.balance=balance;
	}

	public wxUser(String openid,double balance) //更新用户兑换后的燃烧券余额
	{
		super();
		this.openid=openid;
		this.balance=balance;
	}

public wxUser(int userid, String openid, int inviteId, String nickName, String city, String country,
		String avatarUrl, int gender, String language, String province, double balance) {
	super();
	this.userid = userid;
	this.openid = openid;
	this.inviteId = inviteId;
	this.nickName = nickName;
	this.city = city;
	this.country = country;
	this.avatarUrl = avatarUrl;
	this.gender = gender;
	this.language = language;
	this.province = province;
	this.balance = balance;
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
 * @return the openid
 */
public String getOpenid() {
	return openid;
}

/**
 * @param openid the openid to set
 */
public void setOpenid(String openid) {
	this.openid = openid;
}


/**
 * @return the inviteId
 */
public int getInviteId() {
	return inviteId;
}

/**
 * @param inviteId the inviteId to set
 */
public void setInviteId(int inviteId) {
	this.inviteId = inviteId;
}

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
 * @return the city
 */
public String getCity() {
	return city;
}

/**
 * @param city the city to set
 */
public void setCity(String city) {
	this.city = city;
}

/**
 * @return the country
 */
public String getCountry() {
	return country;
}

/**
 * @param country the country to set
 */
public void setCountry(String country) {
	this.country = country;
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
 * @return the gender
 */
public int getGender() {
	return gender;
}

/**
 * @param gender the gender to set
 */
public void setGender(int gender) {
	this.gender = gender;
}

/**
 * @return the language
 */
public String getLanguage() {
	return language;
}

/**
 * @param language the language to set
 */
public void setLanguage(String language) {
	this.language = language;
}

/**
 * @return the province
 */
public String getProvince() {
	return province;
}

/**
 * @param province the province to set
 */
public void setProvince(String province) {
	this.province = province;
}


/**
 * @return the balance
 */
public double getBalance() {
	return balance;
}

/**
 * @param balance the balance to set
 */
public void setBalance(double balance) {
	this.balance = balance;
}


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */

	@Override
	public String toString() {
		return "wxUser{" +
				"userid=" + userid +
				", openid='" + openid + '\'' +
				", inviteId=" + inviteId +
				", nickName='" + nickName + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", avatarUrl='" + avatarUrl + '\'' +
				", gender=" + gender +
				", language='" + language + '\'' +
				", province='" + province + '\'' +
				", balance=" + balance +
				'}';
	}
}
