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
 * 水务集团de 每月20号9点发送用户抄表信息(当月用水信息)
 * 
 * 抄表信息
 * 
 * success
 */

@Scheduled(cron = "0 0 9 20 * ?")
public class MonthInfoJob implements Job {

	private static Logger log = Logger.getLogger(MonthInfoJob.class);
	
	//户抄表信息模板ID
	//private static final String moudleId="d3Y7QLPzAVKPUHz37r0n4oVYQyb73LqbyZ1tdLYUTYA";
	
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
				//获取该用户的当月的用水情况 
				String userNo = userBindFlow.getUserNo().toString().trim();
				if(userBindFlow==null || StrKit.isBlank(userNo)){
					continue;
				}
				MonthWaterInfo monthWaterInfo = getCurrentMonthWater(userNo);
				if(monthWaterInfo==null){
					continue;
				}
				
				DataItem dataItem = getMonthInfoMudle(monthWaterInfo,userBindFlow);
			    //生成群发模板
			    String json=TempToJson.getTempJson(openId, PropKit.get("wx_mouldID_MonthInfoJob","BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY"),"#FF0000", "", dataItem);
				System.out.println(json);
				getApiConfig();
				ApiResult apiResult = TemplateMsgApi.send(json);
					//如果发送失败
					if(!checking(apiResult)){
						log.info("发送月使用水费失败的id:"+userBindFlow.getId()+"error:"+apiResult.getJson());
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
			//Shutoffwaterflow.dao.findByIdLoadColumns(sfWaterFlow.getId(), "id,state").set("state", 2).update();
			log.info("当日使用水费所有消息推送完毕"+System.currentTimeMillis());
		}
	}
	
	private MonthWaterInfo getCurrentMonthWater(String userNo){
	
			String backString = YouPuSocketService.getUserWaterInfo(userNo,null,null);
			//响应码
			String code =YouPuSocketService.isSuccessConnect(backString);
			if("00".equals(code)){
				//返回数据拼接为List
				List<?> dataList = YouPuSocketService.getBackList(backString);
				int l = dataList.size();
				//取最后8个
				MonthWaterInfo monthWaterInfo = new MonthWaterInfo();
				monthWaterInfo.setYearMonth(dataList.get(l-8).toString());
				monthWaterInfo.setAgoAmount(dataList.get(l-7).toString());
				monthWaterInfo.setCurrentAmount(dataList.get(l-6).toString());
				monthWaterInfo.setAmount(dataList.get(l-5).toString());
				monthWaterInfo.setMustAmount(dataList.get(l-4).toString());
				monthWaterInfo.setFee(dataList.get(l-3).toString());
				monthWaterInfo.setWxFee(dataList.get(l-2).toString());
				monthWaterInfo.setFlag(dataList.get(l-1).toString());
				return monthWaterInfo;
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
		//String errmsg=jsonObject.getString("errmsg");
		if (status==0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 模板
	 * @return DataItem
	 */
	private static DataItem getMonthInfoMudle(MonthWaterInfo monthWaterInfo,Userbindflow userBindFlow){
		/*	客户姓名：{{userName.DATA}}
		客户编号：{{userNumber.DATA}}
		用水地址：{{address.DATA}}
		计费月份：{{month.DATA}}
		计费水量：{{power.DATA}}userBindFlow
		水费金额：{{pay.DATA}}
		{{remark.DATA}}*/
		
		DataItem dataItem=new DataItem();
		dataItem.setUserName(new TempItem(userBindFlow.getUserName(),"#FF0000"));
		dataItem.setUserNumber(new TempItem(userBindFlow.getUserNo().toString(),"#FF0000"));
		dataItem.setAddress(new TempItem(userBindFlow.getAddress(),"#FF0000"));
		dataItem.setMonth(new TempItem(monthWaterInfo.getYearMonth(), "#0000FF"));
		dataItem.setPower(new TempItem(monthWaterInfo.getMustAmount(),"#FF0000"));
		dataItem.setPay(new TempItem(monthWaterInfo.getFee(),"#FF0000"));
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
