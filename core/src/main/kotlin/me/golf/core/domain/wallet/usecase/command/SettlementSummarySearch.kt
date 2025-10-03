package me.golf.core.domain.wallet.usecase.command

data class SettlementSummarySearch(
    val settlementId: Long? = null,
    val page: Int,
    val limit: Int,
) {
    init {
        require(page >= 0) { "page must be non-negative" }
        require(limit > 0) { "limit must be positive" }
        require(limit <= 1000) { "limit must be greater than 1000" }
    }
}