package com.example.nearby_finder.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.libraries.places.api.model.Place
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.collection.IsIterableContainingInOrder
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class BubbleSortTest {
    private val bubbleSort = BubbleSort()

    @Test
    fun itemsSortedCorrectly() {
        val itemA = PlaceItem("This should be last", "3", "Last")
        val itemB = PlaceItem("First", "1", "First")
        val itemC = PlaceItem("I'm number 2", "2", "Second")
        val itemsToSort = mutableListOf(itemA, itemB, itemC)
        val sortedItems = mutableListOf(itemB, itemC, itemA)

        bubbleSort.sortList(itemsToSort)

        assertEquals(sortedItems, itemsToSort)
    }
}