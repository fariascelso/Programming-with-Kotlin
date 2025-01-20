class BingoNumber(val name: String, val age: Int) {
    operator fun compareTo(other: BingoNumber): Int {
        return when {
            age < other.age -> -1
            age > other.age -> 1
            else -> 0
        }
    }
}