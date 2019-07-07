package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.wxUserMapper;
import com.example.demo.entity.wxUser;

@Service("wxUserService")
public class wxUserServiceImpl implements wxUserService {

	@Autowired
	private wxUserMapper userMapper;

	public boolean isexistUser(String thisopenid) {

		if (userMapper.isexistUser(thisopenid) >= 1) {
			return true;
		}
		return false;

	}

	public void insertUser(wxUser wxUser) {
		userMapper.insertUser(wxUser);
	}

	public wxUser selectUser(String openid) {

		return userMapper.selectUser(openid);
	}

	public boolean getUserId(String openid) {
		if (userMapper.getUserId(openid) != null) {
			return true;
		}
		return false;
	}

	public double getbalance(String openid) { return userMapper.getbalance(openid); }

    public void addbalance(wxUser addbalance){ userMapper.addbalance(addbalance);};

    public double thatbalance(Integer thatuserid){return userMapper.thatbalance(thatuserid);}
	
	public void updatebalance(wxUser update) { userMapper.updatebalance(update); }

	public void updateuser(wxUser user){ userMapper.updateuser(user); }
}