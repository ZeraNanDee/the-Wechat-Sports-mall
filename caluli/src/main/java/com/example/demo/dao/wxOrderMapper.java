package com.example.demo.dao;

import com.example.demo.entity.wxOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface wxOrderMapper {

    public void insertOrder(wxOrder order);//提交订单

    public List<wxOrder> getOrder(int userid,int statusCode);//用户订单状态

    public List<wxOrder> getShopRecord(int userid);//查看用户的兑换记录

    public Integer getOrderNumber(int userid,int statusCode);//查询各订单状态的数量

    public void stuatsCodeChange(int orderid,int statusCode);//更改订单状态码

    public Integer getOrderCount(int status);//管理员查看订单数量

    public List<wxOrder> getOrderAll(int statusCode);//管理员查看各个状态的订单信息
}
