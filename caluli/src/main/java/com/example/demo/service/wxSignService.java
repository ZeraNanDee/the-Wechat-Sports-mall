package com.example.demo.service;

import com.example.demo.entity.wxSign;

public interface wxSignService {
	
	public boolean getSigned(String openid);
	
	public Integer countSigned(String openid);
	
	public void insertSign(wxSign sign);

}
