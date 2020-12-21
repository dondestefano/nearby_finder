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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class PlacesViewModel(private val repository: PlacesRepository): ViewModel() {

    var places: LiveData<List<PlaceItem>> = PlaceManager.getFetchedPlacesAsFlow().asLiveData()

    private val bubbleSort = BubbleSort()

    private fun insertAll() = viewModelScope.launch {
        repository.deleteAll()
        val list = bubbleSort.sortList(PlaceManager.getFetchedPlaces())
        repository.insertAll(list)
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
                    places = PlaceManager.getFetchedPlacesAsFlow().asLiveData()
                }

                override fun onLost(network: Network?) {
                    if (PlaceManager.getFetchedPlacesAsFlow() != repository.places) {
                        insertAll()
                    }
                    places = repository.places.asLiveData()
                }
            }
            )

        } catch (e: Exception) {

        }
    }
}