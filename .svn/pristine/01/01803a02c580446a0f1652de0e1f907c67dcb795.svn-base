package com.desksoft.wechat.common.config;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.desksoft.wechat.common.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

public class GlobalInterceptor implements Interceptor {

	public GlobalInterceptor() {
		
	}
	@Override
	public void intercept(Invocation inv) {
		String href =  inv.getActionKey();
		String[] urls =getUrlsBySys();
		//如果true,不需要验证登录
		if(!isChkHref(urls,href)){
			inv.invoke();
		}else{
			Controller controller = inv.getController();
			User user = (User)controller.getSessionAttr("_USER_SESSION_KEY");
			if(user==null){
				controller.redirect("/tologin");
			}else{
				inv.invoke();
				controller.setAttr("onlineUser", user);
			}
			
		}

	}
	
	
	private boolean isChkHref(String[] urls,String actionKey){
		if(urls==null){
			return false;
		}
		 for(int i=0; i < urls.length; i++) {
			 String url = urls[i].trim();
			 if(actionKey.contains(url)){
				 return true;
			 } 
		 }
			
		return false;
	}
	
	
	private String[]  getUrlsBySys(){
		PropKit.use("jfinal_config.txt");
		String urls = PropKit.get("checkHref");
		if(isHasXchar(urls.trim())){
			System.out.println("jfinal_config.txt  nocheckHref  has 特殊字符 ");
			return null;
		}
		String[]  destString = urls.trim().split(",");
		return destString;
	}
	
	
	private boolean isHasXchar(String url){
		if(StringUtils.isBlank(url)){
			return true;
		}
		//去掉英文逗号和/
		  String regEx = "[`~!@#$%^&*()+=|{}':;'\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		  Pattern p = Pattern.compile(regEx);
		  java.util.regex.Matcher m = p.matcher(url);
		  //System.out.println(m.find());
		  return m.find();
	}

}
