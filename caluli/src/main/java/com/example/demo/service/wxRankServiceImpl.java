package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.wxRankMapper;
import com.example.demo.entity.wxRank;

@Service("wxRankService")
public class wxRankServiceImpl implements wxRankService {

	@Autowired
	private wxRankMapper rankMapper;
	@Override
	public List<wxRank> getFirst() {
		List<wxRank> list=new ArrayList<wxRank>();
	
		list.add(rankMapper.getAllRecord().get(0));//取出第一名
		return list;
	}

	@Override
	public List<wxRank> getSecond() {
		List<wxRank> list=new ArrayList<wxRank>();
		list.add(rankMapper.getAllRecord().get(1));//取出第二名
		return list;
	}

	@Override
	public List<wxRank> getThird() {
		List<wxRank> list=new ArrayList<wxRank>();
		list.add(rankMapper.getAllRecord().get(2));//取出第三名
		return list;
	}


	@Override
	public List<wxRank> getOtherRank() {
		List<wxRank> list=new ArrayList<wxRank>();
		
		for(int i=0;i<rankMapper.getAllRecord().size()-3;i++)
		{
			list.add(rankMapper.getAllRecord().get(3+i));
		}
		return list;
		
	}

	
}
