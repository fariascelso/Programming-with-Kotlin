interface Printable {
    fun print(): Unit
}

enum class Word: Printable {
    HELLO {
        override fun print() {
            println("Word is HELLO")
        }
    } ,
    BYEW {
        override fun print() {
            println("Word is BYE")
        }
    }
}