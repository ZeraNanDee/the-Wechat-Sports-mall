package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.wxSign;

@Repository
@Mapper
public interface wxSignMapper {

	public Integer getSigned(String openid);//查询今日是否签到
	
	public Integer countSigned(String openid);//统计此用户签到总天数
	
	public void insertSign(wxSign sign);//插入此用户的今日签到
}
