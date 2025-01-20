class Mappings {
    private val map = hashMapOf<Int, String>()
    private fun String.stringAdd() {
        map.put(this@Mappings.hashCode(), this)
    }
    fun add(str: String) = str.stringAdd()
}