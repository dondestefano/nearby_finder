package com.example.nearby_finder.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.nearby_finder.data.PlacesRepository
import com.example.nearby_finder.managers.NetworkManager
import com.example.nearby_finder.managers.SharedPrefHelper
import kotlinx.coroutines.launch

class PlacesViewModel(private val repository: PlacesRepository): ViewModel() {

    var places = repository.places
    // ViewModelProvider will take care of the lifecycle of the ViewModel.
    // Makes sure we always get the right instance of the PlacesViewModel class.
    class PlacesViewModelFactory(private val repository: PlacesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlacesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)  //Launching a new coroutine to insert the data in a non-blocking way
    fun findNearby(context: Context) = viewModelScope.launch {
        if (NetworkManager.isNetworkConnected) {
            SharedPrefHelper.generateNewPassword(context)
            SharedPrefHelper.encryptionPassword?.let {
                repository.fetchPlaceFromApi(context, it)
                Toast.makeText(context, "Network Found: fetching from Google", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "No network: Connect and try again", Toast.LENGTH_LONG).show()
        }
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
                    // Network Connectivity is found, repository calls fun getting places from the stored cache
                    repository.getCachedPlaces()
                    Toast.makeText(context, "Network Found: fetching from cache", Toast.LENGTH_LONG).show()

                }

                override fun onLost(network: Network?) {
                    // Network Connectivity loss, repository calls fun getting places from the stored cache
                    repository.getCachedPlaces()
                    Toast.makeText(context, "Network Lost: fetching from cache", Toast.LENGTH_LONG).show()

                }
            }
            )

        } catch (e: Exception) {

        }
    }
}