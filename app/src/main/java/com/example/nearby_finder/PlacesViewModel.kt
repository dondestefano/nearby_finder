package com.example.nearby_finder

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.nearby_finder.data.BubbleSort
import com.example.nearby_finder.data.PlaceItem
import com.example.nearby_finder.data.PlacesRepository
import com.example.nearby_finder.managers.NetworkManager
import com.example.nearby_finder.managers.PlaceManager
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class PlacesViewModel(private val repository: PlacesRepository): ViewModel() {

    var places = repository.places

    class PlacesViewModelFactory(private val repository: PlacesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlacesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun findNearby(context: Context) = viewModelScope.launch {
        repository.fetchPlaceFromApi(context)
    }

    fun saveToCache() = viewModelScope.launch {
        repository.updateCache()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun isOnline(context: Context) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    repository.getCachedPlaces()
                }

                override fun onLost(network: Network?) {
                    repository.getCachedPlaces()
                }
            }
            )

        } catch (e: Exception) {

        }
    }
}