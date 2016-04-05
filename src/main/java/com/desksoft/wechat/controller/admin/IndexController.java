package com.desksoft.wechat.controller.admin;

import com.desksoft.wechat.common.config.GlobalInterceptor;
import com.jfinal.core.ActionKey;
import com.jfinal.log.Log;


//@Before(IocInterceptor.class)
public class IndexController extends BaseController {


	 private static final Log log = Log.getLog(GlobalInterceptor.class);
	 
	public void index() {
		log.info("1");
		log.debug("cuowu");
		log.warn("33");
		setAttr("onlineUser", getOnLineUser());
		render("index.html");
	}
	@ActionKey("/index")
	public void index2() {
		setAttr("onlineUser", getOnLineUser());
		render("index.html");
	}
	
}
