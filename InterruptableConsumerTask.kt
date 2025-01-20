import java.util.Random
import java.util.concurrent.BlockingQueue

class InterruptableConsumerTask(val queue: BlockingQueue<Int>): Runnable {

    override fun run() {
        try {
            while (!Thread.interrupted()) {
                val element = queue.take()
                println("I am processing element $element")
            }
        } catch (e: InterruptedException) {
            println("Terminando")
        }
    }
}