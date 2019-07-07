package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.wxUserMapper;
import com.example.demo.entity.wxOrder;
import com.example.demo.entity.wxProduct;
import com.example.demo.entity.wxUser;
import com.example.demo.service.wxOrderService;
import com.example.demo.service.wxProductService;
import com.example.demo.service.wxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private wxUserMapper usermapper;

    @Autowired
    private wxUserService userService;

    @Autowired
    private wxOrderService orderService;

    @Autowired
    private wxProductService productService;

    @PostMapping("/postOrder")//兑换商品后提交订单
    public Map<String, Object> postOrder(@CookieValue(value = "JSEESION" ,required = false)String openid,@RequestHeader(value="defaultAddressId",required=false) String[] header,@RequestBody JSONObject jsonObject)  {
        Map<String, Object> msg=new HashMap<String, Object>();

        String addressCookie=header[0];
        int addressid=Integer.parseInt(addressCookie);
        jsonObject=jsonObject.getJSONObject("products");
        int productid=jsonObject.getIntValue("productId");
        int pCount=jsonObject.getIntValue("pCount");
        int userid=usermapper.getUserId(openid);
        System.out.println("addressId:"+addressid+",productid："+productid+",userid:"+userid);
        int statusCode=0;//0代表待发货
        wxOrder order=new wxOrder(userid, productid, addressid, statusCode);
        orderService.insertOrder(order);//提交订单信息到数据库

        double price=jsonObject.getDoubleValue("price");//购买后余额的更新
        System.out.println("商品价格:"+price);
        double balance=userService.getbalance(openid);
        if (balance>0) {

            balance = balance - price;
            BigDecimal b = new BigDecimal(balance);
            balance = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("购买后余额:" + balance);
            wxUser update = new wxUser(openid, balance);
            userService.updatebalance(update);
            System.out.println("balance数据更新成功");
            if (pCount>0)
            pCount=pCount-1;
            wxProduct product=new wxProduct(productid,pCount);
            productService.updatePcount(product);
            System.out.println("库存更新成功");

            msg.put("status","1");
            msg.put("balance",balance);
            msg.put("pCount",pCount);
            msg.put("msg","商品兑换成功");
        }

        return  msg;
    }

    @PostMapping("/getOrder")//根据订单状态码，展示此用户的订单信息
    public Map<String, Object> getOrder(@CookieValue(value = "JSEESION" ,required = false)String openid,@RequestBody JSONObject jsonObject)  {
        int userid=usermapper.getUserId(openid);
        int statusCode=jsonObject.getIntValue("statusCode");
        List<wxOrder> list=orderService.getOrder(userid,statusCode);
        System.out.println(list);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("list",list);
        return msg;
    }

    @GetMapping("/getShopRecord")//兑换记录
    public Map<String, Object> getOrder(@CookieValue(value = "JSEESION" ,required = false)String openid)  {
        int userid=usermapper.getUserId(openid);
        List<wxOrder> product=orderService.getShopRecord(userid);
        System.out.println(product);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("product",product);
        return msg;
    }

    @GetMapping("/getOrderSum")//统计订单状态的数量
    public Map<String, Object> getOrderSum(@CookieValue(value = "JSEESION" ,required = false)String openid)  {
        int userid=usermapper.getUserId(openid);
        int status0=0;
        int status1=1;
        int status2=2;
        int status3=3;

        int count0=orderService.getOrderNumber(userid,status0);
        int count1=orderService.getOrderNumber(userid,status1);
        int count2=orderService.getOrderNumber(userid,status2);
        int count3=orderService.getOrderNumber(userid,status3);
        System.out.println("count0="+count0+",count1="+count1+",count2="+count2+",coun3="+count3);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("count0",count0);
        msg.put("count1",count1);
        msg.put("count2",count2);
        msg.put("count3",count3);
        return msg;

    }

    @PostMapping("/revoke")//撤销订单
    public Map<String, Object> revoke(@RequestBody JSONObject jsonObject)  {
        System.out.println("撤销的商品ID："+jsonObject);
        int orderid=jsonObject.getIntValue("id");
        int statusCode=3;
        orderService.stuatsCodeChange(orderid,statusCode);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("msg","已经撤销订单，请到退货栏查看");
        return msg;
    }

    @PostMapping("/confirm")//确认收货订单
    public Map<String, Object> confirm(@RequestBody JSONObject jsonObject) {
        System.out.println("确认收货的商品ID："+jsonObject);
        int orderid=jsonObject.getIntValue("id");
        int statusCode=2;
        orderService.stuatsCodeChange(orderid,statusCode);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("msg","确认收货成功");
        return msg;
    }

    @PostMapping("/finish")//确认收货订单
    public Map<String, Object> finish(@RequestBody JSONObject jsonObject) {
        System.out.println("已完成订单的商品ID："+jsonObject);
        int orderid=jsonObject.getIntValue("id");
        int statusCode=666;
        orderService.stuatsCodeChange(orderid,statusCode);
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("status","1");
        msg.put("msg","商品交易完成");
        return msg;
    }


}
