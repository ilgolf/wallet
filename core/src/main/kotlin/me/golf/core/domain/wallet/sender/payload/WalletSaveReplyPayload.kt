package me.golf.core.domain.wallet.sender.payload

data class WalletSaveReplyPayload(
    val orderId: String,
    val traceId: String
)
