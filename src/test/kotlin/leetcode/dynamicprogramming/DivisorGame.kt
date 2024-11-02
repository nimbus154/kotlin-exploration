package leetcode.dynamicprogramming

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

// https://leetcode.com/problems/divisor-game/description/

/*
Alice and Bob take turns playing a game, with Alice starting first.
Initially, there is a number n on the chalkboard. On each player's turn, that player makes a move consisting of:
    Choosing any x with 0 < x < n and n % x == 0.
    Replacing the number n on the chalkboard with n - x.
Also, if a player cannot make a move, they lose the game.
Return true if and only if Alice wins the game, assuming both players play optimally.

alice starts
some n is chosen
choose any x between 1 and n - 1
x must be a divisor of n (n is max 1000)
replace n with n - x

n; which factors are available?
What does it mean to play optimally? Always choosing the highest divisor?

1 is always the last value; once it gets to 1,

1 -> false
2 -> true (1 is the only choice for Alice. She makes it. It goes to Bob, who loses)
3 -> false (Alice chooses 1 or 2, she chooses 1, which gives Bob 2, so bob wins)
4 -> 2 or 1. Alice chooses 1, which gives Bob 3
5 -> 1. Alice chooses 1, which gives Bob 4, so Alice loses.

given some n, get candidates of x. Choose the x such that n - x == false

Example 1:
Input: n = 2
Output: true
Explanation: Alice chooses 1, and Bob has no more moves.

Example 2:
Input: n = 3
Output: false
Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.

6 -> 3, 2, 1

Constraints:
    1 <= n <= 1000
 */

class DivisorGameSolution {
    private fun getMoves(n: Int): List<Int> {
        val n1 = if (n % 2 == 0) n else n - 1
        return (1 .. (n1 / 2))
            .filter { n % it == 0 }
            .filter { n - it > 0}
    }

    fun divisorGame(n: Int): Boolean =
        // could have just written n % 2 == 0
        (1..n).fold(listOf()) { memo: List<Boolean>, i ->
            val moves = getMoves(i)
            if (moves.isEmpty()) memo + false
            else memo + moves.any { memo.getOrElse(i - it - 2) { true } }
        }
        .last()
}

class DivisorGameTest {
    @ParameterizedTest
    @CsvSource(
        "2,true",
        "3,false",
        "4,true",
        "5,false",
        "6,true",
    )
    fun test(n: Int, aliceWins: Boolean) {
        val actual = DivisorGameSolution().divisorGame(n)
        assertEquals(aliceWins, actual)
    }
}