package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.wxSignMapper;
import com.example.demo.entity.wxSign;

@Service("wxSignService")
public class wxSignServiceImpl implements wxSignService {

	@Autowired
	private wxSignMapper signMapper;
	@Override
	public boolean getSigned(String openid) {
		
		if (signMapper.getSigned(openid)>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Integer countSigned(String openid) {
		return signMapper.countSigned(openid);
	}
	
	public void insertSign(wxSign sign) {
		signMapper.insertSign(sign);
	}

}
