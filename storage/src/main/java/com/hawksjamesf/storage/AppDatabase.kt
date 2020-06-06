package com.hawksjamesf.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
@TypeConverters(Converters::class)
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

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null
        const val DATABASE_NAME="app_db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                    .addCallback(ob∂çject : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
//                        }
//                    })
                    .build()
        }
    }

}