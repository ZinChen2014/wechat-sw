package com.desksoft.wechat.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author LiuWenLong
 * @version 2013-4-7 上午9:26:48
 * @description TODO
 * @copyright 杭州迪科软件 保留所有权利
 */
public class RandomUtil {
	/**
	 * 权重选择算法
	 *
	 * @param slavesMap
	 *            slaveName-key，weight-value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String weightRandom(Map<String, Integer> slavesMap) throws Exception {
		if (slavesMap == null || slavesMap.size() <= 0) {
			throw new Exception("权重随机算法：指定参数错误");
		}

		String[] slaves = new String[slavesMap.size()];
		int[] weights = new int[slavesMap.size()];

		Iterator it = slavesMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry) it.next();
			slaves[i] = entry.getKey();
			weights[i] = entry.getValue();
			i++;
		}

		return weightRandom(slaves, weights);
	}

	/**
	 * 权重选择算法
	 *
	 * @param slaves
	 * @param weights
	 * @return
	 */
	public static String weightRandom(String[] slaves, int[] weights) throws Exception {
		if (slaves == null || weights == null || slaves.length <= 0 || weights.length <= 0
				|| slaves.length != weights.length) {
			throw new Exception("权重随机算法：指定参数错误");
		}
		String ret = null;

		int[][] sections = new int[slaves.length][2];

		// 初始化
		int totalWeight = 0;
		for (int i = 0; i < slaves.length; i++) {
			sections[i][0] = totalWeight + 1;
			totalWeight += weights[i];
			sections[i][1] = totalWeight;
		}

		// 随机，并判断区间
		int random = RandomUtils.nextInt(totalWeight) + 1;
		for (int i = 0; i < slaves.length; i++) {
			if (random >= sections[i][0] && random <= sections[i][1]) {
				ret = slaves[i];
				break;
			}
		}

		return ret;
	}

	private static String[] strChars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
			"d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
			"z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"W", "X", "Y", "Z" };
	
	private static String[] strNumChars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	
	/**
	 * 产生规定长度的随机字符串
	 *
	 * @param strLength
	 *            需要的随机字符串的长度
	 * @param strLength
	 *            产生随机数的种子
	 * @return
	 * @author jianfeng.si
	 */
	public static String getRandomString(int strLength) {
		StringBuffer resString = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < strLength; i++) {
			resString.append(strChars[rnd.nextInt(59)]);
		}

		return resString.toString();
	}
	
	/**
	 * 产生规定长度的随机字符串 (只包含數字)
	 * @param strLength
	 *            需要的随机字符串的长度
	 */
	public static String getNumRandomString(int strLength) {
		StringBuffer resString = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < strLength; i++) {
			resString.append(strNumChars[rnd.nextInt(10)]);
		}
		return resString.toString();
	}
	
	/**
	 * 字符串解密
	 */
	public static String decodeStr(String decodeStr) {
		return decodeStr.substring(8);
	}
	/**
	 * 字符串加密
	 */
	public static String encodeStr(String encodeStr) {
		String randomKey = RandomUtil.getRandomString(8);
		return StringUtil.join(randomKey,encodeStr);
	}
}
