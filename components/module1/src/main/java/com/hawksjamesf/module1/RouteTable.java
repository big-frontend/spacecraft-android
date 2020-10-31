package com.hawksjamesf.module1;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/31/2020  Sat
 */
class RouteTable {
    Map<String,Class<?>> table=new HashMap<String,Class<?>>(){
        {
            put("module1_page", PageActivity.class);
        }
    };
}
