package com.example.nearby_finder.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.nearby_finder.util.Encryption
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import kotlin.concurrent.thread

class PlacesRepository(private val cacheDao: CacheDao) {
    var places = MutableLiveData<MutableList<PlaceItem>>()
    val bubbleSort = BubbleSort()

    @WorkerThread
    suspend fun updateCache() {
        cacheDao.deleteAll()
        cacheDao.insertAll(places.value as List<PlaceItem>)
    }

    @WorkerThread
    suspend fun insert(placeItem: PlaceItem) {
        cacheDao.insert(placeItem)
    }

    @WorkerThread
    suspend fun deleteAll() {
        cacheDao.deleteAll()
    }

    fun getCachedPlaces() {
        thread {
            places.postValue(cacheDao.getPlaces())
        }
    }

    suspend fun fetchPlaceFromApi(context: Context) {
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

                    //Add necessary val for encryption
                    val nameToEncrypt = placeLikelihood.place.name?.toByteArray()
                    val nameUnavailable = "Name unavailable".toByteArray()

                    val map = Encryption.encrypt(nameToEncrypt ?: nameUnavailable, Encryption.key)

                    val place = PlaceItem(
                            Base64.encodeToString(map["encrypted"], Base64.NO_WRAP),
                        placeLikelihood.place.address ?: "Address unavailable",
                        attributions ?: "No image",
                            Base64.encodeToString(map["salt"], Base64.NO_WRAP),
                            Base64.encodeToString(map["iv"], Base64.NO_WRAP)
                    )
                    newList.add(place)

                    Log.i(
                        "SUCCESS",
                        "Place: '${placeLikelihood.place.name}' Encryption: '${place.name}'"
                    )
                }

                bubbleSort.sortAlphabetical(newList)
                places.postValue(newList)

            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    Log.e("ERR", "Place not found: ${exception.message}")
                    val statusCode = exception.statusCode
                    Log.e("ERR", "Status code: $statusCode")
                }
            }
    }

    init {
        getCachedPlaces()
    }
}