package com.hawksjamesf.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
@Entity(tableName = "profile")
data class Profile(
        @PrimaryKey @ColumnInfo(name = "id") val profiledId: Int,
        val mobile: String,
        var token: String?,
        var refreshToken: String?
) {

    override fun toString(): String {
        return "Profile(id=$profiledId, mobile='$mobile', token=$token, refreshToken=$refreshToken)"
    }
}