package com.cmic.sso.util;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author JeremyTang
 * @version 1.2.0
 * @title 字符串格式化
 * @copyright Copyright (c) 2016
 * @company
 * @date ${date} ${tags}
 */
public class StringFormat {

	private static final int ROW_LENGTH = 58;

	public static String logcatFormat(String resource) {
		if (resource.contains(",") || (resource.contains(";"))) {
			return jsonFormat(resource);
		}
		return strngFormat(resource);
	}

	/**
	 * 类json文件格式转换
	 * 
	 * @param resource
	 * @return
	 */
	private static String jsonFormat(String resource) {
		resource = resource.replace("\n", "").substring(1,
				resource.length() - 1);
		String[] strArray = null;
		if (resource.contains(",")) {
			strArray = resource.split(",");
		} else if (resource.contains(";")) {
			strArray = resource.split(";");
		}
		StringBuffer sb = new StringBuffer();
		if (strArray != null) {
			for (String s : strArray) {
				String[] values = s.split(":");
				try {
					if(values.length == 3){
//						sb.append("【" + values[0].substring(1, values[0].length() - 1)
//								+ "】："
//								+ "\n");
						sb.append("【" + values[1].substring(1, values[1].length() - 1)
								+ "】：" + values[2].substring(1, values[2].length() - 1)
								+ "\n");
					}else{
						sb.append("【" + values[0].substring(1, values[0].length() - 1)
								+ "】：" + values[1].substring(1, values[1].length() - 1)
								+ "\n");
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("字符串格式异常");
				}

			}
		}
		return sb.toString();
	}

	/**
	 * 生成回调结果的json对象
	 *
	 * @return JSONObject对象
	 */
	public static JSONObject getLoginResult(String resultCode, String resultDes) {
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put("resultCode", resultCode);
			jsonobj.put("authType", "");
			jsonobj.put("authTypeDes", "");
			jsonobj.put("resultDesc", resultDes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	/**
	 * String 字符串切换
	 * 
	 * @param resource
	 * @return
	 */
	private static String strngFormat(String resource) {
		resource = resource.replace("\n", "").trim();
		StringBuffer sb = new StringBuffer();
		int rowNumber = 0;
		while (true) {
			int start = rowNumber * ROW_LENGTH;
			int end = (rowNumber + 1) * ROW_LENGTH;
			if (end >= resource.length()) {
				sb.append(resource.substring(start, resource.length()));
				break;
			} else {
				sb.append(resource.substring(start, end) + "\n");
			}
			rowNumber++;
		}
		return sb.toString();
	}

}
