package leetcode.binarytree

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/kth-smallest-element-in-a-bst/description/?envType=study-plan-v2&envId=top-interview-150

private class BstKthSmallestSolution {
    fun count(node: TreeNode?): Int {
        if (node == null) return 0
        return 1 + count(node.left) + count(node.right)
    }

    fun kthSmallest(root: TreeNode?, k: Int): Int {
        if (root == null) return 0

        val leftCount = count(root.left)
        return if (leftCount == (k - 1)) root.`val`
        else if (leftCount >= k) kthSmallest(root.left, k)
        else kthSmallest(root.right, k - leftCount - 1)
    }
}

private data class BstKthSmallestTestCase(
    val root: TreeNode?,
    val k: Int,
    val expected: Int
)

private class BstKthSmallestTest {
    companion object {
        @JvmStatic
        fun getTestData() = listOf(
            BstKthSmallestTestCase(null, 100, 0),
            BstKthSmallestTestCase(
                TreeNode(3, TreeNode(1, null, TreeNode(2)), TreeNode(4)),
                1,
                1
            ),
            BstKthSmallestTestCase(
                TreeNode(
                    5,
                    TreeNode(
                        3,
                        TreeNode(2, TreeNode(1), null),
                        TreeNode(4)
                    ),
                    TreeNode(6)
                ),
                3,
                3
            ),
            BstKthSmallestTestCase(TreeNode(1, null, TreeNode(2)), 2, 2)
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testCase: BstKthSmallestTestCase) {
        val actual = BstKthSmallestSolution().kthSmallest(testCase.root, testCase.k)
        Assertions.assertEquals(testCase.expected, actual)
    }
}
