package com.cmic.sso.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 32byte keys compress 16byte
 * 
 * @author changlh
 * 
 */
public class MD5STo16Byte {
	/**
	 * 将16进制范围的字母或数字的字符转换成对应的整数， 0－9 a－f｜A－F则转换成10－15
	 * 
	 * @param ch
	 * @return
	 */
	private static char char2Int(char ch) {
		if (ch >= '0' && ch <= '9')
			return (char) (ch - '0');
		if (ch >= 'a' && ch <= 'f')
			return (char) (ch - 'a' + 10);
		if (ch >= 'A' && ch <= 'F')
			return (char) (ch - 'A' + 10);
		return ' ';
	}

	/**
	 * 将两个字符转换成一个字节表示
	 * 
	 * @param str
	 * @return
	 */
	private static byte str2Bin(char[] str) {
		byte chn;
		char[] tempWord = new char[2];

		tempWord[0] = char2Int(str[0]); // make the B to 11 -- 00001011
		tempWord[1] = char2Int(str[1]); // make the 0 to 0 -- 00000000

		chn = (byte) ((tempWord[0] << 4) | tempWord[1]); // to change the BO to
		// 10110000

		return chn;
	}

	/**
	 * 将32长度的字符数组压缩生成标准的16位字节数组的MD5
	 * 
	 * @param md5chs32len
	 *            32长度的MD5字符串的字符数组
	 * @return
	 */
	public static byte[] compress(char[] md5chs32len) {
		char[] tem = new char[2];
		byte[] sDst = new byte[md5chs32len.length / 2];
		int j = 0;
		for (int i = 0; i + 1 < md5chs32len.length; i += 2) {
			tem[0] = md5chs32len[i];
			tem[1] = md5chs32len[i + 1];
			sDst[j++] = (byte) (str2Bin(tem));
		}
		return sDst;
	}

	/**
	 * 将16字节的MD5数组转换成32长度的字符串
	 * 
	 * @param md5b16
	 * @return
	 */
	public static String unCompress(byte[] md5b16) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < md5b16.length; i++) {
			byte b = md5b16[i];
			sb.append(Integer.toHexString((b >> 4) & 0x0F));
			sb.append(Integer.toHexString(b & 0x0F));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 将明文用MD5算法加密后并压缩成16字节数组
	 * 
	 * @param text
	 *            明文
	 * @return 16字节数组
	 * @author sunyiping 2011-10-13 下午12:50:51
	 */
	public static byte[] encrypt2MD5toByte16(String text) {
		String md5 = getMD5Str32(text);
		if (md5 != null) {
			return compress(md5.toCharArray());
		}
		return null;
	}

	/**
	 * 获取MD5加密后的32位字符串
	 * 
	 * @param str
	 *            明文
	 * @return 返回MD5加密后的32位串
	 * @author sunyiping 2011-10-13 下午12:12:45
	 */
	public static String getMD5Str32(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		return byteToHexString(messageDigest.digest());
	}

	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}
}
