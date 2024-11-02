package coroutineexploration

import kotlinx.coroutines.runBlocking

fun main() {
    println("main start")
    runBlocking {
        println("runBlocking start")
        println("runBlocking end")
    }
    println("main end")
}