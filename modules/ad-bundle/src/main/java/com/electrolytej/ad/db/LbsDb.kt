package com.electrolytej.ad.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.electrolytej.ad.db.dao.LbsDao
import com.electrolytej.ad.db.model.LBS

@Database(entities = [LBS::class], version = 1, exportSchema = false)
abstract class LbsDb : RoomDatabase() {
    abstract fun lbsDao(): LbsDao

    companion object {
        private var instance: LbsDb? = null

        @Synchronized
        fun get(context: Context): LbsDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context, LbsDb::class.java, "lbs_db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Log.d("", "onCreate")
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            Log.d("", "onOpen")
                        }

                    }).build()
            }
            return instance!!
        }
    }
}