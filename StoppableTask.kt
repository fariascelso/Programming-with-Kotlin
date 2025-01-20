import kotlin.concurrent.thread

class StoppableTask: Runnable {

    @Volatile var running = true
    override fun run() {
        thread {
            while (running) {
                println("Hello, I am running on a thread until I am stopped")
            }
        }
    }
}