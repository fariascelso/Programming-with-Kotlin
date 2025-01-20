//Construção de label (rótulos) this@label

class A {
    private val someField: Int = 1
    inner class B {
        private val someField: Int = 1
        fun foo(s: String) {
            println("Field <someField> from B " + this.someField)
            println("Field <someField> from B " + this@B.someField)
            println("Field <someField> from B " + this@A.someField)
        }
    }
}