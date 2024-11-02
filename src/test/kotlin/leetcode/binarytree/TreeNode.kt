package leetcode.binarytree

internal class TreeNode(var `val`: Int, l: TreeNode? = null, r: TreeNode? = null) {
    var left: TreeNode? = l
    var right: TreeNode? = r

    fun TreeNode?.toInOrderList(): List<TreeNode> {
        if (this == null) return emptyList()
        return left.toInOrderList() + listOf(this) + right.toInOrderList()
    }

    override fun toString(): String =
        toInOrderList()
            .map { it.`val` }
            .joinToString(prefix = "[", postfix = "]", separator = ", ")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is TreeNode) return false

        return `val` == other.`val`
                && (left == other.left)
                && (right == other.right)
    }
}