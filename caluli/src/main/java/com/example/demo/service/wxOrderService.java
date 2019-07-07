package com.example.demo.service;

import com.example.demo.entity.wxOrder;

import java.util.List;

public interface wxOrderService
{
    public void insertOrder(wxOrder order);

    public List<wxOrder> getOrder(int userid,int statusCode);

    public List<wxOrder> getShopRecord(int userid);

    public int getOrderNumber(int userid,int statusCode);

    public void stuatsCodeChange(int orderid,int statusCode);

    public int getOrderCount(int stauts);

    public List<wxOrder> getOrderAll(int statusCode);
}
