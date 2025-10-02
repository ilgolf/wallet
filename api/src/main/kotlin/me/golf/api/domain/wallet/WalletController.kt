package me.golf.api.domain.wallet

import me.golf.api.domain.wallet.request.SettlementSummaryRequest
import me.golf.api.domain.wallet.response.SettlementSummaryResponse
import me.golf.core.common.PageResponse
import me.golf.core.domain.wallet.usecase.WalletUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/wallets")
class WalletController(
    private val walletUseCase: WalletUseCase
) {

    @GetMapping
    fun getSettlements(request: SettlementSummaryRequest): ResponseEntity<PageResponse<SettlementSummaryResponse>> {
        val result = walletUseCase.getSettlements(request.toSearch())
        val content = result.data.map { SettlementSummaryResponse.toResponse(it) }

        return ResponseEntity.ok(PageResponse.create(content, result.total))
    }
}