package me.golf.core.domain.wallet.usecase

import me.golf.core.common.PageResponse
import me.golf.core.domain.wallet.usecase.command.SettlementSummarySearch
import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand
import me.golf.core.domain.wallet.usecase.result.SettlementSummaryResult

interface WalletUseCase {

    fun create(command: WalletSaveCommand)
    fun getSettlements(search: SettlementSummarySearch): PageResponse<SettlementSummaryResult>
}