package com.hawksjamesf.spacecraft.listener

import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/24/2019  Fri
 */
class SampleAttachedBehavior :CoordinatorLayout.AttachedBehavior {
    override fun getBehavior(): CoordinatorLayout.Behavior<*> = SampleBehavior()
}