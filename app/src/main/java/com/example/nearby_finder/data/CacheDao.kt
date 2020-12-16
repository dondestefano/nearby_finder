package com.example.nearby_finder.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nearby_finder.data.DummyData
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {

    @Query("SELECT * from place WHERE name = :name")
    fun get(name: String): DummyData?

    @Query("select * from place")
    fun getPlaces(): Flow<List<DummyData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( places: List<DummyData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: DummyData)

    @Query("DELETE FROM place")
    suspend fun deleteAll()
}