package com.jamesfchen.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
@Entity(tableName = "profile",
        indices = [Index(value = ["mobile", "token"], unique = true)]
)
//@DatabaseView("SELECT id,mobile FROM profile")
data class Profile(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val profiledId: Int,
        @ColumnInfo(name = "mobile")
        val mobile: String,
        var token: String?,
        var refreshToken: String?
) {

    override fun toString(): String {
        return "Profile(id=$profiledId, mobile='$mobile', token=$token, refreshToken=$refreshToken)"
    }
}