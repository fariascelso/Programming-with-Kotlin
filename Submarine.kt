class Submarine {
    fun fire() {
        println("Firing torpedoes")
    }
    fun submerge() {
        println("Submerging")
    }

    fun Submarine.fire() {
        println("Fire on board!")
    }
    fun Submarine.submerge(depth: Int) {
        println("Submerging to a depth of $depth fathoms")
    }
}