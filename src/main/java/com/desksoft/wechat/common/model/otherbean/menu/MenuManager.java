package com.desksoft.wechat.common.model.otherbean.menu;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.desksoft.wechat.common.model.otherbean.template.DataItem;
import com.desksoft.wechat.common.model.otherbean.template.TempItem;
import com.desksoft.wechat.service.TempToJson;
import com.jfinal.kit.JsonKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MediaApi;
import com.jfinal.weixin.sdk.api.MediaApi.MediaType;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;

/**
 * @author Javen
 * @Email javenlife@126.com
 * 菜单管理器类 
 */
public class MenuManager  {
	 public static void main(String[] args) { 
		  
		   ApiConfig ac = new ApiConfig();
			
			// 配置微信 API 相关常量
			ac.setAppId("wx154f74654ae04891");
			ac.setAppSecret("58e78802d9554dbe29608551c2a62d86");
		    ApiConfigKit.setThreadLocalApiConfig(ac);
		   
		   // sendMsg();
		    // 将菜单对象转换成json字符串
			   //有问题：主菜单项多了一个type
		/*	  String jsonMenu = JsonKit.toJson(getTestMenu()).toString();
			  System.out.println(jsonMenu);
		   //创建菜单
*/	     //  ApiResult apiResult=MenuApi.createMenu(jsonMenu);
	       
	       /**
		     * 获得所有永久素材的media_ID，分别付给第二列和第三列(除最后一个)的media_ID按钮，生成菜单
		     */
	       ApiResult apiResult=MediaApi.batchGetMaterial(MediaType.NEWS,0,20);
	       
	       System.out.println(apiResult.getJson());
	 }  
	 
	 
	 
	 
	 
		/**
		 * 发送模板消息
		 */
		public static void sendMsg()
		{
			
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
			//return apiResult.getJson();
		}
	    /** 
	     * 组装菜单数据 第一列和第三列第五个是Click，写死，后台接管，其他的去mediaID，
	     * 取永久素材库，可以编辑
	     *  
	     * @return 
	     */  
	    private static Menu getTestMenu() { 
	    	
	    	ClickButton btn11 = new ClickButton();  
	        btn11.setName("户号绑定");  
	        btn11.setType("click");
	        btn11.setKey("rselfmenu_1_1");
	  
	        ClickButton btn12 = new ClickButton();  
	        btn12.setName("取消绑定");  
	        btn12.setType("click");  
	        btn12.setKey("rselfmenu_1_2");;  
	  
	        ClickButton btn13 = new ClickButton();  
	        btn13.setName("我的用水");  
	        btn13.setType("click");  
	        btn13.setKey("rselfmenu_1_3");
	        
	        ClickButton btn14 = new ClickButton();  
	        btn14.setName("立即缴费");  
	        btn14.setType("click");  
	        btn14.setKey("rselfmenu_1_4");
	        
	        ClickButton btn15 = new ClickButton();  
	        btn15.setName("水费预存");  
	        btn15.setType("click");  
	        btn15.setKey("rselfmenu_1_5");
	        
	      //对
	        MediaButton btn21 = new MediaButton();  
	        btn21.setName("停水信息");  
	        btn21.setType("media_id");  
	        btn21.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w");
	      //对
	        MediaButton btn22 = new MediaButton();  
	        btn22.setName("水质公示");  
	        btn22.setType("media_id");  
	        btn22.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4rWVDPpRtqKLvJEK5FLuofY"); 
	        
	        	
	        MediaButton btn23 = new MediaButton();  
	        btn23.setName("水价信息");  
	        btn23.setType("media_id");  
	        btn23.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w");
	        
	        MediaButton btn24 = new MediaButton();  
	        btn24.setName("缴费方式");  
	        btn24.setType("media_id");  
	        btn24.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w");
	        
	        MediaButton btn25 = new MediaButton();  
	        btn25.setName("营业网点");  
	        btn25.setType("media_id");  
	        btn25.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w");
	  
	      //对
	        MediaButton btn31 = new MediaButton();  
	        btn31.setName("公司简介");  
	        btn31.setType("media_id");  
	        btn31.setMedia_id("4RBv4loNhfB-_ZGGqfoycsOuk2iVwnWSF5nG5MZ7P-0"); 
	  
	        
	        MediaButton btn32 = new MediaButton();  
	        btn32.setName("用水法规");  
	        btn32.setType("media_id");  
	        btn32.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w"); 
	      //对
	        MediaButton btn33 = new MediaButton();  
	        btn33.setName("用水常识");  
	        btn33.setType("media_id");  
	        btn33.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4rYQBIFf7ha2-QHu_wKqEDs");   
	  
	        MediaButton btn34 = new MediaButton();  
	        btn34.setName("办事指南");  
	        btn34.setType("media_id");  
	        btn34.setMedia_id("Nvw_0FqOVwh6CjdxC7Qf4o0MYNVDAFUvI0ERUMYI_4w");
	        
	        ClickButton btn35 = new ClickButton();  
	        btn35.setName("积分兑换");  
	        btn35.setType("click");  
	        btn35.setKey("rselfmenu_3_5"); 
	        
	        ComButton mainBtn1 = new ComButton();  
	        mainBtn1.setName("我查查");  
	        mainBtn1.setSub_button(new Button[] {btn11, btn12, btn13, btn14, btn15});  
	  
	        ComButton mainBtn2 = new ComButton();  
	        mainBtn2.setName("水信息");  
	        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
	  
	        ComButton mainBtn3 = new ComButton();  
	        mainBtn3.setName("水知识");  
	        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 , btn35});
	  
	        /** 
	         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
	         *  
	         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
	         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
	         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
	         */  
	        Menu menu = new Menu();  
	        menu.setButton(new Button[] { mainBtn1,mainBtn2, mainBtn3 });  
	  
	        return menu;  
	    }
	    
	    
	    
}
