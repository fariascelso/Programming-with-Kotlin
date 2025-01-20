open class Fruit {
    fun isSafeToEat(): Boolean { return false }
}

class Apple: Fruit()
class Orange: Fruit()

class Crate<T>(val elements: MutableList<T>) {
    fun add(t:T) = elements.add(t)
    fun last() = elements.last()
}
