package com.example.myapplication.test

/**
 * first push or enqueue in stack/queue.
 * then pop/deque item if stack/queue is not empty.
 * check if it's not unvisited.
 * if not mark it as visited
 * enqueue/push all unvisited adjacent to queue/stack
 * repeat it
 */
fun main() {
    /*println(numIslands(arrayOf(charArrayOf('1','1','1','1','0'), charArrayOf('1','1','0','1','0'),
        charArrayOf('1','1','0','0','0'), charArrayOf('0','0','0','0','0'))))
    println(countSubIslands(arrayOf(intArrayOf(1,1,1,0,0), intArrayOf(0,1,1,1,1),
        intArrayOf(0,0,0,0,0), intArrayOf(1,0,0,0,0), intArrayOf(1,1,0,1,1)),
        arrayOf(intArrayOf(1,1,1,0,0), intArrayOf(0,0,1,1,1),
            intArrayOf(0,1,0,0,0), intArrayOf(1,0,1,1,0), intArrayOf(0,1,0,1,0))))*/

    println(maxAreaOfIsland(arrayOf(intArrayOf(1,1,0,0,0), intArrayOf(1,1,0,0,0),
        intArrayOf(0,0,0,1,1), intArrayOf(0,0,0,1,1)
    )))
}

fun numIslands(grid: Array<CharArray>): Int {
    var noOfIslands = 0
    grid.forEachIndexed {
        row, charArray ->
        charArray.forEachIndexed {
            column, char ->
            if (grid[row][column] == '1') {
                dfs(grid, row, column)
                noOfIslands ++
            }
        }
    }
    return noOfIslands
}

fun dfs(grid: Array<CharArray>, row: Int, column: Int) {
    if (row < 0 || column < 0 || row >= grid.size || column >= grid[0].size ) {
        return
    }
    if (grid[row][column] == '0') {
        return
    }
    grid[row][column] = '0'
    dfs(grid, row + 1, column)
    dfs(grid, row, column + 1)
    dfs(grid, row -1 , column)
    dfs(grid, row, column - 1)
}

fun countSubIslands(grid1: Array<IntArray>, grid2: Array<IntArray>): Int {
    /*var subIsland = 0
    grid2.forEachIndexed {
        row, item ->
        run {
            item.forEachIndexed { column, value ->
                run {
                    if (grid2[row][column] == 1) {
                        if (dfs1(grid1, grid2, row, column)) {
                            subIsland++
                        }
                    }
                }
            }
        }
    }*/
    var ans = 0
    for (i in grid2.indices) {
        for (j in grid2[0].indices) {
            if (grid2[i][j] == 1 && dfs1(grid1, grid2, i, j)) {
                ans++
            }
        }
    }

    return ans
    //return subIsland
}

fun dfs1(grid1: Array<IntArray>, grid2: Array<IntArray>, row: Int, column: Int): Boolean {
    if (row < 0 || column < 0 || row >= grid2.size || column >= grid2[0].size || grid2[row][column] == 0) {
        return true
    }
    var ans = grid1[row][column] == 1
    grid2[row][column] = 0
    for ((r,c) in dirs) {
        ans = ans and dfs1(grid1, grid2, row + r, column + c)
    }
    return ans

    /*if (row < 0 || row >= grid2.size || col < 0 || col >= grid2[0].size || grid2[row][col] == 0) return true

    var ans = grid1[row][col] == 1

    grid2[row][col] = 0
    for ((r, c) in dirs) {
        ans = ans and dfs(grid1, grid2, row + r, col + c)
    }

    return ans*/
}

/**
 * [0,0,1,0,0,0,0,1,0,0,0,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * [0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
 */
fun maxAreaOfIsland(grid: Array<IntArray>): Int {
    var maxArea = 0
    grid.forEachIndexed {
        row, item ->
        item.forEachIndexed {
            column, _ ->
            run {
                if (grid[row][column] == 1) {
                    dfsForInt(grid, row, column)
                    maxArea = maxOf(maxArea, area)
                    area = 0
                }
            }
        }
    }
    return maxArea
}

var area = 0

fun dfsForInt(grid: Array<IntArray>, row: Int, column: Int) {
    if (row < 0 || column < 0 || row >= grid.size || column >= grid[0].size || grid[row][column] == 0) {
        return
    }
    area ++
    grid[row][column] = 0
    for ((i, j) in dirs) {
        dfsForInt(grid, row + i, column + j)
    }
}


val dirs = arrayOf (
    intArrayOf(-1, 0),
    intArrayOf(1, 0),
    intArrayOf(0, -1),
    intArrayOf(0, 1)
)