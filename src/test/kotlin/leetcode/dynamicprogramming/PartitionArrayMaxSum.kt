package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.max

// https://leetcode.com/problems/partition-array-for-maximum-sum/description/

/*
Given an integer array arr, partition the array into (contiguous) subarrays
of length at most k. After partitioning, each subarray has their values changed
to become the maximum value of that subarray.
Return the largest sum of the given array after partitioning. Test cases are
generated so that the answer fits in a 32-bit integer.

Example 1:
Input: arr = [1,15,7,9,2,5,10], k = 3
Output: 84
Explanation: arr becomes [15,15,15,9,10,10,10]

Example 2:
Input: arr = [1,4,1,5,7,3,6,1,9,9,3], k = 4
Output: 83
[4,4,7,7,7,9,9,9,9,9,9]

Example 3:
Input: arr = [1], k = 1
Output: 1

Constraints:
    1 <= arr.length <= 500
    0 <= arr[i] <= 109
    1 <= k <= arr.length

 array can be partitioned a number of ways:
 if you remove k elements from some part of the array, you want the remaining parts to be maximized
 say arr.len = 5, k = 2,
     you can partition the array into groups of 1
     1 1 1 1 1
     2 1 1 1
     1 2 1 1
     1 1 2 1
     1 1 1 2
     2 1 2
     1 2 2
     2 2 1
 get some partition, then the max value of the remaining elements in the array
 k = 1 -> sum(array)

 max value(array) = max(
 each index in the array participates in the solution
 in some partition of (1..k) size

 k = 3
 max of
     a | b c d e
     a b | c d e
     a b c | d e
mafOf(
    sum(a, maxOf(b, c, d, e)
    sum(ab, maxOf(cde))
    sum(abc, de)
)

table = (starting index, partition size) -> sum
max of
    for i in 1 to k
        max of (arr.slice(0, i), maxOf(arr.slice(i, arr.size))
 could this be done bottom-up?
 sure, but how?
 index, partition size
 0, 1 -> elt
 1, 1 ->  elt


 */

// each element in the solution array ("dp") is the solution that array
// if that element was the last in the array
// consider arr [1,2,3] k = 500
// to start, we look at just 1, as if were the whole array
// even with k of 500, 1 is the only value we consider, so dp[0] = 1
// then we move to index 1 and consider the array [1, 2]
// now we consider k. k is really min(k, arr.size) so we treat k as 2
// our solutions are [1] [2] (k = 1, k = 1), for a total of 3, or [2, 2] (k = 2) for a total of 4
// so dp[1] = 4 (dp = [1, 4])
// moving to 3, k = min(k, arr.size) = 3
// possible solutions include [1,2,3], [[2,2],3], [1,[3,3] or [3,3,3]
// we evaluate, from this position maxSum(1..k slices of array) + dp[i - (1..k)]
// the max of these values is dp[2]
// with our array, that becomes the solution: dp.last()
class PartitionArrayMaxSumSolution {
    private fun maxSum(partition: IntArray): Int = partition.max() * partition.size

    fun maxSumAfterPartitioning(arr: IntArray, k: Int): Int {
        if (arr.size == 1) return arr.first()
        if (k == 1) return arr.sum()

        return arr.foldIndexed(emptyList()) { idx, dp: List<Int>, i ->
            val maxAtIdx = (0 until k).maxOf { k1 ->
                val start = max(idx - k1, 0)
                val partition = arr.slice(start..idx).toIntArray()
                maxSum(partition) + dp.getOrElse(start - 1) { 0 }
            }
            dp + maxAtIdx
        }.last()
    }

    fun maxSumAfterPartitioningRec(arr: IntArray, k: Int): Int {
        if (arr.isEmpty()) return 0
        if (arr.size == 1) return arr.first()
        if (k == 1) return arr.sum()
        return (1..k).maxOf { i ->
            val partitionIndex = i.coerceAtMost(arr.size)
            val p1 = arr.slice(0 until partitionIndex).toIntArray()
            val p2 = arr.slice(partitionIndex until arr.size).toIntArray()
            val p1Sum = maxSum(p1)
            val p2Sum = maxSumAfterPartitioningRec(p2, k)
            p1Sum + p2Sum
        }
    }
}

class PartitionArrayMaxSumTest {
    data class TestCase(
        val arr: IntArray,
        val k: Int,
        val output: Int
    )

    companion object {
        @JvmStatic
        fun getTestData(): List<TestCase> = listOf(
            TestCase(intArrayOf(1,15,7,9,2,5,10), 3, 84),
            TestCase(intArrayOf(1,4,1,5,7,3,6,1,9,9,3), 4, 83),
            TestCase(intArrayOf(1), 1, 1),
            TestCase(
                intArrayOf(20779,436849,274670,543359,569973,280711,252931,424084,361618,430777,136519,749292,933277,477067,502755,695743,413274,168693,368216,677201,198089,927218,633399,427645,317246,403380,908594,854847,157024,719715,336407,933488,599856,948361,765131,335089,522119,403981,866323,519161,109154,349141,764950,558613,692211),
                26,
                0
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testCase: TestCase) {
        val actual = PartitionArrayMaxSumSolution().maxSumAfterPartitioning(testCase.arr, testCase.k)
        Assertions.assertEquals(testCase.output, actual)
    }
}
