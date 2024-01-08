package com.cmic.sso.tokenValidate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cmic.sso.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by haoxin on 2017/5/31.
 */

public class Request {
    private static final String TAG = "Request";
    private Context mContext;
    private static Request mInstance = null;

    protected Request() {
    }

    protected Request(Context context) {
        mContext = context;
    }

    /**
     * 使用单例模式
     */
    public static Request getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Request.class) {
                if (mInstance == null) {
                    mInstance = new Request(context);
                }
            }
        }
        return mInstance;
    }

    //校验Token
    public void tokenValidate(Bundle values, RequestCallback callback) {
//            private String strictcheck;//strictcheck	必选	2	string	验证源ip合法性，填写”1”，统一认证会校验sourceid与出口ip对应关系（申请sourceid时需提供业务出口ip，可以多个IP）
//            private String version; //version	必选	2	string	填1.0
//            private String msgid; //msgid	必选	2	string	标识请求的随机数即可(1-36位)
//            private String systemtime;//systemtime	必选	2	string	请求消息发送的系统时间，精确到毫秒，共17位，格式：20121227180001165
//            private String appid;//appid	必选	2	string	业务在统一认证申请的应用id
//            private String apptype;//apptype	必选	2	string	参见附录“渠道编码定义”1|BOSS 2|web 3|wap 4|pc客户端 5|手机客户端
//            private String sourceid;//sourceid	可选	2	string	业务集成统一认证的标识，需提前申请，申请指南见附录一
//            private String ssotosourceid;//ssotosourceid	可选	2	string	单点登录时使用，填写被登录业务的sourceid
//            private String expandparams;//expandparams	扩展参数	2	Map	map(key,value)

        TokenValidateParameter params = new TokenValidateParameter();
        params.setStrictcheck("0");
        params.setVersion("2.0");
        params.setMsgid(generateNonce32());
        params.setSystemtime(getCurrentTime());
        params.setAppid(values.getString("appId"));
        params.setToken(values.getString(Constant.KEY_TOKEN));
        params.setSign(params.generateSign(Constant.APP_SECRET));
        String url = Constant.HTTP_BASE_URL;
        doCommonRequest(url, params, callback);
    }
    public static String generateNonce32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    public static String getCurrentTime() {
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sf.format(data);
    }

    //本机号码校验
    public void phoneValidate(Bundle values, RequestCallback callback) {
//            private String strictcheck;//strictcheck	必选	2	string	验证源ip合法性，填写”1”，统一认证会校验sourceid与出口ip对应关系（申请sourceid时需提供业务出口ip，可以多个IP）
//            private String version; //version	必选	2	string	填1.0
//            private String msgid; //msgid	必选	2	string	标识请求的随机数即可(1-36位)
//            private String systemtime;//systemtime	必选	2	string	请求消息发送的系统时间，精确到毫秒，共17位，格式：20121227180001165
//            private String appid;//appid	必选	2	string	业务在统一认证申请的应用id
//            private String apptype;//apptype	必选	2	string	参见附录“渠道编码定义”1|BOSS 2|web 3|wap 4|pc客户端 5|手机客户端
//            private String sourceid;//sourceid	可选	2	string	业务集成统一认证的标识，需提前申请，申请指南见附录一
//            private String ssotosourceid;//ssotosourceid	可选	2	string	单点登录时使用，填写被登录业务的sourceid
//            private String expandparams;//expandparams	扩展参数	2	Map	map(key,value)

        PhoneValidateParameter params = new PhoneValidateParameter();
        PhoneValidateParameter.BodyBean bodyBean = new PhoneValidateParameter.BodyBean();
        PhoneValidateParameter.HeaderBean headerBean = new PhoneValidateParameter.HeaderBean();
        //version
        headerBean.setVersion("1.0");
        //msgid
        String msgId = generateNonce32();
        headerBean.setMsgId(msgId);
        //timeStamp
        String timeStamp = getCurrentTime();
        headerBean.setTimestamp(timeStamp);
        //appid
        headerBean.setAppid(Constant.APP_ID);

        bodyBean.setRequesterType("0");
        bodyBean.setOpenType("1");

        String phoneNumMg = values.getString("phone") + Constant.APP_KEY + timeStamp;
        String phoneNum = SHA(phoneNumMg, "SHA-256");
        String upPhoneNum = exChange(phoneNum);
        bodyBean.setPhoneNum(upPhoneNum);

        bodyBean.setToken(values.getString(Constant.KEY_TOKEN));

        String signMg = Constant.APP_ID + msgId + upPhoneNum + timeStamp + values.getString(Constant.KEY_TOKEN) + Constant.VERSION;
        String sign = sha256_HMAC(signMg, Constant.APP_KEY);
        bodyBean.setSign(sign);

        params.setBody(bodyBean);
        params.setHeader(headerBean);
        String url = Constant.HTTP_VALIDATE_TOKEN_URL;
        doCommonRequest(url, params, callback);
    }

    public static String exChange(String str){
        StringBuffer sb = new StringBuffer();
        if(str!=null){
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
                if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }else{
                    sb.append((c));
                }
            }
        }

        return sb.toString();
    }

    private String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byte2hex(bytes);
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String byte2hex(byte[] b)
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    //对外请求接口 校验token
    public  void doCommonRequest(final String url, TokenValidateParameter params, final RequestCallback callback) {
        Log.e(TAG, "request https url : " + url + ">>>>>>> PARAMS : " + params.toJson().toString());
        new HttpUtils().requestHttp(url, params.toJson().toString(),  new HttpUtils.Response() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "request success , url : " + url + ">>>>result : " + result);
                try {
                    JSONObject json = new JSONObject(result);



                    callback.onRequestComplete(json.optString("resultCode"), json.optString("desc"), json);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError("102223", "数据解析异常");
                }

            }

            @Override
            public void onError(String errorCode, String msg) {

                JSONObject object = new JSONObject();
                try {
                    object.put("resultCode", errorCode);
                    object.put("desc", msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "request failed , url : " + url
                        + ">>>>>errorMsg : " + object.toString());
                if (callback != null) {
                    callback.onRequestComplete(errorCode, msg, object);
                }
            }
        });
    }

    //对外请求接口 本机号码校验
    public  void doCommonRequest(final String url, PhoneValidateParameter params, final RequestCallback callback) {
        Log.e(TAG, "request https url : " + url + ">>>>>>> PARAMS : " + params.toJson().toString());
        new HttpUtils().requestHttp(url, params.toJson().toString(),  new HttpUtils.Response() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "request success , url : " + url + ">>>>result : " + result);
                try {
                    JSONObject json = new JSONObject(result);



                    callback.onRequestComplete(json.optString("resultCode"), json.optString("desc"), json);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError("102223", "数据解析异常");
                }

            }

            @Override
            public void onError(String errorCode, String msg) {

                JSONObject object = new JSONObject();
                try {
                    object.put("resultCode", errorCode);
                    object.put("desc", msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "request failed , url : " + url
                        + ">>>>>errorMsg : " + object.toString());
                if (callback != null) {
                    callback.onRequestComplete(errorCode, msg, object);
                }
            }
        });
    }


}
