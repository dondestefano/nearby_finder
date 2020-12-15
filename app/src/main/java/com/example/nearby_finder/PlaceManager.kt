package com.example.nearby_finder

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import java.util.*


object PlaceManager {

    fun fetchPlace(context: Context) {

        // Create a new Places client instance.
        val placesClient = Places.createClient(context)

        if (ActivityCompat.checkSelfPermission((context as Activity?)!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((context as Activity?)!!, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
            ), 10)
        }

        // Define a Place ID.
        val placeId = "1"

        // Specify the fields to return.
        val placeFields: List<Place.Field> = listOf(
                Place.Field.ID,
                Place.Field.NAME,
        )

        val currentPlace = FindCurrentPlaceRequest.newInstance(placeFields)

        placesClient.findCurrentPlace(currentPlace)

        // Construct a request object, passing the place ID and fields array.
        //val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.findCurrentPlace(currentPlace)
                .addOnSuccessListener { response: FindCurrentPlaceResponse ->
                    val place = response.placeLikelihoods
                    Log.i("SUCCESS", "Place found: $place")

                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        Log.e("ERR", "Place not found: ${exception.message}")
                        val statusCode = exception.statusCode
                        Log.e("ERR", "Status code: $statusCode")
                        TODO("Handle error with given status code")
                    }
                }

    }

}
