package com.desksoft.wechat.controller.bindingUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.desksoft.wechat.common.model.Refeeflow;
import com.desksoft.wechat.common.model.Scoreflow;
import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.common.model.Userbindflow;
import com.desksoft.wechat.common.model.otherbean.WaterUserInfo;
import com.desksoft.wechat.controller.admin.BaseController;
import com.desksoft.wechat.service.YouPuSocketService;
import com.jfinal.kit.StrKit;

/**
 * 
 * 
 * @author Joker
 *
 *绑定户号，解绑户号 推荐人
 *
 */
public class BindingUserController extends BaseController {
	
	private static Logger log = Logger.getLogger(BindingUserController.class);
	
	/**
	 * 解绑单个
	 */
	public void unbindSingle(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String,Object> map = new HashMap<String,Object>();
			String openID = getPara("openId").trim();
			boolean bflag = Userbindflow.me.unbindSingleUser(openID,getParaToInt("userno"));
			if(bflag){
				map.put("flag", true);
			}else{
				map.put("flag", false);
			}
	        // 跳转到前台发起请求的路径
	        renderJson(map);
		}else{
			renderText("非法URL");
		}
	}
	
	
	
	/**
	 * 加载解绑列表
	 */
	public void unbindUser(){
		String openId = getPara("openId",null);
		if(StringUtils.isNotBlank(openId)){
			List<Userbindflow> list = Userbindflow.me.selectBindingUserList(openId);
			if(list.size()>0){
				setAttr("objList", list);
				render("unbind.html");
			}else{
				render("addUser.html");
			}
				
		}else{
			renderText("非法URL");
		}
		
	}
	
	
	/**
	 * 推荐人
	 * @param tjCode
	 * @param openID
	 */
	private boolean addRefeer(String tjCode,String openID){
		if(User.dao.isValidTjCode(tjCode, openID)){
			//推荐人加10分
			boolean flag2 = User.dao.addScoreByTjCode(tjCode,10);
			if(flag2){
				//插入记录
				User refeer = User.dao.getUserBytjCode(tjCode);
				//推荐人 积分记录添加一条，User表推荐人增加积分
				Refeeflow.dao.addRefeeFlow(openID, refeer.getOpenid(), null);
				//积分
				Scoreflow.dao.addScoreFlow(refeer.getOpenid(), refeer.getId(), 1, 10);
			}
			return true;
		}else{
			log.debug("微信号为"+openID+"的推荐码无效，无效码："+tjCode);
			return false;
		}
	}
	
	/**
	 * 绑定用户信息3
	 */
	public void bind(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String,Object> map = new HashMap<String,Object>();
			String openID = getPara("openId").trim();
			String mobileno = getPara("mobileno").trim();
			// 如果获得推荐人的推荐码 ,推荐人记录插如一条记录，并加10分
			String tjCode = getPara("refeer").trim();
			boolean flaU = true;
			if(StrKit.notBlank(tjCode)&&StrKit.notBlank(openID)){
				 flaU = addRefeer(tjCode, openID);
			}
			if(flaU){
				int flag = Userbindflow.me.addBindUser(openID,getParaToInt("userno"),mobileno,getPara("address"),getPara("userName"));
				
				if(flag==1){
					map.put("flag", true);
				}else if(flag==3){
					map.put("flag", true);
					map.put("msg", "该户号你有过绑定记录,无需再绑定");
				}else if(flag==2){
					map.put("flag", true);
					map.put("msg", "该户号你有过绑定记录,已重新绑定成功");
				}else if(flag==0){
					map.put("flag", false);
					map.put("msg", "信息绑定失败");
				} else{
					map.put("flag", false);
					map.put("msg", "系统异常");
				}
			}else{
				map.put("flag", false);
				map.put("msg", "你的推荐码无效，请重新输入，或者不输入");
			}
			
	        // 跳转到前台发起请求的路径
	        renderJson(map);
		}else{
			renderText("非法URL");
		}
	}
	
	
	/**
	 * 加载用户信息2
	 */
	public void toBind(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String,Object> map = new HashMap<String,Object>();
			String userno = getPara("userno");
			if(StrKit.notBlank(userno)){
				String temp = YouPuSocketService.getUserInfoData(userno);
				String[] temps = temp.split("\\|");
				//响应码
				String ReturnCode  = temps[0].substring(42, 44);
				
				if("00".equals(ReturnCode )){
					
					// 组装成List<bean>,返回给页面
					map.put("userName", temps[2]);
					map.put("address", temps[3]);
					List<WaterUserInfo> list = new ArrayList<WaterUserInfo>();
					for(int i=4;i<temps.length;i+=6){
						WaterUserInfo waterUserInfo = new WaterUserInfo();
						waterUserInfo.setIndex(temps[i]);
						waterUserInfo.setRePay(temps[i+1]);
						waterUserInfo.setCardId(temps[i+2]);
						waterUserInfo.setUserNo(temps[i+3]);
						waterUserInfo.setFaName(temps[i+4]);
						waterUserInfo.setAddress(temps[i+5]);
						list.add(waterUserInfo);
					}
					
					map.put("wtList", list);
					
					String openID = getPara("openId").trim();
					map.put("userno", userno);
					map.put("openID", openID);
					map.put("phoneNum", getPara("mobileno"));
					setAttr("attrObj", map);
		        render("confirmUser.html");
				}else{
					renderText("数据加载失败！");
				}
			}

		}else{
			renderText("非法URL");
		}
	}
	
	
	
	/**
	 * 判断户号是否存在1
	 */
	public void isExstBind(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String,Object> map = new HashMap<String,Object>();
			String userno =getPara("userno");
			String openId = getPara("openId");
			//判断该户号是否已经绑定该微信号
			if(Userbindflow.me.isBindingByOpenIdAndUserNo(openId,userno)){
				map.put("flag", 2);
				//map.put("msg", "该户号你有过绑定记录,已为您绑定成功");
				map.put("msg", "成功绑定");
			}else{
				//调用 水务接口，判断是否有效的户号userno
				String temp = YouPuSocketService.getUserInfoData(userno);
				String[] temps = temp.split("\\|");
				//响应码
				String ReturnCode  = temps[0].substring(42, 44);
				if("00".equals(ReturnCode )){
					map.put("flag", 1);
					map.put("msg", "请放心绑定");
				}else if("99".equals(ReturnCode )){
					map.put("flag", -1);
					map.put("msg", "核心系统故障");
				}else{
					map.put("flag", 0);
					map.put("msg", "用户不存在");
				}
			}
			
	        // 跳转到前台发起请求的路径
	        renderJson(map);
		}else{
			renderText("非法URL");
		}
	}
	
}
