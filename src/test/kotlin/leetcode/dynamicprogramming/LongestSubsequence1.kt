package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/longest-unequal-adjacent-groups-subsequence-i/description/

/*
You are given a string array words and a binary array groups both of length n, where words[i] is associated
with groups[i].

Your task is to select the longest alternating
subsequence
from words. A subsequence of words is alternating if for any two consecutive strings in the sequence,
their corresponding elements in the binary array groups differ. Essentially, you are to choose strings
such that adjacent elements have non-matching corresponding bits in the groups array.

Formally, you need to find the longest subsequence of an array of indices [0, 1, ..., n - 1] denoted as
[i0, i1, ..., ik-1], such that groups[ij] != groups[ij+1] for each 0 <= j < k - 1 and then find the words
corresponding to these indices.

Return the selected subsequence. If there are multiple answers, return any of them.

Note: The elements in words are distinct.

Example 1:
Input: words = ["e","a","b"], groups = [0,0,1]
Output: ["e","b"]
Explanation: A subsequence that can be selected is ["e","b"] because groups[0] != groups[2]. Another subsequence that can be selected is ["a","b"] because groups[1] != groups[2]. It can be demonstrated that the length of the longest subsequence of indices that satisfies the condition is 2.

Example 2:
Input: words = ["a","b","c","d"], groups = [1,0,1,1]
Output: ["a","b","c"]
Explanation: A subsequence that can be selected is ["a","b","c"] because groups[0] != groups[1] and groups[1] != groups[2]. Another subsequence that can be selected is ["a","b","d"] because groups[0] != groups[1] and groups[1] != groups[3]. It can be shown that the length of the longest subsequence of indices that satisfies the condition is 3.

Constraints:
    1 <= n == words.length == groups.length <= 100
    1 <= words[i].length <= 10
    groups[i] is either 0 or 1.
    words consists of distinct strings.
    words[i] consists of lowercase English letters.
 */

// find the longest alternating subsequence in groups, covert it to words
// zip the groups together, so you don't lose track of the words
// find the longest alternating subsequence

// if list is length 1, -> empty
// if list is length 2 -> answer is a candidate if the groups aren't equal

// exhaustive approach: find all subsequences of length 2 or greater
// filter to those that are alternating
// return the longest one
// this is exponential time; can we do better?

// a subsequence is alternating if for some index i, there exists an index j > i, such that groups[j] != groups[i]
// ...could I just wipe out consecutive values? I think that would do it ;)
// how can I be sure?
// since any answer is acceptable, it doesn't matter which
// only changes matter; filter out all the non-changes
// Input: words = ["a","b","c","d"], groups = [1,0,1,1]
// Output: ["a","b","c"]

class LongestSubsequence1Solution {
    fun getLongestSubsequence(words: Array<String>, groups: IntArray): List<String> {
        if (groups.isEmpty()) return emptyList()

        return (1 until groups.size).fold(groups[0] to listOf(0)) { (prev, list), i ->
            if (groups[i] == prev) groups[i] to list
            else groups[i] to list + i
        }
            .second
            .map { i -> words[i] }
    }
}

class LongestSubsequence1Test {
    data class TestData(
        val words: Array<String>,
        val groups: IntArray,
        val acceptableAnswers: Set<List<String>>
    )

    companion object {
        @JvmStatic
        fun testData(): List<TestData> = listOf(
            TestData(
                arrayOf("e", "a", "b"),
                intArrayOf(0, 0, 1),
                setOf(
                    listOf("e", "b"),
                    listOf("a", "b"),
                )
            ),
            TestData(
                arrayOf("a", "b", "c", "d"),
                intArrayOf(1, 0, 1, 1),
                setOf(
                    listOf("a","b","c"),
                    listOf("a","b","d"),
                )
            ),
            TestData(
                arrayOf("a", "b", "c"),
                intArrayOf(0, 0, 0),
                setOf(listOf("a"), listOf("b"), listOf("c"))
            ),
            TestData(
                arrayOf("a", "b", "c"),
                intArrayOf(1, 1, 1),
                setOf(listOf("a"), listOf("b"), listOf("c"))
            ),
            TestData(
                arrayOf("a"),
                intArrayOf(1),
                setOf(listOf("a"))
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("testData")
    fun testAllCases(testData: TestData) {
        val answer = LongestSubsequence1Solution().getLongestSubsequence(testData.words, testData.groups)
        assertTrue(testData.acceptableAnswers.contains(answer),
            "Expected one of ${testData.acceptableAnswers.joinToString()}; got $answer")
    }
}