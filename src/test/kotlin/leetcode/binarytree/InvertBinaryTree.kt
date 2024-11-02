package leetcode.binarytree

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class InvertBinaryTreeSolution {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return root

        val tmp = root.left
        root.left = root.right
        root.right = tmp

        invertTree(root.left)
        invertTree(root.right)

        return root
    }
}

private data class TestCase(
    val input: TreeNode?,
    val expected: TreeNode?,
)

private class InvertBinaryTreeTest {
    companion object {
        @JvmStatic
        fun getTestCases(): List<TestCase> = listOf(
            TestCase(null, null),
            TestCase(TreeNode(1), TreeNode(1)),
            TestCase(
                TreeNode(2, TreeNode(1), TreeNode(3)),
                TreeNode(2, TreeNode(3), TreeNode(1)),
            ),
//                Input: root = [4,2,7,1,3,6,9]
//                Output: [4,7,2,9,6,3,1]
            TestCase(
                TreeNode(
                    4,
                    TreeNode(2, TreeNode(1), TreeNode(3)),
                    TreeNode(7, TreeNode(6), TreeNode(9)),
                ),
                TreeNode(
                    4,
                    TreeNode(7, TreeNode(9), TreeNode(6)),
                    TreeNode(2, TreeNode(3), TreeNode(1)),
                ),
            ),
        )
    }
    /*
Input: root = []
Output: []
Input: root = [4,2,7,1,3,6,9]
Output: [4,7,2,9,6,3,1]
Input: root = [2,1,3]
Output: [2,3,1]
     */
    @ParameterizedTest
    @MethodSource("getTestCases")
    fun `inverts a binary tree`(testCase: TestCase) {
        val actual = InvertBinaryTreeSolution().invertTree(testCase.input)
        assertDeepEquals(testCase.expected, actual)
    }

    private fun assertDeepEquals(t1: TreeNode?, t2: TreeNode?) {
        if (t1 == null || t2 == null) {
            Assertions.assertEquals(t1, t2)
            return
        }

        Assertions.assertEquals(t1.`val`, t2?.`val`)
        assertDeepEquals(t1.left, t2.left)
        assertDeepEquals(t1.right, t2.right)
    }
}