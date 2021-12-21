package com.jamesfchen.bundle1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jamesfchen.ibc.Router;
import com.jamesfchen.ibc.router.INativeRouter;

/**
 * Copyright ® $ 2021
 * All right reserved.
 * <p>
 * author jamesfchen
 *
 * @email hawksjamesf@gmail.com
 * since 7月/08/2021  周四
 */
@Router(name = "bundle1router")
public class Bundle1Router implements INativeRouter {
    @Override
    public boolean go(@NonNull Context cxt, @Nullable String page,@Nullable Bundle bundle){
        if ("sayme".equalsIgnoreCase(page)){
            Intent intent = new Intent(cxt, SayMeActivity.class);
            cxt.startActivity(intent);
            return true;
        }
        return false;
    }
}
