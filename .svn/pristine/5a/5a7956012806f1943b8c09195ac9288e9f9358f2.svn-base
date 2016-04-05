package com.desksoft.wechat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.common.model.otherbean.template.DataItem;
import com.desksoft.wechat.common.model.otherbean.template.TempItem;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;

// 推送服务  
public class LargeMsgService {
	
	
	/**
	 * 发送每月的用水情况
	 */
	public void sendMonthInfo(User user){
		
		// 调用接口 
		DataItem dataItem=new DataItem();
		dataItem.setKeyword1(new TempItem("欠费停水","#FF0000"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		String time=sdf.format(new Date());
		dataItem.setKeyword2(new TempItem(time, "#0000FF"));
		dataItem.setKeyword3(new TempItem("电风扇发顺","#FF0000"));
		dataItem.setRemark(new TempItem("您的订单已提交，我们将尽快发货，祝生活愉快", "#008000"));
		
		String json=TempToJson.getTempJson("oa5uZuEm53BTuVGJhdYWlwFITMqg", "BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY",
				"#FF0000", "", dataItem);
		System.out.println(json);

		ApiResult apiResult = TemplateMsgApi.send(json);
		System.out.println(apiResult.getJson());
		
	}
	
	
}
