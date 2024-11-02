package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TripletSum {
    fun threeSum(nums: IntArray): List<List<Int>> {
        return listOf()
    }
}

class TripletSumTest {

    @Test
    fun `all 0s`() {
        val input = intArrayOf(0, 0, 0)
        val expected = listOf(
            listOf(0, 0, 0)
        )
        val actual = TripletSum().threeSum(input)

        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun `no correct answers`() {
        val input = intArrayOf(0, 1, 1)
        val expected: List<List<Int>> = listOf()
        val actual = TripletSum().threeSum(input)

        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    private fun assertEquals(expected: List<List<Int>>, actual: List<List<Int>>) {
        val expectedSet = expected.map {
            it.toSet()
        }.toSet()

        val actualSet = actual.map {
            it.toSet()
        }.toSet()

        assertEquals(expectedSet, actualSet)
    }
}