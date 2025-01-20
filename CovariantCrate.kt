class CovariantCrate<out T>(val elements: List<T>) {
    fun last(): T = elements.last()
}

