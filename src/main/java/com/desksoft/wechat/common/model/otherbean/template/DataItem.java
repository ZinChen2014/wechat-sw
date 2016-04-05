/**
 *  @author Joker
 *  编辑模板
 */
package com.desksoft.wechat.common.model.otherbean.template;

public class DataItem {
	
	private TempItem first;
	private TempItem product;
	private TempItem price;
	private TempItem time;
	
	//停水通知 keyword1 keyword2 keyword3
	private TempItem keyword1;
	private TempItem keyword2;
	private TempItem keyword3;
	
	//水费催缴提醒  keyword1 keyword2 keyword3 keyword4 keyword5
	private TempItem keyword4;
	private TempItem keyword5;
	
	//月度水费通知 
	private TempItem userName;
	private TempItem userNumber;
	private TempItem address;
	private TempItem month;
	private TempItem power;
	private TempItem pay;
	
	
	
	public TempItem getUserName() {
		return userName;
	}
	public void setUserName(TempItem userName) {
		this.userName = userName;
	}
	public TempItem getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(TempItem userNumber) {
		this.userNumber = userNumber;
	}
	public TempItem getAddress() {
		return address;
	}
	public void setAddress(TempItem address) {
		this.address = address;
	}
	public TempItem getMonth() {
		return month;
	}
	public void setMonth(TempItem month) {
		this.month = month;
	}
	public TempItem getPower() {
		return power;
	}
	public void setPower(TempItem power) {
		this.power = power;
	}
	public TempItem getPay() {
		return pay;
	}
	public void setPay(TempItem pay) {
		this.pay = pay;
	}
	//公用的
	private TempItem remark;
	public TempItem getFirst() {
		return first;
	}
	public void setFirst(TempItem first) {
		this.first = first;
	}
	public TempItem getProduct() {
		return product;
	}
	public void setProduct(TempItem product) {
		this.product = product;
	}
	public TempItem getPrice() {
		return price;
	}
	public void setPrice(TempItem price) {
		this.price = price;
	}
	public TempItem getTime() {
		return time;
	}
	public void setTime(TempItem time) {
		this.time = time;
	}
	public TempItem getRemark() {
		return remark;
	}
	public void setRemark(TempItem remark) {
		this.remark = remark;
	}
	public TempItem getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(TempItem keyword1) {
		this.keyword1 = keyword1;
	}
	public TempItem getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(TempItem keyword2) {
		this.keyword2 = keyword2;
	}
	public TempItem getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(TempItem keyword3) {
		this.keyword3 = keyword3;
	}
	public TempItem getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(TempItem keyword4) {
		this.keyword4 = keyword4;
	}
	public TempItem getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(TempItem keyword5) {
		this.keyword5 = keyword5;
	}
	
	
}
