package com.hawksjamesf.map.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LBS(@PrimaryKey(autoGenerate = true)
               var index: Int = 0,
               var appCellInfo: AppCellInfo?,
               var appLocation: AppLocation?)