package com.example.nearby_finder

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.nearby_finder.data.DummyData
import com.google.android.libraries.places.api.model.Place

class PlacesRepository(private val database: CacheDatabase) {
    val places: LiveData<List<DummyData>> = database.cacheDao.getPlaces()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(places: List<DummyData>) {
        database.cacheDao.insertAll(places)
    }
}