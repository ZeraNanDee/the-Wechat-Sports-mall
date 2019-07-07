
package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.entity.*;
import com.example.demo.service.wxInviteService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.wxStepService;
import com.example.demo.service.wxUserService;
import com.example.demo.controller.decode.AES;
import com.example.demo.controller.sendcode.HttpClientUtil;
import com.example.demo.controller.sendcode.JsonUtils;
import com.example.demo.dao.wxStepMapper;
import com.example.demo.dao.wxUserMapper;

@RequestMapping("/Cal")
@RestController // 证明是controller层并且返回json
public class LoginController {

	@Autowired
	private wxUserService userService;

	@Autowired
	private wxInviteService inviteService;

	@PostMapping("/login") // 用户初次登录
	public Map<String, Object> login(@RequestBody pass pass) throws Exception {

		Map<String, String> param = new HashMap<String, String>();
		Map<String, Object> msg = new HashMap<String, Object>();

//获取用户的code，用过URL和APPID 和 SECREET还有code向微信服务器获得OPENID和SEESION_KEY
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		param.put("appid", "wxb65a65e06d15723b");
		param.put("secret", "84564a8bb7ec1d343d0fa194e572be40");
		param.put("js_code", pass.getCode());
		param.put("grant_type", "authorization_code");

		String wxResult = HttpClientUtil.doGet(url, param);

		WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
		String session_key = wxSessionModel.getSession_key();
		String openid = wxSessionModel.getOpenid();
		System.out.println("用户session_key:" + session_key);
		System.out.println(openid);

		try {
			// 利用AES类Base64解密算法解密用户敏感信息
			byte[] bytes = AES.decrypt(Base64.decodeBase64(session_key), Base64.decodeBase64(pass.getIv()),
					Base64.decodeBase64(pass.getEncryptedData()));
			String theUser = new String(bytes, "UTF8");
			double balance=userService.getbalance(openid);
			if (theUser != null || theUser.length() > 0) {
				msg.put("status", "1");
				msg.put("msg", "登录成功");
				msg.put("id", openid);
				msg.put("balance",balance);
				System.out.println("The User====" + theUser);
			}
			wxUser user = JSON.parseObject(theUser, wxUser.class);
			int inviteid=0;
			inviteid=pass.getInvite_id();
			user.setInviteId(inviteid);
			String thisopenid = user.getOpenid();

			if (userService.isexistUser(thisopenid)) {
				int userid=usermapper.getUserId(thisopenid);
				msg.put("user_id", userid);
				System.out.println("该用户已存在");
			} else {
				if (inviteid!=0) {
					int thatuserid=inviteid;//邀请人的id即是此人的inviteid
					double thatbalance=userService.thatbalance(thatuserid);
					thatbalance+=10.00;//邀请人balance增加10；
					BigDecimal b = new BigDecimal(thatbalance);
					thatbalance= b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					wxUser addbalance=new wxUser(thatuserid, thatbalance);
					userService.addbalance(addbalance);
					System.out.println("邀请人获得了10个燃烧券");

					double thisbalance=10.00;
					user.setBalance(thisbalance);//被邀请人首次登陆balance增加10个;
					System.out.println("被邀请人获得了10个燃烧券");

					wxInvite invite=new wxInvite(openid,thatuserid);
					inviteService.insertInviteRecord(invite);//插入一条邀请记录

				}
				userService.insertUser(user);// 数据库没有用户数据就注册。
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;

	}

	@Autowired
	private wxUserMapper usermapper;

	@Autowired
	private wxStepMapper stepmapper;

	@Autowired
	private wxStepService stepService;
	

	@SuppressWarnings("null")
	@PostMapping("/step") // 用户授权获取微信步数
	public Map<String, Object> step(@RequestBody pass pass1) throws Exception {

		Map<String, String> param = new HashMap<String, String>();
		Map<String, Object> msg1 = new HashMap<String, Object>();
		// 登录凭证不能为空
		if (pass1.getCode() == null || pass1.getCode().length() == 0) {
			msg1.put("status", "0");
			msg1.put("msg", "code 不能为空");
			return msg1;
		}
//获取用户的code，用过URL和APPID 和 SECREET还有code向微信服务器获得OPENID和SEESION_KEY
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		param.put("appid", "wxb65a65e06d15723b");
		param.put("secret", "84564a8bb7ec1d343d0fa194e572be40");
		param.put("js_code", pass1.getCode());
		param.put("grant_type", "authorization_code");

		String wxResult = HttpClientUtil.doGet(url, param);

		WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
		String sessionkey = wxSessionModel.getSession_key();
		String openid = wxSessionModel.getOpenid();
		System.out.println("用户步数session_key：" + sessionkey);

		try {
			// 利用AES类Base64解密算法解密用户敏感信息
			byte[] bytes = AES.decrypt(Base64.decodeBase64(sessionkey), Base64.decodeBase64(pass1.getIv()),
					Base64.decodeBase64(pass1.getEncryptedData()));
			String theStep = new String(bytes, "UTF8");
			System.out.println(theStep);// 根据官方文档，解密后获取到用户过去30天的JSON字符串组成的步数
			JSONObject jsonObject = JSON.parseObject(theStep);// 将字符串转化为json形式取出今日步数
			JSONObject jsstep = (JSONObject) jsonObject.getJSONArray("stepInfoList").get(30);// 获取微信用户当前今天的步数
			String getstep = jsstep.getString("step");
			int steptotal = Integer.parseInt(getstep);

			if(steptotal>0) {
			
				if (userService.getUserId(openid)) {
					int userid = usermapper.getUserId(openid);
					System.out.println("用户ID是" + userid);
					if (stepmapper.isexistUserid(userid)>1) {//用户已经注册过步数表，同时已经兑换过燃烧券
						System.out.println("该用户已经兑换过燃烧券");
						 int step=0;
					     step= stepmapper.selectstep(userid);
						steptotal = steptotal - step;// 今日的当前步数
								
						double restticket = (double) (steptotal / 2000.0);
						BigDecimal a1 = new BigDecimal(restticket);
						restticket = a1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 设置步数可以兑换多少燃烧券，四舍五入保留两位
						System.out.println("今日步数：" + steptotal + "  可兑换的燃烧券：" + restticket);

						double restcal = (double) (steptotal) / 40;
						BigDecimal b1 = new BigDecimal(restcal);
						restcal = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 卡路里保留两位小数，四舍五入
						
						System.out.println("今日卡路里：" + restcal);
						msg1.put("status", "1");
						msg1.put("msg", "得到今日卡路里");
						msg1.put("cal", restcal);
						msg1.put("step", steptotal);
						msg1.put("ticket", restticket);
						
						step=steptotal+step;
						wxStep upstep = new wxStep(userid, step, restticket);
						stepmapper.updatestep(upstep);
	
					}
					else if (stepmapper.isexistUserid(userid)==1) {//判断是否已经将步数写入过数据库
						System.out.println("该用户已经注册过步数表");
						double ticket = (double) (steptotal / 2000.0);
						BigDecimal a = new BigDecimal(ticket);
						ticket = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 设置步数可以兑换多少燃烧券，四舍五入保留两位
						System.out.println("今日步数：" + steptotal + "  可兑换的燃烧券：" + ticket);

						double cal = Double.parseDouble(getstep) / 40;
						BigDecimal b = new BigDecimal(cal);
						cal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 卡路里保留两位小数，四舍五入
						System.out.println("今日卡路里：" + cal);

						msg1.put("status", "1");
						msg1.put("msg", "得到今日卡路里");
						msg1.put("cal", cal);
						msg1.put("step",steptotal);
						msg1.put("ticket", ticket);

					}
          			else //用户今日还没有获取步数，还没有注册今日的步数表
					{
					double ticket = (double) (steptotal / 2000.0);
					BigDecimal a = new BigDecimal(ticket);
					ticket = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 设置步数可以兑换多少燃烧券，四舍五入保留两位
					System.out.println("今日步数：" + steptotal + "  可兑换的燃烧券：" + ticket);

					double cal = Double.parseDouble(getstep) / 40;
					BigDecimal b = new BigDecimal(cal);
					cal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 卡路里保留两位小数，四舍五入
					System.out.println("今日卡路里：" + cal);

					wxStep instep = new wxStep(userid, steptotal, ticket);// 注册用户对应的步数表
					stepService.insertStep(instep);

					msg1.put("status", "1");
					msg1.put("msg", "得到今日卡路里");
					msg1.put("cal", cal);
					msg1.put("step",steptotal);
					msg1.put("ticket", ticket);

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg1;
	}

}
