package com.desksoft.wechat.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.kit.XmlKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;

/**
 * @author Joker
 * 兴业银行 公众号支付调用接口
 */
public class XyBankPayService {
	
	
	//商户密钥 
	String MerchantKey = "9d101c97133837e13dde2d32a5054abb";
	
	//获得token_ID url
	private static String gatewayUrl = "https://pay.swiftpass.cn/pay/gateway";
	
	//支付URL
	private static String jspayUrl = "https://pay.swiftpass.cn/pay/jspay";
	
	
	public static String pushOrder(Map<String, String> params) {
		return HttpUtils.post(gatewayUrl, PaymentKit.toXml(params));
	}
	
	
	public static String getNoticeUrl(){
		String CONTEXT_PATH = "";
		
		String devType = PropKit.get("devType");
		if(StringUtils.isBlank(devType)){
			return CONTEXT_PATH;
		}
		if("dev".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_DEV");
		else if("test".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_TEST");
		else if("online".equalsIgnoreCase(devType))
			CONTEXT_PATH = PropKit.get("CONTEXT_PATH_ONLINE");
		
		return CONTEXT_PATH;
	}
	
	public static String formatTimeStamp(Date date){
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		System.out.println(time.format(date)); 
		return time.format(date);
	}
	
	
	public static String getPayUri(String token_ID){
		return jspayUrl+"?token_id="+token_ID+"&showwxtitle=1";
	}


	/**
	    * UTF-8编码
	    *
	    * @param source
	    * @return
	    */
	  public static String urlEncodeUTF8(String source) {
	    String result = source;
	    try {
	      result = java.net.URLEncoder.encode(source, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return result;
	  }
	
	public static void main(String[] args) {
		String xmlString = "<xml><body><![CDATA[测试支付]]></body><mch_create_ip><![CDATA[127.0.0.1]]></mch_create_ip><mch_id><![CDATA[001075552110006]]></mch_id>"
							+"<nonce_str><![CDATA[1409196838]]></nonce_str>"
							+"<notify_url><![CDATA[http://227.0.0.1:9001/javak/sds?123&23=3]]></notify_url>"
							+"<out_trade_no><![CDATA[141903606228]]></out_trade_no>"
							+"<service><![CDATA[pay.weixin.jspay]]></service>"
							+"<sign><![CDATA[83684D9546F261997EFF2ECFAC372583]]></sign>"
							+"<total_fee><![CDATA[1]]></total_fee>"
							+"</xml>";
		Node document =  XmlKit.parse(xmlString);
		String body = XmlKit.documentText((org.w3c.dom.Document) document, "body"); 
			 System.out.println(body);
	}
	
	
}
