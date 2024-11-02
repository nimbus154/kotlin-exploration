package coroutineexploration

import kotlinx.coroutines.*

suspend fun main() = coroutineScope {
    println("main start")
    launch {
        println("launch block start")
        delay(10)
        println("launch block end")
    }
    println("main end")
}