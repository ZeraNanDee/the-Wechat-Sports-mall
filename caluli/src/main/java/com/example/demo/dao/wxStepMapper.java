package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.wxStep;

@Repository
@Mapper
public interface wxStepMapper {

	public void insertStep(wxStep step);//注册今日的微信步数表
	
	public Integer isexistUserid(Integer userid);//判断今日数据库是否已经兑换了该用户的步数
	
	public Integer exchange(String openid);//步数兑换今日礼券，存入用户balance/查询当天此用户的步数
	
	public Integer selectstep(Integer userid);//获取今日的当前步数
	
	public void updatestep(wxStep step);//更新今日的当前步数
	
	public Double getticket(String openid);//更新今日的当前步数
	
	public List<Integer> selectStepRecord();//查询所有人的今日运动步数

	
}
