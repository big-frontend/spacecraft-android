package com.cmic.sso.tokenValidate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ljx on 2018/1/2.
 */

public class PhoneValidateParameter {
    private HeaderBean header;
    private BodyBean body;
    //private String appid;
    //private String timestamp;



    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }


    public static class HeaderBean {
//        "msgId ": "61237890345",
//        "timestamp ": "20160628180001165",
//        "version ": "1.0",
//         "appId ": "0008"

        private String version; //version	必选	2	string	填1.0
        private String msgId; //msgid	必选	2	string	标识请求的随机数即可(1-36位)
        private String timestamp ;//systemtime	必选	2	string	请求消息发送的系统时间，精确到毫秒，共17位，格式：20121227180001165
        private String appid;//appid	必选	2	string	业务在统一认证申请的应用id

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getMsgId() {
            return msgId;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

    }

    public static class BodyBean {
        /**
         * token : 8484010001320200344E6A5A4551554D784F444E474E446C434E446779517A673340687474703A2F2F3139322E3136382E31322E3233363A393039302F0300040353EA68040006313030303030FF00203A020A143C6703D7D0530953C760744C7D61F5F7B546F12BC17D65254878748C
         */

        private String token;//token	必选	2	string	需要解析的凭证值。
        private String openType;
        private String requesterType;
        //private String message;
        //private String expandParams;
        private String phoneNum;
        private String sign;

        public void setRequesterType(String requesterType) {
            this.requesterType = requesterType;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRequesterType() {
            return requesterType;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public String getSign() {
            return sign;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOpenType() {
            return openType;
        }

        public void setOpenType(String openType) {
            this.openType = openType;
        }
    }


    public JSONObject toJson() {
        JSONObject mainJson = new JSONObject();
        JSONObject headerJson = new JSONObject();
        JSONObject bodyJson = new JSONObject();
        try {
            headerJson.put("version",header.getVersion());
            headerJson.put("msgId",header.getMsgId());
            headerJson.put("timestamp",header.getTimestamp());
            headerJson.put("appId",header.getAppid());
            mainJson.put("header",headerJson);

            bodyJson.put("token",body.getToken());
            bodyJson.put("requesterType",body.getRequesterType());
            bodyJson.put("phoneNum",body.getPhoneNum());
            bodyJson.put("openType",body.getOpenType());
            bodyJson.put("sign",body.getSign());
            mainJson.put("body",bodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainJson;
    }
}
