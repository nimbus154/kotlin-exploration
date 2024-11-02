package leetcode.linkedlist

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class RemoveNthSolution {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        // n = remove nth from end (1 - remove last, 2 = remove second to last)
        // [1], 1 -> null
        // [1], 2 -> [1]
        // [1,2], 2 -> [2]
        // [1,2], 1 -> [1]
        // n = "number of positions away from null"
        // last element = 1 position away from null
        // null = end of list
        /*
        n < len(list) -> mutate list
        n == len(list) -> remove head
        n > len(list) -> return list

        is head null? return null
        now, len(list) > 0
        advance lead until n, or n - 1?

        if lead found the end (lead == null)
            either len(list) > n -> head
            or len(list) == n -> head.next

        now we know len(list) > n
        so we advance all the pointers until leader is null
        leader = leader.next
        update = remove
        remove = remove.next

        when leader = null
        update = remove.next
        return head
         */

        if (head == null) return null

        var endFinder: ListNode? = head
        var i = 0
        while (endFinder != null && i < n) {
            endFinder = endFinder.next
            i++
        }

        if (endFinder == null) {
            // found the end
            // either n > list.size (do nothing) or n == list.size (remove head)
            return if (n == i) head.next else head
        }

        // n > list.size
        var toRemove: ListNode? = head
        var toUpdate: ListNode? = head

        while (endFinder != null) {
            endFinder = endFinder.next
            toUpdate = toRemove
            toRemove = toRemove?.next
        }

        toUpdate?.next = toRemove?.next

        return head
    }
}


private data class TestCaseRemoveNth(
    val input: ListNode?,
    val n: Int,
    val output: ListNode?,
) {
    companion object {

    }
    override fun toString(): String {
        return "input ${input.toString()}, n ${n}, output: ${output?.toString()}"
    }
}

private class TestRemoveNth {
    companion object {
        @JvmStatic
        fun getTestCases(): List<TestCaseRemoveNth> = listOf(
            TestCaseRemoveNth(null, 10, null),
            TestCaseRemoveNth(ListNode(1), 1, null),
            TestCaseRemoveNth(ListNode(1), 2, ListNode(1)),
            TestCaseRemoveNth(
                ListNode(1, ListNode(2)),
                1,
                ListNode(1)
            ),
            TestCaseRemoveNth(
                ListNode(1, ListNode(2)),
                2,
                ListNode(2)
            ),
            TestCaseRemoveNth(
                ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5))))),
                2,
                ListNode(1, ListNode(2, ListNode(3, ListNode(5)))),
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getTestCases")
    fun `remove nth from end`(testCase: TestCaseRemoveNth) {
        val actual = RemoveNthSolution().removeNthFromEnd(testCase.input, testCase.n)
        Assertions.assertEquals(testCase.output?.toList(), actual?.toList())
    }
}