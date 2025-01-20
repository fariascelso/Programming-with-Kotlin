import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.concurrent.CyclicBarrier

class CopyTask(
    val dir: Path,
    val paths: List<Path>,
    val barrier: CyclicBarrier
): Runnable {
    override fun run() {
        for (path in paths) {
            val dest = dir.resolve(path)
            Files.copy(path, dest, StandardCopyOption.REPLACE_EXISTING)
            barrier.await()
        }
    }
}