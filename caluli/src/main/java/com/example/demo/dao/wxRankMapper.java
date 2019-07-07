package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.wxRank;

@Repository
@Mapper
public interface wxRankMapper {
	
	public List<wxRank> getAllRecord();//全国今日运动步数排名

}
