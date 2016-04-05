package com.desksoft.wechat.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import com.jfinal.kit.StrKit;

public class FileOperation {
	public static BufferedReader bufread;

	/**
	 * 创建文本文件.
	 * 
	 * @throws IOException
	 * 
	 */
	public static void creatTxtFile(File filename) throws IOException {
		if (!filename.exists()) {
			filename.createNewFile();
			System.err.println(filename + "已创建！");
		}
	}

	/**
	 * 读取文本文件.
	 * 
	 */
	public static String readTxtFile(File filename) {
		String read;
		FileReader fileread;
		String readStr = "";
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("文件内容是:" + "\r\n" + readStr);
		return readStr;
	}

	/**
	 * 写文件.
	 * @param newStr 新加入的文件
	 * @param readStr 原本有的文件
	 * @param filename
	 * @throws IOException
	 */
	public static void writeTxtFile(String newStr,String readStr,File filename) throws IOException {
		// 先读取原有文件内容，然后进行写入操作+ "\r\n"回车换行
		String filein = readStr + "\r\n" + newStr ;
		if(StrKit.isBlank(readStr)){
			filein = newStr;
		}	
		
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			mm.writeBytes(filein);
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e2) {
					// TODO 自动生成 catch 块
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将文件中指定内容的第一行替换为其它内容.
	 * 
	 * @param oldStr
	 *            查找内容
	 * @param replaceStr
	 *            替换内容
	 */
	public static void replaceTxtByStr(String oldStr, String replaceStr,File file) {
		String temp = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该行前面的内容
			for (int j = 1; (temp = br.readLine()) != null && !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// 将内容插入
			buf = buf.append(replaceStr);

			// 保存该行后面的内容
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * main方法测试
	 * 
	 * @param s
	 * @throws IOException
	 */
	public static void main(String[] s) throws IOException {
		 String path = "D:/suncity.txt";
		 File filename = new File(path);
		FileOperation.creatTxtFile(filename);
		String readStr = FileOperation.readTxtFile(filename);
		FileOperation.writeTxtFile("288888",readStr,filename);
		//FileOperation.replaceTxtByStr("", null,filename);
	}

}
