package leetcode.linkedlist

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

private class ReverseSolution {
    private tailrec fun reverse(head: ListNode?, acc: ListNode?): ListNode? =
        if (head == null) acc
        else {
            val tail = head.next
            head.next = acc
            reverse(tail, head)
        }

    private fun reverseIterative(initHead: ListNode?): ListNode? {
        var acc: ListNode? = null
        var head = initHead
        while (head != null) {
            val tail = head.next
            head.next = acc

            acc = head
            head = tail
        }

        return acc
    }

//    fun reverseList(head: ListNode?): ListNode? = reverse(head, null)
    fun reverseList(head: ListNode?): ListNode? = reverseIterative(head)
}

private data class ReverseTestCase(
    val input: ListNode?,
    val expected: ListNode?,
)

private class ReverseTest {

    companion object {
        @JvmStatic
        fun getTestData(): List<ReverseTestCase> = listOf(
            ReverseTestCase(ListNode.from(emptyList()), null),
            ReverseTestCase(ListNode.from(listOf(1,2)), ListNode.from(listOf(2,1))),
            ReverseTestCase(ListNode.from(listOf(1,2,3)), ListNode.from(listOf(3,2,1))),
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun test(case: ReverseTestCase) {
        val actual = ReverseSolution().reverseList(case.input)
        Assertions.assertEquals(case.expected?.toList(), actual?.toList())
    }
}
