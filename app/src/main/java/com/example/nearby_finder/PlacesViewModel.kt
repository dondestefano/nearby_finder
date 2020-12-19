package com.example.nearby_finder

import android.app.Application
import androidx.lifecycle.*
import com.example.nearby_finder.data.PlaceItem
import com.example.nearby_finder.data.PlacesRepository
import com.example.nearby_finder.managers.NetworkManager
import com.example.nearby_finder.managers.PlaceManager
import com.example.nearby_finder.managers.Status
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.launch

class PlacesViewModel(private val repository: PlacesRepository): ViewModel() {

    val places: LiveData<List<PlaceItem>> = repository.places.asLiveData()

    fun insert(place: PlaceItem) = viewModelScope.launch {
        repository.insert(place)
    }

    private fun insertAll() = viewModelScope.launch {
        repository.deleteAll()
        repository.insertAll(PlaceManager.getFetchedPlaces())
    }

    fun testNetwork() = viewModelScope.launch {
        repository.deleteAll()
        repository.insertAll(createDummyData())
    }

    // TODO: Remove this when Places call is in place.
    private fun createDummyData(): List<PlaceItem> {

        return if (NetworkManager.isNetworkConnected) {
            val placeOne = PlaceItem("We got", "Pizzeria", "https://i.pinimg.com/originals/dc/ca/20/dcca201c915e6b8be4d5b9385c343662.jpg")
            val placeTwo = PlaceItem("Internet", "Moj", "http:image")

            mutableListOf(placeOne, placeTwo)
        } else {
            val placeThree = PlaceItem("No internet", "Grill",  "https://media-cdn.tripadvisor.com/media/photo-s/10/59/74/be/greasy-spoon-hagagatan.jpg")

            mutableListOf(placeThree)
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

    fun observePlaceManager(lifecycleOwner: LifecycleOwner) {
        PlaceManager.status.observe(lifecycleOwner) {
            when (it) {
                Status.SUCCESS -> {
                    insertAll()
                }

                Status.FAILED -> {

                }
                else -> {

                }
            }

        }
    }
}