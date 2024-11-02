package leetcode

// https://leetcode.com/problems/group-anagrams/submissions/1240434106/

import kotlin.test.Test
import kotlin.test.assertEquals

class GroupAnagramsSolution {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val groups = strs.groupBy {
            it
                .split("")
                .sorted()
        }

        return groups
            .values
            .map { it.sorted() }
            .sortedBy { it.size }
            .toList()
    }
}

class GroupAnagramsTest {
    @Test
    fun `empty`() {
        val actual = GroupAnagramsSolution().groupAnagrams(arrayOf(""))
        assertEquals(listOf(listOf("")), actual)
    }

    @Test
    fun `single`() {
        val actual = GroupAnagramsSolution().groupAnagrams(arrayOf("a"))
        assertEquals(listOf(listOf("a")), actual)
    }

    @Test
    fun `sample`() {
        val input = arrayOf("eat", "tea", "tan", "ate", "nat", "bat")
        val expected = listOf(
            listOf("bat"),
            listOf("nat", "tan"),
            listOf("ate", "eat", "tea")
        )
        val actual = GroupAnagramsSolution().groupAnagrams(input)
        assertEquals(expected, actual)
    }
}
