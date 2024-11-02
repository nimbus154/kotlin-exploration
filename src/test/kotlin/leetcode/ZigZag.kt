package leetcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// Problem 6
// https://leetcode.com/problems/zigzag-conversion/

data class State(
    val output: MutableList<String>,
    val pos: Int = 0,
    val increment: Boolean = true,
)

fun convert(s: String, numRows: Int): String = if (numRows == 1) s else
    s.fold(State(MutableList(numRows) { "" })) { (output, pos, increment), c ->
        val row = output.getOrElse(pos) { "" }
        output[pos] = row + c

        val newPos = if (increment) pos + 1 else pos - 1
        val newIncrement = if (increment) newPos < (numRows - 1) else newPos < 1

        State(output, newPos, newIncrement)
    }.output.joinToString("")


class ConvertTest {

    @Test
    fun `Single element`() {
        val actual = convert("A", 1)
        assertEquals("A", actual)
    }

    @Test
    fun `long, 3 row`() {
        val actual = convert("PAYPALISHIRING", 3)
        assertEquals("PAHNAPLSIIGYIR", actual)
    }

    @Test
    fun `long, 4 row`() {
        val actual = convert("PAYPALISHIRING", 4)
        assertEquals("PINALSIGYAHRPI", actual)
    }

    @Test
    fun `AB 1`() {
        val actual = convert("AB", 1)
        assertEquals("AB", actual)
    }
}