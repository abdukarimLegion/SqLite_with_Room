package com.naa.sqlite.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naa.sqlite.source.room.dao.GroupDao
import com.naa.sqlite.source.room.dao.StudentDao
import com.naa.sqlite.source.room.entity.GroupData
import com.naa.sqlite.source.room.entity.StudentData

@Database(entities = [GroupData::class, StudentData::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}