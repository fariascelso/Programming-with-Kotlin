class Ellipsis : Shape() {
    override fun isHit(x: Int, y: Int): Boolean {
        val xRadius = Width.toDouble() / 2
        val yRadius = Height.toDouble() / 2
        val centerX = XLocation + xRadius
        val centerY = YLocation + yRadius
        if (xRadius == 0.0 || yRadius == 0.0)
            return false
        val normalizedX = centerX - XLocation
        val normalizedY = centerY - XLocation
        return (normalizedX * normalizedX) /
                (xRadius * xRadius) +
                (normalizedY * normalizedY) /
                (yRadius * yRadius) <= 1.0
    }
}

class Rectangle : Shape() {
    override fun isHit(x: Int, y: Int): Boolean {
        return x >= XLocation && x <= (XLocation + Width)
                && y >= YLocation && y <= (YLocation + Height)
    }
}