class Counter(val k: Int) {
    operator fun plus(j: Int): Counter = Counter(k + j)
}