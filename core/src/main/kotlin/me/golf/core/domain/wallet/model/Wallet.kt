package me.golf.core.domain.wallet.model

import jakarta.persistence.*
import me.golf.core.common.BaseEntity
import me.golf.core.domain.order.model.Order
import me.golf.core.domain.seller.model.Seller
import org.hibernate.annotations.DynamicInsert
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "wallet")
@DynamicInsert
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "fee", nullable = false)
    val fee: BigDecimal,

    @Column(name = "settlement_status", nullable = false)
    val settlementStatus: SettlementStatus,

    @Column(name = "settlement_complete_date", nullable = true)
    val settlementCompleteDate: LocalDateTime? = null,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    val seller: Seller,
): BaseEntity()
