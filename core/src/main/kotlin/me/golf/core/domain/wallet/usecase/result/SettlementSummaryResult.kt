package me.golf.core.domain.wallet.usecase.result

import me.golf.core.domain.wallet.model.SettlementStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class SettlementSummaryResult(
    val id: Long,
    val amount: BigDecimal,
    val companyName: String?,
    val representName: String,
    val fee: BigDecimal,
    val settlementStatus: SettlementStatus,
    val completeAt: LocalDateTime?,
    val createdAt: LocalDateTime,
)
