package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// https://leetcode.com/problems/simplify-path/description/

fun simplifyPath(path: String): String {
    val noTrailing = path.trimEnd('/')
    val singleSlash = noTrailing.replace(Regex("/+"), "/")

    val tokens = singleSlash
        .split("/")
        .filter { it.isNotEmpty() }

    return tokens.fold(listOf<String>()) { acc, s ->
            if (s == ".") acc
            else if (s != "..") acc + s
            else if (acc.isNotEmpty()) acc.dropLast(1)
            else acc
        }
        .joinToString("/", "/")
}

class SimplifyPathTest {
   @Test
   fun empty() {
       val expected = "/"
       val actual = simplifyPath("/")
       assertEquals(expected, actual)
   }

    @Test
    fun trailingSlash() {
        val expected = "/home"
        val actual = simplifyPath("/home/")
        assertEquals(expected, actual)
    }

    @Test
    fun simpleDotDot() {
        val expected = "/"
        val actual = simplifyPath("/../../")
        assertEquals(expected, actual)
    }

    @Test
    fun doubleSlash() {
        val expected = "/home/foo"
        val actual = simplifyPath("/home//foo")
        assertEquals(expected, actual)
    }

    @Test
    fun complexPath() {
        val expected = "/dev/test"
        val actual = simplifyPath("/..//home/../foo/../dev/test")
        assertEquals(expected, actual)
    }

    @Test
    fun complexPath2() {
        val expected = "/dev/test"
        val actual = simplifyPath("/home/../foo/../dev/test")
        assertEquals(expected, actual)
    }

    @Test
    fun failingCase() {
        val expected = "/c"
        val actual = simplifyPath("/a/./b/../../c/")
        assertEquals(expected, actual)
    }
}
