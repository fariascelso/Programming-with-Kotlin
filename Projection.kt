sealed class Projection<T> {
    abstract fun <U> map(fn: (T) -> U): Projection<U>
    abstract fun getOrElse(or: () -> T): T
}

class ValueProjection<T>(val value: T): Projection<T>() {
    override fun <U> map(fn: (T) -> U): Projection<U> = ValueProjection(fn(value))
    override fun getOrElse(or: () -> T): T = value
}
class EmptyProjection<T>: Projection<T>() {
    override fun <U> map(fn: (T) -> U): Projection<U> = EmptyProjection<U>()
    override fun getOrElse(or: () -> T): T = or()
}

fun <T> Projection<T>.getOrElse(or: () -> T): T = when (this) {
    is EmptyProjection -> or()
    is ValueProjection -> this.value
}

fun <T> exists(fn: (T) -> Boolean) {}
fun <T> filter(fn: (T) -> Boolean): Projection<T> {TODO()}