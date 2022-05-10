package net.sourceforge.simcpux;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jamesfchen.account.R;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.JumpToOfflinePay;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class MainActivity extends Activity{
	
	private Button gotoBtn, regBtn, launchBtn, scanBtn, subscribeMsgBtn,subscribeMiniProgramMsgBtn;
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		checkPermission();
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);

    	regBtn = (Button) findViewById(R.id.reg_btn);
    	regBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    api.registerApp(Constants.APP_ID);
			}
		});
    	
        gotoBtn = (Button) findViewById(R.id.goto_send_btn);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        startActivity(new Intent(MainActivity.this, SendToWXActivity.class));
//		        finish();
			}
		});
        
        launchBtn = (Button) findViewById(R.id.launch_wx_btn);
        launchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "launch result = " + api.openWXApp(), Toast.LENGTH_LONG).show();
			}
		});
        
		subscribeMsgBtn = (Button) findViewById(R.id.goto_subscribe_message_btn);
		subscribeMsgBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SubscribeMessageActivity.class));
//				finish();
			}
		});

        subscribeMiniProgramMsgBtn = (Button) findViewById(R.id.goto_subscribe_mini_program_msg_btn);
        subscribeMiniProgramMsgBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubscribeMiniProgramMsgActivity.class));
			}
		});


		View jumpToOfflinePay = (Button) findViewById(R.id.jump_to_offline_pay);
		jumpToOfflinePay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                int wxSdkVersion = api.getWXAppSupportAPI();
                if (wxSdkVersion >= Build.OFFLINE_PAY_SDK_INT) {
                    api.sendReq(new JumpToOfflinePay.Req());
                }else {
                    Toast.makeText(MainActivity.this, "not supported", Toast.LENGTH_LONG).show();
                }
			}
		});
	}

	private void checkPermission() {
		int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					Constants.PERMISSIONS_REQUEST_STORAGE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case Constants.PERMISSIONS_REQUEST_STORAGE: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				} else {
					Toast.makeText(MainActivity.this,"Please give me storage permission!",Toast.LENGTH_LONG).show();
				}
				return;
			}
		}
	}

}