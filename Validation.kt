sealed class Validation {
    abstract infix operator fun plus(other: Validation): Validation
}

data object Valid: Validation() {
    override fun plus(other: Validation): Validation = when (other) {
        is Invalid -> other
        is Valid -> this
    }
}

class Invalid(val errors: List<String>): Validation() {
    companion object {
        operator fun invoke(error: String) = Invalid(listOf(error))
    }

    override fun plus(other: Validation): Validation = when (other) {
        is Invalid -> Invalid(this.errors + other.errors)
        is Valid -> this
    }
}

fun <T> getOrElse(t:T, or: (List<String>) -> T): T {
    TODO()
}