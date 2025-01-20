class EventStream<in T>(val listener: Listener<T>) {
    fun start() {}
    fun stop() {}
}

class EventStream02<in T>(val listener: Listener<in T>) {
    fun start() {}
    fun stop() {}
}