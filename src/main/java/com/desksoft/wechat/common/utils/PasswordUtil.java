package com.desksoft.wechat.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author LiuWenLong
 * @version 2013-4-7 上午9:25:09
 * @description Base64
 * @copyright 杭州迪科软件 保留所有权利
 */
public class PasswordUtil {

	/**
	 * 将日期增加一个周期值
	 * 
	 * @param start
	 * @param period
	 * @return
	 */
	public static Date getValidTime(Date start, int period) {

		Calendar canlandar = Calendar.getInstance();
		canlandar.setTime(start);

		canlandar.add(Calendar.DATE, period);

		return canlandar.getTime();
	}

	/**
	 * 获取标准长度(AgentConstant.PASSWORD_STANDARD_LENGTH)的随机密码串(未加密)
	 * 
	 * @return
	 * @author lenghao
	 */
	public static String getRandomOperPassword() {

		return RandomUtil.getRandomString(8);
	}

	/**
	 * 获取标准长度(AgentConstant.PASSWORD_STANDARD_LENGTH)的随机密码串(已加密)
	 * 
	 * @param randomKey
	 *            加密随机串
	 * @return
	 * @author lenghao
	 */
	public static String getRadomEncyptedOperPassword(String randomKey) {

		String password = RandomUtil.getRandomString(8);
		MDEncryption mDEncryption = new MDEncryption();
		byte[] encry = mDEncryption.encryptMsg(password + randomKey);

		return mDEncryption.byte2String(encry);
	}

	/**
	 * 获取加密后的密码
	 * 
	 * @param password
	 *            密码明文
	 * @param randomKey
	 *            随机Key
	 * @return
	 * @author lenghao
	 */
	public static String getEncyptedOperPassword(String password, String randomKey) {

		MDEncryption mDEncryption = new MDEncryption();
		byte[] encry = mDEncryption.encryptMsg(password + randomKey);

		return mDEncryption.byte2String(encry);
	}

	/**
	 * 获取标准长度(AgentConstant.RANDOMKEY_STANDARD_LENGTH)的随机串Key值
	 * 
	 * @return
	 * @author lenghao
	 */
	public static String getRandomOperRandomKey() {

		return RandomUtil.getRandomString(32);
	}

	/**
	 * 校验密码
	 * 
	 * @param operLogPwd
	 *            数据库中存储的加密后的原密码
	 * @param password
	 *            密码明文
	 * @param randomKey
	 *            随机Key
	 * @return true 密码正确
	 * @author lenghao
	 */
	public static Boolean isPasswordCorrect(String operLogPwd, String password, String randomKey) {

		MDEncryption mDEncryption = new MDEncryption();
		byte[] encry = mDEncryption.encryptMsg(password + randomKey);

		return MDEncryption.isEqual(mDEncryption.string2Byte(operLogPwd), encry);
	}

	public static void main(String[] args) {
		// 生成密码随机key
		String randomKey = PasswordUtil.getRandomOperPassword();
		System.out.println(randomKey);
		// 生成加密密码
		String password = PasswordUtil.getEncyptedOperPassword("admin", randomKey);
		System.out.println(password);
		// 根据加密密码和随机秘密key校验原密码
		System.out.println(PasswordUtil.isPasswordCorrect("62AEBFA144774D3B377FEEDB40D387C6", "admin", "p50WyuoN"));
	}
}