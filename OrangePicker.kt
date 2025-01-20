class OrangePicker(private val generator: Generator<Orange>) {
    fun pick() {
        val orange = generator.generate()
        peel(orange.toString())
    }

    private fun peel(orange: String): Unit = println("Descascar laranja")
}