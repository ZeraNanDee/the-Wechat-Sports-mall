package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.wxUserMapper;
import com.example.demo.entity.wxSign;
import com.example.demo.entity.wxUser;
import com.example.demo.service.wxSignService;

@RestController
@RequestMapping("/Sign")
public class SignController {

	@Autowired
	private wxSignService signService;
	
	@GetMapping("/getTodaySign")
	public Map<String,Object> Sign(@CookieValue("JSEESION") String openid) {
		
		Map<String, Object> msg=new HashMap<String, Object>();
		
		int signtotal= signService.countSigned(openid);
		System.out.println("签到总天数"+signtotal);
		if (signService.getSigned(openid)) {
			msg.put("status","1" );//今日已经签到
			msg.put("signtotal", signtotal);
			msg.put("sign",true);
		}else {
			msg.put("status","1" );
			msg.put("signtotal", signtotal);
			msg.put("sign",false);
		}

		if (signtotal<5) {
			String rank="运动青铜Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<10) {
			String rank="运动青铜Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<15) {
			String rank="运动青铜Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<20) {
			String rank="运动白银Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<25) {
			String rank="运动白银Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<30) {
			String rank="运动白银Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<35) {
			String rank="运动黄金Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<40) {
			String rank="运动黄金Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<45) {
			String rank="运动黄金Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<50) {
			String rank="运动铂金Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<55) {
			String rank="运动铂金Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<60) {
			String rank="运动铂金Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<65) {
			String rank="运动钻石Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<70) {
			String rank="运动钻石Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<75) {
			String rank="运动钻石Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<80) {
			String rank="运动钻石Ⅵ";
			msg.put("rank",rank);
		}else if (signtotal<90) {
			String rank="运动达人Ⅰ";
			msg.put("rank",rank);
		}else if (signtotal<100) {
			String rank="运动达人Ⅱ";
			msg.put("rank",rank);
		}else if (signtotal<110) {
			String rank="运动达人Ⅲ";
			msg.put("rank",rank);
		}else if (signtotal<120) {
			String rank="运动达人Ⅵ";
			msg.put("rank",rank);
		}else
		{
			String rank="运-动-之-王";
			msg.put("rank",rank);
		}

		return msg;


	}
	
	@Autowired
	private wxUserMapper userMapper;
	
	@GetMapping("/insertSign")
	public Map<String,Object> insertSign(@CookieValue("JSEESION") String openid) {
		
		Map<String, Object> msg1=new HashMap<String, Object>();
		
		int userid=userMapper.getUserId(openid);
		double ticket=2.55;//设置赠送用户的礼券数量
		wxSign sign=new wxSign(userid, ticket);
		double balance=userMapper.getbalance(openid);
		balance+=ticket;
		if (!signService.getSigned(openid)) {
			
			wxUser user=new wxUser(openid,balance);
			signService.insertSign(sign);
			userMapper.updatebalance (user);
			int signtotal= signService.countSigned(openid);
			
			msg1.put("status", "1");
			msg1.put("signtotal",signtotal);
			msg1.put("balance", balance);
			
		}else {
			int signtotal= signService.countSigned(openid);
			msg1.put("status", "0");
			msg1.put("signtotal",signtotal);
			msg1.put("msg", "签到失败");
		}
		return msg1;
	}
}
