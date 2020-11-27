package com.example.nearby_finder

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesService {
    @GET("nearbysearch/json?location={long}.{lat}&radius=500&types=restaurant&key=AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ")
    suspend fun getNearbyLocation(@Path("long")longitude: Double, @Path("lat")latitude: Double): Response<Places>
}