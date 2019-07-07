package com.example.demo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.decode.AES;
import com.example.demo.controller.sendcode.HttpClientUtil;
import com.example.demo.controller.sendcode.JsonUtils;
import com.example.demo.dao.wxStepMapper;
import com.example.demo.entity.WXSessionModel;
import com.example.demo.entity.pass;


@RequestMapping("/record")
@RestController 
public class GetRecord {
	
    @Autowired
	private wxStepMapper stepmapper;
	
	 public static void sort(Integer[] a, int low, int high) {//快速排序算法
	        if(low>=high)
	            return;
	        int i = low;
	        int j = high;
	        int key = a[i];
	        while(i<j){
	        	while(i<j&&a[j]>=key){
	        	j--;
	        	}
	        	if(i<j){
	        	a[i++]=a[j];
	        	}
	        	while(i<j&&a[i]<=key){
	        	i++;
	        	}
	        	if(i<j){
	        	a[j--]=a[i];
	        	}
	        	}
	        a[i] = key;
	        sort(a,low,i-1);
	        sort(a,i+1,high);
	    }

	    public static void quickSort(Integer[] a) {
	        sort(a, 0, a.length-1);
	    }

	@RequestMapping("/weekstep")
	public Map<String, Object> theWeekStep(@RequestBody pass pass1)  throws Exception {
		
		
		
		System.out.println("进入record方法");
		Map<String, String> param = new HashMap<String, String>();
		Map<String, Object> msg1 = new HashMap<String, Object>();
		// 登录凭证不能为空
		if (pass1.getCode() == null || pass1.getCode().length() == 0) {
			msg1.put("status", "0");
			msg1.put("msg", "获取失败");
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
		String session_key = wxSessionModel.getSession_key();
		String openid = wxSessionModel.getOpenid();
		System.out.println("用户步数session_key：" + session_key);

		try {
			// 利用AES类Base64解密算法解密用户敏感信息
			byte[] bytes = AES.decrypt(Base64.decodeBase64(session_key), Base64.decodeBase64(pass1.getIv()),
					Base64.decodeBase64(pass1.getEncryptedData()));
			String theStep = new String(bytes, "UTF8");
			System.out.println(theStep);// 根据官方文档，解密后获取到用户过去30天的JSON字符串组成的步数
			JSONObject jsonObject = JSON.parseObject(theStep);// 将字符串转化为json形式取出今日步数
			int [] step=new int[7];
			double cal[]= new  double[7];
			String[] date=new String[7];
			 int j=0;
			 String getstep = null;
			 String getdate=null;
			 long timestamp=0;
			 SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");//这个是要转成后的时间的格式

			 
			 for (int i = 24; i < 31; i++) {
				  JSONObject jsstep = (JSONObject) jsonObject.getJSONArray("stepInfoList").get(i);// 获取微信用户当前今天的步数
				  getdate=jsstep.getString("timestamp");
				  timestamp=Long.parseLong(getdate)*1000L;//时间戳需要x1000L，否则时间都为1.19号。
		     	  getstep = jsstep.getString("step");
		     	  j=i-24;
			      step[j]= Integer.parseInt(getstep);
			      cal[j]= (double)( step[j]) / 40;
				  date[j]= sdf.format(new Date(timestamp));   // 时间戳转换成时间	    
				  BigDecimal b = new BigDecimal(cal[j]);
				  cal[j] = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 卡路里保留两位小数，四舍五入
				  
			 }
			 
			 Map<String, Object> data = new HashMap<String, Object>();
			 data.put("date", date[0]);
			 data.put("cal", cal[0]);
			 Map<String, Object> data1 = new HashMap<String, Object>();
			 data1.put("date", date[1]);
			 data1.put("cal", cal[1]);
			 Map<String, Object> data2 = new HashMap<String, Object>();
			 data2.put("date", date[2]);
			 data2.put("cal", cal[2]);
			 Map<String, Object> data3 = new HashMap<String, Object>();
			 data3.put("date", date[3]);
			 data3.put("cal", cal[3]);
			 Map<String, Object> data4 = new HashMap<String, Object>();
			 data4.put("date", date[4]);
			 data4.put("cal", cal[4]);
			 Map<String, Object> data5 = new HashMap<String, Object>();
			 data5.put("date", date[5]);
			 data5.put("cal", cal[5]);
			 Map<String, Object> data6 = new HashMap<String, Object>();
			 data6.put("date", date[6]);
			 data6.put("cal", cal[6]);
	
			 Map<Object, Object> datatotal = new HashMap<Object, Object>();
			 datatotal.put(0, data);
			 datatotal.put(1, data1);
			 datatotal.put(2, data2);
			 datatotal.put(3, data3);
			 datatotal.put(4, data4);
			 datatotal.put(5, data5);
			 datatotal.put(6, data6);
			   
		      System.out.println("近一周时间"+java.util.Arrays.toString(date));  
			  System.out.println("近一周卡路里：" + java.util.Arrays.toString(cal));
			  System.out.println("近一周步数：" + java.util.Arrays.toString(step));
			 
			  
			  //每日判断消耗量对应的文本
			  JSONObject jsstep = (JSONObject) jsonObject.getJSONArray("stepInfoList").get(30);// 微信用户今天的步数
				String todaystep = jsstep.getString("step");
				int steptext = Integer.parseInt(todaystep);
				StringBuffer text=new StringBuffer();
				StringBuffer longText=new StringBuffer();
				if (steptext>=0&&steptext<2000) {
					text.append("一 根 帮 帮 糖");
					longText.append("今日的运动量稍微有点少，可以增加慢跑的距离3000m，坚持3-4天。");
				}
				else if (steptext>=2000&&steptext<4000) {
					text.append("一 根 冰 淇 淋");
					longText.append("今日的运动量相对较少，可以适当快走距离2000m，坚持5天。");
				}
				else if (steptext>=4000&&steptext<6000) {
					text.append("一 块 巧 克 力 奶 酪");
					longText.append("今日的运动量是正常上班族步数，晚上慢跑1000米，增加运动量。");
				}
				else if (steptext>=6000&&steptext<8000) {
					text.append("一 块 冰 淇 淋 蛋 糕");
					longText.append("今日的运动量是正常上班族步数，晚上慢跑1000米，增加运动量。");
				}
				else if (steptext>=8000&&steptext<10000) {
					text.append("一  份 炸 鸡 汉 堡");
					longText.append("今日的运动量刚好，晚上适当散散步即可，每天坚持下去。");
				}
				else if (steptext>=10000&&steptext<14000) {
					text.append("一 份 饱 满 的 菲 力 牛 排");
					longText.append("今日的运动量刚好，晚上适当散散步即可，每天坚持下去。");
				}
				else if (steptext>=14000&&steptext<18000) {
					text.append("一 份 全 家 桶 快 餐");
					longText.append("今日的运动量非常高，慢走散步可缓解疲倦，2-3天坚持一次");
				}
				else if (steptext>=18000&&steptext<25000) {
					text.append("一 顿 海 鲜 自 助 餐");
					longText.append("今日的运动量太多了，已经需要休息了，超过了一般人运动量");
				}
				else {
					text.append("一 份 汉 堡 加 一 顿 海 鲜 自 助 餐");
					longText.append("今日的运动量已经超出常人的身体适应度，请安排好运动作息,避免损害健康。");
				}
		
				
			  //用快速排序计算出此用户今日的运动量超过全国多少的人
				List<Integer> list=new ArrayList<Integer>();
	           list.addAll(stepmapper.selectStepRecord());//不能确定数组长度，所有先用list接收，然后转为数组		
               Integer[] a = list.toArray(new Integer[list.size()]);

				        quickSort(a);
				        int temp = 0;
				        for (int min = 0,max = a.length - 1; min < max; min ++,max --) {
				            temp = a[min];
				            a[min] = a[max];
				            a[max] = temp;
				        }
				        int sum=0;
				        double percent=0;
				        // 逆序后输出
                        
                        
				        int score=stepmapper.exchange(openid);//查询此用户今日步数。
				        for (int i = 0; i < a.length; i ++) {
				            if (a[i]<score) {//今日数据库的步数值
							       sum++;
							}
				        }
				  	   percent=(double)sum/(double)a.length;
				       BigDecimal bigDecimal = new BigDecimal(percent);
				       percent = bigDecimal.setScale(2,   RoundingMode.HALF_UP).doubleValue();
				       int getPerson=(int)(percent*100);
			    	   System.out.println(getPerson+"%");//计算出此用户超过今日所有人比例
		
			    	   
			        msg1.put("status", "1");
			        msg1.put("msg", "获取成功");
			        msg1.put("data", datatotal);
			        msg1.put("recordtext", text);
			        msg1.put("longText", longText);
			        msg1.put("percent",getPerson+"%");
			 System.out.println(msg1);
         
        }catch (Exception e) {
			e.getStackTrace();
		}
	
		
		return msg1;
		
	}


}


