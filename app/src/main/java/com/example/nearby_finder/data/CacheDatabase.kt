package com.example.nearby_finder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PlaceItem::class], version = 3)
abstract class CacheDatabase: RoomDatabase() {
    abstract val cacheDao: CacheDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CacheDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): CacheDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CacheDatabase::class.java,
                        "place_database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        // Create the database if it doesn't exist.
        private class WordDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        clearDatabase(database.cacheDao)
                    }
                }
            }
        }


        // Safety clear of database
        suspend fun clearDatabase(cacheDao: CacheDao) {
            cacheDao.deleteAll()
        }
    }
}