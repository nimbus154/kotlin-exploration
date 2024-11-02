package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

// https://leetcode.com/problems/pascals-triangle/description/

class PascalsTriangleSolution {
   fun generate(numRows: Int): List<List<Int>> {
       if (numRows == 1) return listOf(listOf(1))
       val init = listOf(listOf(1), listOf(1, 1))
       if (numRows == 2) return init

       return (2 until numRows).fold(init) { rows, _ ->
           val middle = rows
               .last()
               .zipWithNext()
               .map { it.first + it.second }

           val newRow = listOf(1) + middle + listOf(1)
           rows + listOf(newRow)
       }
   }
}

/*
Example 1:

Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]

Example 2:

Input: numRows = 1
Output: [[1]]
 */

class PascalsTriangleTest {

    @Test
    fun test() {
        val rows = listOf(
            listOf(1),
            listOf(1,1),
            listOf(1,2,1),
            listOf(1,3,3,1),
            listOf(1,4,6,4,1),
        )
        (1..5).forEach { numRows ->
            val answer = PascalsTriangleSolution().generate(numRows)
            val expected = rows.subList(0, numRows)
            assertEquals(expected, answer, "numrows $numRows")
        }
    }
}