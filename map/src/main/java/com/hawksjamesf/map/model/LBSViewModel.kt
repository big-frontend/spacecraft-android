package com.hawksjamesf.map.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.hawksjamesf.map.dao.LbsDb

class LBSViewModel(app: Application) : AndroidViewModel(app) {
    val dao = LbsDb.get(app).lbsDao()
    val allLbsDatas = dao.allLbsDatas().toLiveData(Config(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 200
    ))

    fun insert(appCellInfo: AppCellInfo, appLocation: AppLocation) {
        dao.insert(LBS(appCellInfo = appCellInfo, appLocation = appLocation))
    }
}