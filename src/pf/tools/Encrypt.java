package pf.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import sun.io.MalformedInputException;

public class Encrypt {
	private String encoding = "GB2312";
	
	/**
	 * 加密文件
	 * @param inFileName 需要加密的文件
	 * @param outputFileName 加密后的文件
	 * @param password 加密密码
	 * @throws java.io.IOException
	 */
	public void encryptFile(String inFileName, String outputFileName,
			String password) throws IOException {

		File TxtFile = new File(inFileName);
		getEncode(TxtFile);
		// 根据文件的编码读入文件。
		System.out.println("文件编码格式为：" + encoding);
		InputStream inFile = new FileInputStream(TxtFile);
		byte[] buffer = new byte[65536];
		int ch = 0;
		FileOutputStream newFile = new FileOutputStream(
				new File(outputFileName), false);
		while ((ch = inFile.read(buffer)) != -1) {
			newFile.write(encryptByte(buffer, password.getBytes(), ch), 0, ch);
		}
		inFile.close();
		newFile.close();
	}

	/**
	 * 解密文件
	 * @param sourceName 需要解密的文件
	 * @param outputFileName 解密后的文件
	 * @param password 加密密码
	 * @throws java.io.IOException
	 */
	public void decryptFile(String sourceName, String outputFileName,
			String password) throws IOException {
		encryptFile(sourceName, outputFileName, password);
	}

	private String getEncode(File TxtFile) {
		try {
			SinoDetect sd = new SinoDetect();
			int encode = sd.detectEncoding(TxtFile);
			switch (encode) {
			case 0:
				encoding = "GB2312";
				break;
			case 1:
				encoding = "GBK";
				break;
			case 2:
				encoding = "HZ";
				break;
			case 3:
				encoding = "BIG5";
				break;
			case 4:
				encoding = "EUC_TW";
				break;
			case 5:
				encoding = "ISO_2022_CN";
				break;
			case 6:
				encoding = "UTF-8";
				break;
			case 7:
				encoding = "UNICODE";
				break;
			case 8:
				encoding = "ASCII";
				break;
			case 9:
				encoding = "OTHER";
				break;
			}
		} catch (Exception e) {

		}
		return encoding;
	}

	/**
	 * 将输入的字符串和密码进行异或加密。并根据字符串的编码方式进返回
	 * 
	 * @param sIn
	 * @param sKey
	 * @param encode
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	// private static String encryptString(byte[] sIn,byte[] sKey,String encode)
	// throws UnsupportedEncodingException{
	// int out ;
	// byte[] byO = new byte[sIn.length];
	//		
	// for(int i = 0;i<sIn.length;i++){
	// if(i<sKey.length){
	// out = (sIn[i]^sKey[i]);
	// byO[i] = (byte)out;
	// }else{
	// out = sIn[i]^sKey[sKey.length-1];
	// byO[i] = (byte)out;
	// }
	// }
	//		
	// if(encode==null||encode.equals("")){
	// encode = "ascii";
	// }
	// String pp = new String(byO,encode);
	// return pp;
	// }
	//	
	/**
	 * 将sIn进行加密
	 * 
	 * @param sIn
	 * @param sKey
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws sun.io.MalformedInputException
	 */
	private byte[] encryptByte(byte[] sIn, byte[] sKey, int ch)
			throws UnsupportedEncodingException, MalformedInputException {

		int out;
		byte[] byO = new byte[ch];
		for (int i = 0; i < ch; i++) {
			// 对换行符进行判断

			if (i < sKey.length) {
				out = (sIn[i] ^ sKey[i]);
				byO[i] = (byte) out;
			} else {
				out = sIn[i] ^ sKey[sKey.length - 1];
				byO[i] = (byte) out;
			}
		}
		return byO;

	}

	/**
	 * 第一个参数是 输入文件的名字 第二个参数是 要生成文件的名字 第三个参数是 是否是加密，如果没有第三个参数。那么就默认为加密
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Encrypt et = new Encrypt();
		if (args == null) {
			System.out.println("参数为空。请重新输入参数！");
			return;
		}
		String inputFileName = "";
		String outputFileName = "";
		;
		/*
		 * if (args[0]==null){ System.out.println("没有需要压缩的文件。请重新输入参数！"); return; }
		 * if (args[1]==null){ System.out.println("没有需要输出的文件。请重新输入参数！"); return; }
		 * if (args[2]==null){ isEncrypt = "1"; }
		 */
		inputFileName = "c:\\test_resource.txt";//
		outputFileName = "c:\\test_encrypt.txt";
		System.out.println("输入文件名为：" + inputFileName);
		System.out.println("输出文件名为：" + outputFileName);
		try {

			et.encryptFile(inputFileName,outputFileName,"jstrd");

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end all");

	}
}
