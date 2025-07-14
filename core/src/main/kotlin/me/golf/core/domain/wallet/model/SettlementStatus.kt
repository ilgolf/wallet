package me.golf.core.domain.wallet.model

enum class SettlementStatus(
    val description: String,
) {
    WAIT("대기 중"),
    PROCESSING("진행 중"),
    DONE("완료"),
    FAILED("실패"),
}
