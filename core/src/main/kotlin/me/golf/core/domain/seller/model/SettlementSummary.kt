package me.golf.core.domain.seller.model

import me.golf.core.domain.wallet.model.SettlementStatus
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 정산 정보 조회용 Query Domain model
 * 특징상 Jpa Entity가 아닌 data class 입니다.
 */
data class SettlementSummary(
    val id: Long,
    val amount: BigDecimal,
    val companyName: String?,
    val representName: String,
    val fee: BigDecimal,
    val settlementStatus: SettlementStatus,
    val completeAt: LocalDateTime?,
    val createdAt: LocalDateTime,
)
