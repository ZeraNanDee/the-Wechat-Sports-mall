package com.example.demo.service;

import com.example.demo.dao.wxOrderMapper;
import com.example.demo.entity.wxOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wxOrderService")
public class wxOrderServiceImpl implements wxOrderService
{
    @Autowired
    private wxOrderMapper orderMapper;

    public void insertOrder(wxOrder order){
        orderMapper.insertOrder(order);
    }

    public List<wxOrder> getOrder(int userid,int statusCode){
        List<wxOrder> list=orderMapper.getOrder(userid,statusCode);
        return list;
    }

    public List<wxOrder> getShopRecord(int userid){
        List<wxOrder> product=orderMapper.getShopRecord(userid);
        return product;
    }

    public int getOrderNumber(int userid,int statusCode){
        int count=orderMapper.getOrderNumber(userid,statusCode);
        return count;
    }

    public void stuatsCodeChange(int orderid,int statusCode){
        orderMapper.stuatsCodeChange(orderid,statusCode);
    }

    public int getOrderCount(int stauts){
        int count=orderMapper.getOrderCount(stauts);
        return count;
    }

    public List<wxOrder> getOrderAll(int statusCode){
        List<wxOrder> list=orderMapper.getOrderAll(statusCode);
        return list;
    }
}
