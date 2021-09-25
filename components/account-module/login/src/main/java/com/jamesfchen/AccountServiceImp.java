package com.jamesfchen;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jamesfchen.export.AccountService;

@Route(path = "/account/exportService")
public class AccountServiceImp implements AccountService {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public boolean isLogin() {
        Log.d("cjf", "calling isLogin");
        return false;
    }

}
