package com.electrolytej.ad.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.electrolytej.location.AppCellInfo
import com.electrolytej.location.AppLocation

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