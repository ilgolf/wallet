package me.golf.infra.domain.wallet.repository

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.wallet.model.Wallet
import me.golf.core.domain.wallet.repository.WalletRepository
import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand
import me.golf.infra.domain.wallet.client.PaymentClient
import me.golf.infra.domain.wallet.dao.WalletJpaDao
import org.springframework.stereotype.Repository

@Repository
class WalletRepositoryImpl(
    private val walletJpaDao: WalletJpaDao,
    private val paymentClient: PaymentClient,
): WalletRepository {

    override fun save(wallet: Wallet) {
        walletJpaDao.save(wallet)
    }

    override fun existsByOrderId(order: Order): Boolean {
        return walletJpaDao.existsByOrder(order)
    }

    override fun updateWalletEventFail(command: WalletSaveCommand) {
        paymentClient.sendWalletEventFailMessage(command.userId, command.orderId)
    }
}