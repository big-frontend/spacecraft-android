package com.jamesfchen.bundle2.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jamesfchen.bundle2.location.model.AppCellInfo
import com.jamesfchen.bundle2.location.model.AppLocation

@Entity
class LBS {
    @PrimaryKey(autoGenerate = true)
    @get:JvmName("index")
    var index: Int = 0
    var needUpload=false
    @get:JvmName("add")
    var add=""
    @Embedded
    @get:JvmName("appCellInfo")
    var appCellInfo: AppCellInfo? = null
    @Embedded
    @get:JvmName("appLocation")
    var appLocation: AppLocation? = null

    override fun toString(): String {
        return "LBS(index=$index, needUpload=$needUpload, add='$add', appCellInfo=$appCellInfo, appLocation=$appLocation)"
    }


}