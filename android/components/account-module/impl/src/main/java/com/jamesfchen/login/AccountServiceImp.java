package com.jamesfchen.login;

import android.util.Log;

import com.jamesfchen.export.AccountService;
import com.jamesfchen.ibc.Api;

@Api
public class AccountServiceImp extends AccountService {

    @Override
    public boolean isLogin() {
        Log.d("cjf", "calling isLogin");
        return false;
    }

}
