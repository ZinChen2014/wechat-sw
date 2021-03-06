package com.desksoft.wechat.common.model.otherbean;

/**
 * 
 * 用于绑定用户展示水表界面
 * 
 * @author Joker
 *
 */
public class WaterUserInfo {
	// 表卡数
	private String index;
	// 预存款
	private String rePay;
	// 表卡ID （卡号）
	private String cardId;
	// 卡号(户号)
	private String userNo;
	//开票名称
	private String faName;
	// 安装地址
	private String address;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getRePay() {
		return rePay;
	}
	public void setRePay(String rePay) {
		this.rePay = rePay;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getFaName() {
		return faName;
	}
	public void setFaName(String faName) {
		this.faName = faName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
