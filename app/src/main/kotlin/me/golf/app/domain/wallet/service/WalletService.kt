package me.golf.app.domain.wallet.service

import me.golf.app.domain.wallet.dto.WalletSaveCompleteReplyEvent
import me.golf.core.common.PageResponse
import me.golf.core.domain.order.repository.OrderRepository
import me.golf.core.domain.seller.model.SettlementSummary
import me.golf.core.domain.wallet.converter.toWallet
import me.golf.core.domain.wallet.repository.WalletRepository
import me.golf.core.domain.wallet.usecase.WalletUseCase
import me.golf.core.domain.wallet.usecase.command.SettlementSummarySearch
import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand
import me.golf.core.domain.wallet.usecase.result.SettlementSummaryResult
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val orderRepository: OrderRepository,
    private val eventPublisher: ApplicationEventPublisher
): WalletUseCase {

    @Transactional
    override fun create(command: WalletSaveCommand) {
        val order = orderRepository.getById(command.orderId)

        if (walletRepository.existsByOrderId(order)) {
            return
        }

        val sellers = orderRepository.findSellersByOrderId(command.orderId)
            .map { it.toWallet(order) }

        walletRepository.saveAll(sellers)

        eventPublisher.publishEvent(WalletSaveCompleteReplyEvent(command.orderId))
    }

    override fun getSettlements(search: SettlementSummarySearch): PageResponse<SettlementSummaryResult> {
        val results = orderRepository.findSummaryBySearch(
            settlementId = search.settlementId,
            page = search.page,
            limit = search.limit
        ).map {
            it.toResult()
        }

        return PageResponse.create(results.content, results.totalElements)
    }

    private fun SettlementSummary.toResult(): SettlementSummaryResult {
        return SettlementSummaryResult(
            id = this.id,
            amount = this.amount,
            companyName = this.companyName,
            representName = this.representName,
            fee = this.fee,
            settlementStatus = this.settlementStatus,
            completeAt = this.completeAt,
            createdAt = this.createdAt,
        )
    }
}
