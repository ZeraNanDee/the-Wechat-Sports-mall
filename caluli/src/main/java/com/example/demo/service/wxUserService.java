package com.example.demo.service;

import com.example.demo.entity.wxUser;

public interface wxUserService {
	
	public boolean isexistUser(String thisopenid);
	
	public void insertUser(wxUser wxUser);
	
	public wxUser selectUser(String openid);
	
	public boolean getUserId(String openid);
	
	public double getbalance(String openid);

	public void addbalance(wxUser addbalance);

	public double thatbalance(Integer thatuserid);
	
	public void updatebalance(wxUser update);

	public void updateuser(wxUser user);

}
