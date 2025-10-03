package me.golf.api.domain.wallet.response

import me.golf.core.domain.wallet.model.SettlementStatus
import me.golf.core.domain.wallet.usecase.result.SettlementSummaryResult
import java.math.BigDecimal
import java.time.LocalDateTime

data class SettlementSummaryResponse(
    val settlementId: Long,
    val amount: BigDecimal,
    val companyName: String?,
    val representName: String,
    val fee: BigDecimal,
    val settlementStatus: SettlementStatus,
    val completeAt: LocalDateTime?,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun toResponse(result: SettlementSummaryResult): SettlementSummaryResponse {
            return SettlementSummaryResponse(
                settlementId = result.id,
                amount = result.amount,
                companyName = result.companyName,
                representName = result.representName,
                fee = result.fee,
                settlementStatus = result.settlementStatus,
                completeAt = result.completeAt,
                createdAt = result.createdAt,
            )
        }
    }
}
