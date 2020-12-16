package com.example.nearby_finder

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NearbyFinderApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { CacheDatabase.getInstance(this, applicationScope) }
    val repository by lazy { PlacesRepository(database) }
}