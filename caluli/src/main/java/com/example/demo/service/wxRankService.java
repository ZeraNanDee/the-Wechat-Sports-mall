package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.wxRank;

public interface wxRankService {
	
	public List<wxRank> getFirst();
	
	public  List<wxRank> getSecond();
	
	public  List<wxRank> getThird();
	
	public List<wxRank> getOtherRank(); 
}
