package com.jamesfchen.bundle2.page.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.jamesfchen.bundle2.db.LbsDb
import com.jamesfchen.bundle2.db.model.LBS
import com.jamesfchen.bundle2.util.ioThread

class LBSViewModel(app: Application) : AndroidViewModel(app) {
    val dao = LbsDb.get(app).lbsDao()
    val allLbsDatas = dao.allLbsDatas().toLiveData(Config(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 200
    ))

    fun insert(appCellInfo: _root_ide_package_.com.jamesfchen.bundle2.model.AppCellInfo?, appLocation: _root_ide_package_.com.jamesfchen.bundle2.model.AppLocation?) = ioThread {
        val lbs = LBS()
        lbs.appCellInfo = appCellInfo
        lbs.appLocation = appLocation
        dao.insert(lbs)
    }

    fun clearAll() = ioThread {
        val toList = dao.getAll()
        for (lbs in toList) {
            dao.delete(lbs)
        }
    }
}