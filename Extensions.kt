import java.util.concurrent.ConcurrentHashMap

//fun <T : AutoCloseable, U> whitResource(resource: T, fn: (T) -> U): U {
//    try {
//        return fn(resource)
//    } finally {
//        resource.close()
//    }
//}

inline fun <T : AutoCloseable, U, V> whitResource(resource: T, before: (T) -> U, noinline after: (U) -> V): V {
    val u = try {
        before(resource)
    } finally {
        resource.close()
    }
    return after(u)
}

fun <P1, P2, R> Function2<P1, P2, R>.curried(): (P1) -> (P2) -> R = {
    p1 -> {
        p2 -> this(p1, p2)
    }
}

fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.curried(): (P1) -> (P2) -> (P3) -> R = {
    p1 -> {
        p2 -> {
            p3 -> this(p1, p2, p3)
        }
    }
}

fun <A, R> memoize(fn: (A) -> R): (A) -> R {
    val map = ConcurrentHashMap<A, R>()
    return { a ->
        map.getOrPut(a) {
            fn(a)
        }
    }
}

fun <A, R> Function1<A, R>.memoized(): (A) -> R {
    val map = ConcurrentHashMap<A, R>()
    return {
        a -> map.getOrPut(a) {
            this.invoke(a)
        }
    }
}

fun equals(first: Any, second: Any){
    if (first != second)
        throw RuntimeException("$first was not equal to $second")
}

infix fun Any.shouldEqual(other: Any){
    if (this != other)
        throw RuntimeException("$this was not equal to $other")
}

infix fun <E> Collection<E>.shouldContain(element: E): Unit {
    if (!this.contains(element))
        throw RuntimeException("Collection did not contain $element")
}

infix fun Unit.or(other: Unit) {}

fun <T> contain(rhs: T) = object : Matcher<Collection<T>> {
    override fun test(lhs: Collection<T>) {
        if (!lhs.contains(rhs))
            throw RuntimeException("Collection did not contain $rhs")
    }
}
fun <T> beEmpty() = object : Matcher<Collection<T>> {
    override fun test(lhs: Collection<T>) {
        if (lhs.isNotEmpty())
            throw RuntimeException("Collection should be empty")
    }
}

infix fun <T> T.should(matcher: Matcher<T>) {
    matcher.test(this)
}

inline fun <reified T> runtimeType() {
    println("My type is parameter is " + T::class.qualifiedName)
}

inline fun <reified T> List<Any>.collect(): List<T> {
    return this.filter { it is T }.map { it as T }
}

inline fun <reified T>printT(any: Any) {
    if(any is T)
        println("I am a tee: $any")
}