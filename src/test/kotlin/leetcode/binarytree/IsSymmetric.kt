package leetcode.binarytree

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


private class IsSymmetricSolution {
    fun invert(root: TreeNode?): TreeNode? {
        if (root == null) return null

        val node = TreeNode(root.`val`)

        node.left = invert(root.right)
        node.right = invert(root.left)

        return node
    }

    fun equals(t1: TreeNode?, t2: TreeNode?): Boolean =
        when {
            t1 == null && t2 == null -> true
            t1 == null || t2 == null -> false
            t1.`val` != t2.`val` -> false
            else -> equals(t1.left, t2.left) && equals(t1.right, t2.right)
        }

    fun isSymmetric1(root: TreeNode?): Boolean {
        val inverted = invert(root)
        return equals(inverted, root)
    }

    fun isSymmetric(left: TreeNode?, right: TreeNode?): Boolean =
        when {
            left == null && right == null -> true
            left?.`val` != right?.`val` -> false
            else -> isSymmetric(left?.left, right?.right) && isSymmetric(left?.right, right?.left)
        }

    fun isSymmetric(root: TreeNode?): Boolean {
        return isSymmetric(root?.left, root?.right)
    }
}

private data class IsSymmetricTestCase(
    val input: TreeNode?,
    val expected: Boolean,
)

private class IsSymmetricTest {
    companion object {
        @JvmStatic
        fun getTestData(): List<IsSymmetricTestCase> = listOf(
            IsSymmetricTestCase(null, true),
            IsSymmetricTestCase(TreeNode(1), true),
            IsSymmetricTestCase(
                TreeNode(1,
                    TreeNode(2, TreeNode(3), TreeNode(4)),
                    TreeNode(2, TreeNode(4), TreeNode(3)),
                ),
                true
            ),
            IsSymmetricTestCase(
                TreeNode(1,
                    TreeNode(2, null, TreeNode(3)),
                    TreeNode(2, null, TreeNode(3)),
                ),
                false
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(testCase: IsSymmetricTestCase) {
        val actual = IsSymmetricSolution().isSymmetric(testCase.input)
        Assertions.assertEquals(testCase.expected, actual)
    }
}
