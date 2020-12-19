package com.example.nearby_finder

import android.app.Application
import com.example.nearby_finder.data.CacheDatabase
import com.example.nearby_finder.data.PlacesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NearbyFinderApplication : Application() {

    private var instance: NearbyFinderApplication? = null

    companion object {
        init {
            System.loadLibrary("native_lib")
        }
    }

    fun getmInstance(): NearbyFinderApplication {
        if (instance == null) {
            instance = NearbyFinderApplication()
        }
        return instance as NearbyFinderApplication
    }

    external fun getApiKey(id: Int): String

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CacheDatabase.getInstance(this, applicationScope) }
    val repository by lazy { PlacesRepository(database.cacheDao) }
}