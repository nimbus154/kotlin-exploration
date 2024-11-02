package leetcode

import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class DivSolution {
    // int division should drop fractional part
    // can't use multiplication or division or mod
    // 32-bit signed int
    // if answer is greater than (2^31 - 1), return (2^31 - 1)
    // if less than -2^31, return -2^31
    // divisor != 0
    // both params are 32-bit signed ints

    // shift operators, exponentiation operators
    // dividend >> 2  equivalent to dividing by two

    // could just subtract until 0, but that's slow
    // will probably hit a timeout
    // is there a cleverer way?
    // consider 2^31 -1 / 2

    // ignore signs during calculation
    // then adjust at the end
    // (if both are negative -> positive)
    // (neg divisor, neg dividend) ->

    private tailrec fun negDiv(dividend: Int, divisor: Int, quotient: Int): Int =
        if (divisor < dividend) quotient
        else negDiv(dividend - divisor, divisor, quotient + 1)

    fun divide(dividend: Int, divisor: Int): Int {
        if (dividend == Int.MIN_VALUE && divisor == -1) return Int.MAX_VALUE
        val negDividend = if (dividend > 0) -dividend else dividend
        val negDivisor = if (divisor > 0) -divisor else divisor
        val quotient = negDiv(negDividend, negDivisor, 0)

        return when {
            dividend < 0 && divisor < 0 -> quotient
            dividend > 0 && divisor > 0 -> quotient
            else -> -quotient
        }
    }
}

class DivisionTest {

    @Test
    fun `fractional 10 div 3`() {
        val ans = DivSolution().divide(10, 3)
        assertEquals(3, ans)
    }

    @Test
    fun `negative fractional 7 div -3`() {
        val ans = DivSolution().divide(7, -3)
        assertEquals(-2, ans)
    }

    @Test
    fun `pos dividend, neg divisor`() {
        val ans = DivSolution().divide(10, -2)
        assertEquals(-5, ans)
    }

    @Test
    fun `neg dividend, pos divisor`() {
        val ans = DivSolution().divide(-10, 2)
        assertEquals(-5, ans)
    }

    @Test
    fun `double neg divisor higher`() {
        val ans = DivSolution().divide(-7, -3)
        assertEquals(2, ans)
    }

    @Test
    fun `double neg divisor lower`() {
        val ans = DivSolution().divide(-3, -7)
        assertEquals(0, ans)
    }

    @Test
    fun `max int`() {
        val ans = DivSolution().divide(Int.MAX_VALUE, 2)
        assertEquals(1073741823, ans)
    }

    @Test
    fun `min int`() {
        val ans = DivSolution().divide(Int.MIN_VALUE, -1)
        assertEquals(Int.MAX_VALUE, ans)
    }

    @Test
    fun `min int by 2`() {
        val ans = DivSolution().divide(Int.MIN_VALUE, 2)
        assertEquals(-1073741824, ans)
    }
}