package leetcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// Problem 17
// https://leetcode.com/problems/letter-combinations-of-a-phone-number/

class Solution {
    val phoneDigitMappings = mapOf(
        '2' to listOf("a", "b", "c"),
        '3' to listOf("d", "e", "f"),
        '4' to listOf("g", "h", "i"),
        '5' to listOf("j", "k", "l"),
        '6' to listOf("m", "n", "o"),
        '7' to listOf("p", "q", "r", "s"),
        '8' to listOf("t", "u", "v"),
        '9' to listOf("w", "x", "y", "z"),
    )
    // map the first digit to its letters
    // recursively append to it
    // 23
    // acc: [], letters = a b c -> [a, b, c]
    // [a, b, c], letters = d e f -> [da, db, dc, ea, eb, ec, fa, fb, fc]

    fun letterCombinations(digits: String): List<String> =
        digits.fold(listOf()) { acc, digit ->
            phoneDigitMappings.getOrDefault(digit, listOf())
                .flatMap { letter ->
                    acc
                        .map { it + letter }
                        .ifEmpty { listOf(letter) }
                }
        }
}

class PhoneNumberLetterCombosTest {

    // input: string of digits "digits"
    // 0 <= digits.length <= 4
    // a digit is in range [2-9] inclusive
    // output: all combinations of letter sequences digits correspond to on a phone number
    // combinations can be in any order

    @Test
    fun `empty`() {
        val actual = Solution().letterCombinations("")
        assertEquals(listOf(), actual)
    }

    @Test
    fun `one digit`() {
        val actual = Solution().letterCombinations("2")
        val expected = listOf("a","b","c")
        assertEquals(expected.sorted(), actual.sorted())
    }

    @Test
    fun `two digits`() {
        val actual = Solution().letterCombinations("23")
        val expected = listOf("ad","ae","af","bd","be","bf","cd","ce","cf")
        assertEquals(expected.sorted(), actual.sorted())
    }
}