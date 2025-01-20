class Matrix(val a: Int, val b: Int, val c: Int, val d: Int, ) {
    operator fun plus(matrix: Matrix): Matrix {
        return Matrix(a + matrix.a, b + matrix.b, c + matrix.c, d + matrix.d)
    }
}