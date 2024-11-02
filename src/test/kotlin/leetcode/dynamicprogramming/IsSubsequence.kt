package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

// https://leetcode.com/problems/is-subsequence/description/

/*
Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
A subsequence of a string is a new string that is formed from the original string by deleting some (can be none)
of the characters without disturbing the relative positions of the remaining characters.
(i.e., "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:
Input: s = "abc", t = "ahbgdc"
Output: true

Example 2:
Input: s = "axc", t = "ahbgdc"
Output: false

Constraints:
    0 <= s.length <= 100
    0 <= t.length <= 104
    s and t consist only of lowercase English letters.
 */

class IsSubsequenceSolution {
    tailrec fun isSubsequence(s: String, t: String): Boolean =
        if (s.length > t.length) false
        else if (s.isEmpty()) true
        else if (s[0] == t[0]) isSubsequence(s.substring(1), t.substring(1))
        else isSubsequence(s, t.substring(1))
//            var si = 0
//            var ti = 0
//            while (si < s.length && ti < t.length) {
//                if (s[si] == t[ti]) {
//                    si++
//                }
//                ti++
//            }
//            si == s.length
}

class IsSubsequenceTest {
    @ParameterizedTest
    @CsvSource(value = [
        "abc,ahbgdc,true",
        "axc,ahbgdc,false",
        "b,abc,true",
    ])
    fun tests(s: String, t: String, expected: Boolean) {
        val actual = IsSubsequenceSolution().isSubsequence(s, t)
        assertEquals(expected, actual, "$s, $t")
    }
}