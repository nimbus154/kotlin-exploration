package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

// https://leetcode.com/problems/counting-bits/description/
class CountingBitsSolution {
    fun isPowerOfTwo(n: Int): Boolean = (n and (n - 1)) == 0

    fun countBits(n: Int, ans: IntArray = intArrayOf(0)): IntArray {
        if (n == 0) return ans
        if (n == 1) return ans + 1

        return (2..n).fold(0 to listOf(0, 1)) { (powerOfTwo, acc), i ->
            if (isPowerOfTwo(i)) i to (acc + 1)
            else {
                val bits = 1 + acc[i - powerOfTwo]
                powerOfTwo to (acc + bits)
            }
        }
        .second
        .toIntArray()

    }
}

// Given an integer n, return an array ans of length n + 1
// such that for each i (0 <= i <= n),
// ans[i] is the number of 1's in the binary representation of i.

class CountingBitsTest {
    // 2, [0,1,1]
    // 5, [0,1,1,2,1,2]
    // 0...n
    // how do you count bits in a number?
    // how do you count bits in a number, based on a previous number?
    // 0 -> 0 -> 0
    // 1 -> 1 -> 1
    // 2 -> 10 -> 1
    // 3 -> 11 -> 2
    // 4 -> 100 -> 1
    // 5 -> 101 -> 2
    // 6 -> 110 -> 2
    // 7 -> 111 -> 3
//    @ParameterizedTest
    // three digits are all of the two digits plus 1
    // two digits are single digits plus one
    // cb(2) = 1 + cb(0)
    // cb(3) = 1 + cb(1)
    // cb(4) = 1 + cb(0)  "edge cases" are true powers of two log2(4) = 2 "is a power of two"
    // cb(5) = 1 + cb(1)  (cb(5 - 4)
    // cb(6) = 1 + cb(2)  (cb(6 - 4))
    // cb(7) = 1 + cb(7 - 4) = 1 + cb(3) = 1 + 2 = 3

    // (x & (x - 1)) == 0
    // x = 10
    // x - 1 = 01 -> 0
    // 3 & 2 = 11 & 10 = 10
    // 4 & 3 = 100 & 11 = 0
    // 5 & 4 = 101 & 100 = 001

    val answers = intArrayOf(0,1,1,2,1,2,2,3)

    @ParameterizedTest
    @ValueSource(ints = [0,1,2,3,4,5,6,7])
    fun test(n: Int) {
        val actual = CountingBitsSolution().countBits(n).toList()
        val expected = answers.slice(0..n)
        assertEquals(expected, actual)
    }
}