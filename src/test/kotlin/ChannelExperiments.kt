import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class ChannelExperimentsTest {

    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
    fun `channel things`() = runBlocking {
        val p = produce {
            var i = 0
            while (true) {
                send(i++)
            }
        }
    }
}