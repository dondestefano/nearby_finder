package com.example.nearby_finder.data

import androidx.room.*

@Dao
interface CacheDao {

    @Query("SELECT * from place WHERE name = :name")
    fun get(name: String): PlaceItem?

    @Query("select * from place")
    fun getPlaces(): MutableList<PlaceItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(placeItems: List<PlaceItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(placeItem: PlaceItem)

    @Query("DELETE FROM place")
    suspend fun deleteAll()
}