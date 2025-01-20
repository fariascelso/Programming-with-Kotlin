class RectangleXX: Shape() {
    override fun isHit(x: Int, y: Int): Boolean {
        return x >= XLocation && x <= (XLocation + Width) && y>= YLocation && y <= (YLocation + Height)
    }
}