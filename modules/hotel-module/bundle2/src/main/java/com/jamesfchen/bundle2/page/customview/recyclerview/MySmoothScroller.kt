package com.jamesfchen.bundle2.page.customview.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MySmoothScroller : RecyclerView.SmoothScroller() {
    override fun onStart() {

    }

    override fun onStop() {
    }

    override fun onSeekTargetStep(dx: Int, dy: Int, state: RecyclerView.State, action: Action) {
    }

    override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
    }
}