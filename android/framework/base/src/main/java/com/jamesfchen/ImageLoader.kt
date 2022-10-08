package com.jamesfchen

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/29/2021  Sun
 */
class ImageLoader {
    companion object {
        val lock = Any()

        @Volatile
        private var sINSTANCE: ImageLoader? = null
        fun getInstance(): ImageLoader {
            if (sINSTANCE == null) {
                synchronized(lock) {
                    if (sINSTANCE == null) {
                        sINSTANCE = ImageLoader()
                    }
                }
            }
            return sINSTANCE!!
        }
    }

    init {

    }
}