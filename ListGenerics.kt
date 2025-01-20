sealed class ListGenerics<out T> {
    fun isEmpty() = when (this) {
        is Empty -> true
        is Node -> false
    }
    fun size(): Int = when (this) {
        is Empty -> 0
        is Node -> 1 + this.next.size()
    }
    fun tail(): ListGenerics<T> =when (this) {
        is Node -> this.next
        is Empty -> this
    }
    fun head(): T = when (this) {
        is Node<T> -> this.value
        is Empty -> throw RuntimeException("Empty list")
    }

    operator fun get(pos: Int): T {
        require(pos >= 0, {"Position must be >= 0"})
        return when (this) {
            is Node -> if (pos == 0) head() else this.next.get(pos -1)
            is Empty -> throw IndexOutOfBoundsException()
        }
    }

    fun append(t: @UnsafeVariance T): ListGenerics<T> = when (this) {
        is Node -> Node(this.value, this.next.append(t))
        is Empty -> Node(t, Empty)
    }

    companion object {
        operator fun <T>invoke(vararg values: T): ListGenerics<T> {
            var temp: ListGenerics<T> = Empty
            for (value in values) {
                temp = temp.append(value)
            }
            return temp
        }
    }
}

private class Node<T>(val value: T, val next: ListGenerics<T>): ListGenerics<T>()

private data object Empty : ListGenerics<Nothing>()