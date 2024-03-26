package com.example.test.codingexcercise.csv


fun main() {
    val solution = Solution()
    println(
        minNoRoomRequired(
            arrayOf(
                intArrayOf(8, 12), intArrayOf(2, 7), intArrayOf(3, 19), intArrayOf(16, 19),
                intArrayOf(1, 10), intArrayOf(10, 20), intArrayOf(11, 30), intArrayOf(13, 15),
                intArrayOf(17, 19), intArrayOf(19, 21), intArrayOf(18, 21), intArrayOf(15, 19)
            )
        )
    )
}

/**
 *
 * intArrayOf(1, 10), intArrayOf(2, 7), intArrayOf(3, 19),
 * intArrayOf(8, 12), intArrayOf(10, 20), intArrayOf(11, 30)
 * first sort by start time
 *
 * prepare a map for room no and end time
 * initialy map[1] = endtime
 * iterate through that if next item start time > first item end time then it will use same conf room
 * map[roomNo] = max (end time)
 *
 * then consider end time as next item's
 * i.e. end time = maxOf (first's end, second's end)
 */
fun minNoRoomRequired(intervals: Array<IntArray>): Int {
    var roomNo = 0
    val map = mutableMapOf<Int, Int>()
    intervals.sortBy { it[0] } // sort by start time
    map[++roomNo] = intervals.first().last()
    intervals.drop(1).map { nextItem ->
        val foundRoom = map.keys.find {
            map[it] != null && nextItem.first() >= map[it]!!
        }
        if (foundRoom != null) {
            //it will reuse the same room
            map[foundRoom] = nextItem.last()
        } else {
            //it will go into new room
            map[++roomNo] = nextItem.last()
        }
    }
    return roomNo
}
