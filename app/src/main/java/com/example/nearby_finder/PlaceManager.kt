package com.example.nearby_finder

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.security.AccessController.getContext
import java.util.*


object PlaceManager {

    fun fetchPlace() {
        // Create a new Places client instance.
        val placesClient = Places.createClient()

        if (ActivityCompat.checkSelfPermission(MainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // Define a Place ID.
        val placeId = "1"

        // Specify the fields to return.
        val placeFields: List<Place.Field> = listOf(
                Place.Field.ID,
                Place.Field.NAME
        )

        /*
        val placesFields = mutableListOf<Place.Field>(
                Place.Field.ID,
                Place.Field.NAME
        )
         */


        val currentPlace = FindCurrentPlaceRequest.newInstance(placeFields)

        placesClient.findCurrentPlace(currentPlace)

        // Construct a request object, passing the place ID and fields array.
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    val place = response.place
                    Log.i("TAG", "Place found: ${place.name}")
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: ${exception.message}")
                        val statusCode = exception.statusCode
                        TODO("Handle error with given status code")
                    }
                }

    }


}
