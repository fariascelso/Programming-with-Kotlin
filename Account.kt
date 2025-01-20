class Account {
    var balance = 0.0

    fun add(amount: Double) {
        this.balance += amount
    }
}
class InfixAccount {
    var balance = 0.0

    infix fun add(amount: Double) {
        this.balance += amount
    }
}