package com.example.myapplication.test


fun main() {

    //1,2, -3
    //1, 2, -2

    //1, 3,
    println(printZeroSumSubarray(intArrayOf(1, 2, 7, -4, -3, -2)))
}

fun hasZeroSumSubarray(nums: IntArray): Boolean? {
    // create an empty set to store the sum of elements of each
    // subarray `nums[0…i]`, where `0 <= i < nums.length`
    // input: 1, 3, 9, 2, 5, -7

    // 13 + zerosum = 13
    // set 0, 1, 4, 13, 15, 20, 13
    val set: MutableSet<Int> = HashSet()

    // insert 0 into the set to handle the case when subarray with
    // zero-sum starts from index 0
    set.add(0)
    var sum = 0

    // traverse the given array
    for (value in nums) {
        // sum of elements so far
        sum += value

        // if the sum is seen before, we have found a subarray with zero-sum
        if (set.contains(sum)) {
            return true
        }

        // insert sum so far into the set
        set.add(sum)
    }

    // we reach here when no subarray with zero-sum exists
    return false
}

fun printZeroSumSubarray(nums: IntArray): Boolean? {
    val set: MutableMap<Int, Int> = mutableMapOf()
    var sum = 0
    set.put(sum, -1)
    nums.forEachIndexed { ind, valueSum ->
        sum += valueSum

        if (set.containsKey(sum)) {
            val subArray = nums.filterIndexed { index, _ ->
                index > set.get(key = sum)!! && index <= ind
            }
            println("sub array is : ${subArray.toList()}")
        }

        set.put(sum, ind)

    }
    return false
}

// Utility function to insert <key, value> into the multimap
private fun <K, V> insert(hashMap: MutableMap<K, MutableList<V>>, key: K, value: V) {
    // if the key is seen for the first time, initialize the list
    hashMap.putIfAbsent(key, ArrayList())
    hashMap[key]!!.add(value)
}

// Function to print all subarrays with a zero-sum in a given array
fun printAllSubarrays(nums: IntArray) {
    // create an empty multimap to store the ending index of all
    // subarrays having the same sum
    val hashMap: MutableMap<Int, MutableList<Int>> = HashMap()

    // insert (0, -1) pair into the map to handle the case when
    // subarray with zero-sum starts from index 0
    insert(hashMap, 0, -1)
    var sum = 0

    // traverse the given array
    for (i in nums.indices) {
        // sum of elements so far
        sum += nums[i]

        // if the sum is seen before, there exists at least one
        // subarray with zero-sum
        if (hashMap.containsKey(sum)) {
            val list: List<Int> = hashMap[sum]!!

            // find all subarrays with the same sum
            for (value: Int in list) {
                println(
                    "Subarray [" + (value + 1) + "…" +
                            i + "]"
                )
            }
        }

        // insert (sum so far, current index) pair into the multimap
        insert(hashMap, sum, i)
    }
}

