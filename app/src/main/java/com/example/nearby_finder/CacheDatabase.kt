package com.example.nearby_finder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nearby_finder.data.DummyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DummyData::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract val cacheDao: CacheDao

    companion object {
        @Volatile
        private var INSTANCE: CacheDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): CacheDatabase {

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
                        populateDatabase(database.cacheDao)
                    }
                }
            }
        }


        // Add placeholder data for when the databse is created.
        suspend fun populateDatabase(cacheDao: CacheDao) {
            cacheDao.deleteAll()

            val placeOne = DummyData("Pepe's Italian", "Pizzeria", 125, "https://i.pinimg.com/originals/dc/ca/20/dcca201c915e6b8be4d5b9385c343662.jpg")
            val placeTwo = DummyData("Dirty Diego's Tacos", "Taqueria", 50, "http:image")
            val placeThree = DummyData("Gregory", "Greek", 1345, "https://i.pinimg.com/originals/dc/ca/20/dcca201c915e6b8be4d5b9385c343662.jpg")
            cacheDao.insert(placeOne)
            cacheDao.insert(placeTwo)
            cacheDao.insert(placeThree)
        }
    }
}