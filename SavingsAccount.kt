import java.math.BigDecimal

data class SavingsAccount(
    override val balance: BigDecimal,
    val interestRate: BigDecimal
) : AccountReifiable, Comparable<SavingsAccount> {
    override fun compareTo(other: SavingsAccount): Int = balance.compareTo(other.balance)
}

data class TradingAccount(
    override val balance: BigDecimal,
    val margin: Boolean
) : AccountReifiable, Comparable<TradingAccount> {
    override fun compareTo(other: TradingAccount): Int = balance.compareTo(other.balance)
}

class SavingsAccount2(override val balance: BigDecimal) : Account2
class TradingAccount2(override val balance: BigDecimal): Account2

data class SavingsAccount3(
    override val balance: BigDecimal,
    val interestRate: BigDecimal
) : Account3<AccountReifiable>

data class TradingAccount3(
    override val balance: BigDecimal,
    val margin: Boolean
) : Account3<AccountReifiable>

data class SavingsAccount4(
    override val balance: BigDecimal,
    val interestRate: BigDecimal
) : Account4<SavingsAccount4>

data class TradingAccount4(
    override val balance: BigDecimal,
    val margin: Boolean
) : Account4<TradingAccount4>