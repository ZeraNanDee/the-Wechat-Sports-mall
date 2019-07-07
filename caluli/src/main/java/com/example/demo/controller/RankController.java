package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.wxRank;
import com.example.demo.service.wxRankService;

@RestController
@RequestMapping("/Rank")
public class RankController {
	    
	@Autowired
	private wxRankService rankService;
	    
	@GetMapping("/getRank")
	public Map<String,Object> Sign() {
		
		Map<String, Object> msg=new HashMap<String,Object>();
		List<wxRank> firstlist=new ArrayList<wxRank>();
		List<wxRank> secondlist=new ArrayList<wxRank>();
		List<wxRank> thirdlist=new ArrayList<wxRank>();	
		List<wxRank> otherlist=new ArrayList<wxRank>();	
		
		firstlist.addAll(rankService.getFirst());
		secondlist.addAll(rankService.getSecond());
		thirdlist.addAll(rankService.getThird());
		otherlist.addAll(rankService.getOtherRank());
		
		System.out.println(firstlist);
		System.out.println(secondlist);
		System.out.println(thirdlist);
		System.out.println(otherlist);
		
		msg.put("status", "1");
		msg.put("msg", "获取到排名");
		msg.put("First", firstlist);
		msg.put("Second",secondlist);
		msg.put("Third",thirdlist);
		msg.put("Otherlist",otherlist);
		
		return msg;
	}

}
