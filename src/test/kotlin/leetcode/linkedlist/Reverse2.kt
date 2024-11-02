package leetcode.linkedlist

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class Reverse2Solution {
    private tailrec fun reverse(head: ListNode?, acc: ListNode?, end: ListNode? = null): ListNode? =
        if (head == null || head == end) acc
        else {
            val tail = head.next
            head.next = acc
            reverse(tail, head, end)
        }

    private fun reverseIterative(initHead: ListNode?, end: ListNode? = null): ListNode? {
        var acc: ListNode? = end
        var head = initHead
        while (head != null && head != end) {
            val tail = head.next
            head.next = acc

            acc = head
            head = tail
        }

        return acc
    }

    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? =
        if (head == null) null
        else if (left == right) head
        else {
            var preStart: ListNode? = null
            var start = head
            var end = head

            repeat(left - 1) {
                preStart = start
                start = start?.next
            }

            repeat(right - 1) {
                end = end?.next
            }

            val reversedSection = reverse(start, end?.next, end?.next)
//            val reversedSection = reverseIterative(start, end?.next)
            preStart?.next = reversedSection

            if (left == 1) end else head
        }
        // n = list.size
        // 1 <= n <= 500
        // 1 <= left <= right <= n

        // Input: head = [1,2,3,4,5], left = 2, right = 4
        // Output: [1,4,3,2,5]
        // left and right are not array indexes; they're index + 1
        // left = 2 means list index position 1
        // if left == right, list is unchanged
        // we have two cases
        // left == right -> nop
        // left < right -> actually do work
        //
        // iterate the list until just before left
        // advance right until last element
        // apply reversing algorithm
        // which is start.next = last.next
    // 1 -> 2 -> 3 ; 1.prev.next = 3, 1.next = 3.next 3.next = 1.next
    // 1, 2, 3, 4 -> 4 3 2 1
    // construct a new list
    // tmp = 1.next
    // 1.next = 4.next
    // 4.next = tmp
    //
}

private class Reverse2TestCase(
    val input: ListNode?,
    val left: Int,
    val right: Int,
    val expected: ListNode?,
)

private class Reverse2Test() {
    companion object {
        @JvmStatic

        fun getTestData(): List<Reverse2TestCase> = listOf(
            Reverse2TestCase(ListNode(5), 1, 1, ListNode(5)),
            Reverse2TestCase(
                ListNode.from(listOf(1, 2)),
                1, 2,
                ListNode.from(listOf(2, 1)),
            ),
            Reverse2TestCase(
                ListNode.from(listOf(1, 2, 3, 4, 5)),
                2, 4,
                ListNode.from(listOf(1, 4, 3, 2, 5)),
            ),
            Reverse2TestCase(
                ListNode.from(listOf(1, 2, 3, 4, 5)),
                1, 5,
                ListNode.from(listOf(5, 4, 3, 2, 1)),
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(case: Reverse2TestCase) {
        val actual = Reverse2Solution().reverseBetween(case.input, case.left, case.right)
        Assertions.assertEquals(case.expected?.toList(), actual?.toList())
    }
}

