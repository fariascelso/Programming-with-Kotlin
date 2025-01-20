class Rocket() {
    var lat: Double = 0.0
    var long: Double = 0.0

    fun explode() {
        println("Boom")
    }

    fun setCourse(
        lat: Double = 0.0,
        long: Double = 0.0
    ) {
        require(lat.isValid())
        require(lat.isValid())
        this.lat = lat
        this.long = long
    }
    private fun Double.isValid() = Math.abs(this) <= 180
}