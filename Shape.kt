abstract class Shape protected constructor() {
    private var xLocation: Int = 0
    private var yLocation: Int = 0
    private var width: Double = 0.0
    private var height: Double = 0.0

    var XLocation: Int
        get() = xLocation
        set(value) {
            xLocation = value
        }

    var YLocation: Int
        get() = yLocation
        set(value) {
            yLocation = value
        }

    var Width: Double
        get() = width
        set(value) {
            width = value
        }

    var Height: Double
        get() = height
        set(value) {
            height = value
        }

    abstract fun isHit(x: Int, y: Int): Boolean
}
