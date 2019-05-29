package com.hawksjamesf.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hawksjamesf.storage.model.Profile

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: May/30/2019  Thu
 */
@Database(
        entities = [Profile::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
//    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
//
//    }
//
//    override fun createInvalidationTracker(): InvalidationTracker {
//    }
//
//    override fun clearAllTables() {
//    }

}