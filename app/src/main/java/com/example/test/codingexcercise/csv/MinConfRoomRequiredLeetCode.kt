package com.example.test.codingexcercise.csv

import java.util.PriorityQueue

class Solution {
    fun minMeetingRooms(intervals: Array<IntArray>): Int {
        // Check for the base case. If there are no intervals, return 0
        if (intervals.isEmpty()) {
            return 0
        }

        // Min heap
        val allocator = PriorityQueue<Int>()

        // Sort the intervals by start time
        intervals.sortWith(compareBy { it[0] })

        // Add the first meeting
        allocator.add(intervals[0][1])

        // Iterate over remaining intervals
        for (i in 1 until intervals.size) {
            // If the room due to free up the earliest is free, assign that room to this meeting.
            if (intervals[i][0] >= allocator.peek()) {
                allocator.poll()
            }
            // If a new room is to be assigned, then also we add to the heap,
            // If an old room is allocated, then also we have to add to the heap with updated end time.
            allocator.add(intervals[i][1])
        }

        // The size of the heap tells us the minimum rooms required for all the meetings.
        return allocator.size
    }
}