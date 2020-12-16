package com.example.nearby_finder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nearby_finder.data.DummyData

@Database(entities = [DummyData::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract val cacheDao: CacheDAO

    companion object {
        @Volatile
        private var INSTANCE: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            CacheDatabase::class.java,
                            "message_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}