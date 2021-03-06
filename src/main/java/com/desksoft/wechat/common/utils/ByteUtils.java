package com.desksoft.wechat.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class ByteUtils {

	private final static int BUFFER_SIZE = 4096;

	public static byte[] deCodeBase64(String objStr) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bin = decoder.decodeBuffer(objStr);
		return bin;
	}

	public static byte[] getBytes(String filePath) throws Exception {
		byte[] buffer = null;
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
		byte[] b = new byte[1000];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		fis.close();
		bos.close();
		buffer = bos.toByteArray();
		return buffer;
	}

	public static byte[] inputStreamToByte(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}
		data = null;
		return outStream.toByteArray();
	}
}
