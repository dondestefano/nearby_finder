package com.example.nearby_finder

import android.util.Log
import androidx.lifecycle.*
import retrofit2.Response
import com.google.gson.JsonObject

object PlaceDataManager {
    val places = mutableListOf<Place>()
    var testValue: MutableLiveData<Place> = MutableLiveData()

    private val placeService: PlacesService = RetrofitInstance
        .getRetrofitInstance()
        .create(PlacesService::class.java)

    fun getPlaces(lifecycleOwner: LifecycleOwner, long: Double, lat: Double) {
        // Create LiveData asking for a Posts response and start the API request.
        val responseLiveData: LiveData<Response<Places>> = liveData {
            val response = placeService.getNearbyLocation()
            emit(response)
        }

        // Wait for the response and add the received posts to PostDataManagers Posts.
        responseLiveData.observe(lifecycleOwner, Observer {
            val receivedPlaces = it.body()?.listIterator()
            if (receivedPlaces!=null) {
                while (receivedPlaces.hasNext()){
                    val place = receivedPlaces.next()
                    places.add(place)
                }
            }
        })
    }

    //TODO Remove this after testing
    fun getTestData(lifecycleOwner: LifecycleOwner) {
        val responseLiveData: LiveData<Response<Place>> = liveData {
            val response = placeService.getTestData()
            emit(response)
        }

        // Wait for the response and add the received posts to PostDataManagers Posts.
        responseLiveData.observe(lifecycleOwner, Observer {
            val receivedPlace = it.body()
            if (receivedPlace!=null) {
                testValue.value = receivedPlace
            }
        })
    }
}