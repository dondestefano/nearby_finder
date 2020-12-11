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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.test_textview)
        PlaceManager.fetchPlace()
    }

    override fun onResume() {
        super.onResume()
        PlaceDataManager.getTestData(this)
    }
}