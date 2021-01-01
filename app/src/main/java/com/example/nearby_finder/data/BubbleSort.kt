package com.example.nearby_finder.data

class BubbleSort {

    fun sortLength(list: MutableList<PlaceItem>): MutableList<PlaceItem> {
        var lastUnsorted = list.size - 1
        var isSorted = false

        return if (list.size <= 1) {
            list
        } else {
            while (!isSorted) {
                isSorted = true
                for (i in 0 until lastUnsorted) {
                    if (list[i].name.count() > list[i + 1].name.count()) {
                        swapValues(list, i, i + 1)
                        isSorted = false
                    }
                }
                lastUnsorted--
            }
            list
        }
    }

    fun sortAlphabetical(list: MutableList<PlaceItem>): MutableList<PlaceItem>  {
        var lastUnsorted = list.size - 1
        var isSorted = false

        return if (list.size <= 1) {
            list
        } else {
            while (!isSorted) {
                isSorted = true
                for (i in 0 until lastUnsorted) {
                    if (list[i].name > list[i + 1].name) {
                        swapValues(list, i, i + 1)
                        isSorted = false
                    }
                }
                lastUnsorted--
            }
            list
        }
    }

    private fun swapValues(list: MutableList<PlaceItem>, a: Int, b: Int) {
        val temp = list[b]
        list[b] = list[a]
        list[a] = temp
    }
}