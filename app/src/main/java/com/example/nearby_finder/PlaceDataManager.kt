package com.example.nearby_finder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import retrofit2.Response
import androidx.lifecycle.liveData

object PlaceDataManager {
    val places = mutableListOf<Place>()
    private val placeService: PlacesService = RetrofitInstance
        .getRetrofitInstance()
        .create(PlacesService::class.java)

    fun getPlaces(lifecycleOwner: LifecycleOwner, long: Double, lat: Double) {
        // Create LiveData asking for a Posts response and start the API request.
        val responseLiveData: LiveData<Response<Places>> = liveData {
            val response = placeService.getNearbyLocation(long, lat)
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
}