package me.golf.core.domain.wallet.usecase.command

data class WalletSaveCommand(
    val userId: Long,
    val orderId: String,
)