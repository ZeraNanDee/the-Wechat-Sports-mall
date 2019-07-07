package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.admin;
import com.example.demo.entity.wxOrder;
import com.example.demo.entity.wxProduct;
import com.example.demo.service.adminService;
import com.example.demo.service.wxOrderService;
import com.example.demo.service.wxProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private adminService service;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody admin ad){
        Map<String, Object> msg=new HashMap<String,Object>();

        boolean user=service.checkUser(ad);
        if (user==true){
            msg.put("status",true);
            msg.put("msg","登录成功");
        }
        else {
            msg.put("status",false);
            msg.put("msg","登录失败");
        }

        return msg;
    }

    @Autowired
    private wxOrderService orderService;

    @GetMapping("/getOrderCount")
    public Map<String, Object> getOrderCount(){//管理员查看订单数量
        Map<String, Object> msg=new HashMap<String,Object>();
        int status0=0;
        int status1=1;
        int status2=2;
        int status3=3;
        int count0=orderService.getOrderCount(status0);
        int count1=orderService.getOrderCount(status1);
        int count2=orderService.getOrderCount(status2);
        int count3=orderService.getOrderCount(status3);
        System.out.println("count0="+count0+",count1="+count1+",count2="+count2+",coun3="+count3);
        msg.put("status","1");
        msg.put("count0",count0);
        msg.put("count1",count1);
        msg.put("count2",count2);
        msg.put("count3",count3);
        return msg;
    }

    @PostMapping("/getOrderAll")//管理员查看各状态的订单
    public Map<String, Object> getOrderAll(@RequestBody JSONObject jsonObject) {
        Map<String, Object> msg=new HashMap<String, Object>();
        int statusCode=jsonObject.getIntValue("statusCode");
        List<wxOrder> list=orderService.getOrderAll(statusCode);
        System.out.println(list);
        msg.put("status","1");
        msg.put("list",list);
        return msg;
    }

    @PostMapping("/delivery")//管理员确认商品已发货
    public Map<String, Object> delivery(@RequestBody JSONObject jsonObject) {
        int orderid=jsonObject.getIntValue("id");
        Map<String, Object> msg=new HashMap<String, Object>();
        int statusCode=1;
        orderService.stuatsCodeChange(orderid,statusCode);
        msg.put("status","1");
        msg.put("msg","商品已发货，请到待收货栏查看");
        return  msg;
    }

    @PostMapping("/revokeCheck")//管理员确认商品已发货
    public Map<String, Object> revokeCheck(@RequestBody JSONObject jsonObject) {
        int orderid=jsonObject.getIntValue("id");
        Map<String, Object> msg=new HashMap<String, Object>();
        int statusCode=444;
        orderService.stuatsCodeChange(orderid,statusCode);
        msg.put("status","1");
        msg.put("msg","商品已退货");
        return  msg;
    }

    @Autowired
    private wxProductService productService;

    @PostMapping("/getProduct")
    public Map<String, Object> getProduct(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject);
        Map<String, Object> msg=new HashMap<String, Object>();
        int productId=jsonObject.getIntValue("productId");
        List<wxProduct> list=productService.getProduct(productId);
        msg.put("status","1");
        msg.put("product",list);
        return msg;
    }

    public String dates() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response, PrintWriter writer) throws Exception {

        String productName= (String) request.getParameter("productName");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer pCount=Integer.parseInt(request.getParameter("pCount"));
        Integer productId=Integer.parseInt(request.getParameter("productId"));

        System.out.println("商品名："+productName+"价格："+price+"库存："+pCount);

        File paths=new File(ResourceUtils.getURL("classpath:").getPath());
        MultipartHttpServletRequest req=(MultipartHttpServletRequest)request;
        MultipartFile multipartFile=req.getFile("file");

        String houzhu=multipartFile.getContentType();
        int one=houzhu.lastIndexOf("/");
        String type=houzhu.substring((one)+1,houzhu.length());
        String filename;
        String realPath="E:/STSworkspace/caluli/src/main/resources/static/image/product";
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            List<wxProduct> list=productService.getProduct(productId);
            String picturePath=list.get(0).getPicture();
            String str = picturePath.substring(36, picturePath.indexOf("."));//取图片名字
            System.out.println(str);
            filename =str;
            //将文件的地址和生成的文件名拼在一起
            File file = new File(realPath, filename + "." + type);
            multipartFile.transferTo(file);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isok", 1);
            jsonObject.put("dizhi", "/uploadimg/" + filename + "." + type);

            writer.write(jsonObject.toString());

            wxProduct product=new wxProduct();
            product.setProductId(productId);
            product.setProductName(productName);
            product.setpCount(pCount);
            product.setPrice(price);

            productService.updateProduct(product);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }



    }

}
