package me.golf.app

import me.golf.core.domain.order.repository.OrderRepository
import me.golf.core.domain.wallet.converter.toWallet
import me.golf.core.domain.wallet.repository.WalletRepository
import me.golf.core.domain.wallet.usecase.WalletUseCase
import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val orderRepository: OrderRepository,
): WalletUseCase {

    @Transactional
    override fun create(command: WalletSaveCommand) {
        val order = orderRepository.getById(command.orderId)

        if (walletRepository.existsByOrderId(order)) {
            return
        }

        // todo: orderItem 기준으로 seller 가져오는 로직 추가
        val sellers = order.orderItems.map { it.ticket.seller }.distinct()

        sellers.forEach { walletRepository.save(toWallet(order, it)) }
    }
}