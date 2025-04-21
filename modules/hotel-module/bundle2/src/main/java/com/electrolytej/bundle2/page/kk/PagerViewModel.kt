package com.electrolytej.bundle2.page.kk

import jamesfchen.widget.kk.TabsLayout

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Jun/16/2019  Sun
 */
data class PagerViewModel(
    val tab: TabsLayout.TabItem,
    val contents: List<Any>
)