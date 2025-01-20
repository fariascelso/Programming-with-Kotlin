interface UIElement {
    fun getHeight(): Int
    fun getWidth(): Int
}

class Rectangle2(val x1: Int, val x2: Int, val y1: Int, val y2: Int ): UIElement {
    override fun getHeight() = y2 - y1
    override fun getWidth() = x2 - x1
}

class Panel(val rectangle2: Rectangle2) : UIElement by rectangle2
