package me.golf.infra.domain.wallet.handler.dto

data class WalletMessage(
    val userId: Long,
    val orderId: String,
)
