package leetcode.graph

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/number-of-islands/description/?envType=study-plan-v2&envId=top-interview-150

private class IslandsSolution {
    fun numIslands(grid: Array<CharArray>): Int {
        var count = 0
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                if (grid[r][c] == '1') {
                    findIsland(r, c, grid)
                    count++
                }
            }
        }
        return count
    }

    private fun findIsland(r: Int, c: Int, grid: Array<CharArray>) {
        when {
            r < 0 || r >= grid.size -> Unit
            c < 0 || c >= grid[r].size -> Unit
            grid[r][c] == '0' -> Unit
            else -> {
                grid[r][c] = '0'
                findIsland(r - 1, c, grid)
                findIsland(r + 1, c, grid)
                findIsland(r, c + 1, grid)
                findIsland(r, c - 1, grid)
            }
        }
    }

    private data class Cell(val row: Int, val col: Int)

    fun numIslands1(grid: Array<CharArray>): Int {
        return grid.foldIndexed(0 to emptySet<Cell>()) { rowIndex, acc, row ->
            row.foldIndexed(acc) { colIndex, (count, visited), cellValue ->
                val cell = Cell(rowIndex, colIndex)
                if (cellValue == '0' || cell in visited) count to visited
                else {
                    val island = findIsland1(cell, grid, setOf(cell))
                    (count + 1) to visited + island
                }
            }
        }.first
    }

    private fun findIsland1(cell: Cell, grid: Array<CharArray>, island: Set<Cell>): Set<Cell> {
        val neighbors = setOf(
            Cell(cell.row, cell.col - 1),
            Cell(cell.row, cell.col + 1),
            Cell(cell.row - 1, cell.col),
            Cell(cell.row + 1, cell.col),
        )
        val adjLandCells = neighbors
            .filter { newCell ->
                when {
                    cell.row < 0 || cell.row >= grid.size -> false
                    cell.col < 0 || cell.col >= grid[cell.row].size -> false
                    grid[cell.row][cell.col] == '0' -> false
                    else -> newCell !in island
                }
            }
        return adjLandCells.fold(island + adjLandCells) { island2, newLand ->
            findIsland1(newLand, grid, island2)
        }
    }
}

private data class IslandsTestCase(
    val input: Array<CharArray>,
    val expected: Int,
)

private class IslandsTest {
    companion object {
        @JvmStatic
        fun getTestData() = listOf(
            IslandsTestCase(
                emptyArray<CharArray>(),
                0
            ),
            IslandsTestCase(
                arrayOf(charArrayOf('0')),
                0
            ),
            IslandsTestCase(
                arrayOf(charArrayOf('1')),
                1
            ),
            IslandsTestCase(
                arrayOf(
                    charArrayOf('1','1','1','1','0'),
                    charArrayOf('1','1','0','1','0'),
                    charArrayOf('1','1','0','0','0'),
                    charArrayOf('0','0','0','0','0'),
                ),
                1
            ),
            IslandsTestCase(
                arrayOf(
                    charArrayOf('1','1','0','0','0'),
                    charArrayOf('1','1','0','0','0'),
                    charArrayOf('0','0','1','0','0'),
                    charArrayOf('0','0','0','1','1'),
                ),
                3
            ),
            IslandsTestCase(
                arrayOf(
                    charArrayOf('1','0','0','0','0'),
                    charArrayOf('0','1','0','0','0'),
                    charArrayOf('0','0','1','0','0'),
                    charArrayOf('0','0','0','1','1'),
                ),
                4
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testCase: IslandsTestCase) {
        val actual = IslandsSolution().numIslands(testCase.input)
        Assertions.assertEquals(testCase.expected, actual)
    }
}
