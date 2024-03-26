package com.example.test.dsa

fun islandPerimeter(grid: Array<IntArray>): Int {
    grid.forEachIndexed { row, ints ->
        ints.forEachIndexed { column, i ->
            if (grid[row][column] == 1) {
                isLandDfs(grid, row, column)
                return perimeter
            }
        }
    }
    return perimeter
}

var perimeter = 0

fun isLandDfs(grid: Array<IntArray>, row: Int, column: Int) {
    if (row < 0 || column < 0 || row >= grid.size || column >= grid[0].size) {
        perimeter ++
        return
    }
    if (grid[row][column] == 0) {
        perimeter ++
        return
    }
    if (grid[row][column] == 2) {
        return
    }
    grid[row][column] = 2 // visited
    for ((r, c) in arr) {
        isLandDfs(grid, row + r, column + c)
    }
}

val arr = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))

/**
 * 0 1 1
 * 0 1 1
 * 1 0 1
 *
 * 0 1 0 0
 * 1 1 1 0
 * 0 1 0 0
 * 1 1 0 0
 */
fun main() {
    println(islandPerimeter(arrayOf(intArrayOf(0,1,0,0), intArrayOf(1,1,1,0),
        intArrayOf(0,1,0,0), intArrayOf(1,1,0,0)
    )))
}