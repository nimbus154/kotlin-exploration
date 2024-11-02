package leetcode

import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class BuyAndSellStockSolution2 {
    data class Result(
        val smallest: Int = Int.MAX_VALUE,
        val max: Int = Int.MIN_VALUE,
    )

    // |prices| 30k
    // values 0-10k
    fun maxProfits(prices: IntArray): Int {
        return 0
    }
}

class BuyAndSellStock2Test {

    @Test
    fun `real example`() {
        val input = intArrayOf(7,1,5,3,6,4)
        val actual = BuyAndSellStockSolution2().maxProfits(input)
        assertEquals(7, actual)
    }

    @Test
    fun `ascending values`() {
        val input = intArrayOf(1,2,3,4,5)
        val actual = BuyAndSellStockSolution2().maxProfits(input)
        assertEquals(4, actual)
    }

    @Test
    fun `no profit`() {
        val input = intArrayOf(7,6,4,3,1)
        val actual = BuyAndSellStockSolution2().maxProfits(input)
        assertEquals(0, actual)
    }
}
