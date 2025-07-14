package me.golf.core.domain.ticket.model

import jakarta.persistence.*
import me.golf.core.common.BaseEntity
import me.golf.core.domain.seller.model.Seller
import java.math.BigDecimal

@Entity
@Table(name = "ticket")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false, insertable = false, updatable = false)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    val seller: Seller,

    @Column(nullable = false)
    val price: BigDecimal,
): BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}