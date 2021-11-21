package com.jamesfchen.ibc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author jamesfchen
 * since Aug/17/2019  Sat
 */
@Retention(RetentionPolicy.CLASS)
//@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.LOCAL_VARIABLE})
@Target(ElementType.TYPE)
public @interface Router {
    String name();
}
