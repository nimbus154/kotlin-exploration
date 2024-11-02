package leetcode

import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class BuyAndSellStockSolution {
    data class Result(
        val smallest: Int = Int.MAX_VALUE,
        val max: Int = Int.MIN_VALUE,
    )

    fun maxProfits(prices: IntArray): Int {
        val result = prices.fold(Result()) { acc, i ->
            Result(
                min(acc.smallest, i),
                max(acc.max, i - acc.smallest),
            )
        }
        return max(0, result.max)
    }
        // buy on one day, sell on another
        // buy day must be before sell day
        // max difference between
        // max prices length is 100k
        // naive solution:
        // n^2
        // compare every value with every value after it
        // max diff is the result
        // is there a faster way?
        // can probably be done in n
        // for every value
        // smallest value seen so far
        // max profit so far
}

class BuyAndSellStockTest {

    @Test
    fun `real example`() {
        val input = intArrayOf(7,1,5,3,6,4)
        val actual = BuyAndSellStockSolution().maxProfits(input)
        assertEquals(5, actual)
    }

    @Test
    fun `no profit`() {
        val input = intArrayOf(7,6,4,3,1)
        val actual = BuyAndSellStockSolution().maxProfits(input)
        assertEquals(0, actual)
    }
}