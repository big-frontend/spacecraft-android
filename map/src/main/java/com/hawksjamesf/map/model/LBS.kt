package com.hawksjamesf.map.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LBS {
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0
    @Embedded
    var appCellInfo: AppCellInfo? = null
    @Embedded
    var appLocation: AppLocation? = null

}