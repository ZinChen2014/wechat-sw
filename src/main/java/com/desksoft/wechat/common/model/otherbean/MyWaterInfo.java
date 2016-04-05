package com.desksoft.wechat.common.model.otherbean;

import java.util.List;

/**
 * 我的用水情况
 * 
 * @author Joker
 *
 */
public class MyWaterInfo {
	
	
	//户号
	private String userNo;
	
	//卡号
	private String cardNo;

	//户名
	private String userName;

	//预存款
	private double rePay;
	
	//地址
	private String address;

	//户号
	private List<MonthWaterInfo> waterList;



	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getRePay() {
		return rePay;
	}

	public void setRePay(double rePay) {
		this.rePay = rePay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<MonthWaterInfo> getWaterList() {
		return waterList;
	}

	public void setWaterList(List<MonthWaterInfo> waterList) {
		this.waterList = waterList;
	}
	
}
