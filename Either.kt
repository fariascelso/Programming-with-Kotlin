sealed class Either <out L, out R> {
    fun <T> fold(lfn: (L) -> T, rfn: (R) -> T): T = when (this) {
        is Left -> lfn(this.value)
        is Right -> rfn(this.value)
    }

    fun leftProjection(): Projection<@UnsafeVariance L> = when (this) {
        is Left -> ValueProjection(this.value)
        is Right -> EmptyProjection<L>()
    }

    fun rightProjection(): Projection<@UnsafeVariance R> = when (this) {
        is Left -> EmptyProjection<R>()
        is Right -> ValueProjection(this.value)
    }
}

class Left <out L>(val value: L) : Either<L, Nothing> ()
class Right <out R>(val value: R) : Either<Nothing, R> ()

class Address(val town: String, val postCode: String)

fun getCurrentUser(): Either<String, User> {
    val randomNumber = (1..10).random()

    return if (randomNumber % 2 == 0) {
        Right(User("Name", true))
    } else {
        Left("Erro ao obter usuário.")
    }
}

//fun getUserAddresses(userId: User): Either<String, List<Address>> {
//    // Simulando uma lógica que pode falhar
//    return if (userId > 0) {
//        // Suponha que aqui você obtenha uma lista de endereços para o usuário
//        Right(
//            listOf(
//                Address("Town1", "12345"),
//                Address("Town2", "67890")
//            )
//        )
//    } else {
//        Left("Usuário não encontrado.")
//    }
//}
