import java.math.BigDecimal

interface AccountReifiable {
    val balance: BigDecimal
}

interface Account2 : Comparable<Account2> {
    val balance: BigDecimal
    override fun compareTo(other: Account2): Int = balance.compareTo(other.balance)
}

interface Account3<E: AccountReifiable> : Comparable<E> {
    val balance: BigDecimal
    override fun compareTo(other: E): Int = balance.compareTo(other.balance)
}

interface Account4<E: Account4<E>> : Comparable<E> {
    val balance: BigDecimal
    override fun compareTo(other: E): Int = balance.compareTo(other.balance)
}

sealed interface Accounts{

}
