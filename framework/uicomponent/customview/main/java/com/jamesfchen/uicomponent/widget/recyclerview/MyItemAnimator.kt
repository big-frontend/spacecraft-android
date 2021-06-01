package com.jamesfchen.uicomponent.widget.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class MyItemAnimator : SimpleItemAnimator() {
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
//        Log.d("cjf","");
        return true
    }

    override fun runPendingAnimations() {
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
    }

    override fun endAnimations() {
    }

    override fun isRunning(): Boolean {
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return true
    }
}