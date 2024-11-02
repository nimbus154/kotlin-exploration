package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.min

// https://leetcode.com/problems/count-square-submatrices-with-all-ones/description/
/*
Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.

Example 1:
Input: matrix =
[
  [0,1,1,1],
  [1,1,1,1],
  [0,1,1,1]
]
Output: 15
Explanation:
There are 10 squares of side 1.
There are 4 squares of side 2.
There is  1 square of side 3.
Total number of squares = 10 + 4 + 1 = 15.

Example 2:
Input: matrix =
[
  [1,0,1],
  [1,1,0],
  [1,1,0]
]
Output: 7
Explanation:
There are 6 squares of side 1.
There is 1 square of side 2.
Total number of squares = 6 + 1 = 7.

Constraints:
    1 <= arr.length <= 300
    1 <= arr[0].length <= 300
    0 <= arr[i][j] <= 1

 largest matrix is 300x300
 largest square size is min(n, m)
 Trivial cases (base cases) [0] -> 0, [1] -> 1
 Other base cases: all matrices of size 1
 imagine an array of: square size -> count of values of that square size
 "find all matrices of size 2", then sum the final array
 "find all square matrices of 1..min(n, m)"
 min running time is at least O(nm)

 How do you find a square matrix of size > 1, w/o repeats?
 "Is this spot the top left corner of a square matrix?"
 ...is there a trick I'm missing?
 */

class CountSquareSubmatricesSolution {
    private fun isSquareOfSize(matrix: Array<IntArray>, size: Int, row: Int, col: Int): Boolean {
        if (row + (size - 1) >= matrix.size) return false
        if (col + (size - 1) >= matrix[0].size) return false
        return (row until row + size).all { r1 ->
            (col until col + size).all { c1 -> matrix[r1][c1] == 1 }
        }
    }

    private fun countSquaresOfSize(matrix: Array<IntArray>, size: Int): Int {
        var count = 0
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                if (isSquareOfSize(matrix, size, i, j)) count++
            }
        }
        return count
    }

    fun countSquares1(matrix: Array<IntArray>): Int {
        val maxSquareSize = min(matrix.size, matrix[0].size)
        return (1..maxSquareSize)
            .sumOf { countSquaresOfSize(matrix, it) }
    }

    // if 1, + 1
    // if 1, also: if up, left, up-left are ones, + at least 1
    // dp
    // [0,1,1,1] 3
    // [1,1,2,2] 6
    // [0,1,2,3] 6 = 15
    // if entry is 1:
    // dp[entry] = 1 + min(m[u], m[l], m[lu])
    // matrix
//        [
//            [0,1,1,1],
//            [1,1,1,1],
//            [0,1,1,1]
//        ]
    fun countSquares(matrix: Array<IntArray>): Int {
        return matrix.fold((0 to intArrayOf())) { (total, dp), row ->
            val newDp = row.foldIndexed(intArrayOf()) { j, acc, entry ->
                val dpEntry =
                    if (entry == 0) 0
                    else 1 + listOf(acc.lastOrNull() ?: 0, dp.getOrElse(j) {0}, dp.getOrElse(j - 1) { 0 }).min()
                acc + dpEntry
            }
            (total + newDp.sum()) to newDp
        }.first
    }
}

class CountSquareSubmatricesTest {
    data class TestData (
        val input: Array<IntArray> = emptyArray(),
        val output: Int = 0
    )

    companion object {
        @JvmStatic
        fun getData(): List<TestData> = listOf(
            TestData(
                arrayOf(
                    intArrayOf(0,1,1,1),
                    intArrayOf(1,1,1,1),
                    intArrayOf(0,1,1,1),
                ),
                15
            ),
            TestData(
                arrayOf(
                    intArrayOf(1,0,1),
                    intArrayOf(1,1,0),
                    intArrayOf(1,1,0)
                ),
                7
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun test(testData: TestData) {
        val actual = CountSquareSubmatricesSolution().countSquares(testData.input)
        Assertions.assertEquals(testData.output, actual)
    }
}