package com.jamesfchen;

import android.util.Log;

import com.jamesfchen.export.AccountService;

public class AccountServiceImp extends AccountService {

    @Override
    public boolean isLogin() {
        Log.d("cjf", "calling isLogin");
        return false;
    }

}
