import java.io.Serializable

class Year(val value: Year): Comparable<Year> {
    override fun compareTo(other: Year): Int = this.value.compareTo(other.value)
}
class SerializableYear(val value: Int): Comparable<SerializableYear>, Serializable {
    override fun compareTo(other: SerializableYear): Int = this.value.compareTo(other.value)
}

