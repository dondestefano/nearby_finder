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

    var places = MutableLiveData<List<PlaceItem>>()

    private val bubbleSort = BubbleSort()

    private fun insertAll() = viewModelScope.launch {
        if (PlaceManager.list.value != null) {
            repository.deleteAll()
            repository.insertAll(PlaceManager.list.value as MutableList<PlaceItem>)
        }
    }

    class PlacesViewModelFactory(private val repository: PlacesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlacesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun findNearby(context: Context) {
        thread {
            PlaceManager.fetchPlace(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun isOnline(context: Context) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    places = repository.places
                }

                override fun onLost(network: Network?) {
                    if (PlaceManager.list.value != null) {
                        insertAll()
                    }
                    places = repository.places
                }
            }
            )

        } catch (e: Exception) {

        }
    }
}