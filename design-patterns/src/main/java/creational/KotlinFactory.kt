package creational

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */

interface ViewModel

//=========================================== abstract factory start
interface KotlinAbstractFactory<T> {
    fun create(): T
}

class AndroidViewModel : ViewModel {
    companion object : KotlinAbstractFactory<ViewModel> {
        override fun create(): ViewModel = AndroidViewModel()
    }

}

class CustomViewModel : ViewModel {
    companion object : KotlinAbstractFactory<ViewModel> {
        override fun create(): ViewModel = CustomViewModel()
    }
}

class Activity {
    fun call() {
        var androidViewModel = AndroidViewModel.create()
        var customViewModel = CustomViewModel.create()

    }
}
//=========================================== abstract factory end


//=========================================== static abstract factory start
interface KotlinAbstractFactory2 {
    companion object {
        inline fun <reified T : ViewModel> create(): ViewModel = when (T::class) {
            AndroidViewModel2::class -> AndroidViewModel2()
            CustomViewModel2::class -> CustomViewModel2()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}

class AndroidViewModel2 : ViewModel

class CustomViewModel2 : ViewModel

class Activity2 {
    fun call() {
        var androidViewModel2 = KotlinAbstractFactory2.create<AndroidViewModel2>()
        var customViewModel2 = KotlinAbstractFactory2.create<CustomViewModel2>()

    }
}

//=========================================== static abstract factory end

//=========================================== factory method start
class Fragment {

    companion object {
        fun newInstance(): Fragment {
            val fragment = Fragment()
            return fragment
        }
    }
}

enum class Enum {
    ONE,
    TOW,
    THREE;

    companion object {
        fun valueOf(value: String): Enum? {
            for (e in Enum.values()) {
                if (e.name == value) return e
            }
            return null
        }
    }
}

class Activity3 {
    fun call() {
        var fragment = Fragment.newInstance()
        var enum = Enum.valueOf("ONE")
    }
}


//=========================================== factory method end


