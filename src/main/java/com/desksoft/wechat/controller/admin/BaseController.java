package com.desksoft.wechat.controller.admin;

import java.text.DateFormat;
import java.util.Date;

import com.desksoft.wechat.common.model.User;
import com.jfinal.core.Controller;


/**
 * BaseController  作为所有Controller类的父类，提供一些基础通用的method
 * @author Joker
 *
 */
public class BaseController extends Controller {
	
	
    /**
	 * @session会员信息 会员登录后session中保存的会员对象
	 */
    public static final String USER_SESSION_KEY = "_USER_SESSION_KEY";
	
    public void index() {
		//setAttr("onlineUser", getOnLineUser());
	}
	
	public User getOnLineUser() {
		User user = (User)getSession().getAttribute(USER_SESSION_KEY);
		return user;
	}
	
	public Date getQueryDate(String args){
		Date sDate =null;
		if(!"default".equals(getPara(args))){
			sDate = getParaToDate(args,null);
		}
		if(sDate==null){
			setAttr(args,"default");
		}else{
			setAttr(args,DateFormat.getDateInstance().format(sDate));
		}
		return sDate;
	}

}
