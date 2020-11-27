package com.example.nearby_finder

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesService {
    @GET("nearbysearch/json?location=33.8670522,133.1957362&radius=500&types=restaurant&key=AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ")
    suspend fun getNearbyLocation(): Response<Places>

    //TODO Remove this after testing
    @GET("1")
    suspend fun getTestData(): Response<Place>
}