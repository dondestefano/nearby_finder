package com.example.nearby_finder.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "place")
data class DummyData (
    @ColumnInfo(name= "name") val name: String,
    @ColumnInfo (name= "type") val type: String,
    @ColumnInfo (name= "distance") val distance: Int,
    @ColumnInfo (name= "img_url") val imageUrl: String
)