package com.cmic.sso.tokenValidate;



import com.cmic.sso.util.Constant;
import com.cmic.sso.util.MD5STo16Byte;
import com.cmic.sso.util.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by haoxin on 2017/4/24.
 */

public class TokenValidateParameter {


    /**
     * header : {"strictcheck":"0","version":"1.0","msgid":"40a940a940a940a93b8d3b8d3b8d3b8d","systemtime":"20170515090923489","appid":"10000001","apptype":"5","sourceid":"sourceid","ssotosourceid":"ssotosourceid","expandparams":"expandparams"}
     * body : {"token":"8484010001320200344E6A5A4551554D784F444E474E446C434E446779517A673340687474703A2F2F3139322E3136382E31322E3233363A393039302F0300040353EA68040006313030303030FF00203A020A143C6703D7D0530953C760744C7D61F5F7B546F12BC17D65254878748C"}
     */

    private String version; //version	必选	2	string	填1.0
    private String msgid; //msgid	必选	2	string	标识请求的随机数即可(1-36位)
    private String systemtime;//systemtime	必选	2	string	请求消息发送的系统时间，精确到毫秒，共17位，格式：20121227180001165
    private String strictcheck;//strictcheck	必选	2	string	验证源ip合法性，填写”1”，统一认证会校验sourceid与出口ip对应关系（申请sourceid时需提供业务出口ip，可以多个IP）
    private String appid;
    private String expandparams;//expandparams	扩展参数	2	Map	map(key,value)
    private String sign;
    private String token;
    private String encryptionalgorithm; // 当有加密需要时要填写加密算法目前只支持RSA算法加密（需要填写大写RSA，非大写RSA以及空等走普通MD5签名校验）

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public void setSystemtime(String systemtime) {
        this.systemtime = systemtime;
    }

    public void setStrictcheck(String strictcheck) {
        this.strictcheck = strictcheck;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setExpandparams(String expandparams) {
        this.expandparams = expandparams;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {

        return version;
    }

    public String getMsgid() {
        return msgid;
    }

    public String getSystemtime() {
        return systemtime;
    }

    public String getStrictcheck() {
        return strictcheck;
    }

    public String getAppid() {
        return appid;
    }

    public String getExpandparams() {
        return expandparams;
    }

    public String getSign() {
        return sign;
    }

    public String getToken() {
        return token;
    }

    public String getEncryptionalgorithm() {
        return encryptionalgorithm;
    }

    public void setEncryptionalgorithm(String encryptionalgorithm) {
        this.encryptionalgorithm = encryptionalgorithm;
    }

    public JSONObject toJson() {
        JSONObject mainJson = new JSONObject();
        try {
            mainJson.put("strictcheck",strictcheck);
            mainJson.put("version",version);
            mainJson.put("msgid",msgid);
            mainJson.put("systemtime",systemtime);
            mainJson.put("appid",appid);
            mainJson.put("token",token);
            mainJson.put("sign",sign);
            mainJson.put("encryptionalgorithm",encryptionalgorithm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainJson;
    }

    public String generateSign(String appkey) {
        String str = appid + version + msgid + systemtime + strictcheck + token + appkey;
        return MD5STo16Byte.getMD5Str32(str);
    }

    public String generateSignByRSA() {
        encryptionalgorithm = "RSA";
        String str = appid + token;
        return RSAUtils.generalSign(str, Constant.PRIVATE_KEY);
    }

}
