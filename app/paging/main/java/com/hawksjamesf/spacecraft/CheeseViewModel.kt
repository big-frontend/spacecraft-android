package com.hawksjamesf.spacecraft

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/22/2019  Sun
 */
class CheeseViewModel(app: Application) : AndroidViewModel(app) {
    val dao = CheeseDb.get(app).cheeseDao()

    val allCheeses = dao.allCheesesByName().toLiveData(Config(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 200))

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.delete(cheese)
    }
}