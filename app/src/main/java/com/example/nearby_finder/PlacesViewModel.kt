package com.example.nearby_finder

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.nearby_finder.data.PlacesRepository
import com.example.nearby_finder.managers.SharedPrefHelper
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun findNearby(context: Context) = viewModelScope.launch {
        SharedPrefHelper.generateNewPassword(context)
        SharedPrefHelper.encryptionPassword?.let { repository.fetchPlaceFromApi(context, it) }
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