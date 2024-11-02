package leetcode.arraystring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.min

// https://leetcode.com/problems/trapping-rain-water/?envType=study-plan-v2&envId=top-interview-150

private class TrapRainwaterSolution {
    fun trap2(height: IntArray): Int {
        var leftIndex = 0
        var rightIndex: Int = height.size-1
        var leftMax = 0
        var rightMax = 0
        var sum = 0

        // repeat until our bounds pointers meet
        // they start at the beginning and end of the list
        while (leftIndex < rightIndex) {
            // if the start is lower than the end (is an increasing progression)
            // height increases from start to end
            // 10934
            if (height[leftIndex] < height[rightIndex]) {
                // we do one of two things
                // either the left is increasing (leftMax is < height[leftIndex])
                // or it's decreasing and trapping water
                // < is okay because if leftMax == height[leftIndex], sum += 0
                if (leftMax < height[leftIndex]) {
                    leftMax = height[leftIndex]
                } else {
                    sum += leftMax - height[leftIndex]
                }
                leftIndex++
            } else {
                // height decreases from start to end
                // the left value has more height, so the right pointer determines the amount trapped
                // either the right pointer points to a bound or its max needs to be updated
                // 10201
                if (rightMax < height[rightIndex]) {
                    rightMax = height[rightIndex]
                } else {
                    sum += rightMax - height[rightIndex]
                }
                rightIndex--
            }
        }
        return sum
    }

    fun trap(height: IntArray): Int {
        if (height.size < 3) return 0

        var leftBound: Int = 0
        var rightBound: Int = 0

        var i = 0
        var totalTrapped = 0

        while (i < height.size - 1) {
            if (height[i] <= height[i + 1]) {
                i++
                continue
            }

            // height[i] > height[i + 1]; is decreasing
            leftBound = i
            rightBound = findRightBound(height, leftBound)
            if (rightBound == -1) break

            i = rightBound

            val trapped = computeTrapped(height, leftBound, rightBound)
            totalTrapped += trapped
        }
        return totalTrapped
    }

    private fun findRightBound(height: IntArray, leftBound: Int): Int {
        var localMaxIndex = -1
        var localMax = -1
        var i = leftBound + 1
        while (i < height.size) {
            if (height[i] > localMax) {
                localMax = height[i]
                localMaxIndex = i
            }
            if (height[i] >= height[leftBound]) break
            i++
        }
        return localMaxIndex
    }

    private fun computeTrapped(height: IntArray, leftBound: Int, rightBound: Int): Int {
        val maxHeight = min(height[leftBound], height[rightBound])
        return (leftBound + 1 until rightBound).sumOf { maxHeight - height[it] }
    }
}

private data class TrapRainwaterTestCase(
    val input: IntArray,
    val expected: Int,
)

private class TrapRainwaterTest {
    companion object {
        @JvmStatic
        fun getTestData() = listOf(
            TrapRainwaterTestCase(
                intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1),
                6
            ),
            TrapRainwaterTestCase(
                intArrayOf(0,2,0,1,1,0,1,3,2,1,2,1),
                8
            ),
            TrapRainwaterTestCase(
                intArrayOf(4,2,0,3,2,5),
                9
            ),
            TrapRainwaterTestCase(
                intArrayOf(),
                0
            ),
            TrapRainwaterTestCase(
                intArrayOf(1000),
        0
            ),
            TrapRainwaterTestCase(
                intArrayOf(1000, 10_000),
        0
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(case: TrapRainwaterTestCase) {
        val actual = TrapRainwaterSolution().trap(case.input)
        Assertions.assertEquals(case.expected, actual)
    }
}