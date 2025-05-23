package com.electrolytej.util

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
var View.leftMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart !=value) {
                it.leftMargin = value
                it.marginStart = value
                layoutParams = it
            }
        }
    }

var View.rightMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.rightMargin != value || it.marginEnd != value) {
                it.rightMargin = value
                it.marginEnd = value
                layoutParams = it
            }
        }
    }
var View.topMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.topMargin != value) {
                it.topMargin = value
                layoutParams = it
            }
        }
    }

var View.bottomMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.bottomMargin != value) {
                it.bottomMargin = value
                layoutParams = it
            }
        }
    }


var View.leftPadding
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart !=value) {
                it.leftMargin = value
                it.marginStart = value
//                setPadding() = it
            }
        }
    }

var View.rightPadding
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart !=value) {
                it.leftMargin = value
                it.marginStart = value
                layoutParams = it
            }
        }
    }


var View.leftToRightId
    get() = 0
    @IdRes
    set(value) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.let {
            if (it.leftToRight != value) {
                it.leftToRight = value
                layoutParams = it
            }
        }
    }

var View.rightToLeftId
    get() = 0
    @IdRes
    set(value) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.let {
            if (it.rightToLeft != value) {
                it.rightToLeft = value
                layoutParams = it
            }
        }
    }