package me.golf.core.domain.wallet.usecase.command

data class SettlementSummarySearch(
    val settlementId: Long,
    val page: Int,
    val limit: Int,
) {
}