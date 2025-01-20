class WithLazyProperty {
    val foo: Int by lazy {
        println("Initializing foo")
        2
    }
}