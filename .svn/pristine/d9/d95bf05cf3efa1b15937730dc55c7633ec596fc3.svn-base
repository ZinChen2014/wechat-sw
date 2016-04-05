package com.desksoft.wechat.controller.bindingUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.desksoft.wechat.common.model.Payrecord;
import com.desksoft.wechat.common.model.Shutoffwaterflow;
import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.controller.admin.BaseController;
import com.desksoft.wechat.service.XyBankPayService;
import com.desksoft.wechat.service.YouPuSocketService;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.StrKit;

/**
 * 后台控制 点击按钮 发送 停水信息 ，给所有的绑定用户
 * 发送停水信息 列表展示 
 * @author Joker
 *
 */
public class SendShutWaterInfoInfoController extends BaseController {
	
	private static Logger log = Logger.getLogger(SendShutWaterInfoInfoController.class);
	
	@ActionKey("/admin/getShutOffWaterInfoList")
	public void getShutOffWaterInfoList(){
		setAttr("prjPage", Shutoffwaterflow.dao.getShutoffwaterflowPaginateList(getParaToInt("pageIndex", 1), 10));
		render("shutoffwaterflowPageList.html");
	}
	
	
	/**
	 *  将该发送停水消息的数据存入数据库job，此job，每天5分钟跑一次
	 */
	public void sendShutOffWaterInfo(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			User loginUser = super.getOnLineUser();
			if(loginUser==null){
				log.debug("非登录人员后台操作，当前没有登录用户");
				map.put("flag", false);
				map.put("msg", "请登录后再执行该Job");
				renderJson(map);
			}
			String keyword1 = getPara("keyword1");
			String keyword2 = getPara("keyword2");
			String keyword3 = getPara("keyword3");
			String remark = getPara("remark");
			if(StrKit.notBlank(keyword1) && StrKit.notBlank(keyword2) && StrKit.notBlank(keyword3) && StrKit.notBlank(remark)){
				map.put("flag", false);
				map.put("msg", "请检查提交参数");
				renderJson(map);
			}
			boolean flag =Shutoffwaterflow.dao.saveShutOffWaterJob(keyword1, keyword2, keyword3, remark);
			if(!flag){
				map.put("flag", false);
				map.put("msg", "系统异常，请重新提交");
			}else{
				map.put("flag", flag);
				map.put("msg", "保存成功，系统将会后台开始推送，请查看该记录执行状态");
			}
			renderJson(map);
		}else{
			render("editShutOffWaterInfo.html");
		}
	}
	
	
	/**
	 * 进入 ---编辑停水消息模板
	 */
	public void editShutOffWaterInfo(){
		setAttr("onlineUser", getOnLineUser());
		render("editShutOffWaterInfo.html");
	}
	
	
	
	@ActionKey("/test")
	public void test() {
		for(int i=0;i<10;i++){
			String openId = "oa5uZuEm53BTuVGJhdYWlwFITMqg";
			String userNo = "810023";
			long currentTime = System.currentTimeMillis() ;
			String out_trade_no = "A"+XyBankPayService.formatTimeStamp(new Date(currentTime))+"-"+userNo;
			int fee = 100;
			int realNumber = 1;
			String feeIDs = "54514"+i;
			String realPay ="7.00";
			
			boolean f = Payrecord.dao.addPayRecord(1, openId, userNo, fee, 0, out_trade_no,realNumber,feeIDs);
			
			//支付日期
			String realNumber2 ="1";
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String payDate = sdf.format(time);
			YouPuSocketService.getchkpay(out_trade_no, realNumber2, realPay, payDate, feeIDs);
		
		}
	}
	
	
	
	
}
