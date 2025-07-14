package me.golf.core.domain.order.model

import jakarta.persistence.*
import me.golf.core.domain.orderitem.model.OrderItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    var id: String,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "orderDate", nullable = false)
    val orderDate: LocalDateTime,

    @Column(name = "state", nullable = false)
    val orderState: String,

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val orderItems: List<OrderItem>
) {

    /**
     * 수수료 정책: amount의 10%
     */
    fun fee(): BigDecimal {
        return amount.multiply(FEE_RATE).setScale(2, RoundingMode.HALF_EVEN)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        val FEE_RATE: BigDecimal = BigDecimal.valueOf(0.10)
    }
}