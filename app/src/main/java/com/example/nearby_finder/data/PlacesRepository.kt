package com.example.nearby_finder.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PlacesRepository(private val cacheDao: CacheDao) {
    val places: Flow<List<Place>> = cacheDao.getPlaces()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(places: List<Place>) {
        cacheDao.insertAll(places)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(place: Place) {
        cacheDao.insert(place)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        cacheDao.deleteAll()
    }
}