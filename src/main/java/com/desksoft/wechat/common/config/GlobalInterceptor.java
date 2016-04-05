package com.desksoft.wechat.common.config;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import com.desksoft.wechat.common.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;

public class GlobalInterceptor implements Interceptor {

	 private static final Log log = Log.getLog(GlobalInterceptor.class);
	
	public GlobalInterceptor() {
		
	}
	@Override
	public void intercept(Invocation invocation) {
		String href =  invocation.getActionKey();
		String[] urls =getUrlsBySys();
		//如果true,不需要验证登录
		if(!isChkHref(urls,href)){
			try {
				invocation.invoke();
			} catch (Exception e) {
				logWrite(invocation, e);
			}
		}else{
			Controller controller = invocation.getController();
			User user = (User)controller.getSessionAttr("_USER_SESSION_KEY");
			if(user==null){
				controller.redirect("/tologin");
			}else{
				try {
					invocation.invoke();
					controller.setAttr("onlineUser", user);
				} catch (Exception e) {
					logWrite(invocation, e);
				}
			}
			
		}

	}
	
	private void logWrite(Invocation inv, Exception e) {
		// 开发模式
		if (PropKit.getBoolean("devMode", false)) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder("n---Exception Log Begin---n");
		sb.append("Controller:").append(inv.getController().getClass().getName()).append("n");
		sb.append("Method:").append(inv.getMethodName()).append("n");
		sb.append("Exception Type:").append(e.getClass().getName()).append("n");
		sb.append("Exception Details:");
		log.error(sb.toString(), e);

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
