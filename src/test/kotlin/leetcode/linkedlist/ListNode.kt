package leetcode.linkedlist

internal class ListNode(var `val`: Int, n: ListNode? = null) {
    var next: ListNode? = n

    companion object {
        private tailrec fun from(list: List<Int>, acc: ListNode? = null): ListNode? =
            if (list.isEmpty()) acc
            else from(list.subList(0, list.size - 1), ListNode(list[list.size - 1], acc))

        fun from(list: List<Int>): ListNode? = from(list, null)
    }

    override fun toString(): String = toList().toString()

    private tailrec fun toList(node: ListNode?, acc: List<Int>): List<Int> =
        if (node != null) toList(node.next, acc + node.`val`)
        else acc

    fun toList(): List<Int> = toList(this, emptyList())
}
