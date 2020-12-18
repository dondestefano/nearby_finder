package com.example.nearby_finder.data

import androidx.room.*
import com.example.nearby_finder.data.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {

    @Query("SELECT * from place WHERE name = :name")
    fun get(name: String): Place?

    @Query("select * from place")
    fun getPlaces(): Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( places: List<Place>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: Place)

    @Query("DELETE FROM place")
    suspend fun deleteAll()
}