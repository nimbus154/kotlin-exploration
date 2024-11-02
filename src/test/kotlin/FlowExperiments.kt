import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FlowExperimentsTest {

    @Test
    fun `it terminates`() = runBlocking {
        val sharedFlow = MutableSharedFlow<Int>()

        // consumer
        val result = async {
            sharedFlow
                .transformWhile {
                    emit(it)
                    it < 3
                }.toList()
        }

        // producer
        launch {
            val ctx = coroutineContext
            sharedFlow.emit(1)
            delay(100)
            sharedFlow.emit(2)
            delay(100)
            sharedFlow.emit(3)
        }

        assertEquals(listOf(1, 2, 3), result.await())
    }
}