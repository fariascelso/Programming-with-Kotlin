import java.io.InputStream
import java.math.BigDecimal
import java.time.DateTimeException
import java.util.Date

enum class CardType {
    VISA, MASTERCARD, AMEX
}

open class Payment(val amount: BigDecimal)
class CardPayment(amount: BigDecimal, val number: String, val expireDate: Date, val type: CardType): Payment(amount)

class ChequePayment: Payment {
    constructor(amount: BigDecimal, name: String, bankId: String) : super(amount) {
        this.name = name
        this.bankId = bankId
    }
    var name: String
        get() = this.name
    var bankId: String
        get() = this.bankId
}

interface Drivable {
    fun drive()
}

interface Sailable {
    fun saill()
}

class AmphibiousCar(val name: String) : Drivable, Sailable {
    override fun drive() {
        println("Driving...")
    }

    override fun saill() {
        println("Sailling...")
    }
}

interface IPersistable {
    fun save(stream: InputStream)
}

interface IPrintable {
    fun print()
}

abstract class DocumentAbst(val title: String)

class TextDocument(title: String) : IPersistable, DocumentAbst(title), IPrintable {
    override fun save(stream: InputStream) {
        println("Saving to input stream")
    }

    override fun print() {
        println("Document name: $title")
    }
}