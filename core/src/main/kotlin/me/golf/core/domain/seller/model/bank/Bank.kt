package me.golf.core.domain.seller.model.bank

import jakarta.persistence.*
import me.golf.core.common.BaseEntity
import me.golf.core.domain.seller.model.Seller
import java.time.LocalDateTime

@Entity
@Table(name = "banks")
class Bank(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val accountNumber: String,

    @Column(name = "account_note", nullable = false)
    val depositorName: String,

    @Column(name = "depositor", nullable = false)
    val isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    val seller: Seller,
): BaseEntity()