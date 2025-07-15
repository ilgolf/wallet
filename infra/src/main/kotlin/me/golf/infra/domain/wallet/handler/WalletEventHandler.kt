package me.golf.infra.domain.wallet.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.golf.core.domain.wallet.usecase.WalletUseCase
import me.golf.infra.domain.wallet.converter.toCommand
import me.golf.infra.domain.wallet.handler.dto.WalletMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

interface WalletEventHandler {
    fun handleSaveSettlement(message: String)
}

@Component
internal class KafkaWalletEventHandler(
    private val objectMapper: ObjectMapper,
    private val walletUseCase: WalletUseCase
): WalletEventHandler {

    private val log: Logger = LoggerFactory.getLogger(KafkaWalletEventHandler::class.java)

    @KafkaListener(topics = [WALLET_TOPIC])
    override fun handleSaveSettlement(message: String) {
        kotlin.runCatching { saveSettlementProcess(message) }
            .onFailure { loggingAndThrow(message, it) }
    }

    private fun saveSettlementProcess(message: String) {
        val walletMessage = objectMapper.readValue<WalletMessage>(message)
        walletUseCase.save(walletMessage.toCommand())
    }

    private fun loggingAndThrow(message: String, it: Throwable): Nothing {
        log.error("메시지 처리 실패 : DLQ 이동: {}", message, it)
        throw it
    }

    companion object {
        private const val WALLET_TOPIC = "wallet"
    }
}