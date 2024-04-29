package com.jamesfchen.bundle2

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jamesfchen.bundle2.BR

class ListItem : BaseObservable() {
    @get:Bindable
    var icon: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.icon)
        }

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var text: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
        }
}