package com.example.nearby_finder

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nearby_finder.data.DummyData

@Dao
interface CacheDao {

    @Query("SELECT * from place WHERE name = :name")
    fun get(name: String): DummyData?

    @Query("select * from place")
    fun getPlaces(): LiveData<List<DummyData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( places: List<DummyData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: DummyData)

    @Query("DELETE FROM place")
    fun deleteAll()
}