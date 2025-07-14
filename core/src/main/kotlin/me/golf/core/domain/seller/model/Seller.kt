package me.golf.core.domain.seller.model

import jakarta.persistence.*
import me.golf.core.common.BaseEntity
import me.golf.core.domain.seller.model.bank.Bank

@Entity
class Seller(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "representativeName", nullable = false)
    val representativeName: String,

    @Column(name = "companyName", nullable = true)
    val companyName: String,

    @Column(name = "licenseNumber", nullable = false)
    val licenseNumber: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "phoneNumber", nullable = false)
    val phoneNumber: String,

    @OneToMany(mappedBy = "seller", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val bankInfo: List<Bank>,

    @Column(name = "isActive", nullable = false)
    val isActive: Boolean = true,
): BaseEntity()