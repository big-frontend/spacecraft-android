package com.jamesfchen.myhome.screen.notification

import androidx.databinding.*
import com.jamesfchen.mvvm.ObservableViewModel
import com.jamesfchen.loader.BR

class InfosViewModel : ObservableViewModel() {
    val notification = ObservableField<String>()
    val name = ObservableField("Ada")
    val lastName = ObservableField("Lovelace")
    val likes = ObservableInt(0)

    private fun ObservableInt.increment() {
        set(get() + 1)
    }

    fun onLike() {
        likes.increment()
//     You control when the @Bindable properties are updated using `notifyPropertyChanged()`.
        notifyPropertyChanged(BR.popularity)
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