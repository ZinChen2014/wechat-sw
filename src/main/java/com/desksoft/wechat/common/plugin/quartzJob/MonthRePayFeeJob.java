package com.desksoft.wechat.common.plugin.quartzJob;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.desksoft.wechat.common.model.Userbindflow;
import com.desksoft.wechat.common.model.otherbean.CallBackJson;
import com.desksoft.wechat.common.model.otherbean.MonthWaterInfo;
import com.desksoft.wechat.common.model.otherbean.template.DataItem;
import com.desksoft.wechat.common.model.otherbean.template.TempItem;
import com.desksoft.wechat.common.plugin.Scheduled;
import com.desksoft.wechat.common.utils.ArithUtils;
import com.desksoft.wechat.service.TempToJson;
import com.desksoft.wechat.service.YouPuSocketService;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;


/**
 * @author Joker
 * 水务集团 每月月底9点发送用户欠费信息(调动缴费申请接口)
 * 
 * success
 * 
 */
@Scheduled(cron = "0 0 9 L * ?")
public class MonthRePayFeeJob implements Job {

	private static Logger log = Logger.getLogger(MonthRePayFeeJob.class);
	
	//户抄表信息模板ID
//	private static final String moudleId="nmBpywF_Py_dKM7MyRgorqnaO6WIUcJ59kIy4u-YUKk";
	
	//每批次处理条数
	private static final int maxSize = 1000;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		//每次取1000条,处理
		int maxID = 0;
		List<Userbindflow> list = Userbindflow.me.getUserBindFlowListById(maxID);
		if(list.size()>0){
			dealMonthInfo(maxID);
		}

	}
	
	/**
	 * 处理一个批次1000个用户推送
	 * 循环
	 * @param maxID  
	 * @return flag maxID  Map<String,Object>
	 */
	private void dealMonthInfo(int maxID){
		
		List<Userbindflow> list = Userbindflow.me.getUserBindFlowListById(maxID);
		if(list.size()<=0){
			log.info("当日所有消息推送完毕"+System.currentTimeMillis());
			return;
		}
		
		//处理一个批次1000个支付记录
		try {
			//获取分批所有的绑定自来水户号的且关注微信的用户，分别给他们微信号发送
			for(Userbindflow userBindFlow:list){
				String openId=userBindFlow.getOpenID();
				if(StrKit.isBlank(openId)||openId.length()!=28){
					continue;
				}
				//获取该用户的当月的欠费情况 
				String userNo = userBindFlow.getUserNo().toString().trim();
				if(userBindFlow==null || StrKit.isBlank(userNo)){
					continue;
				}
				MonthWaterInfo monthWaterInfo = getCurrentMonthWater(userNo);
				if(monthWaterInfo==null ||  monthWaterInfo.getFee()==null ||  "0.00".equals(monthWaterInfo.getFee())||  "0".equals(monthWaterInfo.getFee())){
					continue;
				}
				
				DataItem dataItem = getMonthInfoMudle(userBindFlow.getAddress(),monthWaterInfo.getFee());
			    //生成群发模板
			    String json=TempToJson.getTempJson(openId, PropKit.get("wx_mouldID_MonthRePayFeeJob","BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY"),"#FF0000", "", dataItem);
				System.out.println(json);
				getApiConfig();
				ApiResult apiResult = TemplateMsgApi.send(json);
					//如果发送失败
					if(!checking(apiResult)){
						log.info("发送欠钱水费失败的id:"+userBindFlow.getId()+"error:"+apiResult.getJson());
					}
				//System.out.println(apiResult.getJson());
			}
			log.info("成功处理1000个"+System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			if(list.size()>0){
			//	log.debug(System.currentTimeMillis()+"出现异常，最小id是："+list.get(0).getId()+"此次停水通知的ID"+sfWaterFlow.getId());
			}else{
		//		log.debug(System.currentTimeMillis()+"出现异常"+"此次停水通知的ID"+sfWaterFlow.getId());
			}
			
		}
		
		if(list.size()==maxSize){
			Userbindflow pr = list.get(list.size()-1);
			 
			 if(null!=pr && null!=pr.getId() && pr.getId()>0){
				 
				 log.debug("maxID:"+pr.getId());
				 
				 //循环
				 dealMonthInfo(pr.getId());
			 }
		}else{
			log.info("当日使用欠钱水费所有消息推送完毕"+System.currentTimeMillis());
		}
	}
	
	private MonthWaterInfo getCurrentMonthWater(String userNo){
	
			String backString = YouPuSocketService.getpayData(userNo,null,null);
			//响应码
			String code =YouPuSocketService.isSuccessConnect(backString);
					
			List<?> dataList = YouPuSocketService.getBackList(backString);
			if("00".equals(code)){

				//其他信息
				String num = (String) dataList.get(3);//一共多少笔交易
				double    waterFee = 0;
				double    fund = 0;
				double    total;
				for(int i=4;i<dataList.size();i+=4){
					waterFee = ArithUtils.add(waterFee,Double.parseDouble((String) dataList.get(i+2)));
					fund = ArithUtils.add(fund,Double.parseDouble((String) dataList.get(i+3)));
				}
				total = ArithUtils.add(waterFee,fund);
				//取最后8个
				MonthWaterInfo monthWaterInfo = new MonthWaterInfo();
				monthWaterInfo.setFee(String.valueOf(total));
				return monthWaterInfo;
			}else if("11".equals(code)){//不欠费
				return null;
			}else{
				return null;
			}
		}
	
	
	/**
	 * 检测是否发送成功
	 * @param ApiResult apiResult
	 * @return Boolean
	 */
	public boolean checking(ApiResult apiResult){
		boolean flag = false;
		CallBackJson callBackJson= JsonKit.parse(apiResult.getJson(), CallBackJson.class);
		int status=callBackJson.getErrcode();
		if (status==0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 模板
	 * @return DataItem
	 */
	private static DataItem getMonthInfoMudle(String address,String fee){
		/*
		 * 
		 * {{first.DATA}} 用水地址：{{keyword1.DATA}} 未交水费：{{keyword2.DATA}}
		 * {{remark.DATA}}
		 */
		
		String remark="由于数据存在延迟，如您未交费请及时交纳，如您已交费请忽略本次提醒，给您带来不便敬请谅解。";
		DataItem dataItem=new DataItem();
		
		dataItem.setKeyword1(new TempItem(address,"#FF0000"));
		dataItem.setKeyword2(new TempItem(fee, "#0000FF"));
		dataItem.setRemark(new TempItem(remark,"#FF0000"));
		return dataItem;
	}
	
	
	/**
	 * 	// 配置微信 API 相关常量
	 */
	public static void getApiConfig(){
		PropKit.use("jfinal_config.txt");
		ApiConfig ac = new ApiConfig();
		ac.setAppId(PropKit.get("appId").trim());
		ac.setAppSecret(PropKit.get("appSecret").trim());
	    ApiConfigKit.setThreadLocalApiConfig(ac);
	}
	
}
