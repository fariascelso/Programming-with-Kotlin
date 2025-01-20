open class Animal

class Sheep : Animal()
class Frog : Animal()

abstract class Farm {
    abstract fun get(): Animal
}

abstract class SheepFarm : Farm() {
    override fun get(): Animal {
        return Sheep()
    }
}
