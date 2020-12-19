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
        val list = bubbleSortList(PlaceManager.getFetchedPlaces())
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

    // TODO: Remove this when Places call is in place.
    fun testNetwork() = viewModelScope.launch {
        repository.deleteAll()
        val list = bubbleSortList(createDummyData())
        repository.insertAll(list)
    }

    private fun createDummyData(): MutableList<PlaceItem> {
        return if (NetworkManager.isNetworkConnected) {
            val placeTwo = PlaceItem("Internet", "Moj", "http:image")
            val placeThree = PlaceItem("And this should be last", "Moj", "http:image")
            val placeOne = PlaceItem("We got that", "Pizzeria", "https://i.pinimg.com/originals/dc/ca/20/dcca201c915e6b8be4d5b9385c343662.jpg")

            mutableListOf(placeTwo, placeThree, placeOne)
        } else {
            val placeThree = PlaceItem("No internet", "Grill",  "https://media-cdn.tripadvisor.com/media/photo-s/10/59/74/be/greasy-spoon-hagagatan.jpg")

            mutableListOf(placeThree)
        }
    }

    private fun bubbleSortList(list: MutableList<PlaceItem>): MutableList<PlaceItem> {
        var numSwaps = 0
        var isSorted = false

        var lastUnsorted = list.size - 1

        if (list.size <= 1) {
            return list
        } else {
            while (!isSorted) {
                isSorted = true
                for (i in 0 until lastUnsorted) {
                    if (list[i].name.count() > list[i + 1].name.count()) {
                        swapValues(list, i, i + 1)
                        numSwaps++
                        isSorted = false
                    }
                }
                lastUnsorted--
            }
            for(i in list) {
                println(i)
            }
            return list
        }
    }

    private fun swapValues(list: MutableList<PlaceItem>, a: Int, b: Int) {
        val temp = list[b]
        list[b] = list[a]
        list[a] = temp
    }
}