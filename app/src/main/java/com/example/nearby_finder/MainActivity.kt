package com.example.nearby_finder

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    var apiKey = "AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext,apiKey)

        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.test_textview)

        PlaceManager.fetchPlace()
    }

    override fun onResume() {
        super.onResume()
    }
}