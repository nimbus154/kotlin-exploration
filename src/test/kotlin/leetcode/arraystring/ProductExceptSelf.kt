package leetcode.arraystring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/*
https://leetcode.com/problems/product-of-array-except-self/description/?envType=study-plan-v2&envId=top-interview-150

Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

You must write an algorithm that runs in O(n) time and without using the division operation.

Example 1:
Input: nums = [1,2,3,4]
Output: [24,12,8,6]

Example 2:
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]

Constraints:
    2 <= nums.length <= 105
    -30 <= nums[i] <= 30
    The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */

/*
O(n), no division operator

easy solution would be production of all, then map divide by the entry. That's forbidden.

O(n^2) would be nested loop and skip the equal values

What is the product of the array? a[0] * a[1] * ... * a[size - 1]
O(n) doesn't necessarily mean one pass

Could I use some kind of bitwise operator instead?
Or could I use repeated subtraction?

We know every value divides the total.
Does any index tell us anything about other indexes?
Only if we have the product and can divide.

I am stuck.
Does the sum tell us anything about the product? Could I subtract from the sum,,,?

Prefix and suffix.
Consider two indexes. The prefixes and suffixes are mostly identical.
index 5 has prefix 0-4 and suffix 6-105,
index 7 has prefix 0-6 and suffix 8-105.
Can I compute prefix and suffixes as I go?

prefix[0] = a[0]
prefix[1] = prefix[i - 1] * a[1]
prefix[2] = prefix[i - 1] * a[2]

suffix[0] = suffix[size - 1] * a[0]

prefix = product of every value _at and before_ the index
suffix = product of every value _at and after_ the index

so "prefix" should be the product of every value up to that point
and suffix should be the product of every value after that point

the product of those two should be the result

return prefix[i - 1] + suffix[i + 1]

 */

private class ProductExceptSelfSolution {
    // O(1) space complexity
    fun productExceptSelf(nums: IntArray): IntArray {
        val output = IntArray(nums.size)

        var prefix = 1
        nums.forEachIndexed { i, num ->
            output[i] = prefix
            prefix *= num
        }

        var suffix = 1
        (nums.size - 1 downTo 0).forEach { i ->
           output[i] = suffix * output[i]
            suffix *= nums[i]
        }

        return output
    }

    // O(n)
    fun productExceptSelfON(nums: IntArray): IntArray {
        val prefixes = IntArray(nums.size)
        val suffixes = IntArray(nums.size)

        prefixes[0] = nums[0]
        (1 until nums.size).forEach { i ->
            val v = nums[i]
            val prefix = prefixes[i - 1]
            prefixes[i] = prefix * v
        }

        suffixes[nums.size - 1] = nums.last()
        (nums.size - 2 downTo 0).forEach { i ->
            val v = nums[i]
            val suffix = suffixes[i + 1]
            suffixes[i] = suffix * v
        }

        return nums.indices
            .map { i -> prefixes.getOrElse(i - 1) { 1 } * suffixes.getOrElse(i + 1) { 1 } }
            .toIntArray()
    }
}

private data class ProductExceptSelfTestCase(
    val input: IntArray,
    val expected: IntArray,
)

private class ProductExceptSelfTest {

    companion object {
        @JvmStatic
        fun getData() = listOf(
            ProductExceptSelfTestCase(
                intArrayOf(3, 2, -4),
                intArrayOf(-8, -12, 6),
            ),
            ProductExceptSelfTestCase(
                intArrayOf(1, 2, 3, 4),
                intArrayOf(24, 12, 8, 6),
            ),
            ProductExceptSelfTestCase(
                intArrayOf(-1, 1, 0, -3, 3),
                intArrayOf(0, 0, 9, 0, 0),
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `test cases`(testCase: ProductExceptSelfTestCase) {
        val (input, expected) = testCase
        val actual = ProductExceptSelfSolution().productExceptSelf(input)

        Assertions.assertEquals(expected.toList(), actual.toList())
    }

}