package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

// https://leetcode.com/problems/count-sorted-vowel-strings/description/
/*
Given an integer n, return the number of strings of length n that consist only of
vowels (a, e, i, o, u) and are lexicographically sorted.
A string s is lexicographically sorted if for all valid i, s[i]
is the same as or comes before s[i+1] in the alphabet.

Example 1:
Input: n = 1
Output: 5
Explanation: The 5 sorted strings that consist of vowels only are ["a","e","i","o","u"].

Example 2:Input: n = 2
Output: 15
Explanation: The 15 sorted strings that consist of vowels only are
["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"].
Note that "ea" is not a valid string since 'e' comes after 'a' in the alphabet.

1 = a: 1, e: 1, i: 1, o: 1, u: 1 = 5
2 = a: 5, e: 4, i: 3, o: 2, u: 1 = 15
computing rows
3 = a: 15, e: sum of previous row minus a of prev - 10, e
a[i] = sum of the previous row
e[i] = e[i - 1]
    a gets all the previous values (5)
    e gets all the previous values, minus the as
    i gets all the previous values, minus the a and es
    o, minus a e i
    u, only us

1
    u will only ever be 1

Example 3:
Input: n = 33
Output: 66045

Constraints:
    1 <= n <= 50
 */

class CountSortedVowelStringsSolution {
    fun countVowelStrings(n: Int): Int =
        (2..n).fold(intArrayOf(1, 1, 1, 1, 1)) { prev, _ ->
            val prevSum = prev.sum()
            intArrayOf(
                prevSum, // a
                prevSum - prev[0], // e
                prevSum - prev[0] - prev[1], // i
                prevSum - prev[0] - prev[1] - prev[2], // o
                1
            )
        }.sum()
}

class CountSortedVowelStringsTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            "1,5",
            "2,15",
            "33,66045",
        ]
    )
   fun testCase(input: Int, expected: Int) {
       val actual = CountSortedVowelStringsSolution().countVowelStrings(input)
        Assertions.assertEquals(expected, actual)
   }
}
