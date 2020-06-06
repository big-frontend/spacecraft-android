package com.hawksjamesf.map.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hawksjamesf.map.model.LBS

@Dao
interface LbsDao {
    @Query("SELECT * FROM lbs")
    fun allLbsDatas(): DataSource.Factory<Int, LBS>
    @Query("SELECT * FROM lbs")
    fun getAll(): List<LBS>
    @Insert
    fun insert(lbs: LBS)
    @Delete
    fun delete(lbs:LBS)

}
