package com.example.nearby_finder

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val PLACES_URL = "https://maps.googleapis.com/maps/api/place/"
        const val TEST_URL = "https://jsonplaceholder.typicode.com/todos/"

        private const val BASE_URL = TEST_URL
        fun getRetrofitInstance(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}