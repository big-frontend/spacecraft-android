package com.hawksjamesf.bundle2.route;

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
@Router(name = "bundle2router")
public class Bundle2Router implements INativeRouter {
    @Override
    public boolean go(@NonNull Context cxt, @Nullable String page,@Nullable Bundle bundle){
        if ("sayhi".equalsIgnoreCase(page)){
            Intent intent = new Intent(cxt, SayHiActivity.class);
            cxt.startActivity(intent);
            return true;
        }
        return false;
    }
}
