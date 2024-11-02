package leetcode

import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class ContainerSolution {
    // input: height, IntArray
    //  each entry represents a line of height h[i]
    // output: max amount of water a container can store (area)
    tailrec fun maxArea(heights: IntArray, low: Int = 0, high: Int = heights.size - 1, currentMax: Int = 0): Int {
        // 2 <= height.size <= 100,000
        // 0 <= height[i] <= 10,000
        // answer is some h[i] * abs(i - j)
        // where i and j are indices of endpoints
        // largest size starting at a given index
        // when do we stop measuring? when does the container become invalid?
        // is it just all pairs differences?
        // what is the formula for area?
        // stopIndex - startIndex * min(h[startIndex], h[stopIndex])
        // 1 - 0 * 1
        // 8 - 1 * 7 = 49

        // intArrayOf(1,8,6,2,5,4,8,3,7)
        // observation: need a combination of max diff between indices and height
        // need a combo of big diff and high height
        // can we smartly eliminate?
        // O(n^2) is easy
        // O(n) possible?
        if (low >= high) return currentMax
        val candidateMax = (high - low) * min(heights[low], heights[high])
        val newMax = max(currentMax, candidateMax)
        return if (heights[low] > heights[high]) maxArea(heights, low, high - 1, newMax)
        else maxArea(heights, low + 1, high, newMax)
    }
}

class ContainerWithMostWaterTest {

    @Test
    fun `example`() {
        val heights = intArrayOf(1,8,6,2,5,4,8,3,7)
        val area = ContainerSolution().maxArea(heights)
        assertEquals(49, area)
    }

    @Test
    fun `example2`() {
        val heights = intArrayOf(1,8,6,2,5,4,8,3,8,7)
        val area = ContainerSolution().maxArea(heights)
        assertEquals(56, area)
    }

    @Test
    fun `1,1`() {
        val heights = intArrayOf(1, 1)
        val area = ContainerSolution().maxArea(heights)
        assertEquals(1, area)
    }
}