class CollectionMatchers<T>(val collection: Collection<T>) {

    fun contains(rhs: T) {
        if (!collection.contains(rhs))
            throw RuntimeException("Collection did not contain $rhs")
    }
    fun notContains(rhs: T) {
        if (collection.contains(rhs))
            throw RuntimeException("Collection should not contain $rhs")
    }
    fun haveSizeLess(size: Int) {
        if (collection.size >= size)
            throw RuntimeException("Collection should have size less than $size")
    }

    infix fun <T> Collection<T>.should(fn: CollectionMatchers<T>.() -> Unit) {
        val matchers = CollectionMatchers(this)
        matchers.fn()
    }
}