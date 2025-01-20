import java.math.BigDecimal

data class ShoppingItem(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val quantity: Int
)