package leetcode.arraystring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.Collections.swap

// need to inspect quickly by value (insert, remove)
// uniform random access to all elements
// suggests a flat array, since you can access buckets equally
// hash table seems like a good option, but there is not a consistent random
// could use two data structures: a map of value to index and an array
// but removing makes the array sparse
// even using a hash table has trouble with random access, since they're sparsely populated
// insert and remove could use a hash table
// getRandom needs an array
// Is there a way to getRandom with a map?
//

class RandomizedSet {
    private val map = mutableMapOf<Int, Int>()
    private val list = mutableListOf<Int>()

    fun insert(`val`: Int): Boolean {
        if (`val` in map) return false
        list.add(`val`)
        map[`val`] = list.size - 1
        return true
    }

    fun remove(`val`: Int): Boolean {
        if (`val` !in map) return false
        val index = map[`val`]!!
        val lastIndex = list.size - 1
        if (index != lastIndex) {
            val lastElt = list[lastIndex]
            swap(list, index, lastIndex)
            map[lastElt] = index
        }
        list.removeLast()
        map.remove(`val`)
        return true
    }

    fun getRandom(): Int {
        return list.random()
    }
}


class RandomizedSetTest() {
    @Test
    fun operations() {
        val randomizedSet = RandomizedSet()

        Assertions.assertTrue(randomizedSet.insert(1))
        Assertions.assertFalse(randomizedSet.remove(2))
        Assertions.assertTrue(randomizedSet.insert(2))

        Assertions.assertTrue(randomizedSet.getRandom() in setOf(1, 2))

        Assertions.assertTrue(randomizedSet.remove(1))
        Assertions.assertFalse(randomizedSet.remove(1))
        Assertions.assertFalse(randomizedSet.insert(2))
        Assertions.assertEquals(2, randomizedSet.getRandom())
    }

    @Test
    fun ops2() {
        val randomizedSet = RandomizedSet()
        Assertions.assertTrue(randomizedSet.insert(0))
        Assertions.assertTrue(randomizedSet.insert(1))
        Assertions.assertTrue(randomizedSet.remove(0))
        Assertions.assertTrue(randomizedSet.insert(2))
        Assertions.assertTrue(randomizedSet.remove(1))
        Assertions.assertEquals(2, randomizedSet.getRandom())
    }

    @Test
    fun ops3() {
        val randomizedSet = RandomizedSet()
        Assertions.assertFalse(randomizedSet.remove(0))
        Assertions.assertFalse(randomizedSet.remove(0))
        Assertions.assertTrue(randomizedSet.insert(0))
        Assertions.assertEquals(0, randomizedSet.getRandom())
        Assertions.assertTrue(randomizedSet.remove(0))
        Assertions.assertTrue(randomizedSet.insert(0))
    }
}