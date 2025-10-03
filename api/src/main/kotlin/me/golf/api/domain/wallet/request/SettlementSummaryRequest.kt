package me.golf.api.domain.wallet.request

import me.golf.core.domain.wallet.usecase.command.SettlementSummarySearch

data class SettlementSummaryRequest(
    val settlementId: Long?,
    val page: Int,
    val limit: Int,
) {
    fun toSearch(): SettlementSummarySearch {
        return SettlementSummarySearch(
            settlementId = this.settlementId,
            page = this.page,
            limit = this.limit
        )
    }
}
