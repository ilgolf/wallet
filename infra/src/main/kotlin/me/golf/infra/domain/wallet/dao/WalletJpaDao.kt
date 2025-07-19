package me.golf.infra.domain.wallet.dao

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.wallet.model.Wallet
import org.springframework.data.jpa.repository.JpaRepository

interface WalletJpaDao: JpaRepository<Wallet, Long> {
    fun existsByOrder(order: Order): Boolean
}
