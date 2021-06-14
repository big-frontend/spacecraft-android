package com.jamesfchen.bundle1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StarterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("cjf","StarterActivity");
        startActivity(new Intent(this, HotelMainActivity.class));
    }
}