package com.cmic.sso.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cmic.sso.sdk.view.AuthThemeConfig;
import com.cmic.sso.sdk.view.BackPressedListener;
import com.cmic.sso.sdk.view.CheckBoxListener;
import com.cmic.sso.sdk.view.LoginClickListener;
import com.cmic.sso.sdk.view.LoginPageInListener;
import com.cmic.sso.sdk.auth.AuthnHelper;
import com.cmic.sso.sdk.auth.TokenListener;
import com.cmic.sso.tokenValidate.Request;
import com.cmic.sso.tokenValidate.RequestCallback;
import com.cmic.sso.util.Constant;
import com.cmic.sso.util.SpUtils;
import com.cmic.sso.util.StringFormat;
import com.jamesfchen.account.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener {

    protected String TAG = "MainActivity";
    private static final int RESULT = 0x111;
    protected static final int RESULT_OF_SIM_INFO = 0x222;
    private Context mContext;
    public String mResultString;
    private TextView phoneEt;
    private String mSDKVersion = null;
    private TokenListener mListener;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE = 1000;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN = 2000;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN = 3000;
    private static final int CMCC_SDK_REQUEST_GET_PHONE_INFO_CODE = 1111;
    private static final int CMCC_SDK_REQUEST_MOBILE_AUTH_CODE = 2222;
    private static final int CMCC_SDK_REQUEST_LOGIN_AUTH_CODE = 3333;
    private static final int CMCC_SDK_REQUEST_TOKEN_VALIDATE_CODE = 4444;
    private static final int CMCC_SDK_REQUEST_PHONE_VALIDATE_CODE = 5555;
    private String mAccessToken;
    private AuthnHelper mAuthnHelper;
    public ResultDialog mResultDialog;
    private String[] operatorArray = {"未知", "移动", "联通", "电信"};
    private String[] networkArray = {"未知", "数据流量", "纯WiFi", "流量+WiFi"};
    boolean isShowResult = true;
    AuthThemeConfig.Builder themeConfigBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //需在gradle文件中配置自己的appId、appKey、签名
        initSDK();
        init();
    }

    @Override
    protected void onDestroy() {
        mAuthnHelper.setAuthThemeConfig(null);
        mAuthnHelper.setPageInListener(null);
        mListener = null;
        super.onDestroy();
    }

    Dialog alertDialog;
    private void initSDK(){
        Log.e(TAG, System.currentTimeMillis() + " ");
        System.out.print(System.currentTimeMillis());
        AuthnHelper.setDebugMode(true);
        mAuthnHelper = AuthnHelper.getInstance(mContext.getApplicationContext());
        mAuthnHelper.setPageInListener(new LoginPageInListener() {
            @Override
            public void onLoginPageInComplete(String resultCode, JSONObject jsonObj) {
                if(resultCode.equals("200087")){
                    Log.d("initSDK","page in---------------");
                }
            }
        });
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        themeConfigBuilder = new AuthThemeConfig.Builder()
                .setStatusBar(0xff0086d0,false)//状态栏颜色、字体颜色
//                .setAuthContentView(getLayoutInflater().inflate(R.layout.empty_layout,relativeLayout,false))
                .setAuthLayoutResID(R.layout.empty_layout)

                .setClauseLayoutResID(R.layout.title_layout,"returnId")
                .setNavTextSize(20)
                .setNavTextColor(0xff0085d0)//导航栏字体颜色
                .setNavColor(Color.BLUE)

                .setNumberSize(20,true)////手机号码字体大小
                .setNumberColor(0xff333333)//手机号码字体颜色
                .setNumberOffsetX(30)//号码栏X偏移量
                .setNumFieldOffsetY_B(100)
                .setNumFieldOffsetY(100)//号码栏Y偏移量

//                .setLogBtnText("本机号码一键登录")//登录按钮文本
                .setLogBtnTextColor(0xffffffff)//登录按钮文本颜色
                .setLogBtnImgPath("umcsdk_login_btn_bg")//登录按钮背景
                .setLogBtnText(" ",0xffffffff,15,false)
                .setLogBtnOffsetY_B(200)//登录按钮Y偏移量
                .setLogBtnOffsetY(200)//登录按钮Y偏移量
//                .setLogBtn(500,30)
                .setLogBtnMargin(30,30)
                .setCheckTipText("")
                .setBackPressedListener(new BackPressedListener() {
                    @Override
                    public void onBackPressed() {
                        Toast.makeText(MainActivity.this,"返回键回调", Toast.LENGTH_SHORT).show();
                    }
                })
                .setLogBtnClickListener(new LoginClickListener() {
                    @Override
                    public void onLoginClickStart(Context context, JSONObject jsonObj) {
                        alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if(event.getAction() == KeyEvent.ACTION_UP){
                                    dialog.dismiss();
                                    alertDialog = null;
                                }
                                return keyCode == KeyEvent.KEYCODE_BACK;
                            }
                        });
                        alertDialog.show();
                        alertDialog.setContentView(LayoutInflater.from(mContext).inflate(R.layout.loading_alert,null));
                    }

                    @Override
                    public void onLoginClickComplete(Context context, JSONObject jsonObj) {
                        if(alertDialog != null && alertDialog.isShowing()){
                            alertDialog.dismiss();
                            alertDialog = null;
                        }
                    }
                })

                .setCheckBoxListener(new CheckBoxListener() {
                    @Override
                    public void onLoginClick(Context context, JSONObject jsonObj) {
                        Toast.makeText(context,"自定义勾选文本", Toast.LENGTH_LONG).show();
                    }
                })

                .setCheckedImgPath("umcsdk_check_image")//checkbox被勾选图片
                .setUncheckedImgPath("umcsdk_uncheck_image")//checkbox未被勾选图片
                .setCheckBoxImgPath("umcsdk_check_image","umcsdk_uncheck_image",9,9)
                .setPrivacyState(false)//授权页check

                .setPrivacyAlignment("登录即同意" + AuthThemeConfig.PLACEHOLDER + "应用自定义服务条款一、应用自定义服务条款二、条款3和条款4并使用本机号码校验","应用自定义服务条款一","https://www.baidu.com","应用自定义服务条款二","https://www.hao123.com",
                        "条款3","http://www.sina.com","条款4","http://gz.58.com")
                .setPrivacyText(10,0xff666666,0xff0085d0,false, true)
                .setClauseColor(0xff666666,0xff0085d0)//条款颜色

                .setPrivacyMargin(20,30)
                .setPrivacyOffsetY(30)//隐私条款Y偏移量
                .setPrivacyOffsetY_B(50)//隐私条款Y偏移量
                .setCheckBoxLocation(1)
                .setAppLanguageType(0)
                .setBackButton(true)
                .setPrivacyAnimation("umcsdk_anim_shake")
                .setPrivacyBookSymbol(true);

//                .setAuthPageActIn("in_activity","out_activity")
//                .setAuthPageActOut("in_activity","out_activity")

//                .setAuthPageWindowMode(300, 300)
//                .setAuthPageWindowOffset(0,0)
//                .setThemeId(R.style.loginDialog);
//                .setWindowBottom(1)

        mAuthnHelper.setAuthThemeConfig(themeConfigBuilder.build());
        Log.e(TAG, System.currentTimeMillis() + " ");
        mSDKVersion= AuthnHelper.SDK_VERSION;
    }

    private void init() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.wap_login1).setOnClickListener(this);
        findViewById(R.id.get_user_info).setOnClickListener(this);
        findViewById(R.id.pre_getphone).setOnClickListener(this);
        findViewById(R.id.validate_phone_bt).setOnClickListener(this);
        findViewById(R.id.network_btn).setOnClickListener(this);
        findViewById(R.id.wap_login_display).setOnClickListener(this);
        findViewById(R.id.wap_login_display_window).setOnClickListener(this);
        findViewById(R.id.del_scrip).setOnClickListener(this);
        TextView mVersionText = findViewById(R.id.text_version);
        mVersionText.setText(mSDKVersion);
        phoneEt = findViewById(R.id.phone_et);
        mResultDialog = new ResultDialog(mContext);
        mListener = new TokenListener() {
            @Override
            public void onGetTokenComplete(int SDKRequestCode, JSONObject jObj) {
                if (jObj != null) {
                    try {
                        if(isShowResult){
                            //时间
                            long phoneTimes = SpUtils.getLong("phoneTimes");
                            jObj.put("phoneTimes", System.currentTimeMillis() -  phoneTimes + "ms");
                            mResultString = jObj.toString();
                        }
                        if (jObj.has("token")) {
                            mAccessToken = jObj.optString("token");
                            HashMap<String, String> map = new HashMap<>(2);
                            map.put("token", mAccessToken);
//                            MobclickAgent.onEvent(MainActivity.this, "user_token", map);
                            mAuthnHelper.quitAuthActivity();
                            mHandler.sendEmptyMessageDelayed(RESULT, 200);
                            isShowResult = false;
                        } else {
                            mHandler.sendEmptyMessage(RESULT);
                        }
//                        MobclickAgent.onEvent(MainActivity.this, "getResult", jObj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        isShowResult = true;
        String overTime = ((EditText)findViewById(R.id.over_time)).getText().toString();
        if(!TextUtils.isEmpty(overTime)){
            mAuthnHelper.setOverTime(Integer.valueOf(overTime));
        }else {
            mAuthnHelper.setOverTime(8000);
        }
        int id = v.getId();
        if (id == R.id.get_user_info) {
            getUserInfo(Constant.APP_ID, Constant.APP_SECRET);
        } else if (id == R.id.pre_getphone) {
            PGWGetMobile();
        } else if (id == R.id.wap_login1) {
            implicitLogin();
        } else if (id == R.id.validate_phone_bt) {
            phoneValidate();
        } else if (id == R.id.network_btn) {
            getNetAndOperator();
        } else if (id == R.id.wap_login_display) {
            themeConfigBuilder.setAuthPageWindowMode(0, 0)
                    .setThemeId(-1);
            mAuthnHelper.setAuthThemeConfig(themeConfigBuilder.build());
            displayLogin();
        } else if (id == R.id.wap_login_display_window) {
            themeConfigBuilder.setAuthPageWindowMode(300, 300)
                    .setNumFieldOffsetY(50)
                    .setLogBtnOffsetY(120)
//                        .setWindowBottom(1)
//                        .setAuthPageWindowOffset(0,0)
                    .setThemeId(R.style.loginDialog);
            mAuthnHelper.setAuthThemeConfig(themeConfigBuilder.build());
            displayLogin();
        } else if (id == R.id.del_scrip) {
            deleteScrip();
        }
    }

    private void deleteScrip() {
        mAuthnHelper.delScrip();
        Toast.makeText(this,"清除scrip成功", Toast.LENGTH_LONG).show();
    }

    private void displayLogin() {
        SpUtils.putLong("getPrePhoneTimes", 0);
        SpUtils.putLong("phoneTimes", System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN);
            }else {
                mAuthnHelper.loginAuth(Constant.APP_ID, Constant.APP_KEY , mListener, CMCC_SDK_REQUEST_LOGIN_AUTH_CODE);
            }
        }else {
            mAuthnHelper.loginAuth(Constant.APP_ID, Constant.APP_KEY , mListener, CMCC_SDK_REQUEST_LOGIN_AUTH_CODE);
        }
    }

    private void getUserInfo(String appid, String appSecret) {
        tokenValidate(appid, appSecret, mAccessToken, mListener);
    }

    public void tokenValidate(final String appId, final String appKey, final String token, final TokenListener listener) {
        Bundle values = new Bundle();
        values.putString("appSecret", appKey);
        values.putString("appId", appId);
        values.putString("token", token);
        SpUtils.putLong("phoneTimes", System.currentTimeMillis());
        Request.getInstance(mContext).tokenValidate(values, new RequestCallback() {
            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonObj) {
                Log.i("Token校验结果：", jsonObj.toString());
                listener.onGetTokenComplete(CMCC_SDK_REQUEST_TOKEN_VALIDATE_CODE, jsonObj);
                String phone = jsonObj.optString("msisdn");
                HashMap<String, String> map = new HashMap<>(2);
                map.put("token", mAccessToken);
                map.put("msisdn", phone);
//                MobclickAgent.onEvent(MainActivity.this, "user_phone", map);
//                MobclickAgent.onEvent(MainActivity.this, "tokenValidateResult", jsonObj.toString());
            }
        });
    }

    /**
     * 需要权限：READ_PHONE_STATE， ACCESS_NETWORK_STATE
     * operatorType获取网络运营商: 0.未知 1.移动流量 2.联通流量网络 3.电信流量网络
     * networkType 网络状态：0未知；1流量 2 wifi；3 数据流量+wifi
     */
    private void getNetAndOperator(){
        JSONObject jsonObject = mAuthnHelper.getNetworkType(mContext);
        int operator,net;
        try {
            operator = Integer.parseInt(jsonObject.getString("operatortype"));
            net = Integer.parseInt(jsonObject.getString("networktype"));
            jsonObject.put("operatorType",operatorArray[operator]);
            jsonObject.put("networkType",networkArray[net]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mResultString = jsonObject.toString();
        mResultDialog.setResult(StringFormat.logcatFormat(mResultString));
    }

    private void phoneValidate(){
        phoneValidate(Constant.APP_ID, Constant.APP_KEY, mAccessToken, mListener);
    }

    public void phoneValidate(final String appId, final String appKey, final String token, final TokenListener listener) {
        Bundle values = new Bundle();
        values.putString("appKey", appKey);
        values.putString("appId", appId);
        values.putString("token", token);
        values.putString("phone", phoneEt.getText().toString());
        SpUtils.putLong("phoneTimes", System.currentTimeMillis());
        Request.getInstance(mContext).phoneValidate(values, new RequestCallback() {
            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonObj) {
                Log.i("Token校验结果：", jsonObj.toString());
                listener.onGetTokenComplete(CMCC_SDK_REQUEST_PHONE_VALIDATE_CODE, jsonObj);
            }
        });
    }

    private void PGWGetMobile(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE);
            }else {
                SpUtils.putLong("phoneTimes", System.currentTimeMillis());
                mAuthnHelper.getPhoneInfo(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_GET_PHONE_INFO_CODE);
            }
        }else {
            SpUtils.putLong("phoneTimes", System.currentTimeMillis());
            mAuthnHelper.getPhoneInfo(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_GET_PHONE_INFO_CODE);
        }
    }

    private void implicitLogin() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN);
            }else {
                SpUtils.putLong("phoneTimes", System.currentTimeMillis());
                mAuthnHelper.mobileAuth(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_MOBILE_AUTH_CODE);
            }
        }else {
            SpUtils.putLong("phoneTimes", System.currentTimeMillis());
            mAuthnHelper.mobileAuth(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_MOBILE_AUTH_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SpUtils.putLong("phoneTimes", System.currentTimeMillis());
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE:
                mAuthnHelper.getPhoneInfo(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_GET_PHONE_INFO_CODE);
                break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN:
                mAuthnHelper.mobileAuth(Constant.APP_ID, Constant.APP_KEY, mListener, CMCC_SDK_REQUEST_MOBILE_AUTH_CODE);
                break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN:
                mAuthnHelper.loginAuth(Constant.APP_ID, Constant.APP_KEY , mListener, CMCC_SDK_REQUEST_LOGIN_AUTH_CODE);
                break;
            default:
                break;
        }
    }

    Handler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> referenceActivity;
        private MyHandler(MainActivity activity){
            referenceActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = referenceActivity.get();
            if (mainActivity != null && !mainActivity.isFinishing()) {
                switch (msg.what) {
                    case RESULT:
                        referenceActivity.get().mResultDialog.setResult(StringFormat
                                .logcatFormat(referenceActivity.get().mResultString));
                        break;
                    case RESULT_OF_SIM_INFO:
                        referenceActivity.get().mResultDialog.setResult(referenceActivity.get().mResultString);
                    default:
                        break;
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }


}
