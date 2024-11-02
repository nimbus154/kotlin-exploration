package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/generate-parentheses/description/

/*
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

Example 1:
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Example 2:
Input: n = 1
Output: ["()"]

Constraints:
    1 <= n <= 8

 0 ""
 1 ()
 2 ()() f0f1, (()) f1f0  (it) ()it
 3
    f0f2 ()()(),()(())
    f1f1 (())()
    f2f2 (()()), ((()))
 */

class ParenGenSolution {
    fun generateParenthesis(n: Int): List<String> = (0 until n).fold(listOf(listOf(""))) { dp, k ->
            val newDp = dp.flatMapIndexed { i, parens ->
                val suffixes = dp.getOrElse(k - i) { emptyList() }
                parens.flatMap {
                    suffixes.map { suffix -> "($it)$suffix" }
                }
            }
            dp.plusElement(newDp)
        }.last()
}

class ParenGenTest {
    companion object {
        @JvmStatic
        fun getTestData(): List<Pair<Int, Set<String>>> = listOf(
            1 to setOf("()"),
            2 to setOf("()()", "(())"),
            3 to setOf("((()))","(()())","(())()","()(())","()()()"),
            4 to setOf("(((())))","((()()))","((())())","((()))()","(()(()))","(()()())","(()())()","(())(())","(())()()","()((()))","()(()())","()(())()","()()(())","()()()()"),
            5 to setOf(
                "((((()))))","(((()())))","(((())()))","(((()))())","(((())))()","((()(())))","((()()()))","((()())())","((()()))()","((())(()))","((())()())","((())())()","((()))(())","((()))()()","(()((())))","(()(()()))","(()(())())","(()(()))()","(()()(()))","(()()()())","(()()())()","(()())(())","(()())()()","(())((()))","(())(()())","(())(())()","(())()(())","(())()()()","()(((())))","()((()()))","()((())())","()((()))()","()(()(()))","()(()()())","()(()())()","()(())(())","()(())()()","()()((()))","()()(()())","()()(())()","()()()(())","()()()()()"
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testData: Pair<Int, Set<String>>) {
        val (input, expected) = testData
        val actual = ParenGenSolution().generateParenthesis(input).toSet()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun t() {
        println("a")
        (0 until 1).forEach { println(it) }
        println("b")
        (0 until 2).forEach { println(it) }
    }
}
