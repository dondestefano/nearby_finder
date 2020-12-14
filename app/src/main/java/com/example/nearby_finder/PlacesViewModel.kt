package com.example.nearby_finder

import androidx.lifecycle.ViewModel
import com.example.nearby_finder.data.DummyData

class PlacesViewModel: ViewModel() {

    val places: List<DummyData> = createDummyData()


    private fun createDummyData(): List<DummyData> {

        val placeOne = DummyData("Pepe's Italian", "Pizzeria", 125, "https://i.pinimg.com/originals/dc/ca/20/dcca201c915e6b8be4d5b9385c343662.jpg")
        val placeTwo = DummyData("Dirty Diego's Tacos", "Taqueria", 50, "http:image")
        val placeThree = DummyData("Greasy Joe's Platter", "Grill", 500, "https://media-cdn.tripadvisor.com/media/photo-s/10/59/74/be/greasy-spoon-hagagatan.jpg")

        return mutableListOf(placeOne, placeTwo, placeThree)
    }
}