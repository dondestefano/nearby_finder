package com.example.nearby_finder.data

import androidx.annotation.WorkerThread
import com.example.nearby_finder.data.CacheDao
import com.example.nearby_finder.data.DummyData
import kotlinx.coroutines.flow.Flow

class PlacesRepository(private val cacheDao: CacheDao) {
    val places: Flow<List<DummyData>> = cacheDao.getPlaces()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(places: List<DummyData>) {
        cacheDao.insertAll(places)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(place: DummyData) {
        cacheDao.insert(place)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        cacheDao.deleteAll()
    }
}