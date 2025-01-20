class WithPrivateSetter(property: Int) {
    var SomeProperty: Int = 0
        private set(value) {
            field = value
        }

    init {
        SomeProperty = property
    }
}

open class WithInheritance {
    open var isAvailable: Boolean = false
        get() = field
        protected set(value) {
            field = value
        }
}

open class WithInheritanceDerived(isAvailable: Boolean) : WithInheritance() {
    override var isAvailable: Boolean = isAvailable
        get() {
            return super.isAvailable
        }
        set(value) {
            println("WithInheritanceDerived.isAvailable")
            field = value
        }

    fun doSomething() {
        isAvailable = false
    }
}