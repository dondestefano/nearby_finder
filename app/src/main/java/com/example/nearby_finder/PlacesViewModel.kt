package com.example.nearby_finder

import androidx.lifecycle.ViewModel
import com.example.nearby_finder.data.DummyData

class PlacesViewModel: ViewModel() {

    val places: List<DummyData> = createDummyData()


    private fun createDummyData(): List<DummyData> {

        val placeOne = DummyData("Pepe's Italian", "Pizzeria", 125, "http:image")
        val placeTwo = DummyData("Dirty Diego's Tacos", "Taqueria", 50, "http:image")
        val placeThree = DummyData("Greasy Joe's Platter", "Grill", 500, "http:image")

        return mutableListOf(placeOne, placeTwo, placeThree)
    }
}