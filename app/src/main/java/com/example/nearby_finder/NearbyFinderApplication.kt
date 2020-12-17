package com.example.nearby_finder

import android.app.Application
import com.example.nearby_finder.data.CacheDatabase
import com.example.nearby_finder.data.PlacesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NearbyFinderApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CacheDatabase.getInstance(this, applicationScope) }
    val repository by lazy { PlacesRepository(database.cacheDao) }
}