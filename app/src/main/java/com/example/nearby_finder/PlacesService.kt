package com.example.nearby_finder

import retrofit2.Response
import retrofit2.http.GET

interface PlacesService {
    @GET("place")
    suspend fun getPost(): Response<Place>
}