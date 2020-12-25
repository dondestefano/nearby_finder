package com.example.nearby_finder.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.nearby_finder.managers.PlaceManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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
                    val place = PlaceItem(
                        placeLikelihood.place.name ?: "Name unavailable",
                        placeLikelihood.place.address ?: "Address unavailable",
                        attributions ?: "No image"
                    )
                    newList.add(place)

                    Log.i(
                        "SUCCESS",
                        "Place: '${placeLikelihood.place.name}' Adress: '${placeLikelihood.place.address}' Photo Metadata: '${attributions}'"
                    )
                }

                bubbleSort.sortList(newList)
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