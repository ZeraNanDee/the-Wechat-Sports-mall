package com.example.demo.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.wxAddressMapper;
import com.example.demo.dao.wxUserMapper;
import com.example.demo.entity.wxAddress;
import com.example.demo.service.wxAddressService;

@RestController
@RequestMapping("/Address")
public class AddressController {
	
	@Autowired
	private wxAddressService addressService;
	@Autowired
	private wxUserMapper userMapper;
	
	@GetMapping("/getAddress")
	@ResponseBody
	public  List<wxAddress> getAddress(@CookieValue("JSEESION") String openid) {
		
	    List<wxAddress> wxAddress=addressService.getAddress(openid);//获取全部地址信息
	    System.out.println(wxAddress);

		return  wxAddress;
	}
	
	@PostMapping("/insertAddress")
	public void insertAddress(@CookieValue("JSEESION") String openid,@RequestBody JSONObject jsonObject) {
      String region= jsonObject.get("region").toString();//添加插入新地址
      String name=jsonObject.get("name").toString();
      String phoneNumber=jsonObject.get("phoneNumber").toString();
      String detail=jsonObject.get("detail").toString();
      wxAddress wxAddress=new wxAddress();
      wxAddress.setDetail(detail);
      wxAddress.setPhoneNumber(phoneNumber);
      wxAddress.setName(name);
      wxAddress.setRegion(region);
		int userid=userMapper.getUserId(openid);
	  wxAddress.setUserid(userid);
	   System.out.println(wxAddress);
	      addressService.insertAddress(wxAddress);
	    System.out.println("新增地址");
	}

	
	@PostMapping("/deleteAddress")
	public Map<String, Object> deleteAddress(@CookieValue("JSEESION") String openid,@RequestBody JSONObject jsonObject) {
		String addressid=jsonObject.get("id").toString();//删除地址信息
		System.out.println("addressid="+addressid);
		int id=Integer.parseInt(addressid);
		Map<String, Object> msg=new HashMap<String,Object>();
		addressService.deleteAddress(id);
		msg.put("status", "1");
		msg.put("msg", "删除成功");
		
		return msg;
	}

	@PostMapping("/updateAddress")
	public Map<String, Object> updateAddress(@CookieValue("JSEESION") String openid,@RequestBody JSONObject jsonObject) {
		String region= jsonObject.get("region").toString();//修改编辑地

		String name=jsonObject.get("name").toString();
		String phoneNumber=jsonObject.get("phoneNumber").toString();
		String detail=jsonObject.get("detail").toString();
		wxAddress wxAddress=new wxAddress();
		wxAddress.setDetail(detail);
		wxAddress.setPhoneNumber(phoneNumber);
		wxAddress.setName(name);
		wxAddress.setRegion(region);
		int userid=userMapper.getUserId(openid);
		wxAddress.setUserid(userid);

		Map<String, Object> msg=new HashMap<String,Object>();
		System.out.println(wxAddress);
		addressService.updateAddress(wxAddress);

		msg.put("status", "1");
		msg.put("msg", "修改成功");

		return msg;
	}

}
