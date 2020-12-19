package com.example.nearby_finder

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nearby_finder.fragments.PlaceListFragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nearby_finder.managers.NetworkManager
import com.example.nearby_finder.managers.PlaceManager
import com.google.android.libraries.places.api.Places

class MainActivity : AppCompatActivity() {

    var apiKey = NearbyFinderApplication().getmInstance().getApiKey(1)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      
        Places.initialize(this, apiKey)

        PlaceManager.fetchPlace(this)

        NetworkManager.registerNetworkCallback(this)
      
        showFragment(PlaceListFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}