import kotlin.math.sqrt

class Roots(pos: Double, neg: Double) {
    fun roots(k: Int): Roots {
        require(k >=0)
        val root = sqrt(k.toDouble())
        return Roots(root, -root)
    }
}