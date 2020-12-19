package com.example.nearby_finder.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PlacesRepository(private val cacheDao: CacheDao) {
    val places: Flow<List<PlaceItem>> = cacheDao.getPlaces()

    @WorkerThread
    suspend fun insertAll(placeItems: List<PlaceItem>) {
        cacheDao.insertAll(placeItems)
    }

    @WorkerThread
    suspend fun insert(placeItem: PlaceItem) {
        cacheDao.insert(placeItem)
    }

    @WorkerThread
    suspend fun deleteAll() {
        cacheDao.deleteAll()
    }
}