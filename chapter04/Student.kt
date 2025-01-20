package chapter04

class Student(val name: String, val registered: Boolean, credits: Int) {
    constructor(name: String): this(name, false, 0)
    constructor(name: String, registered: Boolean): this(name, registered, 0)
}