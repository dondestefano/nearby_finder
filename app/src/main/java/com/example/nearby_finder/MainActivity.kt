package com.example.nearby_finder

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nearby_finder.fragments.PlaceListFragment
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import java.util.*

class MainActivity : AppCompatActivity() {
    var apiKey = "AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      
        Places.initialize(this, apiKey)

        PlaceManager.fetchPlace(this)
      
        showFragment(PlaceListFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()

    }
}