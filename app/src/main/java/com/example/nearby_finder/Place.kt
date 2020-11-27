package com.example.nearby_finder

import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("completed")
    var completed: Boolean
)