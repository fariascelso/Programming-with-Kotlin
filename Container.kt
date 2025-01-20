open class Container {
    protected open val fieldA: String = "Some value"
}

class DerivedContainer: Container() {
    public override val fieldA: String = "Something value"
}