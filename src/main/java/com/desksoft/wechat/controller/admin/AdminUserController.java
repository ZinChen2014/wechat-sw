package com.desksoft.wechat.controller.admin;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import com.desksoft.wechat.common.model.Payrecord;
import com.desksoft.wechat.common.model.Refeeflow;
import com.desksoft.wechat.common.model.Scoreflow;
import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.common.model.Userbindflow;
import com.jfinal.core.ActionKey;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Page;

public class AdminUserController extends BaseController {
	
	@ActionKey("/user/login")
	public void login(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			String data = getPara("data");
			User user =Json.getJson().parse(data, User.class);

			Map<String, Object> map = User.dao.checkLoginByUser(user);

	        if ((Boolean) map.get("flag")) {
	            // 保存session
	        	setSessionAttr(USER_SESSION_KEY, User.dao.getUserByUserName(user.getUsername()));
	           // getSession().setAttribute(USER_SESSION_KEY, user);
	        } 
	        // 跳转到前台发起请求的路径
	        renderJson(map);
		}else{
			render("login.html");
		}
		
	}
	
	@ActionKey("/tologin")
	public void tologin(){
		if(getSessionAttr(USER_SESSION_KEY)==null){
			render("login.html");
		}else{
			 redirect("/index");
		}
		
	}
	
	@ActionKey("/user/logout")
	public void logout(){
		 getSession().setAttribute(USER_SESSION_KEY, null);
		 redirect("/admin/login");
	}
	
	public void getBindingUserList(){
		Date startDate = getQueryDate("startDate");
		Date endDate = getQueryDate("endDate");				
		int userNo = getParaToInt("userNo",0);
		setAttr("userNo",userNo);
		setAttr("prjPage", Userbindflow.me.getBindingUserPaginateList(getParaToInt("pageIndex", 1), 10,startDate,endDate,userNo));
		render("bindingUserPageList.html");
	}
	
	public void getPayRecordList(){
		Date startDate = getQueryDate("startDate");
		Date endDate = getQueryDate("endDate");				
		int userNo = getParaToInt("userNo",0);
		setAttr("userNo",userNo);
		Page<Payrecord> result = Payrecord.dao.getPayRecordPaginateList(getParaToInt("pageIndex", 1), 10,startDate,endDate,userNo);
		for(Payrecord payrecord:result.getList()){
			float num= (float)payrecord.getFee()/100;   
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
			String realPay = df.format(num);//返回的是String类型 
			payrecord.setRemark(realPay);
		}
		setAttr("prjPage", result);
		render("payRecordPageList.html");
	}
	
	public void getRefeeFlowList() {
		Date startDate = getQueryDate("startDate");
		Date endDate = getQueryDate("endDate");
		String openID = getPara("openID","default");
		setAttr("openID", openID);
		setAttr("prjPage",Refeeflow.dao.getRefeeFlowPaginateList(getParaToInt("pageIndex", 1), 10, startDate, endDate, openID));
		render("getRefeeFlowList.html");
	}
	
	public void getScoreFlowList() {
		Date startDate = getQueryDate("startDate");
		Date endDate = getQueryDate("endDate");
		String openID = getPara("openID","default");
		setAttr("openID", openID);
		setAttr("prjPage",Scoreflow.dao.getScoreFlowPaginateList(getParaToInt("pageIndex", 1), 10, startDate, endDate, openID));
		render("getScoreFlowList.html");
	}
	
	public void editPwd(){
		int id = getParaToInt(0,1);
		if(id>0){
			setAttr("editUser", (User)User.dao.findById(id));
		}else{
			setAttr("editUser", getOnLineUser());
		}
		render("editPassword.html");
	}
	
	@ActionKey("/user/updatePassword")
	public void updatePassword(){
		String type = getPara(0);		
		if ("json".equals(type)) {
			Map<String, Object> map = User.dao.updatePassword(getPara("username"),getPara("password"),getPara("newpwd"));
	        // 跳转到前台发起请求的路径
	        renderJson(map);
		}else{
			 redirect("/index");
		}
	
	}
//	@ActionKey("/user/delBindingUser")
//	public void delBindingUser() {
//		UserBindFlow.me.delBindingUser(getParaToInt());
//		redirect("/user/getBindingUserList");
//	}
	
	/*public void  addUser(){
		//String userName = getPara(0);
		//String password = getPara(1);
		String remark = PasswordUtil.getRandomOperPassword();
		String password = PasswordUtil.getEncyptedOperPassword("111111", remark);
		new User().set("username", "admin").set("type", 2).set("remark", remark).set("password", password).save();
	}*/
}
