/**
 *  接管微信的信息，菜单事件，关注等Func
 *  
 *  		    @author Joker
 */

package com.desksoft.wechat.controller.microMsg;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.desksoft.wechat.common.model.User;
import com.desksoft.wechat.common.model.Userbindflow;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

import freemarker.template.TemplateModelException;

public class WechatMsgController extends MsgControllerAdapter {

	
	public String Regex = "[\\+ ~!@#%^-_=]?";
	static Log logger = Log.getLog(WechatMsgController.class);
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));

		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 实现父类抽方法，处理文本消息 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal weixin
	 * sdk的基本功能： 本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
	 * 其它类型的消息会在随后的方法中进行测试
	 */
	protected void processInTextMsg(InTextMsg inTextMsg) {
		OutCustomMsg outMsg= new OutCustomMsg(getInMsg());
		outMsg.setContent(inTextMsg.getContent());
		render(outMsg);
		// 帮助提示 renderOutTextMsg("\t文本消息已成功接收，内容为： "  + "\n\n");
		
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	protected void processInImageMsg(InImageMsg inImageMsg) {

	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
	}

	/**
	 * 实现父类抽方法，处理视频消息
	 */
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
	}

	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
	}

	/**
	 * 实现父类抽方法，处理地址位置消息
	 */
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		
	}

	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
	}

	@Override
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
	}

	@Override
	protected void processInMassEvent(InMassEvent inMassEvent) {
		logger.debug("测试方法：processInMassEvent()");
		renderNull();
	}

	/**
	 * 实现父类抽方法，处理自定义菜单事件
	 */
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		
		
		
		String opneID = inMenuEvent.getFromUserName();
		
		if(StringUtils.isNotBlank(opneID)){
			logger.debug("菜单事件：" + opneID);
		}else{
			logger.debug("无法获得当前的微信号OpenID,异常");
		}
		
		String evenKey = inMenuEvent.getEventKey();
		boolean flag = "rselfmenu_1_1".equalsIgnoreCase(evenKey);
		boolean flah = Userbindflow.me.isBindingUser(opneID);
		if(flah){
			render(dealMenuClickEvent(inMenuEvent));
		}else{
			if(flag){
				 final String NETWORK = getNETWORK();
				 OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
				 String title = "户号绑定";
				 String description = "欢迎订阅嵊州水务，您可以将户号与微信号进行绑定，轻松获得更加直观敏捷的信息，为避免客户号绑定出错，绑定时校验预留的手机号码。点击“阅读全文”进行绑定操作。";
				 String picUrl = NETWORK+"/img/bindingFirst.jpg";
				 String url = NETWORK+"/pages/user/addUser.html?data={\"openId\":\""+opneID+"\"}";
				 outMsg.addNews(title, description, picUrl, url);
				 render(outMsg);
			}else{
				 render(getUnbindingMsg(inMenuEvent));
			}
			
		}
	}
	
	private String getNETWORK(){
		//fm 的全局变量
		String devType = PropKit.get("devType");
		if("dev".equalsIgnoreCase(devType))
			return PropKit.get("CONTEXT_PATH_DEV");
		else if("test".equalsIgnoreCase(devType))
			return PropKit.get("CONTEXT_PATH_TEST");
		else if("online".equalsIgnoreCase(devType))
			return PropKit.get("CONTEXT_PATH_ONLINE");
		else
			return null;
	}
	
	/**
	 *  处理未绑定户号的微信粉丝
	 *  
	 * @param inMenuEvent
	 * @return msg
	 */
	private OutTextMsg getUnbindingMsg(InMenuEvent inMenuEvent){
		OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
		outMsg.setContent("您的微信号没有和户号绑定，请先绑定您的户号");
		return outMsg;
	}
	
	/**
	 * 处理已绑定户号的菜单点击事件
	 * 
	 * @param inMenuEvent
	 * @return
	 */
	private OutNewsMsg dealMenuClickEvent(InMenuEvent inMenuEvent){
		
		final String NETWORK = getNETWORK();
		OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
		String opneID = inMenuEvent.getFromUserName();
		String evenKey = inMenuEvent.getEventKey();
		System.out.println("____________evenKey:"+evenKey+"_____________________");
		
		String title = "错误信息";
		String description = "当前进入了一个错误信息，请联系微信号管理员";
		String picUrl = "";
		String url = "http://mp.weixin.qq.com/wiki/10/6380dc743053a91c544ffd2b7c959166.html"; 
		
		if("rselfmenu_1_1".equalsIgnoreCase(evenKey)){
			  title = "户号绑定";
			  description = "欢迎订阅嵊州水务，您可以将户号与微信号进行绑定，轻松获得更加直观敏捷的信息，为避免客户号绑定出错，绑定时校验预留的手机号码。点击“阅读全文”进行绑定操作。";
			  picUrl = NETWORK+"/img/bindingFirst.jpg";
			  url = NETWORK+"/pages/user/addUser.html?data={\"openId\":\""+opneID+"\"}";
		}else if("rselfmenu_1_2".equalsIgnoreCase(evenKey)){
			
			 title = "取消绑定";
			 description = "欢迎订阅嵊州水务，您可以取消微信与户号的绑定";
			 picUrl = NETWORK+"/img/unbindFirst.jpg";
			 url = NETWORK+"/bindingUser/unbindUser?openId="+opneID; 
			 
		}else if("rselfmenu_1_3".equalsIgnoreCase(evenKey)){
		
			 title = "我的用水";
			 description = "欢迎订阅嵊州水务，您可以实时查询当前一年的用水情况和缴费情况。点击“阅读全文”查看详细。";
			 picUrl = NETWORK+"/img/myWater.jpg";
			 url = NETWORK+"/usedWater/getUsedWaterList?openId="+opneID; 
			
		}else if("rselfmenu_1_4".equalsIgnoreCase(evenKey)){
			
			 title = "立即缴费";
			 description = "欢迎订阅嵊州水务，只要您绑定了用水客户号，就可以通过微信财付通缴纳水费，实现足不出户，动动手指就可以缴费水费。";
			 picUrl = NETWORK+"/img/payWater.jpg";
			 url = NETWORK+"/usedWater/payWater?openId="+opneID;
			 
		}else if("rselfmenu_1_5".equalsIgnoreCase(evenKey)){
			
			 title = "水费预存";
			 description = "欢迎订阅嵊州水务，当您绑定了用水客户号，就可以通过微信财付通直接预存水费。";
			 picUrl = NETWORK+"/img/repay.jpg";
			 url = NETWORK+"/usedWater/rePayWater?openId="+opneID;
			
		}else if("rselfmenu_3_5".equalsIgnoreCase(evenKey)){
			
			 title = "积分兑换";
			 description = "欢迎订阅嵊州水务，预存得积分,您可以查看自己的积分,分享你的推荐码，也可以获得更多积分噢";
			 picUrl = NETWORK+"/img/jifen.jpg";
			 url = NETWORK+"/myScore/getMyScore?openId="+opneID;
			 
		}
		
		outMsg.addNews(title, description, picUrl, url);
		return outMsg;
	}
	
	
	@Override
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
	}

	/**
	 * 实现父类抽方法，处理链接消息 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
	 */
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
	}

	@Override
	protected void processInCustomEvent(InCustomEvent inCustomEvent) {
		System.out.println("processInCustomEvent() 方法测试成功");
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注消息
	 */
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
		outMsg.setContent("欢迎关注嵊州自来水有限公司" );
		String openID = inFollowEvent.getFromUserName();
		//如果为取消关注事件,更新数据库
		if("unsubscribe".equals(inFollowEvent.getEvent())){
			User.dao.cancelFollow(openID); 
		}else{//如果关注的话
			//String wxName = getWxName(openID);
			User.dao.addMsgUser(openID,null);
		}
		// 如果为取消关注事件，将无法接收到传回的信息
		render(outMsg);
	}
	
	//有问题，暂时搁置
	private String getWxName(String openID){
		if(StrKit.isBlank(openID)){
			return "";
		}else{
			//getApiConfig();
			String json = UserApi.getUserInfo(openID).getJson();
			JSONObject jo=JSONObject.parseObject(json);
			//UserInfo user= JsonKit.parse(json, UserInfo.class);
			if(jo!=null && jo.get("nickname")!=null){
				return jo.get("nickname").toString();
			}else{
				return "";
			}
			
		}
	}
	
	// 处理接收到的模板消息是否送达成功通知事件
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
		String status = inTemplateMsgEvent.getStatus();
		renderOutTextMsg("模板消息是否接收成功：" + status);
	}

	@Override
	protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
		/*logger.debug("摇一摇周边设备信息通知事件：" + inShakearoundUserShakeEvent.getFromUserName());
		OutTextMsg outMsg = new OutTextMsg(inShakearoundUserShakeEvent);
		outMsg.setContent("摇一摇周边设备信息通知事件UUID：" + inShakearoundUserShakeEvent.getUuid());
		render(outMsg);*/
	}

	@Override
	protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
		/*logger.debug("资质认证成功通知事件：" + inVerifySuccessEvent.getFromUserName());
		OutTextMsg outMsg = new OutTextMsg(inVerifySuccessEvent);
		outMsg.setContent("资质认证成功通知事件：" + inVerifySuccessEvent.getExpiredTime());
		render(outMsg);*/
	}

	@Override
	protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
		/*logger.debug("资质认证失败通知事件：" + inVerifyFailEvent.getFromUserName());
		OutTextMsg outMsg = new OutTextMsg(inVerifyFailEvent);
		outMsg.setContent("资质认证失败通知事件：" + inVerifyFailEvent.getFailReason());
		render(outMsg);*/
	}
}
