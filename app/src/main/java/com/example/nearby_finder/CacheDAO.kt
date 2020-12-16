package com.example.nearby_finder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nearby_finder.data.DummyData

@Dao
interface CacheDAO {
    @Query("SELECT * FROM place")
    fun getAll(): LiveData<List<DummyData>>

    @Query("SELECT * from place WHERE name = :name")
    fun get(name: String): DummyData?

    @Query("SELECT * FROM place WHERE name IN (:placeNames)")
    fun loadAllByIds(placeNames: Array<String>): List<DummyData>

    @Insert
    fun insert (place: DummyData)

    @Insert
    fun insertAll(vararg places: DummyData)

    @Delete
    fun delete(place: DummyData)

    @Query("DELETE FROM place")
    fun deleteAll()
}