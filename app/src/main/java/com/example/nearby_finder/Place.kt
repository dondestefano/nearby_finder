package com.example.nearby_finder

import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("content")
    var name: String
)