package com.example.nearby_finder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class PlaceItem (
        @PrimaryKey
        @ColumnInfo (name= "name") val name: String,
        @ColumnInfo (name= "address") val address: String,
        @ColumnInfo (name= "img_url") val imageUrl: String,
        @ColumnInfo (name= "salt") val salt: String,
        @ColumnInfo (name= "iv") val iv: String
)
