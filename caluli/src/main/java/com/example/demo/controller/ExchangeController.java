package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.wxStepMapper;
import com.example.demo.dao.wxUserMapper;
import com.example.demo.entity.wxStep;
import com.example.demo.entity.wxUser;
import com.example.demo.service.wxUserService;

@RestController
@RequestMapping("/Exchange")
public class ExchangeController {

	@Autowired
	private wxStepMapper stepmapper;
	
	@Autowired
	private wxUserService userService;
	
	@Autowired
	private wxUserMapper userMapper;
	
	@PostMapping("/steps")
	public Map<String, Object> Exchange(@CookieValue("JSEESION") String openid) throws Exception {

		 Map<String, Object> msg1=new HashMap<String, Object>();
		
		System.out.println("进入Exchange方法");
		int step = stepmapper.exchange(openid);
		System.out.println("获取到用户当前步数：" + step);
	   
			
			double balance=userService.getbalance(openid);
			System.out.println("当前用户的余额为:"+balance);
			
			double ticket=stepmapper.getticket(openid);
	 	    BigDecimal a = new BigDecimal(ticket);
	 	    ticket= a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	 	    
	 	    balance=balance+ticket;//兑换燃烧券加上原来用户的燃烧券，当前拥有的燃烧券
	 	    BigDecimal b = new BigDecimal(balance);
	 	    balance= b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	 	   
	 	   
	 	    int userid=userMapper.getUserId(openid);
	 	    wxStep record=new wxStep(userid, step, ticket);//同时保存一条最近兑换的记录，表示目前兑换到哪里。
	 	    stepmapper.insertStep(record);
	 	  
	 	   System.out.println("兑换后的总余额为："+balance);
		   wxUser update=new wxUser(openid, balance);
		   userService.updatebalance(update);
	 	   System.out.println("balance数据更新成功");
	 	   
	 	  msg1.put("status", "1");
	 	  msg1.put("cal", 0);
	 	  msg1.put("balance",balance);  
		
			

		return msg1;
	    

	}
	
	
	@GetMapping("/refresh")
	public Map<String, Object> Refresh(@CookieValue("JSEESION") String openid) {
		
		System.out.println("进入Refresh方法");
		
		double balance=userService.getbalance(openid);
		System.out.println("当前用户的余额为:"+balance);
		 Map<String, Object> msg=new HashMap<String, Object>();
	
			msg.put("status", "1");
		    msg.put("balance",balance);
	
		return msg;
		
	}
	
	
}
