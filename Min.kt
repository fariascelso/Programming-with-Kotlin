object Min {
    operator fun invoke(a: Int, b: Int): Int = if (a <= b) a else b
    operator fun invoke(a: Int, b: Int, c: Int): Int = invoke(invoke(a,b),c)
    operator fun invoke(a: Int, b: Int, c: Int, d: Int): Int = invoke(invoke(a,b), invoke(c,d))
    operator fun invoke(a: Long, b: Long): Long = if (a <= b) a else b
    operator fun invoke(a: Long, b: Long, c: Long): Long =  invoke(invoke(a,b),c)
    operator fun invoke(a: Long, b: Long, c: Long, d: Long): Long = invoke(invoke(a,b), invoke(c,d))
}