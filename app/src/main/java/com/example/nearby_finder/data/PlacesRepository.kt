package com.example.nearby_finder.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import okhttp3.Dispatcher
import kotlin.concurrent.thread

class PlacesRepository(private val cacheDao: CacheDao) {
    var places = MutableLiveData<List<PlaceItem>>()

    @WorkerThread
    suspend fun insertAll(placeItems: MutableList<PlaceItem>) {
        cacheDao.insertAll(placeItems)
        updatePlaces()
    }


    @WorkerThread
    suspend fun insert(placeItem: PlaceItem) {
        cacheDao.insert(placeItem)
    }

    @WorkerThread
    suspend fun deleteAll() {
        cacheDao.deleteAll()
    }

    private fun updatePlaces() {
        thread {
            places.postValue(cacheDao.getPlaces())
        }
    }

    init {
        updatePlaces()
    }
}