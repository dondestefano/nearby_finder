package com.example.nearby_finder

import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("long")
    val long: Long,
    @SerializedName("lat")
    var lat: Long,
    @SerializedName("content")
    var name: String
)