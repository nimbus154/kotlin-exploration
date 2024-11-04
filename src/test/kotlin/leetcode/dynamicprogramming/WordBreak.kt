package leetcode.dynamicprogramming

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

// https://leetcode.com/problems/word-break/description/?envType=study-plan-v2&envId=top-interview-150

/*
Given a string s and a dictionary of strings wordDict, return true if
s can be segmented into a space-separated sequence of one or more dictionary words.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

1 <= s.length <= 300
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 20
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.

A solution is true if
s is in dict (base case)
 -> ignore empty string, since it's outside the problem bounds

true if s in D
or f(s - w, D) is true

find all w in D:
    if w == s: return true
    if w is a prefix of s: return f(s - w, D)
 return false

 How to do this bottom-up?
 Prefix or affix doesn't make a huge difference
 let wb[i] = true if the substring of s starting at index i would return true
 wb[0] is the answer or wb[s.length] is the ansewr if you do affixes

 How to populate wb?
 wb[0] = first character in s is in wd
 wb[1] = True if any is true
    leetcode
    t: (lee, t) (le, et) (l, eet) (leet)
    c: (leet, c) (lee, tc) (le, etc) (l, eetc) (leetc)

    create all possible affixes (start at end, go to first; it's possible first will be in wd)
    if affix in wd && wb[wb.len - affix] is true
 */

private class WordBreakSolution {

    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        return s.indices.fold(emptyList<Boolean>()) { wb, i ->
            val wbi = (i downTo 0).any { start ->
                val affix = s.substring(start, i + 1)
                affix in wordDict && wb.getOrElse(start - 1) { true }
            }

            wb + wbi
        }.last()
        // 0
        // 0 to 0
        // substring (0,0)
    }

    fun wordBreakTopDown(s: String, wordDict: List<String>): Boolean {
        wordDict.forEach { word ->
            if (word == s) return true
            else if (s.startsWith(word)) return wordBreak(s.substring(word.length), wordDict)
        }
        return false
    }
}

private data class WordBreakTestCase(
    val s: String,
    val wordDict: List<String>,
    val expected: Boolean
)

private class WordBreakTest {
    companion object {
        @JvmStatic
        fun getData() = listOf(
            WordBreakTestCase( "leetcode", listOf("leet","code"), true),
            WordBreakTestCase( "leetcod", listOf("leet","code"), false),
            WordBreakTestCase("applepenapple", listOf("apple","pen"), true),
            WordBreakTestCase("catsandog", listOf("cats","dog","sand","and","cat"), false)
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun test(testCase: WordBreakTestCase) {
        val actual = WordBreakSolution().wordBreak(testCase.s, testCase.wordDict)
        Assertions.assertEquals(testCase.expected, actual)
    }
}