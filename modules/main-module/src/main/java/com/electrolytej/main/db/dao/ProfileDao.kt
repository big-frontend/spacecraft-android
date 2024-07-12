package com.jamesfchen.storage.dao

import androidx.room.*
import com.jamesfchen.storage.model.Profile

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

    @Transaction
    @Query("SELECT * FROM profile WHERE id = :profileMobile")
    fun getProfileById(profileMobile:String): Profile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(profileList: List<Profile>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(profileList: List<Profile>)

    @Delete
    fun deleteByProfile(profile: Profile)



}