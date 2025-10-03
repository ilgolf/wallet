package me.golf.core.domain.wallet.usecase.command

data class SettlementSummarySearch(
    val settlementId: Long? = null,
    val page: Int,
    val limit: Int,
) {
}