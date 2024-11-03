package leetcode.arraystring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.max
import kotlin.math.min

// https://leetcode.com/problems/h-index/description/?envType=study-plan-v2&envId=top-interview-150

private class HIndexSolution {
    fun hIndex(citations: IntArray): Int =
        citations
            .sorted()
            .foldIndexed(0) { index, acc, value ->
                val papers = citations.size - index
                val h = min(papers, value)
                max(acc, h)
            }
}

/*
H Index
len(array) number of papers
array[i] number of citations
H Index = max # of papers with at least n citations

number of papers with at least n citations
max value answer can be is len(array), since that's number of papers

The h-index is defined as the maximum value of h such that
the given researcher has published at least h papers
that have each been cited at least h times.

h = number of citations
h = number of papers w/at least


nax(h) where
how many papers w/ > 0 citations?, > 1, > 2


1 paper cited 100 times -> 1
[100, 2] -> 2

Naive process
for c in array
    cmax = array.filter { it >= c }
    total max = max(cmax, totalmax)

O(n^2)

sort list O(nlogn)
list.foldIndexed(0) (i, c) ->
    if list.size - i >= c
        max(acc, c)
    else acc
max(acc,

how many papers have at least n citations?
1,2,3,4,5 -> 3
0,100 -> 1
 */

private data class HIndexTestCase(
    val input: IntArray,
    val expected: Int,
)

private class HIndexTest {
    companion object {
        @JvmStatic
        fun getData() = listOf(
            HIndexTestCase(intArrayOf(3,0,6,1,5), 3),
            HIndexTestCase(intArrayOf(1,3,1), 1),
            HIndexTestCase(intArrayOf(1), 1),
            HIndexTestCase(intArrayOf(100), 1),
            HIndexTestCase(intArrayOf(), 0),
            HIndexTestCase(intArrayOf(0), 0),
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun test(testCase: HIndexTestCase) {
        val actual = HIndexSolution().hIndex(testCase.input)
        Assertions.assertEquals(testCase.expected, actual)
    }
}