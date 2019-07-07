package com.example.demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.wxUser;


@Repository
@Mapper
public interface wxUserMapper  {

	public Integer isexistUser(String thisopenid);//用于判断用户是否存在
 
	public void insertUser(wxUser user);//注册微信用户
	
	public wxUser selectUser(String openid);//查询用户头像和名字
	
	public Integer getUserId(String openid);//获取微信用户ID，与步数表级联
	
	public Double getbalance(String openid);//获取当前用户的balance

	public void addbalance(wxUser addbalance);//增加邀请人和被邀请的balance+10

	public Double thatbalance(Integer thatuserid);//查询邀请人用户的balance

	public void updatebalance(wxUser update);//用户每次更新的balance

	public void updateuser(wxUser user);//更新用户信息
	
}
