import java.util.Random
import java.util.concurrent.BlockingQueue

class ConsumerTask(val queue: BlockingQueue<Int>){

    @Volatile var running = true

    fun run() {
        while (running) {
            val element = queue.take()

            println("I am processing element $element")
        }
    }
}