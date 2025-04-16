package com.electrolytej.bundle2.page.infos

import android.util.Log
import androidx.databinding.*
import androidx.lifecycle.viewModelScope
import com.electrolytej.bundle2.ObservableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class InfosViewModel : ObservableViewModel() {
    val notification = ObservableField<String>()

    val name = ObservableField("Ada")
    val lastName = ObservableField("Lovelace")
    val likes = ObservableInt(0)

    init {
        viewModelScope.launch {
//            Observable.interval(1, TimeUnit.SECONDS)
//                .subscribe {
//                   notification?.set("您有一条新消息，请注意查收 ${it}")
//                }
            flow<Int> {
                repeat(4) {
                    emit(it)
                    delay(500)
                }
            }.collect {
                notification.set("您有一条新消息，请注意查收 ${it}")
            }
        }
        notification.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.d("cjf","发起网络请求 ${propertyId}")
            }
        })
    }

    private fun ObservableInt.increment() {
        set(get() + 1)
    }

    fun onLike() {
        likes.increment()
//     You control when the @Bindable properties are updated using `notifyPropertyChanged()`.
//        notifyPropertyChanged(BR.popularity)
    }

    @Bindable
    fun getPopularity(): Popularity {
        return likes.get().let {
            when {
                it > 9 -> Popularity.STAR
                it > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }
        }
    }

    enum class Popularity {
        NORMAL,
        POPULAR,
        STAR
    }

    // popularity is exposed as LiveData using a Transformation instead of a @Bindable property.
//    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
//        when {
//            it > 9 -> Popularity.STAR
//            it > 4 -> Popularity.POPULAR
//            else -> Popularity.NORMAL
//        }
//    }


}