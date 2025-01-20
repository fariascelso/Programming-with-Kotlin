interface Shape02 {
    val Area: Double
    get;
}

class Rectangle02(val width: Double, val height: Double): Shape02 {
    override val Area: Double
        get() = width * height
    val isSquare: Boolean = width == height
}