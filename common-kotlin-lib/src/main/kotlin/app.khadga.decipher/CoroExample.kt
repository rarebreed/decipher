package app.khadga.decipher

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun simple(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(1000) // pretend we are computing it
        yield(i) // yield next value
    }
}

suspend fun randomDelay(): Long {
    val delayed = Random.nextLong(1, 5) * 1000
    delay(delayed)
    println("Delayed $delayed millisecond")
    return delayed
}

fun main() = runBlocking {
    val timed = measureTimeMillis {
        val result = coroutineScope {
            listOf(
                async { randomDelay() },
                async { randomDelay() },
                async { randomDelay() }
            ).sumOf {
                // unlike javascript, await() does not block
                it.await()
            }
        }
        println("Result is $result")
    }
    println("Tasks took $timed milliseconds")
}