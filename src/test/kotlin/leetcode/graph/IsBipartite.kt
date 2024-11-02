package leetcode.graph

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class IsBipartiteSolution {
    private data class Entry(
        val node: Int,
        val color: MutableSet<Int>,
        val neighborColor: MutableSet<Int>,
    )

    fun isBipartite(graph: Array<IntArray>): Boolean {
        val unvisited = graph.indices.toMutableSet()
        val queue = mutableListOf<Int>(0)
        val colors = mutableMapOf<Int, Boolean>(0 to true)
        while (unvisited.isNotEmpty()) {
            if (queue.isEmpty()) {
                val next = unvisited.random()
                colors[next] = true
                unvisited.remove(next)
                queue.add(next)
            }
            val current = queue.removeFirst()
            unvisited.remove(current)

            graph[current].forEach { node ->
                if (colors.containsKey(node) && colors[node] == colors[current]) return false
                colors[node] = !(colors[current]!!)
                if (node in unvisited) queue.add(node)
            }
        }
        return true
    }

    fun isBipartite1(graph: Array<IntArray>): Boolean {
        val start = 0
        val set1 = setOf(start).toMutableSet()
        val set2 = emptySet<Int>().toMutableSet()

        val queue = listOf(Entry(start, set1, set2)).toMutableList()
        val visited = emptySet<Int>().toMutableSet()

        while (visited.size != graph.size) {
            val current = queue.removeFirstOrNull()
            if (current == null) {
                val nodes = graph.indices.toSet()
                val unvisited = nodes.subtract(visited)
                val next = unvisited.toList().first()
                val entry = Entry(next, set1, set2)
                queue.add(entry)
                continue
            }
            visited.add(current.node)

            graph[current.node].forEach { node ->
                if (node in current.color) return false
                current.neighborColor.add(node)
                if (node !in visited) {
                    val entry = Entry(node, current.neighborColor, current.color)
                    queue.add(entry)
                }
            }
        }
        return true
    }
}

private data class IsBipartiteTestCase(
    val input: Array<IntArray>,
    val output: Boolean,
)

private class IsBipartiteTest {
    companion object {
        @JvmStatic
        fun getTestData(): List<IsBipartiteTestCase> = listOf(
            IsBipartiteTestCase(
                arrayOf(
                    intArrayOf(),
                    intArrayOf(2,4,6),
                    intArrayOf(1,4,8,9),
                    intArrayOf(7,8),
                    intArrayOf(1,2,8,9),
                    intArrayOf(6,9),
                    intArrayOf(1,5,7,8,9),
                    intArrayOf(3,6,9),
                    intArrayOf(2,3,4,6,9),
                    intArrayOf(2,4,5,6,7,8)
                ),
                false
            ),
            IsBipartiteTestCase(
                arrayOf(
                    intArrayOf(1),
                    intArrayOf(0),
                ),
                true
            ),
            IsBipartiteTestCase(
                arrayOf(
                    intArrayOf(1,2,3),
                    intArrayOf(0,2),
                    intArrayOf(0,1,3),
                    intArrayOf(0,2),
                ),
                false
            ),
            IsBipartiteTestCase(
                arrayOf(
                    intArrayOf(1,3),
                    intArrayOf(0,2),
                    intArrayOf(1,3),
                    intArrayOf(0,2),
                ),
                true
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(case: IsBipartiteTestCase) {
        val actual = IsBipartiteSolution().isBipartite(case.input)
        Assertions.assertEquals(case.output, actual)
    }
}
