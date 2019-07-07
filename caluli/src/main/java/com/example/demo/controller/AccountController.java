package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;


import com.example.demo.dao.wxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.wxUser;
import com.example.demo.service.wxUserService;

@RestController
@RequestMapping("/Account")
public class AccountController {

	@Autowired
	private wxUserService getUserService;
	
	@GetMapping("/getUser")

	public Map<String,String> AccountUser(@CookieValue("JSEESION") String getopenid) {

		System.out.println("进入getUser");
		Map<String,String> wxuser=new HashMap<String,String>();

	    if (getopenid!=null&&getUserService.isexistUser(getopenid)) {
			System.out.println(getopenid);
			
			wxUser user=getUserService.selectUser(getopenid);
			String getAvatarUrl=user.getAvatarUrl();
			String getNickname=user.getNickName();
			wxuser.put("status", "1");
			wxuser.put("avatarUrl", getAvatarUrl);
			wxuser.put("nickName", getNickname);
			return wxuser;
		} else {
			System.err.println("暂时未获取到openid");
		}
		return null;

	}
	@Autowired
	private wxUserMapper usermapper;

	@PostMapping("/reSetUser")
	public Map<String,String> reSetUser(@CookieValue("JSEESION") String openid,@RequestBody wxUser user) {
		int userid=usermapper.getUserId(openid);
		user.setUserid(userid);
		System.out.println(user.toString());
		getUserService.updateuser(user);
		Map<String,String> msg=new HashMap<String,String>();
		msg.put("msg","更新成功");
		return msg;
	}

}
