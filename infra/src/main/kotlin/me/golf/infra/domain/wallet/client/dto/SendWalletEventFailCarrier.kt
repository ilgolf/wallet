package me.golf.infra.domain.wallet.client.dto

data class SendWalletEventFailCarrier(
    val orderId: String,
    val userId: Long,
    val changeType: ChangeType
) {
}