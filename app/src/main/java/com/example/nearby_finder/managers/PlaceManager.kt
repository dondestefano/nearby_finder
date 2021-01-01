package com.example.nearby_finder.managers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nearby_finder.data.BubbleSort
import com.example.nearby_finder.data.PlaceItem
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse

object PlaceManager {
    var list = MutableLiveData<List<PlaceItem>>()
    private val bubbleSort = BubbleSort()

    fun fetchPlace(context: Context) {
        // Create a new Places client instance.
        val placesClient = Places.createClient(context)

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ActivityCompat.checkSelfPermission(
                (context as Activity?)!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 10
            )
        }

        // Specify the fields to return.
        val placeFields: List<Place.Field> = listOf(
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.PHOTO_METADATAS
        )
        // Construct a request object, passing the placeFields array.
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        placesClient.findCurrentPlace(request)
                .addOnSuccessListener { response: FindCurrentPlaceResponse ->
                    val newList = mutableListOf<PlaceItem>()
                    for (placeLikelihood: PlaceLikelihood in response.placeLikelihoods) {
                        val photoMetadata = placeLikelihood.place.photoMetadatas?.get(0)
                        val attributions = photoMetadata?.attributions
                        val place = PlaceItem(
                            placeLikelihood.place.name ?: "Name unavailable",
                            placeLikelihood.place.address ?: "Address unavailable",
                            attributions ?: "No image",
                                "Salt",
                                "Iv"
                        )
                        newList.add(place)

                        Log.i(
                            "SUCCESS",
                            "Place: '${placeLikelihood.place.name}' Adress: '${placeLikelihood.place.address}' Photo Metadata: '${attributions}'"
                        )
                    }
                    bubbleSort.sortLength(newList)
                    list.postValue(newList)

                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        Log.e("ERR", "Place not found: ${exception.message}")
                        val statusCode = exception.statusCode
                        Log.e("ERR", "Status code: $statusCode")
                    }
                }
    }
}
