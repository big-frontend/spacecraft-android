package com.jamesfchen.ibc.router

import android.content.Context
import android.os.Bundle

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author: jamesfchen
 * since: Jul/02/2021  Fri
 */
interface INativeRouter: IRouter {
    fun go(cxt: Context, page: String?, bundle: Bundle?): Boolean
}