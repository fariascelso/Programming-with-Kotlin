import java.util.Random
import java.util.concurrent.BlockingQueue

class ProducerTask(val queue: BlockingQueue<Int>){

    @Volatile var running = true
    private val random = Random()

    fun run() {
        while (running) {
            Thread.sleep(1000)
            queue.put(random.nextInt())
        }
    }
}