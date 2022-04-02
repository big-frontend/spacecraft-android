package com.jamesfchen.export;

import com.jamesfchen.ibc.Api;
import com.jamesfchen.ibc.cbpc.IExport;

@Api
public abstract class AccountService extends IExport {
    protected abstract boolean isLogin();
}
