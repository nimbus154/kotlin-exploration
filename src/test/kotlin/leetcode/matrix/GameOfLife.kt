package leetcode.matrix

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class GameOfLifeSolution {
    fun gameOfLife1(board: Array<IntArray>): Unit {
        val newBoard = board.mapIndexed { i, row ->
            row.mapIndexed { j, value ->
                val liveNeighbors = countNeighbors(board, i, j)

                if (value == 1) {
                    when {
                        liveNeighbors < 2 -> 0
                        liveNeighbors > 3 -> 0
                        else -> 1
                    }
                } else {
                    if (liveNeighbors == 3) 1 else 0
                }
            }
        }

        newBoard.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                board[i][j] = value
            }
        }
    }

    private fun countNeighbors1(board: Array<IntArray>, i: Int, j: Int): Int =
        listOf(
            i - 1 to j - 1,
            i - 1 to j,
            i - 1 to j + 1,
            i to j - 1,
            i to j + 1,
            i + 1 to j - 1,
            i + 1 to j,
            i + 1 to j + 1,
        ).sumOf {
            board.getOrNull(it.first)?.getOrNull(it.second) ?: 0
        }

    fun gameOfLife(board: Array<IntArray>): Unit {
        board.forEachIndexed { i, row ->
            row.forEachIndexed() { j, value ->
                val liveNeighbors = countNeighbors(board, i, j)

                val newVal = if (value == 1) {
                    when {
                        liveNeighbors < 2 -> 0
                        liveNeighbors > 3 -> 0
                        else -> 1
                    }
                } else {
                    if (liveNeighbors == 3) 1 else 0
                }

                board[i][j] = value or (newVal shl 1)
            }
        }

        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                board[i][j] = (value shr 1)
            }
        }
    }

    private fun countNeighbors(board: Array<IntArray>, i: Int, j: Int): Int =
        listOf(
            i - 1 to j - 1,
            i - 1 to j,
            i - 1 to j + 1,
            i to j - 1,
            i to j + 1,
            i + 1 to j - 1,
            i + 1 to j,
            i + 1 to j + 1,
        ).sumOf {
            (board.getOrNull(it.first)?.getOrNull(it.second) ?: 0) and 0b01
        }
}

private data class GameOfLifeTestCase(
    val input: Array<IntArray>,
    val expected: Array<IntArray>,
)

private class GameOfLifeTest {
    companion object {
        @JvmStatic
        fun getTestData() = listOf(
            GameOfLifeTestCase(
                arrayOf(intArrayOf(0,1,0),intArrayOf(0,0,1),intArrayOf(1,1,1),intArrayOf(0,0,0)),
                arrayOf(intArrayOf(0,0,0),intArrayOf(1,0,1),intArrayOf(0,1,1),intArrayOf(0,1,0))
            ),
            GameOfLifeTestCase(
                arrayOf(intArrayOf(1,1),intArrayOf(1,0)),
                arrayOf(intArrayOf(1,1),intArrayOf(1,1))
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testCase: GameOfLifeTestCase) {
        GameOfLifeSolution().gameOfLife(testCase.input)
        testCase.expected.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                Assertions.assertEquals(value, testCase.input[i][j], "${i}, ${j}")
            }
        }
    }
}