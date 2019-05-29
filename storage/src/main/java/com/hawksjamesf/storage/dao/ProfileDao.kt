package com.hawksjamesf.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.hawksjamesf.storage.model.Profile

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: May/30/2019  Thu
 */
@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getProfile(): Profile
}