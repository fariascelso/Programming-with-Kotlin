import kotlin.random.Random

class RandomLongs(seed: Long) {
    private val random = Random(seed)
    operator fun invoke(): Long = random.nextLong()
}