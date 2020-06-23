package com.hawksjamesf.map.dao

import androidx.paging.DataSource
import androidx.room.*
import com.hawksjamesf.map.model.LBS

@Dao
interface LbsDao {
    @Query("SELECT * FROM lbs")
    fun allLbsDatas(): DataSource.Factory<Int, LBS>
    @Query("SELECT * FROM lbs WHERE needUpload = :needUpload")
    fun getNeedUploadDatas(needUpload:Boolean=true): DataSource.Factory<Int, LBS>
    @Query("SELECT * FROM lbs")
    fun getAll(): List<LBS>
    @Insert
    fun insert(lbs: LBS)
    @Update
    fun update(lbs: LBS)
    @Delete
    fun delete(lbs:LBS)

}
