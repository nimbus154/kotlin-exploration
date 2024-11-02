package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

// https://leetcode.com/problems/all-possible-full-binary-trees/description/
/*
Given an integer n, return a list of all possible full binary trees with n nodes.
Each node of each tree in the answer must have Node.val == 0.

Each element of the answer is the root node of one possible tree.
You may return the final list of trees in any order.

A full binary tree is a binary tree where each node has exactly 0 or 2 children.

Input: n = 7
Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]

Example 2:

Input: n = 3
Output: [[0,0,0]]

1 <= n <= 20

1 -> [[0]]
2 -> [] // can't make any full binary trees
3 -> [[0,0,0]]
4 -> []
5 ->

if > 1, root must always have two children
if > 3, children must always have two children

Seems like all even numbers can't be full

 */

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

data class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun makeNode(right: TreeNode? = null, left: TreeNode? = null): TreeNode {
    val node = TreeNode(0)
    node.right = right
    node.left = left
    return node
}

class Solution {
    private fun getAllNodePairValues(k: Int): List<Pair<Int, Int>> =
        (1 until k step 2).map { it to (k - it) }

    fun allPossibleFBT(n: Int): List<TreeNode?> {
        if (n % 2 == 0) return emptyList()

        val init: Map<Int, List<TreeNode?>> = mapOf(1 to listOf(makeNode()))
        val lookup = (3..n step 2).fold(init) { memo, i ->
            val pairs = getAllNodePairValues(i - 1)
            val trees = pairs
                .flatMap { (leftVal, rightVal) ->
                    memo[leftVal]!!.flatMap { left ->
                        memo[rightVal]!!.map { right ->
                            makeNode(left, right)
                        }
                    }
                }

            memo + (i to trees)
        }

        return lookup[n]!!
    }
}

class AllFullBinaryTreesTest {
    @Test
    fun n1() {
        val actual = Solution().allPossibleFBT(1)
        val expected = listOf(makeNode())
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun n2() {
        val actual = Solution().allPossibleFBT(2)
        val expected: List<TreeNode> = emptyList()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun n3() {
        val actual = Solution().allPossibleFBT(3)
        val expected = listOf(
            makeNode(makeNode(), makeNode())
        )
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun n5() {
        val actual = Solution().allPossibleFBT(5)
        val expected = setOf(
            makeNode(makeNode(makeNode(), makeNode()), makeNode()),
            makeNode(makeNode(), makeNode(makeNode(), makeNode())),
        )
        Assertions.assertEquals(expected, actual.toSet())
    }

    @Test
    fun n7() {
        val actual = Solution().allPossibleFBT(7)
        val fiveNodeA = makeNode(makeNode(makeNode(), makeNode()), makeNode())
        val fiveNodeB = makeNode(makeNode(), makeNode(makeNode(), makeNode()))
        val balanced = makeNode(makeNode(), makeNode())
        val expected = setOf(
            makeNode(fiveNodeA, makeNode()),
            makeNode(makeNode(), fiveNodeA),
            makeNode(balanced, balanced),
            makeNode(fiveNodeB, makeNode()),
            makeNode(makeNode(), fiveNodeB),
        )
        Assertions.assertEquals(expected, actual.toSet())
    }
}