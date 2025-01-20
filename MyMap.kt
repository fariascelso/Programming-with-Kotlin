class MyMap<K, V> {
    private val map = mutableMapOf<K, V>()

    fun put(key: K, value: V) {
        map[key] = value
    }

    fun get(key: K): V? {
        return map[key]
    }
}