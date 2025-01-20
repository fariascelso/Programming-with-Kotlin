class Aircraft(name: String, manufacturer: String, capacity: Int) {
    companion object {
        fun boeing(name: String, capacity: Int) = Aircraft(name, "Boeing", capacity)
    }
}