interface WithId {
    val id: String
}

data class WithIdImpl(override val id: String) : WithId

class Record (id: String) : WithId by Record.identifier(id) {
    companion object Record {
        fun identifier(identifier: String) = WithIdImpl(identifier)
    }
}