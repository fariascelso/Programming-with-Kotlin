class Static {
    fun showFirstCharacter(input: String): Char {
        if(input.isEmpty()) throw IllegalArgumentException()
        return input.first()
    }
}