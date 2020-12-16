package com.example.nearby_finder.managers

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi


object NetworkManager {
    var isNetworkConnected = false

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback(context: Context) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    isNetworkConnected = true
                }

                override fun onLost(network: Network?) {
                    isNetworkConnected = false
                }
            }
            )
            isNetworkConnected = false
        } catch (e: Exception) {
            isNetworkConnected = false
        }
    }
}