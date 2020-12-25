package com.example.nearby_finder.data

class BubbleSort {

    fun sortList(list: MutableList<PlaceItem>): MutableList<PlaceItem> {
        var lastUnsorted = list.size - 1
        var numSwaps = 0
        var isSorted = false

        if (list.size <= 1) {
            return list
        } else {
            while (!isSorted) {
                isSorted = true
                for (i in 0 until lastUnsorted) {
                    if (list[i].name.count() > list[i + 1].name.count()) {
                        swapValues(list, i, i + 1)
                        numSwaps++
                        isSorted = false
                    }
                }
                lastUnsorted--
            }
            return list
        }
    }

    private fun swapValues(list: MutableList<PlaceItem>, a: Int, b: Int) {
        val temp = list[b]
        list[b] = list[a]
        list[a] = temp
    }
}