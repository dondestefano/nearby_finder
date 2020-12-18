package com.example.nearby_finder

import androidx.lifecycle.*
import com.example.nearby_finder.data.PlaceItem
import com.example.nearby_finder.data.PlacesRepository
import com.example.nearby_finder.managers.NetworkManager
import kotlinx.coroutines.launch

class PlacesViewModel(private val repository: PlacesRepository): ViewModel() {

    val places: LiveData<List<PlaceItem>> = repository.places.asLiveData()

    fun insert() = viewModelScope.launch {
        val placeTwo = PlaceItem("Jonas korv", "Moj", "http:image")
        repository.insert(placeTwo)
    }

    fun delete() = viewModelScope.launch {
        repository.deleteAll()
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
}