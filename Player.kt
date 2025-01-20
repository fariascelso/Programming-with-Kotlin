class Player(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
    val height: Double by map
}

class Player02(val maps: MutableMap<String, Any?>) {
    val name: String by maps
    val age: Int by maps
    val height: Double by maps
}