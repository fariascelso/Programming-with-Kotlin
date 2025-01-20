object RectangleObj {

    fun printArea(width: Int, height: Int): Unit {
        val area = calculateArea2(width, height)
        println("The area is $area")
    }
    private fun calculateArea(width: Int, height: Int): Int {
        return width * height
    }
    private fun calculateArea2(width: Int, height: Int): Int =  width * height
}