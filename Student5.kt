class Student5(s: String, i: Int) {
    private val name: String
        get() {
            TODO()
        }
    private val age: Int
        get() {
            TODO()
        }
}

class Student6(name: String, age: Int) {

    public var Name = ""
        set(value) {
            field = value
        }

    public var Age = 20
        set(value) {
            field = value
        }

    init {
        Name = name
        Age = age
    }
}