package me.golf.infra.domain.wallet.client.dto

enum class ChangeType(
    val description: String,
) {
    SUCCESS("성공 요청"),
    FAILURE("실패 요청"),
}