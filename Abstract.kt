import java.util.*

abstract class Abstract {
    abstract fun doSomething()
}

open class AParent protected constructor() {
    open fun someMethod(): Int = Random().nextInt()
}

abstract class DDerived : AParent() {
    abstract override fun someMethod(): Int
}

class  AlwaysOne : DDerived() {
    override fun someMethod(): Int {
        return 1
    }
}

abstract class SingleEngineAirplane protected constructor() {
    abstract fun fly()
}

class CesnaAirplane : SingleEngineAirplane () {
    final override fun fly() {
        println("Flying a cesna")
    }
}

open class Base {
    open val property1: String
        get() = "Base::value"
}

class Derived1: Base() {
    override val property1: String
        get() = "Derived::value"
}

class Derived2(val property: String) : Base() {}

open class BaseB(open val propertyFoo: String) {

}

class DerivedB : BaseB("") {
    private var _propFoo: String = ""
    override var propertyFoo: String
        get() = _propFoo

        set(value) {
            _propFoo = value
        }
}