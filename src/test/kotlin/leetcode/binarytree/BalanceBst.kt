package leetcode.binarytree

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/balance-a-binary-search-tree/description/

private class BalanceBstSolution {
    fun TreeNode?.toInOrderList(): List<TreeNode> {
        if (this == null) return emptyList()
        return left.toInOrderList() + listOf(this) + right.toInOrderList()
    }

    fun toInOrderList(node: TreeNode?, acc: MutableList<TreeNode>) {
        if (node == null) return
        toInOrderList(node.left, acc)
        acc.add(node)
        toInOrderList(node.right, acc)
    }

    fun balanceBST(root: TreeNode?): TreeNode? {
        if (root == null) return null
        val inOrder = mutableListOf<TreeNode>()
        toInOrderList(root, inOrder)
        return toBalancedBst(inOrder, 0, inOrder.size - 1)
    }

    private fun toBalancedBst(inOrder: List<TreeNode>, start: Int, end: Int): TreeNode? {
        if (start > end) return null
        val midpoint = start + ((end - start) / 2)
        val root = inOrder[midpoint]
        root.left = toBalancedBst(inOrder, start, midpoint - 1)
        root.right = toBalancedBst(inOrder, midpoint + 1, end)
        return root
    }
}

private data class BalanceBstTestCase(
    val input: TreeNode?,
    val expected: List<TreeNode?>,
)

private class BalanceBstTest {
    companion object {
        @JvmStatic
        fun getTestCases(): List<BalanceBstTestCase> = listOf(
            BalanceBstTestCase(
                TreeNode(
                    1,
                    null,
                    TreeNode(
                        2,
                        null,
                        TreeNode(
                            3,
                            null,
                            TreeNode(4)
                        )
                    )
                ),
                listOf(
                    TreeNode(
                        2,
                        TreeNode(1),
                        TreeNode(3, null, TreeNode(4))
                    ),
                   TreeNode(
                       3,
                       TreeNode(1, null, TreeNode(2)),
                       TreeNode(4)
                   )
                )
            ),
            BalanceBstTestCase(
                TreeNode(2, TreeNode(1), TreeNode(3)),
                listOf(
                    TreeNode(2, TreeNode(1), TreeNode(3)),
                )
            ),
            BalanceBstTestCase(
                null,
                listOf(null)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestCases")
    fun test(testCase: BalanceBstTestCase) {
        val actual = BalanceBstSolution().balanceBST(testCase.input)
        Assertions.assertTrue(
            testCase.expected.any { it == actual },
            actual.toString()
        )
    }
}
