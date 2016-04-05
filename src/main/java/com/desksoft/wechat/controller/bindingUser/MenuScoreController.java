package com.desksoft.wechat.controller.bindingUser;

import org.apache.log4j.Logger;

import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.controller.admin.BaseController;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.StrKit;

/**
 * 后台控制 点击按钮 发送 停水信息 ，给所有的绑定用户
 * 发送停水信息 列表展示 
 * @author Joker
 *
 */
public class MenuScoreController extends BaseController {
	
	private static Logger log = Logger.getLogger(MenuScoreController.class);
	
	/**
	 * 我的积分和推荐码
	 */
	
	@ActionKey("/myScore/getMyScore")
	public void getShutOffWaterInfoList(){
		String openId = getPara("openId",null);
		if(StrKit.notBlank(openId)){
			User user = User.dao.getUserByOpenId(openId);
			setAttr("ower", user);
		}else{
			log.debug("异常，微信没有获取到openID"+System.currentTimeMillis());
		}
		render("myScore.html");
	}
	
	
	
}
