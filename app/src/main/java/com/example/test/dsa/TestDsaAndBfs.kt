package com.example.test.dsa

import java.util.LinkedList

import java.util.Queue


/**
 * first push or enqueue in stack/queue.
 * then pop/deque item if stack/queue is not empty.
 * check if it's not unvisited.
 * if not mark it as visited
 * enqueue/push all unvisited adjacent to queue/stack
 * repeat it
 */

fun main() {
    /*rottenOrangesTimeBfs(arrayOf(intArrayOf(2,1,1), intArrayOf(0,1,1),
        intArrayOf(1,0,1)
    ))
    println(findJudge(3, arrayOf(intArrayOf(1, 3), intArrayOf(2, 3))))*/
    /*plainDsa(arrayOf(intArrayOf(0,1), intArrayOf(0,2),
        intArrayOf(1,3), intArrayOf(1,4)), 2, 5)*/
    /*plainBfsMatrix(arrayOf(intArrayOf(0,1), intArrayOf(0,2),
        intArrayOf(1,3), intArrayOf(1,4)), 2, 5)*/
    plainBfsList(arrayOf(intArrayOf(0,1), intArrayOf(0,2),
        intArrayOf(1,3), intArrayOf(1,4)), 2, 5)
}

fun orangesRotting(grid: Array<IntArray>): Int {
    var maxCount = 0
    grid.forEachIndexed {
        row, value ->
        run {
            value.forEachIndexed {
                column, _ ->
                run {
                    if (grid[row][column] == 2) {
                        // rotten orange
                        dsf(grid, row, column)
                        maxCount = maxOf(maxCount, localCount)
                        localCount = 0
                    }
                }
            }
        }
    }
    return maxCount
}

var localCount = 0

fun dsf(grid: Array<IntArray>, row: Int, column: Int) {
    if (row < 0 || column < 0 || row >= grid.size -1 || column >= grid[0].size) {
        return
    }
    if (grid[row][column] == 1 || grid[row][column] == 0) {
        return
    }
    localCount ++
    grid[row][column] = 2
    dsf(grid, row + 1, column)
    dsf(grid, row - 1, column)
    dsf(grid, row, column + 1)
    dsf(grid, row, column - 1)
}

fun rottenOrangesTimeBfs(grid: Array<IntArray>) {
    var time = 0
    val isVisited = Array(grid.size){
        BooleanArray(grid[0].size)
    }
    val queue: Queue<Pair<Int, Pair<Int, Int>>> = LinkedList()
    grid.forEachIndexed {
        row, values ->
        run {
            values.forEachIndexed {
                column, _ ->
                if (grid[row][column] == 2) {
                    queue.add(Pair(0, Pair(row, column)))
                }
            }
        }
    }
    while (queue.isNotEmpty()) {
        val poppedItem = queue.remove()
        val row = poppedItem.second.first
        val column = poppedItem.second.second
        if (row < 0 || column < 0 || row >= grid.size || column >= grid[0].size || isVisited[row][column]) {
            continue
        }
        if (grid[row][column] == 0) {
            continue
        }
        time = poppedItem!!.first // initialization place is imp.
        /*println("poppedItem is: $poppedItem")
        println("${grid[row][column]}")*/
        isVisited[row][column] = true
        grid[row][column] = 2
        queue.add(Pair(time+1, Pair(row + 1, column)))
        queue.add(Pair(time+1, Pair(row - 1, column)))
        queue.add(Pair(time+1, Pair(row, column + 1)))
        queue.add(Pair(time+1, Pair(row, column - 1)))
    }
    grid.forEachIndexed {
            row, values ->
        run {
            values.forEachIndexed {
                    column, _ ->
                if (grid[row][column] == 1) {
                    return
                }
            }
        }
    }
    println("total time is: $time")
}

fun BFS(grid: Array<IntArray>) {
    val h = grid.size
    if (h == 0) return
    val l = grid[0].size
    val visited = Array(h) {
        BooleanArray(
            l
        )
    }
    val queue: Queue<String> = LinkedList()
    queue.add(0.toString() + "," + 0)
    println("Breadth-First Traversal: ")
    while (!queue.isEmpty()) {
        val x = queue.remove()
        val row = x.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toInt()
        val col = x.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].toInt()
        if (row < 0 || col < 0 || row >= h || col >= l || visited[row][col]) continue
        visited[row][col] = true
        print(grid[row][col].toString() + " ")
        queue.add(row.toString() + "," + (col - 1)) //go left
        queue.add(row.toString() + "," + (col + 1)) //go right
        queue.add((row - 1).toString() + "," + col) //go up
        queue.add((row + 1).toString() + "," + col) //go down
    }
}

fun findJudge(n: Int, trust: Array<IntArray>): Int {
    val map = HashMap<Int, MutableSet<Int>>()
    trust.forEachIndexed { _, ints ->
        if (map.containsKey(ints[1])) {
            val values = map[ints[1]]!!.toMutableSet()
            values.add(ints[0])
            map[ints[1]] = values
        } else {
            map[ints[1]] = mutableSetOf(ints[0])
        }
        /*ints.forEachIndexed { column, valuex ->
            if (map.containsKey(column)) {
                val values = map[column]!!.toMutableSet()
                values.add(row)
                map[column] = values
            } else {
                map[column] = mutableSetOf(row)
            }
        }*/
    }
    map.keys.iterator().forEach {
        if (map[it]!!.size == n - 1) {
            var duplicate = false
            map.values.iterator().forEach { second ->
                if (second.contains(it)) {
                    duplicate = true
                }
            }
            if (!duplicate) {
                return it
            }
        }
    }
    return -1
}

fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray> {
    val colorWhichNeedToChange = image[sr][sc]
    dfs(image, sr, sc, color, colorWhichNeedToChange)
    return image
}

fun dfs(image: Array<IntArray>, sr: Int, sc: Int, color: Int, colorWhichNeedToChange: Int) {
    if (sr < 0 || sc < 0 || sr >= image.size || sc >= image[0].size) {
        return
    }
    if (image[sr][sc] == color || image[sr][sc] != colorWhichNeedToChange) {
        return
    }
    image[sr][sc] = color
    dfs(image, sr + 1, sc, color, colorWhichNeedToChange)
    dfs(image, sr - 1, sc, color, colorWhichNeedToChange)
    dfs(image, sr, sc + 1, color, colorWhichNeedToChange)
    dfs(image, sr, sc - 1, color, colorWhichNeedToChange)
}

fun findIfPathExists() {

}

/**
 * 0 1
 * 1 0
 *
 */
fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
    return false
}

fun plainDsa(edges: Array<IntArray>, source: Int, n: Int) {
    // fill the matrix with connection state if edges matrix only shows the connection between node/vertex
    // [[0,1],[1,2],[2,0]]
    // above shows that 0 is connected with 1, 1 is connected with 2 and so on
    // most cases like island type question matrix will be already filled
    val matrix = Array(n) { IntArray(n) }
    for (edge in edges) {
        matrix[edge[0]][edge[1]] = 1
        matrix[edge[1]][edge[0]] = 1 // since undirected graph
    }
    val visited = BooleanArray(n)
    baseDsaMatrix(matrix, source, visited)
}

fun baseDsaMatrix(edges: Array<IntArray>, start: Int, visited: BooleanArray) {
    visited[start] = true
    println("\n$start")
    for (neighbour in 0 until edges[0].size) { // neighbour is y axis value i.e. row/node
        if (edges[start][neighbour] == 1 && !visited[neighbour]) {
            baseDsaMatrix(edges, neighbour, visited)
        }
    }
}

fun plainBfsMatrix(edges: Array<IntArray>, source: Int, n: Int) {
    val matrix = Array(n) {
        IntArray(n)
    }
    for (edge in edges) {
        matrix[edge[0]][edge[1]] = 1
        matrix[edge[1]][edge[0]] = 1
    }
    val visited = BooleanArray(n)
    val queue: Queue<Int> = LinkedList<Int>()
    queue.add(source)

    // start BFS traversing
    var start: Int
    while (queue.isNotEmpty()) {
        val item = queue.remove()
        start = item
        println("\n$item")
        visited[item] = true
        for (neighbour in 0 until n) {
            if (!visited[neighbour] && matrix[start][neighbour] == 1) {
                queue.add(neighbour)
            }
        }
    }
}

fun plainBfsList(edges: Array<IntArray>, source: Int, n: Int) {
    val dummy = ArrayList<Int>()
    dummy.add(3)
    dummy[0] = 2
    val adjacent = ArrayList<LinkedList<Int>>()
    for (index in 0 until n) {
        adjacent.add(LinkedList())
    }
    for (edge in edges) {
        adjacent[edge[0]].add(edge[1])
        adjacent[edge[1]].add(edge[0]) // since its undirected
    }
    val visited = BooleanArray(n)
    val queue: Queue<Int> = LinkedList()
    queue.add(source)

    while (queue.isNotEmpty()) {
        val removedItem = queue.remove()
        println("\n $removedItem")
        visited[removedItem] = true
        val adjacentList = adjacent[removedItem]
        for (item in adjacentList) {
            if (!visited[item]) {
                queue.add(item)
            }
        }
    }
}